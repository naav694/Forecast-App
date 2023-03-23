package com.example.weatherapplowes.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapplowes.R
import com.example.weatherapplowes.domain.model.NavigationSearchScreenEvent
import com.example.weatherapplowes.presentation.ForecastViewModel
import com.example.weatherapplowes.presentation.Screen
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: ForecastViewModel = viewModel()
) {
    var city by remember { mutableStateOf("") }
    val state = viewModel.uiSearchState.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val focus = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.navigationSearchScreenEvent.collect { event ->
            when (event) {
                NavigationSearchScreenEvent.NavigateToListScreen -> {
                    navController.navigate(Screen.WeatherInfoListScreen.route)
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text(stringResource(R.string.enter_city)) },
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
                )
                Spacer(modifier = Modifier.padding(16.dp))
                OutlinedButton(
                    onClick = {
                        focus.clearFocus()
                        if (city.trim().isEmpty()) {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = "Please, Enter a City")
                            }
                        } else {
                            viewModel.getWeatherInfoList(city.uppercase())
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) { Text(text = stringResource(R.string.look_up)) }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }
            if (state.error.isNotEmpty()) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error
                    )
                }
            }
        }
    }
}