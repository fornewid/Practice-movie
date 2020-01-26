package soup.movie.ui.theater.edit.lotte

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import soup.movie.domain.theater.edit.TheaterEditManager
import soup.movie.model.Theater
import soup.movie.ui.base.BaseViewModel
import soup.movie.ui.theater.edit.TheaterEditChildUiModel
import javax.inject.Inject

class LotteEditViewModel @Inject constructor(
    private val manager: TheaterEditManager
) : BaseViewModel() {

    private val _uiModel = MutableLiveData<TheaterEditChildUiModel>()
    val uiModel: LiveData<TheaterEditChildUiModel>
        get() = _uiModel

    init {
        viewModelScope.launch {
            combine(
                manager.asLotteFlow(),
                manager.asSelectedTheaterListFlow()
            ) { lotte, selectedList ->
                TheaterEditChildUiModel(lotte, selectedList)
            }.collect { _uiModel.value = it }
        }
    }

    fun add(theater: Theater): Boolean {
        return manager.add(theater)
    }

    fun remove(theater: Theater) {
        manager.remove(theater)
    }
}
