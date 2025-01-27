package com.example.pamakhir.ui.MahasiswaviewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamakhir.MahasiswaApplication

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { InsertViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { DetailViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { UpdateViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
    }
}

fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)