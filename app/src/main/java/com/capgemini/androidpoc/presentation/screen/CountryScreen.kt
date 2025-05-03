package com.capgemini.androidpoc.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.capgemini.androidpoc.utils.Routes
import com.capgemini.androidpoc.presentation.state.UiState
import com.capgemini.androidpoc.presentation.viewmodel.CountryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(
    viewModel: CountryViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Countries", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF388E3C) // Dark green
                )
            )
        }
    ) { padding ->
        when (uiState) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val countries = (uiState as UiState.Success).countries
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .background(Color(0xFFF1F8E9))
                ) {
                    items(countries) { country ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(Routes.countryDetail(country.name))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFA5D6A7) // Soft green card
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = country.name,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    Text(text = message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
