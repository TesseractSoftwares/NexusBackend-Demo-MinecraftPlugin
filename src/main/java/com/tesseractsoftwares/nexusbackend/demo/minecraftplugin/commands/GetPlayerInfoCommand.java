package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.NexusBackendPlugin;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetPlayerInfoCommand implements CommandExecutor {

    private final NexusBackendPlugin nexusBackendPlugin;
    private final PlayerDataService playerDataService;

    public GetPlayerInfoCommand(NexusBackendPlugin nexusBackendPlugin, PlayerDataService playerDataService) {
        this.nexusBackendPlugin = nexusBackendPlugin;
        this.playerDataService = playerDataService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;

        int playerId = getPlayerId(player);

        if (!nexusBackendPlugin.isAuthenticated(playerId)) {
            player.sendMessage("You must log in before to use this command");
            return true;
        }

        String playerData = playerDataService.getPlayerData(player.getUniqueId().toString());
        player.sendMessage("Your data: " + playerData);

        return false;
    }

    private int getPlayerId(Player player) {
        return 1;
    }
}
