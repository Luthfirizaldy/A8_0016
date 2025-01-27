package com.example.pamakhir.ui.manajemenBangunanViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Bangunan
import com.example.pamakhir.repository.BangunanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val bangunan: Bangunan) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val bgnanRepository: BangunanRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getDetailBangunan(id: String) {
        viewModelScope.launch {
            try {
                val bangunan = bgnanRepository.getBangunanById(id)
                _detailUiState.value = DetailUiState.Success(bangunan)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error: ${e.message}", e)
                _detailUiState.value = DetailUiState.Error
            }
        }
    }

}