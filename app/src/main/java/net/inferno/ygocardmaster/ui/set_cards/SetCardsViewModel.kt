package net.inferno.ygocardmaster.ui.set_cards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.inferno.ygocardmaster.repo.Repository
import net.inferno.ygocardmaster.utils.UIState
import net.inferno.ygocardmaster.model.PlayCard
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SetCardsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedState: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(UIState<List<PlayCard>>())

    val state = _state.asStateFlow()

    init {
        requestData()
    }

    fun requestData() {
        val setName = savedState.get<String>("setName")!!

        viewModelScope.launch(Dispatchers.Main.immediate) {
            _state.emit(UIState(loading = true))

            try {
                val setCards = repository.getCardsInSet(setName)

                _state.emit(
                    UIState(
                        loading = false,
                        data = setCards,
                    )
                )
            } catch (e: HttpException) {
                val message =
                    JSONObject(e.response()?.errorBody()?.string() ?: "{}").get("error") as String?
                        ?: e.message()

                _state.emit(
                    UIState(
                        loading = false,
                        error = Exception(message),
                    )
                )
            } catch (e: Exception) {
                _state.emit(
                    UIState(
                        loading = false,
                        error = e,
                    )
                )
            }
        }
    }
}