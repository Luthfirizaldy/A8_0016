package com.example.pamakhir.model

import kotlinx.serialization.Serializable



@Serializable
data class Kamar(
    val id: String,
    val nomorKamar: String,
    val idBangunan: String,
    val kapasitas: String,
    val statusKamar: String
)