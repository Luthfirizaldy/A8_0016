package com.example.pamakhir.model

import kotlinx.serialization.Serializable



@Serializable
data class Mahasiswa(
    val id: String,
    val namaMahasiswa: String,
    val nomorIdentitas :String,
    val email: String,
    val nomorTelepon: String,
    val idKamar: String
)