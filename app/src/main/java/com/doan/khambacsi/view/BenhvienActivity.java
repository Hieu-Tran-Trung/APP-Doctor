package com.doan.khambacsi.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.doan.khambacsi.R;
import com.doan.khambacsi.config.Constants;
import com.doan.khambacsi.model.Hospital;

public class BenhvienActivity extends AppCompatActivity {

    private ImageView imgImage;
    private TextView txtName,txtPosition,txtHotline,txtWorkTime;
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benhvien);

        initView();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        hospital = (Hospital) getIntent().getSerializableExtra(Constants.KEY_HOSPITAL);

        Glide.with(this)
                .load(hospital.getImage())
                .into(imgImage);

        txtName.setText(hospital.getName());
        txtHotline.setText("Hotline: " + hospital.getHotline());
        txtPosition.setText("Địa chỉ: " + hospital.getPosition());
        txtWorkTime.setText("Giờ làm việc: " + hospital.getTimeStart() + " - " + hospital.getTimeEnd());
    }

    private void initView() {
        imgImage = findViewById(R.id.imgImage);
        txtName = findViewById(R.id.txtName);
        txtPosition = findViewById(R.id.txtPosition);
        txtHotline = findViewById(R.id.txtHotline);
        txtWorkTime = findViewById(R.id.txtWorkTime);
    }

    public void onClickSchedule(View view) {
        Intent intent = new Intent(this, DatlichhenActivity.class);
        intent.putExtra(Constants.KEY_HOSPITAL,hospital);
        startActivity(intent);
    }
}
