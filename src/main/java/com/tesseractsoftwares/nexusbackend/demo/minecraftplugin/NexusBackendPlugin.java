package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class NexusBackendPlugin extends JavaPlugin {

    public void onEnable() {
        getLogger().info("NexusBackendPlugin ha sido habilitado!");
    }

    public void onDisable() {
        getLogger().info("NexusBackendPlugin ha sido deshabilitado!");
    }
}
