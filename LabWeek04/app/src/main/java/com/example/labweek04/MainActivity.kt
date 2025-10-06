package com.example.labweek04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labweek04.data.AppDatabase
import com.example.labweek04.data.ItemRepository
import com.example.labweek04.ui.ItemsScreen
import com.example.labweek04.ui.ItemsViewModel
import com.example.labweek04.ui.ItemsViewModelFactory
import com.example.labweek04.ui.WelcomeScreen
import com.example.labweek04.ui.theme.LabWeek04Theme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Build DB/Repo once & inject into the ViewModel
        val db = AppDatabase.getInstance(this)
        val repo = ItemRepository(db.itemDao())

        setContent {
            LabWeek04Theme {
                val nav = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF7F3F3)
                ) {
                    // Provide VM here so it survives navigation
                    val vm: ItemsViewModel = viewModel(
                        factory = ItemsViewModelFactory(repo)
                    )

                    NavHost(
                        navController = nav,
                        startDestination = "welcome"
                    ) {
                        composable("welcome") {
                            WelcomeScreen(
                                onStartClick = { nav.navigate("items") }
                            )
                        }
                        composable("items") {
                            // A top bar only on the items page (keeps ItemsScreen unchanged)
                            Scaffold(
                                topBar = {
                                    CenterAlignedTopAppBar(
                                        title = { Text("Room Items") },
                                        navigationIcon = {
                                            IconButton(onClick = { nav.popBackStack() }) {
                                                Icon(
                                                    Icons.Filled.ArrowBack,
                                                    contentDescription = "Back"
                                                )
                                            }
                                        },
                                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                            containerColor = Color.Transparent
                                        )
                                    )
                                }
                            ) { padding ->
                                ItemsScreen(
                                    vm = vm,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(padding) // ✅ uses Scaffold's padding
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
