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
    private final LoginCommand loginCommand;

    public GetPlayerInfoCommand(PlayerDataService playerDataService, LoginCommand loginCommand) {
        this.playerDataService = playerDataService;
        this.loginCommand = loginCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias,
            @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;
        String email = loginCommand.getEmail(player);

        if (email == null) {
            player.sendMessage("You must log in before to use this command");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("You must specified which data you want");
            return true;
        }

        List<String> requestedFields = Arrays.asList(args);

        String playerData = playerDataService.getPlayerData(email, requestedFields);
        player.sendMessage(playerData);

        return true;
    }
}
