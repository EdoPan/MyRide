package it.dsmt.myRide.model;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MasterNode {
    private final static String CHAT_REQUESTS_URL = "http://127.0.0.1:8023/chats";
    public static String getChatRequests() throws RuntimeException {
        try {
            // URL del server Erlang
            URL url = new URL(CHAT_REQUESTS_URL);  // Sostituisci con il tuo endpoint Erlang

            // Apri una connessione HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Imposta il metodo di richiesta e l'impostazione per una richiesta di output
            connection.setRequestMethod("GET");

            // Leggi la risposta dal server Erlang
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

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
                // Chiudere la connessione
                connection.disconnect();
             }
            // Stampare o elaborare il corpo della risposta qui
            System.out.println("Response Body: " + response.toString());
            return response.toString();
            // Puoi leggere la risposta dal server utilizzando connection.getInputStream() se necessario

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

