package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCheck {
    public static boolean playerCheck(CommandSender commandSender) {
        if(commandSender instanceof Player) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }
        return false;
    }
}
