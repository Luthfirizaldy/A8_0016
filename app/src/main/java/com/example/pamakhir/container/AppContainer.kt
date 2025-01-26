package com.example.pamakhir.container

import com.example.pamakhir.repository.BangunanRepository
import com.example.pamakhir.repository.KamarRepository
import com.example.pamakhir.repository.MahasiswaRepository
import com.example.pamakhir.repository.NetworkBangunanRepository
import com.example.pamakhir.repository.NetworkKamarRepository
import com.example.pamakhir.repository.NetworkMahasiswaRepository
import com.example.pamakhir.repository.NetworkPembayaranRepository
import com.example.pamakhir.repository.PembayaranRepository
import com.example.pamakhir.service_api.BangunanService
import com.example.pamakhir.service_api.KamarService
import com.example.pamakhir.service_api.MahasiswaService
import com.example.pamakhir.service_api.PembayaranService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val mahasiswaRepository : MahasiswaRepository
    val kamarRepository : KamarRepository
    val bangunanRepository : BangunanRepository
    val pembayaranRepository : PembayaranRepository
}

class MahasiswaContainer : AppContainer {
    private val baseUrl = "http://192.168.1.36:80/aldi/"
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val mahasiswaService : MahasiswaService by lazy {
        retrofit.create(MahasiswaService::class.java)
    }
    private val kamarService : KamarService by lazy {
        retrofit.create(KamarService::class.java)
    }
    private val bangunanService : BangunanService by lazy {
        retrofit.create(BangunanService::class.java)
    }
    private val pembayaranService : PembayaranService by lazy {
        retrofit.create(PembayaranService::class.java)
    }



    override val mahasiswaRepository : MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }
    override val kamarRepository : KamarRepository by lazy {
        NetworkKamarRepository(kamarService)
    }
    override val bangunanRepository : BangunanRepository by lazy {
        NetworkBangunanRepository(bangunanService)
    }
    override val pembayaranRepository : PembayaranRepository by lazy {
        NetworkPembayaranRepository(pembayaranService)
    }



}