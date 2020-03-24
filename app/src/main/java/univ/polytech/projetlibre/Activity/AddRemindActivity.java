package univ.polytech.projetlibre.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import univ.polytech.projetlibre.R;

public class AddRemindActivity extends AppCompatActivity {

    private NumberPicker numberPickerhour;
    private NumberPicker numberPickerminute;
    private NumberPicker numberPickerdosagenumber;
    private Spinner spinnerdosageunit;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remind);

        numberPickerhour = findViewById(R.id.NumberPickerHour);
        numberPickerminute = findViewById(R.id.NumberPickerMinute);
        numberPickerdosagenumber = findViewById(R.id.NumberPickerDosageNumber);
        spinnerdosageunit = findViewById(R.id.spinnerDosageUnit);


        numberPickerhour.setMaxValue(23);
        numberPickerhour.setMinValue(0);
        numberPickerhour.setValue(0);

        numberPickerminute.setMaxValue(59);
        numberPickerminute.setMinValue(0);
        numberPickerminute.setValue(0);

        numberPickerdosagenumber.setMaxValue(1000);
        numberPickerdosagenumber.setMinValue(0);
        numberPickerdosagenumber.setValue(0);

        dataList = new ArrayList<String>();
        dataList.add("tablet");
        dataList.add("g");
        dataList.add("mg");
        dataList.add("ml");
        dataList.add("cup");
        dataList.add("IU");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerdosageunit.setAdapter(adapter);





        /*
        numberPickerhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String toast = "   newVal：" + newVal;
                Toast.makeText(AddRemindActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });

        numberPickerminute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String toast = "   newVal：" + newVal;
                Toast.makeText(AddRemindActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });*/







    }
}
