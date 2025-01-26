package com.example.pamakhir


import android.app.Application
import com.example.pamakhir.container.AppContainer
import com.example.pamakhir.container.MahasiswaContainer

class MahasiswaApplication : Application(){
    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = MahasiswaContainer()
    }
}