package sg.edu.rp.c346.id19029489.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    TextView tvDate, tvBMI, tvBMILine;
    Button btnCal, btnReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        tvDate = findViewById(R.id.textViewShowDate);
        tvBMI = findViewById(R.id.textViewShowBMI);
        tvBMILine = findViewById(R.id.textViewBMIstatement);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float bmi = weight/(height*height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText(datetime);
                tvBMI.setText(String.format("%.3f",bmi));
                etHeight.setText("");
                etWeight.setText("");

                if(bmi == 0.0){
                    tvBMILine.setText("");
                }
                else if(bmi < 18.5){
                    tvBMILine.setText("You are Underweight");
                }
                else if(bmi >= 18.5 && bmi < 25){
                    tvBMILine.setText("Your BMI is Normal");
                }
                else if(bmi >= 25 && bmi < 30){
                    tvBMILine.setText("You are Overweight");
                }
                else if(bmi >= 30){
                    tvBMILine.setText("You are Obese");
                }
                else{
                    tvBMILine.setText("Error in Calculation");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText("");
                tvBMI.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor edit = prefs.edit();
                edit.clear();
                edit.commit();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        String strDateTime = tvDate.getText().toString();
        float strBMI = Float.parseFloat(tvBMI.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("datetime", strDateTime);
        prefEdit.putFloat("BMI", strBMI);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String datetime = prefs.getString("datetime", "");
        float bmi = prefs.getFloat("BMI", 0.0f);

        tvDate.setText(datetime);
        tvBMI.setText(bmi + "");
    }
}