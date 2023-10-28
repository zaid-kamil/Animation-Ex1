package com.example.animationex1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.animationex1.ui.theme.AnimationEx1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationEx1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenOne()
                }
            }
        }
    }
}

@Composable
fun ScreenOne(modifier: Modifier = Modifier) {
    var isPlaying by rememberSaveable { mutableStateOf(false) } // 1
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.halloween_animation))
    val animatedColor by animateColorAsState(
        if (isPlaying) Color.White else Color.Black,
        animationSpec = tween(composition?.duration?.toInt() ?: 600),
        label = "color"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(animatedColor)
    ) {

        val progress by animateLottieCompositionAsState(
            composition,
            isPlaying = isPlaying, // 2
            iterations = LottieConstants.IterateForever,
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isPlaying = !isPlaying
                }
        )
        val animateFont by animateFloatAsState(
            targetValue = if (isPlaying) 30f else 0f,
            label = "font animation",
            animationSpec = tween(600)
        )
        Text(
            text = "Halloween\nAnimation",
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = animateFont.sp,
            lineHeight = 30.sp,
        )
        Box(modifier = Modifier
            .animateContentSize(animationSpec = tween(600))
            .background(animatedColor.copy(alpha = .9f, red = 1f))
            .fillMaxWidth()
            .height(if (isPlaying) 200.dp else 0.dp)) {
        }
    }
}