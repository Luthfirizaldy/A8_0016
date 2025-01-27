package com.example.pamakhir.ui.manajemenPembayaranViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Pembayaran
import com.example.pamakhir.repository.PembayaranRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val pbr: PembayaranRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertPbrState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertPbr() {
        viewModelScope.launch {
            try {
                pbr.insertPembayaran(uiState.insertUiEvent.toPbr())
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
    val idMahasiswa :String = "",
    val jumlah :String = "",
    val tanggalPembayaran:String = "",
    val statusPembayaran:String = "",
)

fun InsertUiEvent.toPbr(): Pembayaran = Pembayaran(
    id = id,
    idMahasiswa = idMahasiswa,
    jumlah = jumlah,
    tanggalPembayaran = tanggalPembayaran,
    statusPembayaran = statusPembayaran
)

fun  Pembayaran.toUiStateKmr(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pembayaran.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id,
    idMahasiswa = idMahasiswa,
    jumlah = jumlah,
    tanggalPembayaran = tanggalPembayaran,
    statusPembayaran = statusPembayaran
)