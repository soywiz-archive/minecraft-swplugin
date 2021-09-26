package com.soywiz.minecraft.swplugin

import com.soywiz.minecraft.swplugin.service.*
import com.soywiz.minecraft.swplugin.util.*
import org.bukkit.*
import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.inventory.*
import org.bukkit.metadata.*
import org.bukkit.plugin.java.*
import kotlin.math.*

@Suppress("unused")
class SwpluginPlugin : JavaPlugin() {
    companion object {
        val WHERE_METADATA_KEY = "where_command"
    }
    val plugin = this
    val places = PlacesService(this)
    val repair = RepairService()
    val teleporters = TeleporterService()

    override fun onEnable() {
        command("repair") {
            executor {
                if (senderPlayer != null) {
                    repair.repair(senderPlayer)
                }

                if (senderCommandBlock != null) {
                    val block = senderCommandBlock.block
                    val world = block.world
                    for (e in world.getNearbyEntities(block.location, 2.0, 2.0, 2.0) { it is Player }) {
                        repair.repair(e as Player)
                    }
                }
            }
        }
        command("where") {
            executor {
                if (senderEntity != null) {
                    val location = senderEntity.location
                    val coordsString = "${location.x.toInt()} ${location.y.roundToInt()} ${location.z.toInt()}"
                    senderEntity.sendMessage("Location: $coordsString")
                    senderEntity.setMetadata(WHERE_METADATA_KEY, FixedMetadataValue(plugin, location))
                }
            }
        }
        command("teleporter") {
            executor {
                val currentLocation = senderEntity?.location
                val savedLocation = senderEntity?.getMetadata(WHERE_METADATA_KEY)?.firstOrNull()?.value()
                if (savedLocation is Location && currentLocation is Location) {
                    teleporters.placeTeleporter(currentLocation, savedLocation.clone().add(0.0, +1.0, 0.0))
                    teleporters.placeTeleporter(savedLocation, currentLocation.clone().add(0.0, +1.0, 0.0))
                    senderEntity?.removeMetadata(WHERE_METADATA_KEY, plugin)
                } else {
                    sender.sendMessage("ERROR: $savedLocation is not a Location")
                }
            }
        }
        command("unteleporter") {
            executor {
                if (senderEntity != null) {
                    teleporters.locateCommandBlock(senderEntity.world, senderEntity.location)?.type = Material.AIR
                }
            }
        }
        command("firework") {
            executor {
                val player = sender as Player
                player.inventory.addItem(ItemStack(Material.FIREWORK_ROCKET, 64))
            }
        }
        command("remember") {
            executor {
                val player = sender as Player
                if (args.isNotEmpty()) {
                    val name = args[0]
                    places.setPlace(name, player.location)
                    sender.sendMessage("Remembered '$name' (${player.location.toIntPosString()})")
                }
            }
        }
        command("remember_priv") {
            executor {
                val player = sender as Player
                if (args.isNotEmpty()) {
                    val userPlaces = places.listPlacesOnlyUser(sender as? Player?)
                    val NUM_PRIV_PLACES = if (player.isOp) 20 else 2
                    if (userPlaces.size > NUM_PRIV_PLACES) {
                        val name = args[0]
                        places.setPlace(name, player.location, player)
                        sender.sendMessage("Remembered '$name' (${player.location.toIntPosString()}) only for ${player.displayName}")
                    } else {
                        sender.sendMessage("Can't have more privately remembered places. MAXIMUM=$NUM_PRIV_PLACES op=${player.isOp}")
                    }
                }
            }
        }
        command("forget") {
            completer { places.listPlaces(sender as? Player?) }
            executor {
                if (args.isNotEmpty()) {
                    val name = args[0]
                    places.removePlace(name, sender as? Player?)
                    sender.sendMessage("Forgot place '$name'")
                }
            }
        }
        command("go") {
            completer { places.listPlaces(sender as? Player?) }
            executor {
                val player = sender as Player
                if (args.isNotEmpty()) {
                    val name = args[0]
                    val location = places.getPlace(name, sender as? Player?)
                    if (location != null) {
                        player.teleport(location)
                    } else {
                        sender.sendMessage("Location '$name' not found")
                    }
                } else {
                    sender.sendMessage("Must specify location")
                }
            }
        }
        command("places") {
            executor {
                senderPlayer?.sendMessage("Places: " + places.listPlaces(sender as? Player?).joinToString(", "))
            }
        }
        command("places_priv") {
            executor {
                senderPlayer?.sendMessage("Places: " + places.listPlacesOnlyUser(sender as? Player?).joinToString(", "))
            }
        }
    }

    fun command(name: String, block: PluginCommand.() -> Unit) {
        getCommand(name)?.apply(block)
    }
}
