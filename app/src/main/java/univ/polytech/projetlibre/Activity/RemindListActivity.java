package univ.polytech.projetlibre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;

import univ.polytech.projetlibre.R;

public class RemindListActivity extends Activity {

    private FloatingActionButton FAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);

        FAB = findViewById(R.id.floatingActionButton);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindListActivity.this,AddRemindActivity.class);
                startActivity(intent);
            }
        });






    }

}
