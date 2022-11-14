package net.inferno.ygocardmaster.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import net.inferno.ygocardmaster.R
import net.inferno.ygocardmaster.ui.main.Routes
import net.inferno.ygocardmaster.view.ErrorView
import net.inferno.ygocardmaster.view.LoadingView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchPageUI(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    val lazyGridState = rememberLazyGridState()
    val state by viewModel.state.collectAsState()

    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    var query by rememberSaveable { mutableStateOf("") }

    val submitQuery = {
        if (query.isNotBlank()) {
            keyboardController?.hide()
            focusRequester.freeFocus()

            viewModel.requestData(query)
        }
    }

    LaunchedEffect(Unit) {
        if (query.isEmpty()) {
            delay(100)
            focusRequester.requestFocus()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            keyboardController?.hide()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicTextField(
                        query,
                        onValueChange = {
                            query = it.replace("\n", "")
                        },
                        decorationBox = {
                            CompositionLocalProvider(
                                LocalContentColor provides LocalContentColor.current.copy(alpha = 0.38f)
                            ) {
                                if (query.isEmpty()) {
                                    Text(
                                        "Search Name...",
                                    )
                                }
                            }

                            it()
                        },
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                submitQuery()
                            },
                            onGo = {
                                submitQuery()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                            capitalization = KeyboardCapitalization.Words,
                        ),
                        cursorBrush = SolidColor(LocalContentColor.current),
                        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                        maxLines = 1,
                        singleLine = true,
                        modifier = Modifier
                            .focusRequester(focusRequester),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navController::popBackStack,
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = submitQuery,
                        enabled = query.isNotBlank(),
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
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
            ErrorView(state.error!!, onRetry = submitQuery)
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