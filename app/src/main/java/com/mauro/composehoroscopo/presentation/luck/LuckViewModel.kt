package com.mauro.composehoroscopo.presentation.luck

package com.mauro.composehoroscopo.presentation.luck

import androidx.lifecycle.ViewModel
import com.mauro.composehoroscopo.domain.model.LuckyModel
import com.mauro.composehoroscopo.domain.providers.RandomCardProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LuckViewModel @Inject constructor(
    private val randomCardProvider: RandomCardProvider
) : ViewModel() {

    private val _state = MutableStateFlow(LuckState())
    val state: StateFlow<LuckState> = _state

    init {
        getRandomLuck()
    }

    fun getRandomLuck() {
        val luck = randomCardProvider.getLucky()
        _state.value = _state.value.copy(currentLuck = luck)
    }
}

data class LuckState(
    val currentLuck: LuckyModel? = null
)