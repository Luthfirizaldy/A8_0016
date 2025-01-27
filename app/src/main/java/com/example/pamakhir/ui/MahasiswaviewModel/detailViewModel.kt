package com.example.pamakhir.ui.MahasiswaviewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Mahasiswa
import com.example.pamakhir.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val mhsRepository: MahasiswaRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getDetailMahasiswa(id: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhsRepository.getMahasiswaById(id)
                _detailUiState.value = DetailUiState.Success(mahasiswa)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error: ${e.message}", e)
                _detailUiState.value = DetailUiState.Error
            }
        }
    }

}