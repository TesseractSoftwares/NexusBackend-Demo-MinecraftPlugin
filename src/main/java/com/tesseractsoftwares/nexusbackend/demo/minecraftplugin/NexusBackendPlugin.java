package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.GetPlayerInfoCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.auth.LoginCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.AuthService;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import com.tesseractsoftwares.nexusbackend.sdkjava.AuthClient;
import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.UUID;

public class NexusBackendPlugin extends JavaPlugin {

    private AuthClient authClient;
    private GraphQLClient graphQLClient;


    private HashMap<Integer, Boolean> authenticatedPlayers;

    public static String prefix = "&8[&c&lTesseractPlugin&8] ";
    private final String version = getDescription().getVersion();

    public void onEnable() {
        String baseUrl = "http://localhost:5009";
        String secretKey = "d9959704ea47a2ff6996fe6723a63198d5c39833e3f12e76f989e4600130c673";

        if (secretKey != null){
            this.authClient = new AuthClient(baseUrl,secretKey);
            this.graphQLClient = new GraphQLClient(baseUrl);
            this.authenticatedPlayers = new HashMap<>();
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
        AuthService authService = new AuthService(authClient);
        PlayerDataService playerDataService = new PlayerDataService(graphQLClient);


        if (getCommand("login") != null) {
            getCommand("login").setExecutor(new LoginCommand(authService, this));
        } else {
            getLogger().severe("The command login is not defined");
        }

        if (getCommand("getplayerinfo") != null) {
            getCommand("getplayerinfo").setExecutor(new GetPlayerInfoCommand(this, playerDataService));
        } else {
            getLogger().severe("The command getplayerinfo is not defined");
        }
    }

    public void authenticatePlayer(int playerId) {
        authenticatedPlayers.put(playerId, true);
    }

    public boolean isAuthenticated(int playerId) {
        return authenticatedPlayers.getOrDefault(playerId, false);
    }
}
