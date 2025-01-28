package com.example.pamakhir.ui.manajemenKamarViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.repository.KamarRepository
import kotlinx.coroutines.launch

class UpdateViewModel(private val kmrRepository: KamarRepository) : ViewModel() {

    var uiStateKamar by mutableStateOf(UpdateUiState())
        private set

    // Fetch existing Mahasiswa data based on ID and set the state
    fun loadingKamarData(KamarId: String) {
        viewModelScope.launch {
            try {
                Log.d("LoadingKamarData", "ID yang diterima: $KamarId")

                val kamar = kmrRepository.getKamarById(KamarId)
                uiStateKamar = UpdateUiState(insertUiEvent = kamar.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updateKamar() {
        viewModelScope.launch {
            try {
                val id = uiStateKamar.insertUiEvent.id
                val kamar = uiStateKamar.insertUiEvent.toKmr()
                kmrRepository.updateKamar(id, kamar)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertKmrState(insertUiEvent: InsertUiEvent) {
        uiStateKamar = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)