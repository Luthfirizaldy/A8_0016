package com.example.pamakhir.ui.manajemenPembayaranViewModel

import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Pembayaran
import com.example.pamakhir.repository.PembayaranRepository

import kotlinx.coroutines.launch
import java.io.IOException



sealed class PembayaranUiState {
    data class Success(val pembayaran: List<Pembayaran>) : PembayaranUiState()
    object  Error : PembayaranUiState()
    object Loading : PembayaranUiState()

}

class HomeViewModel (private val pbr: PembayaranRepository): ViewModel(){
    var kmrUIState: PembayaranUiState by mutableStateOf(PembayaranUiState.Loading)
        private set

    init {
        getPbr()
    }

    fun getPbr(){
        viewModelScope.launch {
            kmrUIState = PembayaranUiState.Loading
            kmrUIState = try {
                PembayaranUiState.Success(pbr.getAllPembayaran())
            }catch (e: IOException){
                PembayaranUiState.Error
            }catch ( e: HttpException){
                PembayaranUiState.Error
            }
        }
    }

    fun deletePbr(id:String){
        viewModelScope.launch {
            try {
                pbr.deletePembayaran(id)
            }catch (e: IOException){
                PembayaranUiState.Error
            }catch ( e:HttpException){
                PembayaranUiState.Error
            }
        }
    }
}