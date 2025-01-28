package com.example.pamakhir.service_api

import com.example.pamakhir.model.Kamar
import com.example.pamakhir.model.Mahasiswa
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface KamarService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("kamar.php")
    suspend fun getAllKamar(): List<Kamar>

    @GET("detailKamar.php/{id}")
    suspend fun getKamarbyId(@Path("id") id: String): Kamar


    @POST("insertKamar.php")
    suspend fun insertKamar(@Body kamar: Kamar)

    @PUT("editKamar.php/{id}")
    suspend fun updateKamar(@Query("id")id: String, @Body kamar: Kamar)

    @DELETE("deleteKamar.php/{id}")
    suspend fun deleteKamar(@Path("id")id: String): retrofit2.Response<Void>
}