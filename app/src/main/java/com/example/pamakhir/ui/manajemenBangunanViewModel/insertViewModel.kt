package com.example.pamakhir.ui.manajemenBangunanViewModel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Bangunan
import com.example.pamakhir.repository.BangunanRepository

import kotlinx.coroutines.launch

class InsertViewModel(private val bgnan: BangunanRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertBgnanState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertBgnan() {
        viewModelScope.launch {
            try {
                bgnan.insertBangunan(uiState.insertUiEvent.toMhs())
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
    val namaBangunan:String = "",
    val jumlahLantai:String = "",
    val alamat:String = "",
)

fun InsertUiEvent.toMhs(): Bangunan = Bangunan(
    id = id,
    namaBangunan = namaBangunan,
    jumlahLantai = jumlahLantai,
    alamat = alamat
)

fun  Bangunan.toUiStateBgnan(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Bangunan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id,
    namaBangunan = namaBangunan,
    jumlahLantai = jumlahLantai,
    alamat = alamat
)