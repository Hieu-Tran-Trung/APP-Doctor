package com.doan.khambacsi.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan.khambacsi.R;
import com.doan.khambacsi.apisetup.DataService;
import com.doan.khambacsi.apisetup.RetrofitConfig;
import com.doan.khambacsi.config.AppConfig;
import com.doan.khambacsi.config.Constants;
import com.doan.khambacsi.model.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtPhoneNumber,edtPassword,edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        edtPhoneNumber  = findViewById(R.id.edtPhoneNumber);
        edtPassword     = findViewById(R.id.edtPassword);
        edtName         = findViewById(R.id.edtName);
    }

    public void onClickRegister(View view) {
        final String phoneNumber  = edtPhoneNumber.getText().toString();
        final String password     = edtPassword.getText().toString();
        final String name         = edtName.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Bạn chưa điền số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.length() != 10){
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Bạn chưa điền mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6){
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Bạn chưa điền họ tên", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Account> call = RetrofitConfig.getInstance().create(DataService.class).getAccount(phoneNumber);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response != null){
                    Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Account account = new Account();
                account.setPhoneNumber(phoneNumber);
                account.setPassword(password);
                account.setDeviceId(AppConfig.getDeviceID());
                account.setName(name);

                Intent intent = new Intent(getApplicationContext(), XacthucActivity.class);
                intent.putExtra(Constants.KEY_ACCOUNT,account);
                intent.putExtra(Constants.KEY_TYPE,Constants.TYPE_REGISTER);
                startActivity(intent);
            }
        });
    }
}
