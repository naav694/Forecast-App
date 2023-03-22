package com.example.weatherapplowes.presentation.forecast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapplowes.R
import com.example.weatherapplowes.domain.model.NavigationListScreenEvent
import com.example.weatherapplowes.presentation.ForecastViewModel
import com.example.weatherapplowes.presentation.Screen
import com.example.weatherapplowes.presentation.forecast.components.WeatherInfoListItem

@Composable
fun WeatherListScreen(
    navController: NavController,
    viewModel: ForecastViewModel = viewModel()
) {
    val listState = viewModel.listState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.navigationListScreenEvent.collect { event ->
            when (event) {
                NavigationListScreenEvent.NavigateToDetail -> {
                    navController.navigate(Screen.WeatherInfoDetailScreen.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = listState.city) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(listState.weatherInfoList) { weatherInfo ->
                WeatherInfoListItem(
                    weatherInfo = weatherInfo,
                    onItemClick = { viewModel.getWeatherInfoDetail(dt = weatherInfo.dt) }
                )
                Divider(modifier = Modifier.height(1.dp))
            }
        }
    }
}