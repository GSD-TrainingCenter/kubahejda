package cz.kubahejda.eet;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
    }

    public void onButtonSendClicked(View view) {

        TextView editText = (TextView) findViewById(R.id.textView2);
        String result = "";
        String url = "http://10.60.3.50/eet/php-eet/simple.php";
        try {
            URL urlObj = new URL(url);
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
        editText.setText(result);
        Snackbar.make(view, "hotovo", Snackbar.LENGTH_LONG).show();

    }
}
