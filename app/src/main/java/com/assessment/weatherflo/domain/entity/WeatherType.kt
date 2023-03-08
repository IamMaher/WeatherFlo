package com.assessment.weatherflo.domain.entity

import androidx.annotation.DrawableRes
import com.assessment.weatherflo.R

sealed class WeatherType(@DrawableRes val iconRes: Int, val desc: String) {
    class ClearSky(desc: String) : WeatherType(iconRes = R.drawable.ic_sunny, desc = desc)
    class FewClouds(desc: String) : WeatherType(iconRes = R.drawable.ic_sunnycloudy, desc = desc)
    class ScatteredClouds(desc: String) : WeatherType(iconRes = R.drawable.ic_cloudy, desc = desc)
    class OvercastClouds(desc: String) : WeatherType(iconRes = R.drawable.ic_very_cloudy, desc = desc)
    class ShowerRain(desc: String) : WeatherType(iconRes = R.drawable.ic_rainshower, desc = desc)
    class Rain(desc: String) : WeatherType(iconRes = R.drawable.ic_rainy, desc = desc)
    class ThunderStorm(desc: String) : WeatherType(iconRes = R.drawable.ic_thunder, desc = desc)
    class Snow(desc: String) : WeatherType(iconRes = R.drawable.ic_snowy, desc = desc)
    class Mist(desc: String) : WeatherType(iconRes = R.drawable.ic_windy, desc = desc)

    companion object {
        fun fromWMO(code: String, desc: String): WeatherType {
            return when (code) {
                "02" -> FewClouds(desc)
                "03" -> ScatteredClouds(desc)
                "04" -> OvercastClouds(desc)
                "09" -> ShowerRain(desc)
                "10" -> Rain(desc)
                "11" -> ThunderStorm(desc)
                "13" -> Snow(desc)
                "50" -> Mist(desc)
                else -> ClearSky(desc)
            }
        }
    }
}