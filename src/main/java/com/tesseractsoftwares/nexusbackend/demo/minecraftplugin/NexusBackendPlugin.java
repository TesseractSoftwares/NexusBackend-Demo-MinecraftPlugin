package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.GetPlayerInfoCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.LoginCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.RegisterCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.AuthService;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.util.ConfigLoader;
import com.tesseractsoftwares.nexusbackend.sdkjava.AuthClient;
import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class NexusBackendPlugin extends JavaPlugin {

    private AuthClient authClient;
    private GraphQLClient graphQLClient;

    private HashMap<String, Boolean> authenticatedPlayers;

    public static String prefix = "&8[&c&lTesseractPlugin&8] ";
    private final String version = getDescription().getVersion();

    public void onEnable() {
        ConfigLoader configLoader = new ConfigLoader();

        String baseUrl = "http://localhost:5286";
        String secretKey = configLoader.getSecretKey();

        if (secretKey != null) {
            this.authClient = new AuthClient(baseUrl, secretKey);
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
        LoginCommand loginCommand = new LoginCommand(this, authService);

        if (getCommand("login") != null) {
            getCommand("login").setExecutor(loginCommand);
        } else {
            getLogger().severe("The command login is not defined");
        }

        if (getCommand("getplayerinfo") != null) {
            getCommand("getplayerinfo").setExecutor(new GetPlayerInfoCommand(playerDataService));
        } else {
            getLogger().severe("The command getplayerinfo is not defined");
        }

        if (getCommand("register") != null) {
            getCommand("register").setExecutor(new RegisterCommand());
        } else {
            getLogger().severe("The command register is not defined");
        }
    }

    public void authenticatePlayer(String email) {
        authenticatedPlayers.put(email, true);
    }
}
