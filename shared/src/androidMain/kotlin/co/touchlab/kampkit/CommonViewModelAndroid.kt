package co.touchlab.kampkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

actual class CommonViewModel : ViewModel(), CommonViewModelInterface {
    override val scope = viewModelScope
    override val log: Kermit by inject { parametersOf("CommonViewModel") }
    override val breedModel: BreedModel = BreedModel()

    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState(loading = true)
    )
    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow

    init {
        observeBreeds()
    }

    override fun updateBreedState(state: DataState<ItemDataSummary>) {
        if (state.loading) {
            val temp = _breedStateFlow.value.copy(loading = true)
            _breedStateFlow.value = temp
        } else {
            _breedStateFlow.value = state
        }
    }
}