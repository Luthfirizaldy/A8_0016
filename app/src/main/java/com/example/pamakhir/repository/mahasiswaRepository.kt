package com.example.pamakhir.repository

import android.util.Log
import com.example.pamakhir.model.Mahasiswa
import com.example.pamakhir.service_api.MahasiswaService
import java.io.IOException

interface MahasiswaRepository{
    suspend fun getAllMahasiswa() : List<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(id : String,mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(id:String)

    suspend fun getMahasiswaById(id: String):Mahasiswa
}

class NetworkMahasiswaRepository(
    private val mahasiswaApiService : MahasiswaService
): MahasiswaRepository{
    override suspend fun getAllMahasiswa(): List<Mahasiswa> =
        mahasiswaApiService.getAllMahasiswa()

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(id: String, mahasiswa: Mahasiswa) {
        mahasiswaApiService.updateMahasiswa(id,mahasiswa)
    }
    override suspend fun deleteMahasiswa(id: String) {
        try {
            val response = mahasiswaApiService.deleteMahasiswa(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Mahasiswa. HTTP Status Code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMahasiswaById(id: String): Mahasiswa {
        Log.d("MahasiswaAPI", "Response: $id")

        val mahasiswa = mahasiswaApiService.getMahasiswabyId(id)

        // Menampilkan response JSON di logcat
        Log.d("MahasiswaAPI", "Response: $mahasiswa")

        return mahasiswa
    }



}