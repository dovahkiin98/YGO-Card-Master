package net.inferno.ygocardmaster.ui.set_cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.inferno.ygocardmaster.R
import net.inferno.ygocardmaster.ui.main.Routes
import net.inferno.ygocardmaster.view.ErrorView
import net.inferno.ygocardmaster.view.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetCardsPageUI(
    navController: NavController,
    setName: String,
    viewModel: SetCardsViewModel = hiltViewModel(),
) {
    val lazyGridState = rememberLazyGridState()
    val state by viewModel.state.collectAsState()

    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        setName,
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
                scrollBehavior = topAppBarScrollBehavior,
            )
        },
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        if (state.loading) {
            LoadingView()
        }

        if (state.isError) {
            ErrorView(state.error!!, onRetry = viewModel::requestData)
        }

        if (state.data != null) {
            val playcards = state.data!!

            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = paddingValues,
                state = lazyGridState,
            ) {
                items(playcards) {
                    Card(
                        onClick = {
                            navController.navigate(Routes.cardImages(it.name))
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it.images.first().urlSmall)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = it.name,
                                placeholder = painterResource(id = R.mipmap.card_back),
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}