package com.soywiz.minecraft.repair

import org.bukkit.plugin.java.*

@Suppress("unused")
class BukkitPlugin : JavaPlugin() {
    override fun onEnable() {
        getCommand("repair")?.setExecutor(RepairCommand())
        getCommand("where")?.setExecutor(WhereCommand())
    }
}
