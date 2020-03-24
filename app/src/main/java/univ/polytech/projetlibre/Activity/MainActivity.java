package univ.polytech.projetlibre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.io.Serializable;

import univ.polytech.projetlibre.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonReminder;
    private Button buttonMedicine;
    private Button buttonQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        buttonReminder = findViewById(R.id.button1);
        buttonMedicine = findViewById(R.id.button2);
        buttonQuit = findViewById(R.id.button3);

        buttonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RemindListActivity.class);
                startActivity(intent);
            }
        });

        buttonMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MedicineListActivity.class);
                startActivity(intent);

            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(0);
            }
        });




    }


}
