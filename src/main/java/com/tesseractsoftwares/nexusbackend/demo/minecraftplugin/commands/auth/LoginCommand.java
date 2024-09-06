package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.auth;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.NexusBackendPlugin;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.AuthService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LoginCommand implements CommandExecutor {
    private final AuthService authService;
    private final NexusBackendPlugin nexusBackendPlugin;

    public LoginCommand(AuthService authService, NexusBackendPlugin nexusBackendPlugin) {
        this.authService = authService;
        this.nexusBackendPlugin = nexusBackendPlugin;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 2) {
            commandSender.sendMessage("Wrong use. You must put /login <email> <password>");
            return true;
        }

        String email = args[0];
        String password = args[1];

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            commandSender.sendMessage("Invalid email, PLIS! write a valid email");
            return true;
        }

        if (password.length() <= 8) {
            commandSender.sendMessage("The password must have 8 characters");
            return true;
        }

        authService.authenticatePlayer(email, password,
                message -> {
                    int playerId = getPlayerId(email);

                    nexusBackendPlugin.authenticatePlayer(playerId);
                    player.sendMessage("Successfully logged: " + message);
                },
                errorMessage -> player.sendMessage("Invalid Credentials: " + errorMessage),
                exception -> player.sendMessage("Error trying to connect to the server"));

        return false;
    }

    private int getPlayerId(String email) {
        return 1;
    }
}
