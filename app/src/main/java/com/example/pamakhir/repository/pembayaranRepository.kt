package com.example.pamakhir.repository

import android.util.Log
import com.example.pamakhir.model.Pembayaran
import com.example.pamakhir.service_api.PembayaranService
import java.io.IOException

interface PembayaranRepository{
    suspend fun getAllPembayaran() : List<Pembayaran>

    suspend fun insertPembayaran(pembayaran: Pembayaran)

    suspend fun updatePembayaran(id : String,pembayaran: Pembayaran)

    suspend fun deletePembayaran(id:String)

    suspend fun getPembayaranById(id: String):Pembayaran
}

class NetworkPembayaranRepository(
    private val pembayaranApiService : PembayaranService
): PembayaranRepository{
    override suspend fun getAllPembayaran(): List<Pembayaran> =
        pembayaranApiService.getAllPembayaran()

    override suspend fun insertPembayaran(pembayaran: Pembayaran) {
        val pmbr = pembayaranApiService.insertPembayaran(pembayaran)
        Log.d("PembayaranAPI", "Response: $pembayaran")

    }

    override suspend fun updatePembayaran(id: String, pembayaran: Pembayaran) {
        pembayaranApiService.updatePembayaran(id,pembayaran)
    }
    override suspend fun deletePembayaran(id: String) {
        try {
            val response = pembayaranApiService.deletePembayaran(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pembayaran. HTTP Status Code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPembayaranById(id: String): Pembayaran {
        val pembayaran = pembayaranApiService.getPembayaranbyId(id)

        // Menampilkan response JSON di logcat
        Log.d("PembayaranAPI", "Response: $pembayaran")

        return pembayaran
    }



}