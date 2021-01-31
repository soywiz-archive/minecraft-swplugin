package com.soywiz.minecraft.repair;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class RepairCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("!!! Repair plugin: " + sender.getClass() + " : " + sender);
        if (sender instanceof Player) {
            repair(((Player) sender));
        }
        if (sender instanceof BlockCommandSender) {
            Block block = ((BlockCommandSender) sender).getBlock();
            World world = block.getWorld();
            for (Entity e : world.getNearbyEntities(block.getLocation(), 2.0, 2.0, 2.0)) {
                if (e instanceof Player) {
                    repair((Player) e);
                }
            }
        }
        //player.getItemOnCursor().setDurability((short) 0);
        return true;
    }

    private void repair(Player player) {
        System.out.println("    !!! Repair plugin player: " + player);
        player.getItemInHand().setDurability((short) 0);
    }
}
