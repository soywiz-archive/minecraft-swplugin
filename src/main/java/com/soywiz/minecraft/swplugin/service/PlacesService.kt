package com.soywiz.minecraft.swplugin.service

import com.soywiz.minecraft.swplugin.*
import org.bukkit.*
import org.bukkit.entity.*

class PlacesService(val plugin: SwpluginPlugin) {
    fun getRootKey(player: Player? = null): String =
        if (player != null) "locations-priv.${player.uniqueId}" else "locations"

    fun getKey(name: String, player: Player? = null): String = "${getRootKey(player)}.$name"

    fun setPlace(name: String, location: Location, player: Player? = null) {
        val key = getKey(name, player)
        plugin.config.set(key, location)
        plugin.saveConfig()
    }

    fun getPlace(name: String, player: Player? = null): Location? {
        return plugin.config.getLocation(getKey(name, player))
            ?: plugin.config.getLocation(getKey(name))
    }

    fun removePlace(name: String, player: Player? = null) {
        val key = getKey(name, player)
        if (plugin.config.getLocation(key) != null) {
            plugin.config.set(key, null)
        } else {
            plugin.config.set(getKey(name), null)
        }
    }

    fun listPlaces(player: Player? = null): List<String> {
        val list = arrayListOf<String>()

        list.addAll(plugin.config.getConfigurationSection(getRootKey())?.getKeys(false)?.toList() ?: listOf())

        if (player != null) {
            list.addAll(plugin.config.getConfigurationSection(getRootKey(player))?.getKeys(false)?.toList() ?: listOf())
        }
        return list
    }
}