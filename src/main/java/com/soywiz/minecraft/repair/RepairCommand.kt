package com.soywiz.minecraft.repair

import org.bukkit.entity.Player
import org.bukkit.World
import org.bukkit.command.*

class RepairCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        println("!!! Repair plugin: " + sender.javaClass + " : " + sender)
        if (sender is Player) {
            repair(sender)
        }
        if (sender is BlockCommandSender) {
            val block = sender.block
            val world = block.world
            for (e in world.getNearbyEntities(block.location, 2.0, 2.0, 2.0)) {
                if (e is Player) {
                    repair(e)
                }
            }
        }
        //player.getItemOnCursor().setDurability((short) 0);
        return true
    }

    private fun repair(player: Player) {
        println("    !!! Repair plugin player: $player")
        player.itemInHand.durability = 0.toShort()
    }
}