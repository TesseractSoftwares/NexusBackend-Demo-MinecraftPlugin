package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services.PlayerDataService;
import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerDataServiceTest {

    private GraphQLClient graphQLClient;
    private PlayerDataService playerDataService;

    @BeforeEach
    void setUp() {
        graphQLClient = mock(GraphQLClient.class);

        playerDataService = new PlayerDataService(graphQLClient);
    }

    @Test
    void testGetPlayerDataSuccess() {
        String email = "player@example.com";
        List<String> fields = Arrays.asList("coins", "level");

        String graphqlResponse = "{ \"data\": { \"userInfo\": { \"coins\": 123, \"level\": 5 } } }";
        when(graphQLClient.executeGraphQLQuery(anyString())).thenReturn(graphqlResponse);

        String result = playerDataService.getPlayerData(email, fields);

        verify(graphQLClient).executeGraphQLQuery(contains("userInfo(email: \"player@example.com\")"));

        assertTrue(result.contains("coins: 123"));
        assertTrue(result.contains("level: 5"));
    }

    @Test
    void testGetPlayerDataInvalidField() {
        String email = "player@example.com";
        List<String> fields = List.of("invalidField");

        String graphqlResponse = "{ \"data\": { \"userInfo\": { } } }";
        when(graphQLClient.executeGraphQLQuery(anyString())).thenReturn(graphqlResponse);

        String result = playerDataService.getPlayerData(email, fields);

        verify(graphQLClient).executeGraphQLQuery(contains("userInfo(email: \"player@example.com\")"));

        assertTrue(result.contains("invalidField: "));
    }

    @Test
    void testGetPlayerDataGraphQLClientError() {
        String email = "player@example.com";
        List<String> fields = Arrays.asList("coins", "level");

        when(graphQLClient.executeGraphQLQuery(anyString())).thenThrow(new RuntimeException("GraphQL Error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> playerDataService.getPlayerData(email, fields));

        assertEquals("GraphQL Error", exception.getMessage());
    }
}
