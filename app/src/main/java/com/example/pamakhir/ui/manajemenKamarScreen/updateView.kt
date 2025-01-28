package com.example.pamakhir.ui.manajemenKamarScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenKamarViewModel.InsertUiEvent
import com.example.pamakhir.ui.manajemenKamarViewModel.PenyediaViewModel
import com.example.pamakhir.ui.manajemenKamarViewModel.UpdateUiState
import com.example.pamakhir.ui.manajemenKamarViewModel.UpdateViewModel

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKamarView(
    id: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // Muat data mahasiswa berdasarkan ID
    LaunchedEffect(id) {
        Log.d("launch", "ID yang di terima : $id")
        viewModel.loadingKamarData(id)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Update Kamar",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.uiStateKamar,
            onSiswaValueChange = viewModel::updateInsertKmrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKamar()
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
fun UpdateBody(
    updateUiState: UpdateUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = updateUiState.insertUiEvent,
            onValueChange = onSiswaValueChange,
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