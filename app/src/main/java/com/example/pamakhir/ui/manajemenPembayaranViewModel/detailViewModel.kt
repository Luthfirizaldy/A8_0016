package com.example.pamakhir.ui.manajemenPembayaranViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Pembayaran
import com.example.pamakhir.repository.PembayaranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val pembayaran: Pembayaran) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val pbrRepository: PembayaranRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getDetailPembayaran(id: String) {
        viewModelScope.launch {
            try {
                val kamar = pbrRepository.getPembayaranById(id)
                _detailUiState.value = DetailUiState.Success(kamar)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error: ${e.message}", e)
                _detailUiState.value = DetailUiState.Error
            }
        }
    }

}