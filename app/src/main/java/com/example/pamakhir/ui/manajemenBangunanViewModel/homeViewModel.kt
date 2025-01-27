package com.example.pamakhir.ui.manajemenBangunanViewModel


import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Bangunan
import com.example.pamakhir.repository.BangunanRepository


import kotlinx.coroutines.launch
import java.io.IOException



sealed class HomeUiState {
    data class Success(val bangunan: List<Bangunan>) : HomeUiState()
    object  Error : HomeUiState()
    object Loading : HomeUiState()

}

class HomeViewModel (private val bgnan: BangunanRepository): ViewModel(){
    var bgnanUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getbgnan()
    }

    fun getbgnan(){
        viewModelScope.launch {
            bgnanUIState = HomeUiState.Loading
            bgnanUIState = try {
                HomeUiState.Success(bgnan.getAllBangunan())
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deletebgnan(id:String){
        viewModelScope.launch {
            try {
                bgnan.deleteBangunan(id)
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e:HttpException){
                HomeUiState.Error
            }
        }
    }
}