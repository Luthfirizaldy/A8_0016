package com.example.pamakhir.ui.mahasiswaScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pamakhir.model.Mahasiswa
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.MahasiswaviewModel.DetailUiState
import com.example.pamakhir.ui.MahasiswaviewModel.DetailViewModel
import com.example.pamakhir.ui.MahasiswaviewModel.PenyediaViewModel
import com.example.pamakhir.ui.manajemenPembayaranScreen.DestinasiPembayaranEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMahasiswaview(
    id: String,
    navigateBack: () -> Unit,
    onUpdateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,  // Pastikan ini ada
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val detailUiState = viewModel.detailUiState.collectAsState().value

    // Load data mahasiswa saat pertama kali layar ditampilkan
    LaunchedEffect(id) {
        viewModel.getDetailMahasiswa(id)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = "Detail Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (detailUiState) {
                is DetailUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())
                is DetailUiState.Success -> DetailContent(
                    mahasiswa = detailUiState.mahasiswa,
                    onUpdateClick = onUpdateClick,
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,  // pastikan disertakan di sini

                )
                is DetailUiState.Error -> OnError(retryAction = { viewModel.getDetailMahasiswa(id) })
            }
        }
    }
}

@Composable
fun DetailContent(
    mahasiswa: Mahasiswa,
    onUpdateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController  // pastikan ada di sini

) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Id: ${mahasiswa.id}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Nama: ${mahasiswa.namaMahasiswa}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Email: ${mahasiswa.email}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Nomor Identitas: ${mahasiswa.nomorIdentitas}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "No Telepon: ${mahasiswa.nomorTelepon}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Id Kamar: ${mahasiswa.idKamar}",
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }
        Button(
            onClick = { onUpdateClick(mahasiswa.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update Data")
        }
        Button(onClick = {
            // Menavigasi ke halaman insert pembayaran (EntryPbrScreen)
            navController.navigate(DestinasiPembayaranEntry.route)
        }) {
            Text("Tambah Pembayaran")
        }
    }
}

@Composable
fun OnLoadingDetail(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = "Loading...")
    }
}

@Composable
fun OnError(retryAction: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "An error occurred. Please try again.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = retryAction) {
                Text(text = "Retry")
            }
        }
    }
}