package com.example.pamakhir.ui.MahasiswaviewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel(private val mhsRepository: MahasiswaRepository) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    // Fetch existing Mahasiswa data based on ID and set the state
    fun loadingMahasiswaData(mahasiswaId: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhsRepository.getMahasiswaById(mahasiswaId)
                uiState = UpdateUiState(insertUiEvent = mahasiswa.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updateMahasiswa() {
        viewModelScope.launch {
            try {
                val id = uiState.insertUiEvent.id
                val mahasiswa = uiState.insertUiEvent.toMhs()
                mhsRepository.updateMahasiswa(id, mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)