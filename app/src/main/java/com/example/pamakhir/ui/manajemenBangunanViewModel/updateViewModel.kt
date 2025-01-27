package com.example.pamakhir.ui.manajemenBangunanViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.repository.BangunanRepository
import kotlinx.coroutines.launch

class UpdateViewModel(private val bgnanRepository: BangunanRepository) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    // Fetch existing Mahasiswa data based on ID and set the state
    fun loadingMahasiswaData(bangunanId: String) {
        viewModelScope.launch {
            try {
                val bangunan = bgnanRepository.getBangunanById(bangunanId)
                uiState = UpdateUiState(insertUiEvent = bangunan.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updateBangunan() {
        viewModelScope.launch {
            try {
                val id = uiState.insertUiEvent.id
                val bangunan = uiState.insertUiEvent.toMhs()
                bgnanRepository.updateBangunan(id, bangunan )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertBngnanState(insertUiEvent: InsertUiEvent) {
        uiState = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)