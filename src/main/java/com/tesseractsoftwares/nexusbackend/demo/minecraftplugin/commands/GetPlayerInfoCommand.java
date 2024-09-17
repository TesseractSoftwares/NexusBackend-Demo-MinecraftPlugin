package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GetPlayerInfoCommand implements CommandExecutor {

    private final PlayerDataService playerDataService;

    public GetPlayerInfoCommand(PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias,
            @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length < 2) {
            player.sendMessage("You must specified which data you want");
            return true;
        }

        String targetEmail = args[0];

        List<String> requestedFields = Arrays.asList(Arrays.copyOfRange(args,1,args.length));

        String playerData = playerDataService.getPlayerData(targetEmail, requestedFields);
        player.sendMessage(playerData);

        return true;
    }
}
