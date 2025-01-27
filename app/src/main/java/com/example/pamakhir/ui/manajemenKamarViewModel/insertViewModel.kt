package com.example.pamakhir.ui.manajemenKamarViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.repository.KamarRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val kmr: KamarRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertKmrState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertKmr() {
        viewModelScope.launch {
            try {
                kmr.insertKamar(uiState.insertUiEvent.toKmr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id:String = "",
    val nomorKamar:String = "",
    val idBangunan :String = "",
    val kapasitas:String = "",
    val statusKamar:String = "",
)

fun InsertUiEvent.toKmr(): Kamar = Kamar(
    id = id,
    nomorKamar = nomorKamar,
    idBangunan = idBangunan,
    kapasitas = kapasitas,
    statusKamar = statusKamar
)

fun  Kamar.toUiStateKmr(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Kamar.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id,
    nomorKamar = nomorKamar,
    idBangunan = idBangunan,
    kapasitas = kapasitas,
    statusKamar = statusKamar
)