package com.doan.khambacsi.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan.khambacsi.R;

public class Phieukhambenh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieukhambenh);
    }

    public void onClickPrint(View view) {
        Toast.makeText(this, "In thành công", Toast.LENGTH_SHORT).show();
    }
}
