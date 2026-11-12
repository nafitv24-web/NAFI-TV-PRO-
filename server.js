const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = 3000;
const PUBLIC_DIR = path.join(__dirname, 'public');
const ASSETS_DIR = path.join(__dirname, 'app', 'src', 'main', 'assets');

// Helper to safely load JSON files
function loadJsonFile(filename, fallback = null) {
  try {
    const fullPath = path.join(ASSETS_DIR, filename);
    if (fs.existsSync(fullPath)) {
      const raw = fs.readFileSync(fullPath, 'utf8');
      return JSON.parse(raw);
    }
  } catch (err) {
    console.error(`Error loading ${filename}:`, err.message);
  }
  return fallback;
}

// Generate M3U playlist for iOS apps (VLC, IPTV Smarters, GSE Smart IPTV)
function generateM3uPlaylist(baseUrl) {
  const liveTvData = loadJsonFile('livetv.json', { categories: [] });
  const sportsData = loadJsonFile('sports.json', { categories: [] });
  
  let m3u = '#EXTM3U name="NAFI TV Pro iOS Playlist"\n';
  
  // 1. Live TV Channels
  if (liveTvData && Array.isArray(liveTvData.categories)) {
    liveTvData.categories.forEach(cat => {
      const groupName = cat.category_name || 'Live TV';
      if (Array.isArray(cat.movies)) {
        cat.movies.forEach(ch => {
          const title = ch.title || 'Channel';
          const logo = ch.poster || '';
          const sources = Array.isArray(ch.sources) ? ch.sources.filter(s => s && s.url && s.url.trim().length > 0) : [];
          
          sources.forEach((src, idx) => {
            const streamUrl = src.url.trim();
            const serverLabel = sources.length > 1 ? ` (${src.server_name || 'S' + (idx + 1)})` : '';
            m3u += `#EXTINF:-1 tvg-id="${encodeURIComponent(title)}" tvg-name="${title}" tvg-logo="${logo}" group-title="${groupName}",${title}${serverLabel}\n`;
            m3u += `${streamUrl}\n`;
          });
        });
      }
    });
  }

  // 2. Sports Events
  if (sportsData && Array.isArray(sportsData.categories)) {
    sportsData.categories.forEach(cat => {
      const groupName = cat.category_name || 'Sports';
      if (Array.isArray(cat.movies)) {
        cat.movies.forEach(ev => {
          const title = ev.title || 'Sports Event';
          const logo = ev.poster || '';
          const sources = Array.isArray(ev.sources) ? ev.sources.filter(s => s && s.url && s.url.trim().length > 0) : [];
          
          sources.forEach((src, idx) => {
            const streamUrl = src.url.trim();
            const serverLabel = sources.length > 1 ? ` (${src.server_name || 'S' + (idx + 1)})` : '';
            m3u += `#EXTINF:-1 tvg-name="${title}" tvg-logo="${logo}" group-title="${groupName}",${title}${serverLabel}\n`;
            m3u += `${streamUrl}\n`;
          });
        });
      }
    });
  }

  // 3. Builtin Starshare & Toffee
  m3u += `#EXTINF:-1 tvg-name="Toffee Live TV (BD)" group-title="PLAYLISTS",Toffee Live TV\nhttps://raw.githubusercontent.com/srhady/toffee-bd/refs/heads/main/toffee_playlist.m3u\n`;

  return m3u;
}

