package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.LoginCommand;
import com.tesseractsoftwares.nexusbackend.sdkjava.AuthClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class NexusBackendPlugin extends JavaPlugin {

    private AuthClient authClient;

    public static String prefix = "&8[&c&lTesseractPlugin&8] ";
    private final String version = getDescription().getVersion();

    public void onEnable() {
        String baseUrl = "http://localhost:5009";
        String secretKey = "d9959704ea47a2ff6996fe6723a63198d5c39833e3f12e76f989e4600130c673";

        if (secretKey != null){
            this.authClient = new AuthClient(baseUrl,secretKey);
        } else {
            getLogger().severe("Can't load the secret key");
        }

        registerCommands();

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eha sido activado! &fVersion: " + version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eGracias por usar mi plugin ;)"));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eha sido desactivado! &fVersion: " + version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eGracias por usar mi plugin ;)"));
    }

    private void registerCommands() {
        if (getCommand("login") != null) {
            getCommand("login").setExecutor(new LoginCommand(authClient));
        } else {
            getLogger().severe("The command login is not defined");
        }
    }
}
