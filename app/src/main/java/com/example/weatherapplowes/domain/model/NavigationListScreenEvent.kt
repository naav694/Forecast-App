package com.example.weatherapplowes.domain.model

sealed class NavigationListScreenEvent {
    object NavigateToDetail: NavigationListScreenEvent()
}
