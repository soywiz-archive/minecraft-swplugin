package com.soywiz.minecraft.repair

import org.bukkit.command.*
import org.bukkit.entity.*

class WhereCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val location = (sender as? Entity)?.location
        sender.sendMessage("Location: $location")
        return true
    }
}