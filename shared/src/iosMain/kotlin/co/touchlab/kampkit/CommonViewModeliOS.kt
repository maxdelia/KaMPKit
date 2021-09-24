package co.touchlab.kampkit

import co.touchlab.kampkit.models.BreedModel
import co.touchlab.kampkit.models.DataState
import co.touchlab.kampkit.models.ItemDataSummary
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

actual class CommonViewModel(
    private val onDataState: (DataState<ItemDataSummary>) -> Unit
) : CommonViewModelInterface {
    override val log: Kermit by inject { parametersOf("CommonViewModel") }
    override val scope: CoroutineScope = MainScope(Dispatchers.Main, log)
    override val breedModel: BreedModel = BreedModel()

    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
        DataState(loading = true)
    )

    init {
        ensureNeverFrozen()
        observeBreeds()
    }

    fun consumeError() {
        _breedStateFlow.value = _breedStateFlow.value.copy(exception = null)
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