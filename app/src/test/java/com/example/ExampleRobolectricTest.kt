package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("NAFI TV Pro", appName)
  }

  @Test
  fun `test sports fetch`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = com.example.data.MediaRepository(context)
    val matches = repo.fetchAllAdminPlaylistsSports()
    println("DEBUG_TEST: Sports count = ${matches.size}")
    if (matches.isNotEmpty()) {
      println("DEBUG_TEST: First match = ${matches[0].title}, url = ${matches[0].streamUrl}")
    }
  }

  @Test
  fun `test activity launch`() {
    val controller = org.robolectric.Robolectric.buildActivity(MainActivity::class.java).setup()
    val activity = controller.get()
    org.junit.Assert.assertNotNull(activity)
  }

  @Test
  fun `workmanager auto initializer is removed from initialization provider`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val providerComponent = android.content.ComponentName(
      context.packageName,
      "androidx.startup.InitializationProvider"
    )
    @Suppress("DEPRECATION")
    val providerInfo = context.packageManager.getProviderInfo(
      providerComponent,
      android.content.pm.PackageManager.GET_META_DATA
    )
    val metaKeys = providerInfo.metaData?.keySet().orEmpty()
    org.junit.Assert.assertFalse(
      "WorkManagerInitializer must be removed from InitializationProvider metadata",
      metaKeys.contains("androidx.work.WorkManagerInitializer")
    )
    val workManagerResult = runCatching { androidx.work.WorkManager.getInstance(context) }
    org.junit.Assert.assertTrue(
      "WorkManager should not be eagerly initialized on startup",
      workManagerResult.isFailure
    )
  }
}
