package com.levp.twimate

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.levp.twimate.ui.theme.TwimateTheme
import java.lang.Exception
import kotlin.math.roundToInt


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
                    XRow { this@MainActivity.riskyStartTwitter() }
                    Spacer(modifier = Modifier.height(4.dp))
                    GifImage()
                    //VideoPlayerScreen(videoUri = uri)
                    Spacer(modifier = Modifier.height(4.dp))
                    //BackupStartButton { this@MainActivity.riskyStartTwitter() }
                }
            }
        }
    }

}

object Values {
    val minHeight = 48
    val maxHeight = 120
}

@Composable
fun XRow(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val infiniteTransition = rememberInfiniteTransition()
    val startHeight = Values.minHeight.toFloat()
    val size by infiniteTransition.animateFloat(
        initialValue = startHeight,
        targetValue = Values.maxHeight.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                startHeight at 3500
            },
            repeatMode = RepeatMode.Restart
        )
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                1f at 900
            },
            repeatMode = RepeatMode.Restart
        )
    )
    val offset by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                0.2f at 1000
                0.55f at 1500
                0.75f at 2000
                0.85f at 2500
                0.85f at 3500
            },
            repeatMode = RepeatMode.Restart
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Values.maxHeight.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(
            modifier = Modifier
                .size(size.roundToInt().dp)
                .alpha(alpha)
                .offset {
                    val resOffset = (screenWidth * offset * density).roundToInt()
                    IntOffset(resOffset, 0)
                },
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.x),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context)
                .data(data = R.raw.muskg)
                .apply(block = {
                    size(Size.ORIGINAL)
                })
                .build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun BackupStartButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Start")
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.x),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

fun Context.riskyStartTwitter() {
    val formattedTwitterAddress = "https://twitter.com/"
    val browseTwitter = Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
    startActivity(browseTwitter);
}

@Preview(showBackground = true)
@Composable
fun MateViewPreview() {
    TwimateTheme {

    }
}