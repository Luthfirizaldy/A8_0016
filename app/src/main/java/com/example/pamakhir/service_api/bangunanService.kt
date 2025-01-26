package com.example.pamakhir.service_api

import com.example.pamakhir.model.Bangunan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BangunanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("Bangunan.php")
    suspend fun getAllBangunan(): List<Bangunan>

    @GET("detailBangunan.php/{id}")
    suspend fun getBangunanbyId(@Path("id") id: String): Bangunan


    @POST("insertBangunan.php")
    suspend fun insertBangunan(@Body bangunan: Bangunan)

    @PUT("editBangunan.php/{id}")
    suspend fun updateBangunan(@Query("id")id: String, @Body bangunan: Bangunan)

    @DELETE("deleteBangunan.php/{id}")
    suspend fun deleteBangunan(@Path("id")id: String): retrofit2.Response<Void>
}