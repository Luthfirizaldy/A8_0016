package com.example.pamakhir.ui.manajemenPembayaranViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.repository.PembayaranRepository
import kotlinx.coroutines.launch

class UpdateViewModel(private val pbrRepository: PembayaranRepository) : ViewModel() {

    var uiStatePembayaran by mutableStateOf(UpdateUiState())
        private set

    // Fetch existing Mahasiswa data based on ID and set the state
    fun loadingPembayaranData(PembayaranId: String) {
        viewModelScope.launch {
            try {
                val pembayaran = pbrRepository.getPembayaranById(PembayaranId)
                uiStatePembayaran = UpdateUiState(insertUiEvent = pembayaran.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updatePembayaran() {
        viewModelScope.launch {
            try {
                val id = uiStatePembayaran.insertUiEvent.id
                val pembayaran = uiStatePembayaran.insertUiEvent.toPbr()
                pbrRepository.updatePembayaran(id, pembayaran)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPbrState(insertUiEvent: InsertUiEvent) {
        uiStatePembayaran = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)