package com.example.pamakhir.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pamakhir.ui.ManajemenBangunanScreen.BangunanHomeScreen
import com.example.pamakhir.ui.ManajemenBangunanScreen.DestinasiBangunanEntry
import com.example.pamakhir.ui.ManajemenBangunanScreen.DestinasiHomeBangunan
import com.example.pamakhir.ui.ManajemenBangunanScreen.DetailBangunanView
import com.example.pamakhir.ui.ManajemenBangunanScreen.EntryBgnanScreen
import com.example.pamakhir.ui.home.DestinasiHome
import com.example.pamakhir.ui.home.HomeScreen
import com.example.pamakhir.ui.mahasiswaScreen.DestinasiEntry
import com.example.pamakhir.ui.manajemenKamarScreen.DestinasiKamarEntry
import com.example.pamakhir.ui.mahasiswaScreen.DestinasiHomeMahasiswa
import com.example.pamakhir.ui.mahasiswaScreen.DetailMahasiswaview
import com.example.pamakhir.ui.mahasiswaScreen.EntryMhsScreen
import com.example.pamakhir.ui.mahasiswaScreen.MahasiswaHomeScreen
import com.example.pamakhir.ui.mahasiswaScreen.UpdateView
import com.example.pamakhir.ui.manajemenKamarScreen.DestinasiHomeKamar
import com.example.pamakhir.ui.manajemenKamarScreen.DetailKamarview
import com.example.pamakhir.ui.manajemenKamarScreen.EntryKmrScreen
import com.example.pamakhir.ui.manajemenKamarScreen.KamarHomeScreen
import com.example.pamakhir.ui.manajemenPembayaranScreen.DestinasiHomePembayaran
import com.example.pamakhir.ui.manajemenPembayaranScreen.DestinasiPembayaranEntry
import com.example.pamakhir.ui.manajemenPembayaranScreen.DetailPembayaranview
import com.example.pamakhir.ui.manajemenPembayaranScreen.EntryPbrScreen
import com.example.pamakhir.ui.manajemenPembayaranScreen.PembayaranHomeScreen

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ){

        composable(route = DestinasiHome.route){
            HomeScreen(
                onMahasiswaClick ={
                    navController.navigate(DestinasiHomeMahasiswa.route)
                },
                onManajemenClick = {
                    navController.navigate(DestinasiHomeKamar.route)
                },
                onManajemenBangunanClick ={
                    navController.navigate(DestinasiHomeBangunan.route)
                },
                onManajemenPembayaranClick = {
                    navController.navigate(DestinasiHomePembayaran.route)
                },
                modifier = modifier
            )
        }

        composable(DestinasiHomeMahasiswa.route){
            MahasiswaHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = {id ->
                    navController.navigate("detail/Mahasiswa/$id")
                }
            )
        }
        composable(DestinasiHomeKamar.route){
            KamarHomeScreen(
                navigateToKamarEntry = {navController.navigate(DestinasiKamarEntry.route)},
                onDetailClick = {id ->
                    navController.navigate("detail/Kamar/$id")
                }
            )
        }
        composable(DestinasiHomeBangunan.route){
            BangunanHomeScreen(
                navigateToBagunanItemEntry = {navController.navigate(DestinasiBangunanEntry.route)},
                onDetailClick = {id ->
                    navController.navigate("detail/Bangunan/$id")
                }
            )
        }
        composable(DestinasiHomePembayaran.route){
            PembayaranHomeScreen(
                navigateToPembayaranEntry = {navController.navigate(DestinasiPembayaranEntry.route)},
                onDetailClick = {id ->
                    navController.navigate("detail/Pembayaran/$id")
                }
            )
        }


        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHomeMahasiswa.route){
                    popUpTo(DestinasiHomeMahasiswa.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiKamarEntry.route){
            EntryKmrScreen(navigateBack = {
                navController.navigate(DestinasiHomeKamar.route){
                    popUpTo(DestinasiHomeKamar.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiBangunanEntry.route){
            EntryBgnanScreen(navigateBack = {
                navController.navigate(DestinasiHomeBangunan.route){
                    popUpTo(DestinasiHomeBangunan.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiPembayaranEntry.route){
            EntryPbrScreen(navigateBack = {
                navController.navigate(DestinasiHomePembayaran.route){
                    popUpTo(DestinasiHomePembayaran.route){
                        inclusive = true
                    }
                }
            })
        }


        composable("detail/Mahasiswa/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailMahasiswaview(
                id = id,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { id ->
                    navController.navigate("detail/Mahasiswa/{id}")
                }
            )
        }
        composable("detail/kamar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailKamarview(
                id = id,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { id ->
                    navController.navigate("detail/kamar/{id}")
                }
            )
        }
        composable("detail/bangunan/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailBangunanView(
                id = id,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { id ->
                    navController.navigate("update/$id")
                }
            )
        }

        composable("detail/pembayaran/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailPembayaranview(
                id = id,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { id ->
                    navController.navigate("update/pembayaran/$id")
                }
            )
        }
        composable("update/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            UpdateView(
                id = id,
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
