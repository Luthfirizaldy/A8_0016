package com.example.pamakhir.ui.ManajemenBangunanScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamakhir.R
import com.example.pamakhir.model.Bangunan
import com.example.pamakhir.navigation.DestinationNavigation
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenBangunanViewModel.HomeUiState
import com.example.pamakhir.ui.manajemenBangunanViewModel.HomeViewModel
import com.example.pamakhir.ui.manajemenBangunanViewModel.PenyediaViewModel


object DestinasiHomeBangunan: DestinationNavigation {
    override val route ="Bangunan"
    override val titleRes = "Home Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BangunanHomeScreen(
    navigateToBagunanItemEntry:()->Unit,
    modifier: Modifier =Modifier,
    onDetailClick: (String) -> Unit ={},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeBangunan.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getbgnan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToBagunanItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Bangunan")
            }
        },
    ) { innerPadding->
        HomeStatus(
            homeUiState = viewModel.bgnanUIState,
            retryAction = {viewModel.getbgnan()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClick = {
                viewModel.deletebgnan(it.id)
                viewModel.getbgnan()

            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when (homeUiState){
        is HomeUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if(homeUiState.bangunan.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data kontak")
                }
            }else{
                BgnLayout(
                    bangunan = homeUiState.bangunan,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_connection_error),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier=modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )

        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BgnLayout(
    bangunan: List<Bangunan>,
    modifier: Modifier = Modifier,
    onDetailClick:(Bangunan)->Unit,
    onDeleteClick: (Bangunan) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(bangunan){ bangunan ->
            BgnCard(
                bangunan = bangunan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(bangunan)},
                onDeleteClick ={
                    onDeleteClick(bangunan)
                }
            )

        }
    }
}

@Composable
fun BgnCard(
    bangunan: Bangunan,
    modifier: Modifier = Modifier,
    onDeleteClick:(Bangunan)->Unit={}
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bangunan.namaBangunan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(bangunan)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                Text(
                    text = bangunan.id,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = bangunan.jumlahLantai,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = bangunan.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}