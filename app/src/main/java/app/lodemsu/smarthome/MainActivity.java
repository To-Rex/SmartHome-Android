package app.lodemsu.smarthome;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Switch switch1 = findViewById(R.id.switch1);
        //firebase realtime database home in on = value 1
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDatabase.child("home").child("on").setValue(1);
            } else {
                mDatabase.child("home").child("on").setValue(0);
            }
        });
    }
}
