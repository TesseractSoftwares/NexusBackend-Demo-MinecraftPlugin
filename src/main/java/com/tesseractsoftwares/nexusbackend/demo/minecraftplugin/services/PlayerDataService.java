package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services;

import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;

public class PlayerDataService {

    private final GraphQLClient graphQLClient;

    public PlayerDataService(GraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
    }

    public String getPlayerData(String playerId) {
        String query = "{ getPlayerInfo(playerId: \"" + playerId + "\") { level coins } }";
        return graphQLClient.executeGraphQLQuery(query);
    }
}
