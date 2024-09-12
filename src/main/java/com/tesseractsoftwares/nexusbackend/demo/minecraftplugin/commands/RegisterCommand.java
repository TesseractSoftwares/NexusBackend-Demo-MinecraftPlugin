package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias,
            @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }
        Player player = (Player) commandSender;

        TextComponent message = new TextComponent("If you want to register go to: ");
        TextComponent url = new TextComponent("https://www.youtube.com/watch?v=dQw4w9WgXcQ");

        url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        url.setColor(ChatColor.BLUE);
        url.setUnderlined(true);

        message.addExtra(url);

        player.sendMessage(message);

        return true;
    }
}
