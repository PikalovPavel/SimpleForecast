package com.example.simpleforecast.UI.Adapter

import com.example.simpleforecast.Data.Local.Database.Entity.City

interface BaseAdapterCallback {
    fun onItemClick(model: City)
}