const server = http.createServer((req, res) => {
  const parsedUrl = url.parse(req.url, true);
  const pathname = parsedUrl.pathname;

  // Enable CORS for all API / streaming requests
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, HEAD, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, Range');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  // API: Get Channels JSON
  if (pathname === '/api/channels') {
    const liveTv = loadJsonFile('livetv.json', { categories: [] });
    const sports = loadJsonFile('sports.json', { categories: [] });
    const movies = loadJsonFile('movies.json', { categories: [] });
    
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify({
      liveTv: liveTv?.categories || [],
      sports: sports?.categories || [],
      movies: movies?.categories || [],
      playlists: [
        {
          id: "pl_toffee_live_bd",
          title: "Toffee Live TV (Bangladesh)",
          url: "https://raw.githubusercontent.com/srhady/toffee-bd/refs/heads/main/toffee_playlist.m3u",
          logo: "https://assets-prod.services.toffeelive.com/Xi_Ga5oBNnOkwJLWkhKP/posters/ef2899d5-1ae0-4fee-aee5-45f9b0b3ba80.png"
        },
        {
          id: "pl_starshare_premium",
          title: "Starshare IPTV (rgkkw.live)",
          url: "http://rgkkw.live:80/get.php?username=4dfoydR2gZ&password=clever3still&type=m3u_plus&output=m3u8",
          logo: "https://images.unsplash.com/photo-1578022761797-b8636ac1773c?w=300&fit=crop"
        }
      ]
    }));
    return;
  }

  // M3U Playlist Export for iOS Players (VLC, IPTV Smarters, GSE Smart IPTV)
  if (pathname === '/playlist.m3u' || pathname === '/nafitv.m3u') {
    const host = req.headers.host || `localhost:${PORT}`;
    const protocol = (req.headers['x-forwarded-proto'] || 'http');
    const baseUrl = `${protocol}://${host}`;
    const m3uContent = generateM3uPlaylist(baseUrl);

    res.writeHead(200, {
      'Content-Type': 'application/x-mpegurl; charset=utf-8',
      'Content-Disposition': 'inline; filename="nafitv.m3u"'
    });
    res.end(m3uContent);
    return;
  }

  // Static File Serving
  let filePath = path.join(PUBLIC_DIR, pathname === '/' ? 'index.html' : pathname);

  // Security check: ensure path is within PUBLIC_DIR
  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403);
    res.end('Forbidden');
    return;
  }

  // Check if file exists
  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      // Fallback to index.html for SPA routes
      const indexPath = path.join(PUBLIC_DIR, 'index.html');
      if (fs.existsSync(indexPath)) {
        res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
        fs.createReadStream(indexPath).pipe(res);
      } else {
        res.writeHead(404, { 'Content-Type': 'text/plain; charset=utf-8' });
        res.end('File not found');
      }
      return;
    }

    // Determine content type
    const ext = path.extname(filePath).toLowerCase();
    const mimeTypes = {
      '.html': 'text/html; charset=utf-8',
      '.js': 'application/javascript; charset=utf-8',
      '.css': 'text/css; charset=utf-8',
      '.json': 'application/json; charset=utf-8',
      '.png': 'image/png',
      '.jpg': 'image/jpeg',
      '.webp': 'image/webp',
      '.svg': 'image/svg+xml',
      '.ico': 'image/x-icon',
      '.apk': 'application/vnd.android.package-archive',
      '.jar': 'application/java-archive',
      '.mobileconfig': 'application/x-apple-aspen-config',
      '.ipa': 'application/octet-stream',
      '.m3u': 'application/x-mpegurl; charset=utf-8',
      '.m3u8': 'application/vnd.apple.mpegurl; charset=utf-8'
    };

    const contentType = mimeTypes[ext] || 'application/octet-stream';
    const headers = { 'Content-Type': contentType, 'Content-Length': stats.size };

    // Set download headers for APK, JAR, mobileconfig and IPA
    if (ext === '.apk') {
      headers['Content-Disposition'] = 'attachment; filename="NAFI_TV_Pro.apk"';
    } else if (ext === '.jar') {
      headers['Content-Disposition'] = 'attachment; filename="NAFI_TV_Desktop.jar"';
    } else if (ext === '.mobileconfig') {
      headers['Content-Disposition'] = 'attachment; filename="NAFI_TV_Pro.mobileconfig"';
    } else if (ext === '.ipa') {
      headers['Content-Disposition'] = 'attachment; filename="NAFI_TV_Pro.ipa"';
    }

    res.writeHead(200, headers);
    fs.createReadStream(filePath).pipe(res);
  });
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`NAFI TV Pro Web & iOS Server running on http://0.0.0.0:${PORT}`);
});
