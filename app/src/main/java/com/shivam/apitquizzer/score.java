package com.shivam.apitquizzer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class score extends AppCompatActivity {

    TextView scored,total;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scored=findViewById(R.id.tvscored);
        total=findViewById(R.id.tvtotal);
        done=findViewById(R.id.btndone);

        scored.setText(String.valueOf(getIntent().getIntExtra("Score",0)));
        total.setText("Out Of "+String.valueOf(getIntent().getIntExtra("Total",0)));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}