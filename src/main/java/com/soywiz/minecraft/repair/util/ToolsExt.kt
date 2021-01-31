package com.soywiz.minecraft.repair.util

import org.bukkit.*
import org.bukkit.block.*

fun World.findBlockNear(pos: Location, distance: Int = 2, cond: (Block) -> Boolean): Block? {
    return findBlockNear(pos.x.toInt(), pos.y.toInt(), pos.z.toInt(), distance, cond)
}

fun World.findBlockNear(x: Int, y: Int, z: Int, distance: Int = 2, cond: (Block) -> Boolean): Block? {
    for (dx in 0..distance * 2) {
        for (dy in 0..distance * 2) {
            for (dz in 0..distance * 2) {
                val rdx = dx - distance
                val rdy = dy - distance
                val rdz = dz - distance
                val block = getBlockAt(x + rdx, y + rdy, z + rdz)
                if (cond(block)) {
                    return block
                }
            }
        }
    }
    return null
}

fun Location.toIntPosString() = "$ix $iy $iz"

val Location.ix get() = x.toInt()
val Location.iy get() = y.toInt()
val Location.iz get() = z.toInt()