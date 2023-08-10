package com.levp.twimate.ui

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.levp.twimate.R

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val density = configuration.densityDpi
    Log.d("hehe", "w = $screenWidth, h = $screenHeight, d = $density")
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val imageRequest = ImageRequest.Builder(context)
        .data(data = R.raw.muskg)
        .build()
    val resHeight = 480 * screenWidth / 600
    Image(
        painter = rememberAsyncImagePainter(imageRequest, imageLoader = imageLoader),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(resHeight.dp),
    )
}