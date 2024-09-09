package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.auth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private static final String url = "URL para registro";
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;

        player.sendMessage("If you want to register go to: " + url);

        return true;
    }
}
