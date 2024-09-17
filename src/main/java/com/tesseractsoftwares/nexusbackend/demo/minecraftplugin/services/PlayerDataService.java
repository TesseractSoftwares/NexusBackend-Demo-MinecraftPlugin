package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesseractsoftwares.nexusbackend.sdkjava.GraphQLClient;

import java.io.IOException;
import java.util.List;

public class PlayerDataService {

    private final GraphQLClient graphQLClient;
    private final ObjectMapper objectMapper;

    public PlayerDataService(GraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
        this.objectMapper = new ObjectMapper();
    }

    private String buildQuery(String email, List<String> fields) {
        StringBuilder fieldsPart = new StringBuilder();
        for (String field : fields) {
            fieldsPart.append(field).append(" ");
        }

        String query = " { userInfo(email: \"" + email + "\") { " + fieldsPart.toString() + "} }";
        return query;
    }

    private String formatResponse(String email, String response, List<String> fields) {
        StringBuilder sb = new StringBuilder();
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            JsonNode userInfo = jsonResponse.path("data").path("userInfo");

            if (userInfo.isMissingNode() || userInfo.isNull()) {
                return "Error: No data found for the email: " + email;
            }

            sb.append("The data of ").append(email).append(": ");

            for (String field : fields) {
                JsonNode fieldValue = userInfo.path(field);
                if (fieldValue.isMissingNode() || fieldValue.isNull()) {
                    sb.append(field).append(": ").append("N/A").append(", ");
                } else {
                    sb.append(field).append(": ").append(fieldValue.asText()).append(", ");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString().trim().replaceAll(", $", "");
    }

    public String getPlayerData(String email, List<String> fields) {
        String query = buildQuery(email, fields);
        String response = graphQLClient.executeGraphQLQuery(query);

        return formatResponse(email, response, fields);
    }
}
