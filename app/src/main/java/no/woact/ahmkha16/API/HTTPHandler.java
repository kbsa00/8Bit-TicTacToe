package no.woact.ahmkha16.API;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Simple HTTPHandler class that takes care of the HTTPRequest and then
 * starts to parse the JSon object.
 *
 * Created by Khalid B. Said
 */

public class HTTPHandler {

    public HTTPHandler(){

    }

    /**
     * This method proceeds to connect to the website and read the API.
     * @param requestedURL
     * @return the JSonResponse
     */
    public String createHTTPRequest(String requestedURL){

        String jsonResponse = null;

        try{
            URL url = new URL(requestedURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            jsonResponse = readStream(inputStream);
        }catch (MalformedURLException ma){

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private String readStream(InputStream inputStream) {
        BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        try{
            String line;
            while ((line = read.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();
    }
}
