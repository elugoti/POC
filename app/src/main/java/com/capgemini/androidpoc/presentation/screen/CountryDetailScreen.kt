package com.capgemini.androidpoc.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.capgemini.androidpoc.presentation.state.CountryDetailUiState
import com.capgemini.androidpoc.presentation.viewmodel.CountryDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    name: String,
    viewModel: CountryDetailViewModel = hiltViewModel()
) {
    // Access the current state value directly
    val state = viewModel.state.value

    LaunchedEffect(name) {
        viewModel.loadCountryDetails(name)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Details of $name", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF388E3C))
            )
        }
    ) { padding ->
        when (val uiState = state) {
            is CountryDetailUiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CountryDetailUiState.Success -> {
                val country = uiState.country
                Column(
                    Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Text("Official Name: ${country.name.official}", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("Capital: ${country.capital?.joinToString() ?: "N/A"}")
                    Text("Region: ${country.region}")
                    Text("Subregion: ${country.subregion ?: "N/A"}")
                    Text("Population: ${country.population}")
                    Text("Timezones: ${country.timezones.joinToString()}")

                    Spacer(Modifier.height(16.dp))
                    AsyncImage(
                        model = country.flags.png,
                        contentDescription = country.flags.alt ?: "Flag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }

            is CountryDetailUiState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    Text(uiState.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}