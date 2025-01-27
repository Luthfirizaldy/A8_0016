package com.example.pamakhir.ui.manajemenKamarViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.repository.KamarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val kamar: Kamar) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val kmrRepository: KamarRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getDetailKamar(id: String) {
        viewModelScope.launch {
            try {
                val kamar = kmrRepository.getKamarById(id)
                _detailUiState.value = DetailUiState.Success(kamar)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error: ${e.message}", e)
                _detailUiState.value = DetailUiState.Error
            }
        }
    }

}