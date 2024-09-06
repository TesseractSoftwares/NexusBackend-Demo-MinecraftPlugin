package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.NexusBackendPlugin;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.auth.LoginCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetPlayerInfoCommand implements CommandExecutor {

    private final NexusBackendPlugin nexusBackendPlugin;
    private final PlayerDataService playerDataService;
    private final LoginCommand loginCommand;

    public GetPlayerInfoCommand(NexusBackendPlugin nexusBackendPlugin, PlayerDataService playerDataService, LoginCommand loginCommand) {
        this.nexusBackendPlugin = nexusBackendPlugin;
        this.playerDataService = playerDataService;
        this.loginCommand = loginCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command only can be used while being a player");
            return true;
        }

        Player player = (Player) commandSender;
        String email = loginCommand.getEmail(player);

        if (email == null) {
            player.sendMessage("You must log in before to use this command");
            return true;
        }

        String playerData = playerDataService.getPlayerData(email);
        player.sendMessage("Your data: " + playerData);

        return true;
    }

}
