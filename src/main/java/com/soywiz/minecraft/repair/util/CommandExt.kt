package com.soywiz.minecraft.repair.util

import org.bukkit.command.*
import org.bukkit.entity.*

class CommandExecInfo(
    val sender: CommandSender,
    val command: Command,
    val label: String,
    val args: Array<String>
) {
    val senderEntity = sender as? Entity?
    val senderPlayer = sender as? Player?
    val senderCommandBlock = sender as? BlockCommandSender?
}

fun PluginCommand.setSimpleExecutor(block: CommandExecInfo.() -> Unit) {
    setExecutor { sender, command, label, args ->
        block(CommandExecInfo(sender, command, label, args))
        true
    }
}

fun PluginCommand.setSimpleCompleter(block: CommandExecInfo.() -> List<String>?) {
    setTabCompleter { sender, command, alias, args ->
        block(CommandExecInfo(sender, command, label, args))
    }
}
