package com.levp.twimate

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.levp.twimate.ui.theme.TwimateTheme
import java.lang.Exception


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        packageManager.setComponentEnabledSetting(
            ComponentName("com.levp.twimate", "com.levp.twimate.MainActivityYa"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            ComponentName("com.levp.twimate", "com.levp.twimate.MainActivityN"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            ComponentName("com.levp.twimate", "com.levp.twimate.MainActivityI"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            ComponentName("com.levp.twimate", "com.levp.twimate.MainActivityY"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )


        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.hyi).toString()
        setContent {
            TwimateTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VideoPlayerScreen(videoUri = uri) {}
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = {
                        this@MainActivity.riskyStartTwitter()
                    }) {
                        Text(text = "StartX")
                    }
                }
            }
        }
    }

}

fun Context.startTwitter() {
    val intent = Intent()
    intent.type = "text/plain"
    intent.action = Intent.ACTION_SEND
    val packageManager = packageManager
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    for (resolveInfo in list) {
        val packageName = resolveInfo.activityInfo.packageName;
        if (packageName != null && packageName.equals("com.twitter.android")) {
            Log.i("hehe", "package is present")
            try {
                val formattedTwitterAddress = "https://twitter.com/home"
                val browseTwitter = Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
                //browseTwitter.putExtra("user_id", twitterId);
                startActivity(browseTwitter);
            } catch (e: Exception) {
                Log.e("hehe", "exexe exception")
            }
        } else {
            Log.i("hehe", "package is not present")
        }
    }
}

fun Context.riskyStartTwitter() {
    //val intent = packageManager.getLaunchIntentForPackage("com.twitter.android")
    //startActivity(intent)
    val formattedTwitterAddress = "https://twitter.com/"
    val browseTwitter = Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
    startActivity(browseTwitter);
}

@Composable
@SuppressWarnings("FunctionName")
fun VideoPlayerScreen(
    videoUri: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var playWhenReady by remember { mutableStateOf(true) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            volume = 0.0f
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            this.playWhenReady = playWhenReady
            prepare()
            play()
        }
    }
    DisposableEffect(
        AndroidView(
            modifier = Modifier.wrapContentSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun MateView(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun MateViewPreview() {
    TwimateTheme {

    }
}