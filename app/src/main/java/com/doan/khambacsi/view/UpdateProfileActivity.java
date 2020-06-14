package com.doan.khambacsi.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan.khambacsi.R;
import com.doan.khambacsi.apisetup.DataService;
import com.doan.khambacsi.apisetup.RetrofitConfig;
import com.doan.khambacsi.model.Account;
import com.doan.khambacsi.utils.AccountUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    UpdateProfileActivity extends AppCompatActivity {

    private EditText edtName,edtGender,edtBirthday,edtCmnd,edtCountry,edtNation,edtPosition;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initView();
        initData();
    }

    private void initData() {
        account = AccountUtils.getInstance().getAccount();

        edtName.setText(account.getName());
        edtGender.setText(account.getGender());
        edtBirthday.setText(account.getBirthday());
        edtCmnd.setText(account.getCmnd());
        edtCountry.setText(account.getCountry());
        edtNation.setText(account.getNation());
        edtPosition.setText(account.getPosition());
    }

    private void initView() {
        edtName         = findViewById(R.id.edtName);
        edtGender       = findViewById(R.id.edtGender);
        edtBirthday     = findViewById(R.id.edtBirthday);
        edtCmnd         = findViewById(R.id.edtCmnd);
        edtCountry      = findViewById(R.id.edtCountry);
        edtNation       = findViewById(R.id.edtNation);
        edtPosition     = findViewById(R.id.edtPosition);
    }

    public void onClickUpdate(View view) {
        final String name     = edtName.getText().toString();
        final String gender   = edtGender.getText().toString();
        final String birthday = edtBirthday.getText().toString();
        final String cmnd     = edtCmnd.getText().toString();
        final String country  = edtCountry.getText().toString();
        final String nation   = edtNation.getText().toString();
        final String position = edtPosition.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng đền tên", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(gender)){
            Toast.makeText(this, "Vui lòng đền giới tính", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(birthday)){
            Toast.makeText(this, "Vui lòng đền ngày sinh", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(cmnd)){
            Toast.makeText(this, "Vui lòng đền chứng minh nhân dân", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(country)){
            Toast.makeText(this, "Vui lòng đền quốc tịch", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(nation)){
            Toast.makeText(this, "Vui lòng đền dân tộc", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(position)){
            Toast.makeText(this, "Vui lòng đền địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<String> call = RetrofitConfig.getInstance().create(DataService.class).updateAccount(
                account.getPhoneNumber(),
                name,
                gender,
                birthday,
                cmnd,
                country,
                nation,
                position
        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    if (response.body() != null){
                        if (response.body().equals("Success")){
                            account.setName(name);
                            account.setGender(gender);
                            account.setBirthday(birthday);
                            account.setCmnd(cmnd);
                            account.setCountry(country);
                            account.setNation(nation);
                            account.setPosition(position);

                            AccountUtils.getInstance().setAccount(account);

                            Toast.makeText(UpdateProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
