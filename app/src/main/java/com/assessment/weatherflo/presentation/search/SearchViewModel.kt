package com.assessment.weatherflo.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherflo.core.exception.Failure
import com.assessment.weatherflo.core.functional.SearchType
import com.assessment.weatherflo.core.functional.searchType
import com.assessment.weatherflo.core.functional.toCoordinate
import com.assessment.weatherflo.core.interactor.UseCase
import com.assessment.weatherflo.data.remote.WeatherApi
import com.assessment.weatherflo.domain.entity.cities.CityEntity
import com.assessment.weatherflo.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val citiesUseCase: CitiesUseCase,
    private val citiesZipUseCase: CitiesZipUseCase,
    private val getCityHistoryUseCase: GetCityHistoryUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val clearCitiesUseCase: ClearCitiesUseCase,
) : ViewModel() {

    var textSearch: MutableStateFlow<String> = MutableStateFlow("")
        private set

    var state by mutableStateOf(SearchState())
        private set

    init {
        textSearch.debounce(500).onEach { query ->
            if (query.length > 1) searchCityResult(query) else handleSearchResult(emptyList())
        }.launchIn(viewModelScope)

        getAllCityHistory()
    }


    private fun searchCityResult(search: String) {
        val searchType = search.searchType()
        val pathType: String
        val queries = mutableMapOf(
            "appid" to WeatherApi.API_KEY,
            "limit" to "5"
        )
        when (searchType) {
            SearchType.Zipcode.value -> {
                pathType = SearchType.Zipcode.type
                queries["zip"] = search
                citiesZipUseCase(viewModelScope, CitiesZipUseCase.Params(queries)) {
                    it.fold({ data -> handleSearchResult(listOf(data)) }, ::handleFailure)
                }
            }
            SearchType.LatLon.value -> {
                pathType = SearchType.LatLon.type
                search.toCoordinate()?.let { (latitude, longitude) ->
                    queries["lat"] = latitude.toString()
                    queries["lon"] = longitude.toString()
                    citiesUseCase(viewModelScope, CitiesUseCase.Params(pathType, queries)) {
                        it.fold(::handleSearchResult, ::handleFailure)
                    }
                } ?: return
            }
            else -> {
                pathType = SearchType.Query.type
                queries["q"] = search
                citiesUseCase(viewModelScope, CitiesUseCase.Params(pathType, queries)) {
                    it.fold(::handleSearchResult, ::handleFailure)
                }
            }
        }
    }

    fun debounceSearch(it: String) {
        textSearch.value = it
    }

    private fun getAllCityHistory() = getCityHistoryUseCase(viewModelScope, UseCase.None()) { it.fold(::handleSearchHistory, ::handleFailure) }

    fun saveCity(city: CityEntity) = saveCityUseCase(viewModelScope, SaveCityUseCase.Params(city.copy(timestamp = System.currentTimeMillis())))

    fun clearHistory() = clearCitiesUseCase(viewModelScope, UseCase.None()) { it.fold(::handleSearchHistory, ::handleFailure) }

    private fun handleSearchResult(data: List<CityEntity>) {
        state = state.copy(
            listResult = data,
            isLoading = false,
            error = null
        )
    }

    private fun handleSearchHistory(data: List<CityEntity>) {
        state = state.copy(
            listHistory = data,
            listPlaceholder = renderPlaceholder(data),
            isLoading = false,
            error = null
        )
    }

    private fun renderPlaceholder(data: List<CityEntity>) =
        mutableListOf("Search every things you want!").apply { addAll(1, data.map { "${it.name}, ${it.country}" }) }

    private fun handleFailure(failure: Failure?) {
        state = state.copy(
            listResult = emptyList(),
            isLoading = false,
            error = failure
        )
    }
}
