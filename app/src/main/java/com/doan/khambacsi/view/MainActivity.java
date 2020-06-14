package com.doan.khambacsi.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doan.khambacsi.R;
import com.doan.khambacsi.adapter.BenhvienAdapter;
import com.doan.khambacsi.model.Account;
import com.doan.khambacsi.model.Hospital;
import com.doan.khambacsi.utils.AccountUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQ = 211;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView imgAvatar;
    private TextView txtName,txtPhoneNumber;
    private RecyclerView recyclerHopital;
    private ArrayList<Hospital> arrayHospital = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        initView();
        initData();
        initRecycler();
        addEvents();
    }

    private void addEvents() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn avatar"), GALLERY_REQ);
            }
        });
    }

    private void initRecycler() {
        recyclerHopital.setHasFixedSize(true);
        recyclerHopital.setLayoutManager(new LinearLayoutManager(this));
        BenhvienAdapter hospitalAdapter = new BenhvienAdapter(arrayHospital);
        recyclerHopital.setAdapter(hospitalAdapter);
    }

    private void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        Account account = AccountUtils.getInstance().getAccount();
        txtName.setText(account.getName());
        txtPhoneNumber.setText(account.getPhoneNumber());

        arrayHospital.addAll(getHospitalDemo());

        Glide.with(this)
                .load(AccountUtils.getInstance().getAccount().getAvatar())
                .error(R.drawable.ic_logo_256dp)
                .into(imgAvatar);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout    = findViewById(R.id.drawerLayout);
        navigationView  = findViewById(R.id.navigationView);
        recyclerHopital = findViewById(R.id.recyclerHopital);

        txtName = navigationView.getHeaderView(0).findViewById(R.id.txtName);
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        txtPhoneNumber = navigationView.getHeaderView(0).findViewById(R.id.txtPhoneNumber);
    }

    public void onClickUpdateFile(MenuItem menuItem){
        startActivity(new Intent(this,UpdateProfileActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onClickMedicalBill(MenuItem menuItem){
        startActivity(new Intent(this, Phieukhambenh.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onClickLogout(MenuItem menuItem){
        AccountUtils.getInstance().logout();

        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            Glide.with(this)
                    .load(uri)
                    .into(imgAvatar);

            Account account = AccountUtils.getInstance().getAccount();
            account.setAvatar(uri.toString());

            AccountUtils.getInstance().setAccount(account);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Hospital> getHospitalDemo(){

        ArrayList<Hospital> arrayHospital = new ArrayList<>();

        Hospital hospital = new Hospital();
        hospital.setHotline("19008198");
        hospital.setName("Bênh viện Da Liễu");
        hospital.setPosition("Huyện Củ Chi");
        hospital.setImage("https://hellobacsi.com/wp-content/uploads/2018/07/di-kham-tai-benh-vien-da-lieu-tphcm.jpg");
        hospital.setTimeStart("8 Am");
        hospital.setTimeEnd("6 Pm");

        arrayHospital.add(hospital);


        return arrayHospital;
    }
}
