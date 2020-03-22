package com.example.simpleforecast.Util

import kotlin.math.roundToInt


fun temperatureConverter(temperature: Double): String {
    return if (temperature > 0) "+$temperature"
    else temperature.toString()
}

fun kmToMsConverter(speed: Double): Int {
    return (speed * 0.277778).roundToInt()
}

fun barConverter(pressure: Double): Int {
    return (pressure * 0.750062).roundToInt()
}


/**
 * convert Cardinal direction to Russian value
 * I don't know why East can come as 'Г'
 */
fun directionConverter(direction: String): String {
    return when (direction) {
        "C", "ССВ", "ССГ" -> "Север"
        "СВ", "ВСВ", "СГ", "ГСГ" -> "Северо-восток"
        "В", "ВЮВ", "Г", "ГЮГ" -> "Восток"
        "ЮВ", "ЮЮВ" -> "Юго-восток"
        "Ю", "ЮЮЗ" -> "Юг"
        "ЮЗ", "ЗЮЗ" -> "Юго-запад"
        "З", "ЗСЗ" -> "Запад"
        "СЗ", "ССЗ" -> "Северо-запад"
        else -> direction
    }
}