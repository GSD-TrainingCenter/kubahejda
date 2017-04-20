package cz.kubahejda.eet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Nová platba");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.makePayment(394.2);

                EditText value = (EditText)findViewById(R.id.value);
                EditText dph = (EditText)findViewById(R.id.dph);
                EditText machine = (EditText)findViewById(R.id.machine);
                EditText place = (EditText)findViewById(R.id.place);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("code", "Cena: "+ value.getText() + "Kč, kód: " + MainActivity.makePayment(Double.parseDouble(value.getText().toString())));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
