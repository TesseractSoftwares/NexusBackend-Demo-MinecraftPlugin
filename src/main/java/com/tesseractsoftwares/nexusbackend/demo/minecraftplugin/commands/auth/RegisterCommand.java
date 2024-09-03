package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.auth;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.util.PlayerCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private static String url = "URL para registro";
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (PlayerCheck.playerCheck(commandSender)) {
            return true;
        }

        return false;
    }
}
