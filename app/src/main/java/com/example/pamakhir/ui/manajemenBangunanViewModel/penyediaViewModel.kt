package com.example.pamakhir.ui.manajemenBangunanViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamakhir.MahasiswaApplication

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiBangunan().container.bangunanRepository) }
        initializer { InsertViewModel(aplikasiBangunan().container.bangunanRepository) }
        initializer { DetailViewModel(aplikasiBangunan().container.bangunanRepository) }
        initializer { UpdateViewModel(aplikasiBangunan().container.bangunanRepository) }
    }
}

fun CreationExtras.aplikasiBangunan(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)