package net.inferno.ygocardmaster.ui.set_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.inferno.ygocardmaster.repo.Repository
import net.inferno.ygocardmaster.utils.UIState
import net.inferno.ygocardmaster.model.CardSet
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SetListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(UIState<List<CardSet>>())

    val state = _state.asStateFlow()

    private var allSets: List<CardSet>? = null

    init {
        requestData()
    }

    fun requestData() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _state.emit(UIState(loading = true))

            try {
                val sets = repository.getSets()

                allSets = sets

                _state.emit(
                    UIState(
                        loading = false,
                        data = sets,
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

    fun applySearch(query: String) {
        if (allSets == null) {
            return
        } else {
            val results = allSets!!.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.code.contains(query, ignoreCase = true)
            }

            _state.value = UIState(
                loading = false,
                data = results,
            )
        }
    }
}