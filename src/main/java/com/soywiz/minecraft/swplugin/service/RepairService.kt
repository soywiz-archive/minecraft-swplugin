package com.soywiz.minecraft.swplugin.service

import org.bukkit.entity.*
import org.bukkit.inventory.*
import org.bukkit.inventory.meta.*
import org.bukkit.inventory.meta.Damageable

class RepairService {
    fun repair(player: Player) {
        println("    !!! Repair plugin player: $player")
        for (item in player.inventory.contents) {
            if (item == null) continue
            item.setSafeDurability(0)
        }
        player.sendMessage("The whole inventory was repaired")
        //player.teleport(Location())
        //player.itemInHand.durability = 0.toShort()
    }

    fun ItemStack.setSafeDurability(value: Int) {
        val meta = itemMeta as? Damageable
        if (meta != null) {
            meta.damage = value
            itemMeta = meta as ItemMeta
        }
    }
}