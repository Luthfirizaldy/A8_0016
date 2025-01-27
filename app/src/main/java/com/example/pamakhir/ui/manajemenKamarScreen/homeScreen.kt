package com.example.pamakhir.ui.manajemenKamarScreen

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
import com.example.pamakhir.model.Kamar
import com.example.pamakhir.navigation.DestinationNavigation
import com.example.pamakhir.ui.CustomTopAppBar
import com.example.pamakhir.ui.manajemenKamarViewModel.KamarUiState
import com.example.pamakhir.ui.manajemenKamarViewModel.HomeViewModel
import com.example.pamakhir.ui.manajemenKamarViewModel.PenyediaViewModel


object DestinasiHomeKamar: DestinationNavigation {
    override val route ="Kamar"
    override val titleRes = "Home Kmr"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KamarHomeScreen(
    navigateToKamarEntry:()->Unit,
    modifier: Modifier =Modifier,
    onDetailClick: (String) -> Unit ={},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeKamar.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKmr()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToKamarEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kamar")
            }
        },
    ) { innerPadding->
        HomeStatus(
            homeUiState = viewModel.kmrUIState,
            retryAction = {viewModel.getKmr()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClick = {
                viewModel.deleteKmr(it.id)
                viewModel.getKmr()

            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: KamarUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kamar) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when (homeUiState){
        is KamarUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is KamarUiState.Success ->
            if(homeUiState.kamar.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data kamar")
                }
            }else{
                KmrLayout(
                    kamar = homeUiState.kamar,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    }
                )
            }
        is KamarUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun KmrLayout(
    kamar: List<Kamar>,
    modifier: Modifier = Modifier,
    onDetailClick:(Kamar)->Unit,
    onDeleteClick: (Kamar) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kamar){ kamar ->
            KmrCard(
                kamar = kamar,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(kamar)},
                onDeleteClick ={
                    onDeleteClick(kamar)
                }
            )

        }
    }
}

@Composable
fun KmrCard(
    kamar: Kamar,
    modifier: Modifier = Modifier,
    onDeleteClick:(Kamar)->Unit={}
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
                    text = kamar.nomorKamar,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(kamar)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                Text(
                    text = kamar.id,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = kamar.statusKamar,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = kamar.nomorKamar,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}