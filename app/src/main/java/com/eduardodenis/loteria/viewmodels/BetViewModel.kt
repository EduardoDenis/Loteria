package com.eduardodenis.loteria.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.eduardodenis.loteria.App
import com.eduardodenis.loteria.data.Bet
import com.eduardodenis.loteria.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


class BetListDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val betRepository: BetRepository

) : ViewModel() {

    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets: StateFlow<List<Bet>> = _bets.asStateFlow()

    init {
        viewModelScope.launch {
            val type = savedStateHandle.get<String>("type") ?: throw Exception("Tipo nao encontrado")
            println("TESTE" + type)
            val result = betRepository.getBets(type)
            _bets.value = result
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//                return super.create(modelClass, extras)

                val application = extras[APPLICATION_KEY]
                val dao = (application as App).db.betDao()
                val repository = BetRepository.getInstance(dao)

                val savedStateHandle = extras.createSavedStateHandle()
                return BetListDetailViewModel(savedStateHandle, repository) as T
            }
        }

    }


}

