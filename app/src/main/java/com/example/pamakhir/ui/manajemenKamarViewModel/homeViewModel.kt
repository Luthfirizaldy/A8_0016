package com.example.pamakhir.ui.manajemenKamarViewModel

import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.repository.KamarRepository

import kotlinx.coroutines.launch
import java.io.IOException



sealed class KamarUiState {
    data class Success(val kamar: List<Kamar>) : KamarUiState()
    object  Error : KamarUiState()
    object Loading : KamarUiState()

}

class HomeViewModel (private val kmr: KamarRepository): ViewModel(){
    var kmrUIState: KamarUiState by mutableStateOf(KamarUiState.Loading)
        private set

    init {
        getKmr()
    }

    fun getKmr(){
        viewModelScope.launch {
            kmrUIState = KamarUiState.Loading
            kmrUIState = try {
                KamarUiState.Success(kmr.getAllKamar())
            }catch (e: IOException){
                KamarUiState.Error
            }catch ( e: HttpException){
                KamarUiState.Error
            }
        }
    }

    fun deleteKmr(id:String){
        viewModelScope.launch {
            try {
                kmr.deleteKamar(id)
            }catch (e: IOException){
                KamarUiState.Error
            }catch ( e:HttpException){
                KamarUiState.Error
            }
        }
    }
}