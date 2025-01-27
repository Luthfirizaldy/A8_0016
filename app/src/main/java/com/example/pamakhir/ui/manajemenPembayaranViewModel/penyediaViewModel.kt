package com.example.pamakhir.ui.manajemenPembayaranViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pamakhir.MahasiswaApplication

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiPembayaran().container.pembayaranRepository) }
        initializer { InsertViewModel(aplikasiPembayaran().container.pembayaranRepository) }
        initializer { DetailViewModel(aplikasiPembayaran().container.pembayaranRepository) }
        initializer { UpdateViewModel(aplikasiPembayaran().container.pembayaranRepository) }
    }
}

fun CreationExtras.aplikasiPembayaran(): MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)