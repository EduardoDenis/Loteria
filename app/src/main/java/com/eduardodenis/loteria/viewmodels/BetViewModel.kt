package com.eduardodenis.loteria.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import kotlin.random.Random

class BetViewModel(
//    private val savedStateHandle: SavedStateHandle,
    private val betRepository: BetRepository

) : ViewModel() {


    //1. USING COMPOSE STATE
    var qtdNumbers by mutableStateOf("")
        private set
    var qtdBets by mutableStateOf("")
        private set

    //2. USING LIVE DATA
    private var _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    private var _showAlert = MutableLiveData(false)
    val showAlert: LiveData<Boolean>
        get() = _showAlert

    var showAlertDialog by mutableStateOf(false)


    val numbers = mutableStateListOf<String>()

    fun updateQtdNumbers(newNumber: String) {
        if (newNumber.length < 3) {
            qtdNumbers = validadeInput(newNumber)
        }
    }

    fun saveBet(type: String) {
        viewModelScope.launch {
            for (num in numbers) {
                val bet = Bet(type = type, numbers = num)
                betRepository.insertBet(bet)
                dismissAlert()
            }
        }

    }

    fun updateQtdBets(newBets: String) {
        if (newBets.length < 3) {
            qtdBets = validadeInput(newBets)
        }
    }

    fun updateNumbers(rule: Int) {
        _result.value = ""
        numbers.clear()

        for (i in 1..qtdBets.toInt()) {
            val res = numberGenerator(qtdNumbers.toInt(), rule)
            numbers.add(res)
            _result.value += "[$i]  "
            _result.value += res
            _result.value += "\n\n"
        }
        _showAlert.value = true

    }

    fun dismissAlert() {
        _showAlert.value = false
    }


    private fun validadeInput(input: String): String {
        val filteredChars = input.filter { chars ->
            chars.isDigit()
        }

        return filteredChars
    }

    private fun numberGenerator(qtd: Int, rule: Int): String {
        val numbers = mutableSetOf<Int>()

        while (true) {
            val n = Random.nextInt(60)

            if (rule == 1) {
                if (n % 2 == 0) {
                    continue
                }
            } else if (rule == 2) {
                if (n % 2 != 0) {
                    continue
                }
            }
            numbers.add(n + 1)
            if (numbers.size == qtd) {
                break
            }
        }

        return numbers.joinToString(" - ")
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//
                val application = extras[APPLICATION_KEY]
                val dao = (application as App).db.betDao()
                val repository = BetRepository.getInstance(dao)

//                val savedStateHandle = extras.createSavedStateHandle()
                return BetViewModel(repository) as T
            }
        }
    }
}