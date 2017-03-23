package cz.kubahejda.eet;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Kuba on 23.3.2017.
 */

public class ServerConnector {
    public static final String serverUrl = "http://192.168.0.101";

    public static String getResponseFromServer(String location) {
        String result = "";
        try {
            URL urlObj = new URL(serverUrl + location);
            URLConnection serverConnection = urlObj.openConnection();

            String data = URLEncoder.encode("yourdata", "UTF-8");
            serverConnection.setDoOutput(true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(serverConnection.getOutputStream());
            streamWriter.write(data);
            streamWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            String line = "";
            result = "";

            Log.d("ERROR","pokus");
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            streamWriter.flush();
            streamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
