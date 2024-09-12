package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.commands.LoginCommand;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.AuthService;
import com.tesseractsoftwares.nexusbackend.sdkjava.callbacks.AuthCallbacks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LoginCommandTest {

    private AuthService authService;
    private NexusBackendPlugin nexusBackendPlugin;
    private LoginCommand loginCommand;
    private Player mockPlayer;
    private Command mockCommand;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        nexusBackendPlugin = mock(NexusBackendPlugin.class);
        mockPlayer = mock(Player.class);
        mockCommand = mock(Command.class);

        loginCommand = new LoginCommand(nexusBackendPlugin, authService);

        when(mockPlayer.getName()).thenReturn("TestPlayer");
    }

    @Test
    void testLoginCommandNotAPlayer() {
        CommandSender mockSender = mock(CommandSender.class);

        boolean result = loginCommand.onCommand(mockSender, mockCommand, "login", new String[] {"email@example.com", "password123"});
        verify(mockSender).sendMessage("This command only can be used while being a player");

        assertTrue(result);
    }

    @Test
    void testLoginCommandInvalidEmailFormat() {
        String[] args = new String[] {"invalidEmail", "password123"};

        boolean result = loginCommand.onCommand(mockPlayer, mockCommand, "login", args);
        verify(mockPlayer).sendMessage("Invalid email, PLIS! write a valid email");
        assertTrue(result);
    }

    @Test
    void testLoginCommandPasswordTooShort() {
        String[] args = new String[] {"email@example.com", "short"};

        boolean result = loginCommand.onCommand(mockPlayer, mockCommand, "login", args);
        verify(mockPlayer).sendMessage("The password must have 8 characters");
        assertTrue(result);
    }

    @Test
    void testLoginCommandSuccess() {
        String[] args = new String[]{"email@example.com", "password123"};

        doAnswer(invocationOnMock -> {
            AuthCallbacks.OnSuccess onSuccess = invocationOnMock.getArgument(2);
            onSuccess.execute("Authentication success");
            return null;
        }).when(authService).authenticatePlayer(anyString(), anyString(), any(), any(), any());

        boolean result = loginCommand.onCommand(mockPlayer, mockCommand, "login", args);
        verify(nexusBackendPlugin).authenticatePlayer("email@example.com");
        verify(mockPlayer).sendMessage(contains("Welcome TestPlayer!!!"));
        assertTrue(result);
    }

    @Test
    void testLoginCommandInvalidCredentials(){
        String[] args = new String[]{ "email@example.com", "wrongPassword" };

        doAnswer(invocationOnMock -> {
            AuthCallbacks.OnInvalidCredentials onInvalidCredentials = invocationOnMock.getArgument(3);
            onInvalidCredentials.execute("Invalid credentials");
            return null;
        }).when(authService).authenticatePlayer(anyString(), anyString(), any(), any(), any());

        boolean result = loginCommand.onCommand(mockPlayer, mockCommand, "login", args);
        verify(mockPlayer).sendMessage(contains("Invalid Credentials"));
        assertTrue(result);
    }

    @Test
    void testLoginCommandErrorConnecting() {
        String[] args = new String[] { "email@example.com", "password123" };

        doAnswer(invocationOnMock -> {
            AuthCallbacks.OnError onError = invocationOnMock.getArgument(4);
            onError.execute(new RuntimeException("Connection error"));
            return null;
        }).when(authService).authenticatePlayer(anyString(), anyString(), any(), any(), any());

        boolean result = loginCommand.onCommand(mockPlayer, mockCommand, "login", args);
        verify(mockPlayer).sendMessage(contains("Error trying to connect to the server"));
        assertTrue(result);
    }
}
