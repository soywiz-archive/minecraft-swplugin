package com.soywiz.minecraft.repair.service

import com.soywiz.minecraft.repair.util.*
import org.bukkit.*
import org.bukkit.block.*
import org.bukkit.block.CommandBlock
import org.bukkit.block.data.type.*

class TeleporterService {
    fun locateCommandBlock(world: World, location: Location): Block? {
        return world.findBlockNear(location) { it.type == Material.COMMAND_BLOCK }
    }

    fun placeTeleporter(place: Location, teleportTo: Location) {
        if (place.world != teleportTo.world) error("Worlds doesn't match")
        placeTeleporter(place.world!!, place.ix, place.iy, place.iz, teleportTo.x, teleportTo.y, teleportTo.z)
    }

    fun placeTeleporter(world: World, x: Int, y: Int, z: Int, toX: Double, toY: Double, toZ: Double) {
        world.getBlockAt(x, y, z).also { block ->
            block.type = Material.COMMAND_BLOCK
            (block.state as CommandBlock).also { cmdBlock ->
                cmdBlock.setCommand("teleport @p $toX $toY $toZ")
                cmdBlock.update()
            }
        }
        world.getBlockAt(x, y + 1, z).also { block2 ->
            block2.type = Material.STONE_BUTTON
            val bd = block2.blockData as Switch
            bd.face = Switch.Face.FLOOR
            bd.facing = BlockFace.NORTH
            block2.setBlockData(bd, false)
        }
    }
}