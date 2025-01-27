package com.example.pamakhir.ui.manajemenPembayaranScreen

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
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.model.Pembayaran
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenPembayaranViewModel.DetailUiState
import com.example.pamakhir.ui.manajemenPembayaranViewModel.DetailViewModel
import com.example.pamakhir.ui.manajemenPembayaranViewModel.PenyediaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPembayaranview(
    id: String,
    navigateBack: () -> Unit,
    onUpdateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val detailUiState = viewModel.detailUiState.collectAsState().value

    // Load data mahasiswa saat pertama kali layar ditampilkan
    LaunchedEffect(id) {
        viewModel.getDetailPembayaran(id)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = "Detail Pembayaran",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (detailUiState) {
                is DetailUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())
                is DetailUiState.Success -> DetailContent(
                    pembayaran = detailUiState.pembayaran,
                    onUpdateClick = onUpdateClick,
                    modifier = Modifier.fillMaxSize()
                )
                is DetailUiState.Error -> OnError(retryAction = { viewModel.getDetailPembayaran(id) })
            }
        }
    }
}

@Composable
fun DetailContent(
    pembayaran: Pembayaran,
    onUpdateClick: (String) -> Unit,
    modifier: Modifier = Modifier
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
                    text = "Id: ${pembayaran .id}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Nomor Kamar: ${pembayaran.idMahasiswa}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "id Bangunan: ${pembayaran.jumlah}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Kapasitas: ${pembayaran.tanggalPembayaran}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Status Kamar: ${pembayaran.statusPembayaran}",
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }
        Button(
            onClick = { onUpdateClick(pembayaran.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update Data")
        }
        Button(
            onClick = { onUpdateClick(pembayaran.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tambah Pembayaran")
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