package it.dsmt.myRide.model;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MasterNode {
    private final static String url = System.getenv("CHAT_REQUESTS_URL");
    public static String getChatRequests() throws RuntimeException {
        try {
            URL url = new URL(MasterNode.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP Response Code: " + responseCode);
            }
            
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } finally {
                connection.disconnect();
             }
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}