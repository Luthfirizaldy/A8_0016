package com.example.pamakhir.service_api

import com.example.pamakhir.model.Mahasiswa
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MahasiswaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("mahasiswa.php")
    suspend fun getAllMahasiswa(): List<Mahasiswa>

    @GET("detailMahasiswa.php/{id}")
    suspend fun getMahasiswabyId(@Path("id") id: String): Mahasiswa


    @POST("insertMahasiswa.php")
    suspend fun insertMahasiswa(@Body mahasiswa:Mahasiswa)

    @PUT("editMahasiswa.php/{id}")
    suspend fun updateMahasiswa(@Query("id")id: String, @Body mahasiswa:Mahasiswa)

    @DELETE("deleteMahasiswa.php/{id}")
    suspend fun deleteMahasiswa(@Path("id")id: String): retrofit2.Response<Void>
}