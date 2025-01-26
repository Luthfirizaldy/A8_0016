package com.example.pamakhir.repository

import android.util.Log
import com.example.pamakhir.model.Bangunan
import com.example.pamakhir.service_api.BangunanService
import java.io.IOException

interface BangunanRepository{
    suspend fun getAllBangunan() : List<Bangunan>

    suspend fun insertBangunan(bangunan: Bangunan)

    suspend fun updateBangunan(id : String,bangunan: Bangunan)

    suspend fun deleteBangunan(id:String)

    suspend fun getBangunanById(id: String):Bangunan
}

class NetworkBangunanRepository(
    private val bangunanApiService : BangunanService
): BangunanRepository{
    override suspend fun getAllBangunan(): List<Bangunan> =
        bangunanApiService.getAllBangunan()

    override suspend fun insertBangunan(bangunan: Bangunan) {
        bangunanApiService.insertBangunan(bangunan)
    }

    override suspend fun updateBangunan(id: String, bangunan: Bangunan) {
        bangunanApiService.updateBangunan(id,bangunan)
    }
    override suspend fun deleteBangunan(id: String) {
        try {
            val response = bangunanApiService.deleteBangunan(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Bangunan. HTTP Status Code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBangunanById(id: String): Bangunan {
        val bangunan = bangunanApiService.getBangunanbyId(id)

        // Menampilkan response JSON di logcat
        Log.d("MahasiswaAPI", "Response: $bangunan")

        return bangunan
    }



}