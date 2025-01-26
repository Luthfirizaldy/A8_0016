package com.example.pamakhir.service_api


import com.example.pamakhir.model.Pembayaran
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PembayaranService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("pembayaran.php")
    suspend fun getAllPembayaran(): List<Pembayaran>

    @GET("detailPembayaran.php/{id}")
    suspend fun getPembayaranbyId(@Path("id") id: String): Pembayaran


    @POST("insertPembayaran.php")
    suspend fun insertPembayaran(@Body pembayaran: Pembayaran)

    @PUT("editPembayaran.php/{id}")
    suspend fun updatePembayaran(@Query("id")id: String, @Body pembayaran: Pembayaran)

    @DELETE("deletePembayaran.php/{id}")
    suspend fun deletePembayaran(@Path("id")id: String): retrofit2.Response<Void>
}