package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.GetPlayerInfoCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.LoginCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetPlayerInfoCommandTest {

    private PlayerDataService playerDataService;
    private LoginCommand loginCommand;
    private GetPlayerInfoCommand getPlayerInfoCommand;
    private Player mockPlayer;
    private Command mockCommand;

    @BeforeEach
    void setUp(){
        playerDataService = mock(PlayerDataService.class);
        loginCommand = mock(LoginCommand.class);
        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);

        getPlayerInfoCommand = new GetPlayerInfoCommand(playerDataService, loginCommand);

        when(mockPlayer.getName()).thenReturn("TestPlayer");
    }

    @Test
    void testGetPlayerInfoCommandNotAPlayer() {
        CommandSender mockSender = mock(CommandSender.class);

        boolean result = getPlayerInfoCommand.onCommand(mockSender, mockCommand, "getplayerinfo", new String[] { "coins "});
        verify(mockSender).sendMessage("This command only can be used while being a player");
        assertTrue(result);
    }

    @Test
    void testGetPlayerInfoCommandNotLoggedIn() {
        when(loginCommand.getEmail(mockPlayer)).thenReturn(null);

        boolean result = getPlayerInfoCommand.onCommand(mockPlayer, mockCommand, "getplayerinfo", new String[]{"coins"});
        verify(mockPlayer).sendMessage("You must log in before to use this command");
        assertTrue(result);
    }

    @Test
    void testGetPlayerInfoCommandNoArguments() {
        when(loginCommand.getEmail(mockPlayer)).thenReturn("email@example.com");

        boolean result = getPlayerInfoCommand.onCommand(mockPlayer, mockCommand, "getplayerinfo", new String[]{});
        verify(mockPlayer).sendMessage("You must specified which data you want");
        assertTrue(result);
    }

    @Test
    void testGetPlayerInfoCommandSuccess() {
        when(loginCommand.getEmail(mockPlayer)).thenReturn("email@example.com");
        when(playerDataService.getPlayerData(anyString(), anyList())).thenReturn("Player Data: Coins: 100, Level: 5");

        String[] args = new String[]{"coins", "level"};
        boolean result = getPlayerInfoCommand.onCommand(mockPlayer, mockCommand, "getplayerinfo", args);

        verify(playerDataService).getPlayerData("email@example.com", Arrays.asList("coins", "level"));
        verify(mockPlayer).sendMessage("Player Data: Coins: 100, Level: 5");
        assertTrue(result);
    }
}
