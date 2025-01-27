package com.example.pamakhir.ui.manajemenKamarViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamakhir.MahasiswaApplication

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiKamar().container.kamarRepository) }
        initializer { InsertViewModel(aplikasiKamar().container.kamarRepository) }
        initializer { DetailViewModel(aplikasiKamar().container.kamarRepository) }
        initializer { UpdateViewModel(aplikasiKamar().container.kamarRepository) }
    }
}

fun CreationExtras.aplikasiKamar(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)