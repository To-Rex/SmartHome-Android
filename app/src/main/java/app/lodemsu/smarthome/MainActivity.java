package app.lodemsu.smarthome;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Switch switch1 = findViewById(R.id.switch1);
        ImageView imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int on = dataSnapshot.child("home").child("on").getValue(Integer.class);
                int dim = dataSnapshot.child("home").child("dim").getValue(Integer.class);
                if (on == 1) {
                    switch1.setChecked(true);
                    switch1.setText("ON");
                    switch1.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    switch1.setChecked(false);
                    switch1.setText("OFF");
                    switch1.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
                if (dim == 1) {
                    imageView.setVisibility(ImageView.VISIBLE);
                } else {
                    imageView.setVisibility(ImageView.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDatabase.child("home").child("on").setValue(1);
            } else {
                mDatabase.child("home").child("on").setValue(0);
            }
        });

        //button popup menu
        button.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, button);
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.workson:
                        mDatabase.child("home").child("door").setValue(1);
                        return true;
                    case R.id.workoff:
                        mDatabase.child("home").child("door").setValue(2);
                        return true;
                    case R.id.autoon:
                        mDatabase.child("home").child("door").setValue(0);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

        button2.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, button2);
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.workson:
                        mDatabase.child("home").child("lamp").setValue(1);
                        return true;
                    case R.id.workoff:
                        mDatabase.child("home").child("lamp").setValue(2);
                        return true;
                    case R.id.autoon:
                        mDatabase.child("home").child("lamp").setValue(0);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });
    }
}
