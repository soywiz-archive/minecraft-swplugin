package com.soywiz.minecraft.swplugin.service

import com.soywiz.minecraft.swplugin.*
import org.bukkit.*

class PlacesService(val plugin: SwpluginPlugin) {
    fun setPlace(name: String, location: Location) {
        plugin.config.set("locations.$name", location)
        plugin.saveConfig()
    }

    fun getPlace(name: String): Location? {
        return plugin.config.getLocation("locations.$name")
    }

    fun removePlace(name: String) {
        plugin.config.set("locations.$name", null)
    }

    fun listPlaces(): List<String> {
        return plugin.config.getConfigurationSection("locations")?.getKeys(false)?.toList() ?: listOf()
    }
}