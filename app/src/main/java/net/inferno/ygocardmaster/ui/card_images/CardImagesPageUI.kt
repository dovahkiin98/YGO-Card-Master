package net.inferno.ygocardmaster.ui.card_images

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import net.inferno.ygocardmaster.R
import net.inferno.ygocardmaster.ui.main.Routes
import net.inferno.ygocardmaster.utils.LocalWindowInsetsController
import net.inferno.ygocardmaster.view.ErrorView
import net.inferno.ygocardmaster.view.LoadingAsyncImage
import net.inferno.ygocardmaster.view.LoadingView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun CardImagesPageUI(
    navController: NavController,
    cardName: String,
    viewModel: CardImageViewController = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val windowInsetsController = LocalWindowInsetsController.current

    val pagerState = rememberPagerState()

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
                            cardName,
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
                    actions = {
                        if (state.hasData) {
                            IconButton(
                                onClick = {
                                    val currentImage = state.data!!.images[pagerState.currentPage]
                                    navController.navigate(
                                        Routes.image(
                                            imageUrl = "https://images.ygoprodeck.com/images/cards_cropped/${currentImage.id}.jpg",
                                            title = cardName,
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.crop_din),
                                    contentDescription = null,
                                )
                            }
                        }
                    },
                )
            }

        },
        containerColor = Color.Black,
    ) { paddingValues ->
        if (state.loading) {
            LoadingView()
        }

        if (state.isError) {
            ErrorView(state.error!!, onRetry = viewModel::requestData)
        }

        if (state.data != null) {
            val playcard = state.data!!

            HorizontalPager(
                count = playcard.images.size,
                contentPadding = paddingValues,
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) {
                val image = playcard.images[it]

                LoadingAsyncImage(
                    url = image.urlLarge,
                    contentDescription = playcard.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures {
                                showSystemUI = !showSystemUI
                            }
                        },
                )
            }
        }
    }
}