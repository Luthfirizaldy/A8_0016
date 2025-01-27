package com.example.pamakhir.ui.MahasiswaviewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamakhir.model.Mahasiswa
import com.example.pamakhir.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val mhs: MahasiswaRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id:String = "",
    val namaMahasiswa:String = "",
    val email:String = "",
    val nomorIdentitas : String ="",
    val nomorTelepon:String = "",
    val idKamar:String = "",
)

fun InsertUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    id = id,
    namaMahasiswa = namaMahasiswa,
    email = email,
    nomorIdentitas =nomorIdentitas,
    nomorTelepon = nomorTelepon,
    idKamar = idKamar
)

fun  Mahasiswa.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id,
    namaMahasiswa = namaMahasiswa,
    email = email,
    nomorIdentitas =nomorIdentitas,
    nomorTelepon = nomorTelepon,
    idKamar = idKamar
)