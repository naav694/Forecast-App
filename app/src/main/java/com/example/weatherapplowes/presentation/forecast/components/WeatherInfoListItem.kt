package com.example.weatherapplowes.presentation.forecast.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapplowes.domain.model.WeatherInfo

@Composable
fun WeatherInfoListItem(
    weatherInfo: WeatherInfo,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick() }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherInfo.info ?: "",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Temp: ${weatherInfo.temp}",
            textAlign = TextAlign.End
        )
    }
}