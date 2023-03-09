package com.assessment.weatherflo.data.remote.dto.cities


import com.squareup.moshi.Json

data class LocalNames(
    @field:Json(name = "ar") val ar: String = "",
    @field:Json(name = "be") val be: String = "",
    @field:Json(name = "ca") val ca: String = "",
    @field:Json(name = "cs") val cs: String = "",
    @field:Json(name = "cy") val cy: String = "",
    @field:Json(name = "de") val de: String = "",
    @field:Json(name = "el") val el: String = "",
    @field:Json(name = "en") val en: String = "",
    @field:Json(name = "eo") val eo: String = "",
    @field:Json(name = "es") val es: String = "",
    @field:Json(name = "fa") val fa: String = "",
    @field:Json(name = "fr") val fr: String = "",
    @field:Json(name = "gl") val gl: String = "",
    @field:Json(name = "he") val he: String = "",
    @field:Json(name = "hi") val hi: String = "",
    @field:Json(name = "ja") val ja: String = "",
    @field:Json(name = "kn") val kn: String = "",
    @field:Json(name = "ko") val ko: String = "",
    @field:Json(name = "oc") val oc: String = "",
    @field:Json(name = "pl") val pl: String = "",
    @field:Json(name = "pt") val pt: String = "",
    @field:Json(name = "ru") val ru: String = "",
    @field:Json(name = "ta") val ta: String = "",
    @field:Json(name = "te") val te: String = "",
    @field:Json(name = "uk") val uk: String = "",
    @field:Json(name = "vi") val vi: String = "",
    @field:Json(name = "zh") val zh: String = ""
)