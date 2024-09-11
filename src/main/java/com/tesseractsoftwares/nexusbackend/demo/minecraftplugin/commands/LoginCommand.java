package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.NexusBackendPlugin;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.AuthService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginCommand implements CommandExecutor {
    private final AuthService authService;
    private final NexusBackendPlugin nexusBackendPlugin;
    private final HashMap<Player, String> authenticatedEmail = new HashMap<>();

    public LoginCommand(NexusBackendPlugin nexusBackendPlugin, AuthService authService) {
        this.nexusBackendPlugin = nexusBackendPlugin;
        this.authService = authService;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias,
            @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        if (args.length != 2) {
            commandSender.sendMessage("Wrong use. You must put /login <email> <password>");
            return true;
        }

        Player player = (Player) commandSender;
        String email = args[0];
        String password = args[1];

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            player.sendMessage("Invalid email, PLIS! write a valid email");
            return true;
        }

        if (password.length() <= 8) {
            player.sendMessage("The password must have 8 characters");
            return true;
        }

        authService.authenticatePlayer(email, password,
                message -> {
                    nexusBackendPlugin.authenticatePlayer(email);
                    authenticatedEmail.put(player, email);
                    player.sendMessage("Successfully logged: " + message + " Welcome " + player.getName() + "!!!");
                },
                errorMessage -> player.sendMessage("Invalid Credentials: " + errorMessage),
                exception -> player.sendMessage("Error trying to connect to the server"));

        return true;
    }

    public String getEmail(Player player) {
        return authenticatedEmail.get(player);
    }
}