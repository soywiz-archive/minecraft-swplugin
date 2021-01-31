package com.soywiz.minecraft.repair

import org.bukkit.plugin.java.*

class BukkitPlugin : JavaPlugin() {
    override fun onEnable() {
        getCommand("repair")!!.setExecutor(RepairCommand())
    }
}
