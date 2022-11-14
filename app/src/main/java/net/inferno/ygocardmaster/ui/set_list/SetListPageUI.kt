package net.inferno.ygocardmaster.ui.set_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.inferno.ygocardmaster.model.CardSet
import net.inferno.ygocardmaster.theme.CardMasterTheme
import net.inferno.ygocardmaster.ui.main.Routes
import net.inferno.ygocardmaster.utils.CustomPreview
import net.inferno.ygocardmaster.utils.UIState
import net.inferno.ygocardmaster.view.ErrorView
import net.inferno.ygocardmaster.view.LoadingView
import net.inferno.ygocardmaster.view.SearchTextField
import java.time.Instant
import java.util.Date
import kotlin.random.Random

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetListPageUI(
    navController: NavController,
    viewModel: SetListViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val lazyListState = rememberLazyListState()
    val state by viewModel.state.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            keyboardController?.hide()
        }
    }

    SetListPageUI(
        state = state,
        lazyListState = lazyListState,
        onApplySearch = {
            viewModel.applySearch(it)
        },
        onNavigateBack = {
            navController.popBackStack()
        },
        onSetClick = {
            navController.navigate(Routes.set(it.name))
        },
        onRetry = {
            viewModel.requestData()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SetListPageUI(
    state: UIState<List<CardSet>>,
    onApplySearch: (String) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    onNavigateBack: (() -> Unit)? = null,
    onRetry: () -> Unit = {},
    onSetClick: (CardSet) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    var searchStarted by rememberSaveable { mutableStateOf(false) }

    var query by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchStarted) {
                        ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                            SearchTextField(
                                query,
                                onValueChange = {
                                    query = it.replace("\n", "")

                                    onApplySearch(query)
                                },
                                hint = {
                                    Text(
                                        "Search Name...",
                                    )
                                },
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        keyboardController?.hide()
                                        focusRequester.freeFocus()
                                    },
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search,
                                    capitalization = KeyboardCapitalization.Words,
                                ),
                                cursorBrush = SolidColor(LocalContentColor.current),
                                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                                modifier = Modifier
                                    .focusRequester(focusRequester),
                            )
                        }
                    } else {
                        Text(
                            "Sets",
                        )
                    }
                },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(
                            onClick = onNavigateBack,
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                },
                actions = {
                    if (state.hasData) {
                        if (!searchStarted) {
                            IconButton(
                                onClick = {
                                    searchStarted = true

                                    coroutineScope.launch {
                                        delay(100)
                                        focusRequester.requestFocus()
                                    }
                                },
                            ) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    searchStarted = false
                                    query = ""
                                    onApplySearch("")
                                },
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Search")
                            }
                        }
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
            ErrorView(state.error!!, onRetry = onRetry)
        }

        if (state.data != null) {
            CardSetList(
                paddingValues = paddingValues,
                lazyListState = lazyListState,
                cardSets = state.data!!,
                onSetClick = onSetClick,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@CustomPreview
@Composable
fun SetListPagePreview() {
    val state = UIState(
        data = buildList {
            repeat(20) {
                add(
                    CardSet(
                        name = "ABC",
                        code = it.toString(),
                        numberOfCards = Random.nextInt(from = 20, until = 100),
                        releaseDate = Date.from(Instant.now()),
                    )
                )
            }
        },
    )

    CardMasterTheme {
        SetListPageUI(
            state = state,
            onApplySearch = {

            },
        ) {

        }
    }
}