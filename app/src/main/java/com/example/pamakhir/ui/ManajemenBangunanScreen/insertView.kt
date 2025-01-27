package com.example.pamakhir.ui.ManajemenBangunanScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamakhir.navigation.DestinationNavigation
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenBangunanViewModel.InsertUiEvent
import com.example.pamakhir.ui.manajemenBangunanViewModel.InsertUiState
import com.example.pamakhir.ui.manajemenBangunanViewModel.InsertViewModel
import com.example.pamakhir.ui.manajemenBangunanViewModel.PenyediaViewModel

import kotlinx.coroutines.launch

object DestinasiBangunanEntry: DestinationNavigation {
    override val route = "bgnan_entry"
    override val titleRes = "Entry Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBgnanScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiBangunanEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onBangunanValueChange = viewModel::updateInsertBgnanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBgnan()
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
    onBangunanValueChange:(InsertUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier =Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onBangunanValueChange,
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
    modifier: Modifier =Modifier,
    onValueChange:(InsertUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaBangunan,
            onValueChange = {onValueChange(insertUiEvent.copy(namaBangunan = it))},
            label = { Text("Nama Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.id,
            onValueChange = {onValueChange(insertUiEvent.copy(id = it))},
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.jumlahLantai,
            onValueChange = {onValueChange(insertUiEvent.copy(jumlahLantai = it))},
            label = { Text("Jumlah Lantai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = {onValueChange(insertUiEvent.copy(alamat = it))},
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )




        if (enabled){
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