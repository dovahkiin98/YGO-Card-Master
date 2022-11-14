package net.inferno.ygocardmaster.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.inferno.ygocardmaster.theme.CardMasterTheme
import net.inferno.ygocardmaster.ui.main.Routes
import net.inferno.ygocardmaster.utils.CustomPreview

@Composable
fun HomePageUI(
    navController: NavController,
) {
    val lazyGridState = rememberLazyGridState()

    HomePageUI(
        lazyGridState = lazyGridState,
    ) {
        navController.navigate(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUI(
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onCardClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Home")
                }
            )
        },
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            state = lazyGridState,
            contentPadding = paddingValues,
            modifier = Modifier,
        ) {
            item {
                Card(
                    onClick = {
                        onCardClick(Routes.SETS)
                    },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            "Sets",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }

            item {
                Card(
                    onClick = {
                        onCardClick(Routes.SEARCH)
                    },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            "Search",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }

            item {
                Card(
                    onClick = {

                    },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {

                }
            }

            item {
                Card(
                    onClick = {

                    },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {

                }
            }
        }
    }
}

@CustomPreview
@Composable
fun HomePageUIPreview() {
    CardMasterTheme {
        HomePageUI {

        }
    }
}
