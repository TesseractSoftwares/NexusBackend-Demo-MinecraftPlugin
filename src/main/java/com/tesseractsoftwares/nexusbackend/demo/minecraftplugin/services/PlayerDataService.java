package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services;

import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;

public class PlayerDataService {

    private final GraphQLClient graphQLClient;

    public PlayerDataService(GraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
    }

    public String getPlayerData(String email) {
        String query = " { userInfo(email: \"" + email + "\") { coins email level } }";
        String response = graphQLClient.executeGraphQLQuery(query);

        System.out.println("GraphQL response: " + response);

        return response;
    }
}
