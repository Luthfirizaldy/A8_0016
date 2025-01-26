package com.example.pamakhir.model

import kotlinx.serialization.Serializable


@Serializable
data class Pembayaran(
    val id: String,
    val idMahasiswa: String,
    val jumlah: String,
    val tanggalPembayaran: String, // yyyy-MM-dd
    val statusPembayaran: String
)