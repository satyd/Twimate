package com.levp.twimate.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.levp.twimate.R
import com.levp.twimate.Values
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
@SuppressWarnings("MagicNumber", "FunctionNaming", "LongMethod")
fun XRow(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val infiniteTransition = rememberInfiniteTransition()
    val startHeight = Values.minHeight.toFloat()
    /*val drawable by infiniteTransition.animateValue(
        initialValue = R.drawable.twitter_img,
        targetValue = R.drawable.twitter_dead,
        typeConverter = ,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                R.drawable.twitter_dead.toFloat() at 1534
            },
            repeatMode = RepeatMode.Restart
        )
    )*/
    var resource by remember { mutableStateOf(R.drawable.twitter_img) }
    var painter = painterResource(id = resource)
    var isAlive by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isAlive) {
        launch {
            delay(1434)
            resource = R.drawable.twitter_dead
            delay(4566)
            resource = R.drawable.twitter_img
            isAlive = !isAlive
        }
    }
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
        initialValue = 0.18f,
        targetValue = 0.69f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                0.18f at 1034
                0.3f at 1234
                0.5f at 1534
                0.85f at 1934
                0.88f at 2100
                0.85f at 3500
            },
            repeatMode = RepeatMode.Restart
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Values.maxHeight.dp)
            .paint(
                painter = painter,
                contentScale = ContentScale.Inside,
                alignment = Alignment.BottomCenter
            ),
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
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}