package com.example.pamakhir.repository

import android.util.Log
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.service_api.KamarService
import java.io.IOException

interface KamarRepository{
    suspend fun getAllKamar() : List<Kamar>

    suspend fun insertKamar(kamar: Kamar)

    suspend fun updateKamar(id : String,kamar: Kamar)

    suspend fun deleteKamar(id:String)

    suspend fun getKamarById(id: String):Kamar
}

class NetworkKamarRepository(
    private val kamarApiService : KamarService
): KamarRepository{
    override suspend fun getAllKamar(): List<Kamar> =
        kamarApiService.getAllKamar()

    override suspend fun insertKamar(kamar: Kamar) {
        kamarApiService.insertKamar(kamar)
    }

    override suspend fun updateKamar(id: String, kamar: Kamar) {
        Log.d("UpdateKamar", "ID yang diterima: $id") // Log ID yang diterima
        Log.d("UpdateKamar", "Data Kamar yang diterima: $kamar")
        kamarApiService.updateKamar(id,kamar)
    }
    override suspend fun deleteKamar(id: String) {
        try {
            val response = kamarApiService.deleteKamar(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Kamar. HTTP Status Code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKamarById(id: String): Kamar {
        val kamar = kamarApiService.getKamarbyId(id)

        // Menampilkan response JSON di logcat
        Log.d("MahasiswaAPI", "Response: $kamar")

        return kamar
    }



}