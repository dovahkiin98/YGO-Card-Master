package net.inferno.ygocardmaster.ui.image

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import net.inferno.ygocardmaster.utils.LocalWindowInsetsController
import net.inferno.ygocardmaster.view.LoadingAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePageUI(
    navController: NavController,
    imageUrl: String,
    title: String,
) {
    val windowInsetsController = LocalWindowInsetsController.current

    var showSystemUI by remember { mutableStateOf(true) }

    DisposableEffect(showSystemUI) {
        if (showSystemUI) {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        } else {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }

        onDispose {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Scaffold(
        topBar = {
            if (showSystemUI) {
                TopAppBar(
                    title = {
                        Text(
                            title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = navController::popBackStack
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black.copy(alpha = 0.4f),
                        titleContentColor = Color.White,
                    ),
                )
            }
        },
        containerColor = Color.Black,
    ) { paddingValues ->
        LoadingAsyncImage(
            url = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures {
                        showSystemUI = !showSystemUI
                    }
                },
        )
    }
}