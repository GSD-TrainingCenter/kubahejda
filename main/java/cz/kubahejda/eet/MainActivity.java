package cz.kubahejda.eet;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
//import org.apache.commons.codec.binary.Hex;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cz.kubahejda.eet.utils.Encryption;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "Bar12345Bar12345";

    public static boolean loggedIn = false;

    public static List<String> codes = new ArrayList<String>();

    public static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (!loggedIn)
            setContentView(R.layout.userloginlayout);
        else {
            setContentView(R.layout.activity_main);
        }
    }

    public void onButtonLogInClicked(View view) {
        String result = "";

        String username = ((EditText) findViewById(R.id.textFieldUserName)).getText().toString();
        String password = ((EditText) findViewById(R.id.textFieldPassword)).getText().toString();
        //TableLayout table = ((TableLayout) findViewById(R.id.tableView));


        if (logIn(username, password)) {
            setContentView(R.layout.activity_main);
            currentUser = username;
            loggedIn = true;
        } else {
            Toast.makeText(getApplicationContext(), "Nesprávné uživatelské jméno nebo heslo.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String returnValue = data.getStringExtra("code");
                    TextView text = (TextView) findViewById(R.id.textView2);
                    codes.add(returnValue);
                    String[] values = new String[codes.size()];
                    codes.toArray(values);
                    ListView list = (ListView)findViewById(R.id.list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, values);
                    list.setAdapter(adapter);
                    ((ViewGroup) text.getParent()).removeView(text);
                }
                break;
            }
        }
    }


    public boolean logIn(String username, String password) {
        String url = ":8080/server/authenticate";
        return ServerConnector.getResponseFromServer(url + "?username=" + username + "&password=" + Encryption.encryptMessage(password, KEY)).compareTo("true") == 0 ? true : false;
    }

    public static String makePayment(double value) {
        String result = "";
        String url = ":8080/server/makeTransaction?username=" + currentUser + "&value="+ value;
        result = ServerConnector.getResponseFromServer(url);
        return (result.split(";")[0]);
        //codes.add(1);
    }

    public void onButtonSendClicked(View view) {
        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
        startActivityForResult(intent, 1);
    }


}

