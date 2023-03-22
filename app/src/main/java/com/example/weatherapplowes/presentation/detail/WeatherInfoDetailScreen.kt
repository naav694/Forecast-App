package com.example.weatherapplowes.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapplowes.R
import com.example.weatherapplowes.presentation.ForecastViewModel

@Composable
fun WeatherInfoDetailScreen(
    navController: NavController,
    viewModel: ForecastViewModel = viewModel()
) {
    val weatherDetail = viewModel.detailState.collectAsState().value
    val city = viewModel.listState.collectAsState().value.city

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = city) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) { Text(text = weatherDetail.temp, fontSize = 86.sp) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) { Text(text = "Feels Like: ${weatherDetail.feelsLike}", fontSize = 36.sp) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) { Text(text = weatherDetail.condition, fontSize = 48.sp) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) { Text(text = weatherDetail.details, fontSize = 18.sp) }
        }
    }
}