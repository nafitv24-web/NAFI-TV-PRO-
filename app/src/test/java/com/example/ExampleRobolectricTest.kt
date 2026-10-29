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
}
