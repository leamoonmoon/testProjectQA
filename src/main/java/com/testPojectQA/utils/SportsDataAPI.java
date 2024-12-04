package com.testPojectQA.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SportsDataAPI {
    private static final String API_URL = "https://api.sportsdata.io/v3/nba/scores/json/Players/DAL?key=58957574730c4ee1b809da2f53525997";

    public static Map<String, Integer> getPlayerNameToIdMap() {
        Map<String, Integer> playerMap = new HashMap<>();
        try {
            // send GET request to the API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                System.err.println("Error fetching data from API, status code: " + statusCode);
                return playerMap;
            }
            // read the response
            InputStream responseStream = connection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseStream);

            // parse players
            for (JsonNode playerNode : rootNode) {
                JsonNode statusNode = playerNode.get("Status");
                if (statusNode != null && "Active".equals(statusNode.asText())) {
                    JsonNode nbaDotComIdNode = playerNode.get("NbaDotComPlayerID");
                    JsonNode firstNameNode = playerNode.get("FirstName");
                    JsonNode lastNameNode = playerNode.get("LastName");

                    if (nbaDotComIdNode != null && firstNameNode != null && lastNameNode != null) {
                        int nbaDotComId = nbaDotComIdNode.asInt();
                        String firstName = firstNameNode.asText().replaceAll("\\.", "").toLowerCase();
                        String lastName = lastNameNode.asText().replaceAll("\\.", "").toLowerCase().replace(" ", "-");
                        String fullName = firstName + "-" + lastName;
                        playerMap.put(fullName, nbaDotComId);
                    } else {
                        System.err.println("Warning: Missing required data for a player");
                    }
                } else {
                    System.err.println("Warning: Inactive player or missing status in response");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerMap;
    }
}
