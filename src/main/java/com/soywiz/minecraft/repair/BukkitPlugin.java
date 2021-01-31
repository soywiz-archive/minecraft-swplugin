package com.soywiz.minecraft.repair;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("repair").setExecutor(new RepairCommand());
    }
}
