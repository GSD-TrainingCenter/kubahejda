package cz.kubahejda.eet;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import org.apache.commons.codec.binary.Hex;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cz.kubahejda.eet.utils.Encryption;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "Bar12345Bar12345";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.userloginlayout);


    }

    public void onButtonLogInClicked(View view) {        String result = "";

        String username = ((EditText) findViewById(R.id.textFieldUserName)).getText().toString();
        String password = ((EditText) findViewById(R.id.textFieldPassword)).getText().toString();
        if (logIn(username, password))
            setContentView(R.layout.activity_main);
        else
        {
            Toast.makeText(getApplicationContext(), "Nesprávné uživatelské jméno nebo heslo.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean logIn(String username, String password) {
        String url = ":8080/server/authenticate";
        return ServerConnector.getResponseFromServer(url + "?username=" + username + "&password=" + Encryption.encryptMessage(password, KEY)).compareTo("true") == 0 ? true : false;
    }

    public void onButtonSendClicked(View view) {

        TextView editText = (TextView) findViewById(R.id.textView2);
        String result = "";
        String url = "/eet/php-eet/simple.php";
        result = ServerConnector.getResponseFromServer(url);
        editText.setText(result);
        Snackbar.make(view, "hotovo", Snackbar.LENGTH_LONG).show();

    }


}

