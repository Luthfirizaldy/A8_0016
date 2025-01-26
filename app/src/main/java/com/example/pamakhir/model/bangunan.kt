package com.example.pamakhir.model

import kotlinx.serialization.Serializable



@Serializable
data class Bangunan(
    val id: String,
    val namaBangunan: String,
    val jumlahLantai : String,
    val alamat: String
)