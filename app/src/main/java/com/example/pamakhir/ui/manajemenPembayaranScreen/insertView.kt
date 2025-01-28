package com.example.pamakhir.ui.manajemenPembayaranScreen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamakhir.navigation.DestinationNavigation
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenPembayaranViewModel.InsertUiEvent
import com.example.pamakhir.ui.manajemenPembayaranViewModel.InsertUiState
import com.example.pamakhir.ui.manajemenPembayaranViewModel.InsertViewModel
import com.example.pamakhir.ui.manajemenPembayaranViewModel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.util.*

object DestinasiPembayaranEntry : DestinationNavigation {
    override val route = "pembayaran_entry"
    override val titleRes = "Entry pembayaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPbrScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiPembayaranEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onPembayaranValueChange = viewModel::updateInsertPbrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPbr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onPembayaranValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPembayaranValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true

) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id,
            onValueChange = { onValueChange(insertUiEvent.copy(id = it)) },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.idMahasiswa,
            onValueChange = { onValueChange(insertUiEvent.copy(idMahasiswa = it)) },
            label = { Text("ID Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.jumlah,
            onValueChange = { onValueChange(insertUiEvent.copy(jumlah = it)) },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Date Picker
        TanggalPembayaranPicker(
            selectedDate = insertUiEvent.tanggalPembayaran,
            onDateSelected = { onValueChange(insertUiEvent.copy(tanggalPembayaran = it)) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = modifier) {
            // Pilihan status pembayaran
            val statusOptions = listOf("Lunas", "Belum Lunas")

            // Menampilkan pilihan radio button secara horizontal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start, // Mengatur agar radio button rata kiri
                verticalAlignment = Alignment.CenterVertically // Menjaga radio button sejajar vertikal
            ) {
                statusOptions.forEach { status ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp) // Memberi sedikit jarak antar radio button
                    ) {
                        // Radio button untuk setiap status
                        RadioButton(
                            selected = insertUiEvent.statusPembayaran == status, // Cek apakah status yang dipilih sama
                            onClick = {
                                onValueChange(insertUiEvent.copy(statusPembayaran = status)) // Mengubah statusPembayaran
                            },
                            enabled = enabled
                        )
                        // Label untuk radio button
                        Text(
                            text = status,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp) // Menjaga jarak antara radio button dan teks
                        )
                    }
                }
            }
        }



        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun TanggalPembayaranPicker(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                onDateSelected(formattedDate)
                showDialog.value = false
            },
            year,
            month,
            day
        ).show()
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {}, // Tidak dapat diubah manual
        label = { Text("Tanggal Pembayaran") },
        modifier = modifier,
        enabled = enabled,
        singleLine = true,
        readOnly = true,
        trailingIcon = {
            androidx.compose.material3.IconButton(onClick = { showDialog.value = true }) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.DateRange, // Ikon kalender bawaan
                    contentDescription = "Pilih Tanggal"
                )
            }
        }
    )
}