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
import com.doan.khambacsi.utils.AccountUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneNumber,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLogin();
        initView();
        initData();
    }

    private void initLogin() {
        if (AccountUtils.getInstance().getAccount() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        Account account = AccountUtils.getInstance().getAccount();

        if (account == null){
            return;
        }

        edtPhoneNumber.setText(account.getPhoneNumber());
    }

    private void initView() {
        edtPhoneNumber  = findViewById(R.id.edtPhoneNumber);
        edtPassword     = findViewById(R.id.edtPassword);
    }

    public void onClickLogin(View view) {
        String phoneNumber = edtPhoneNumber.getText().toString();
        final String password = edtPassword.getText().toString();

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
            Toast.makeText(this, "Mật khẩu phải có tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Account> call = RetrofitConfig.getInstance().create(DataService.class).getAccount(phoneNumber);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response != null){

                    // response là nội dung trả về, response.body nó sẽ ép kiểu cái chuỗi trả về thành đối tượng java cho mình
                    Account account = response.body();

                    // Tài khoản đã tồn tại.

                    if (account.getDeviceId() == null){
                        startVerify(account);
                        return;
                    }

                    // Kiểm tra mật khẩu có trùng hay không
                    if (account.getPassword().equals(password)){
                        // Kiểm tra id thiết bị hiện tại với id thiết bị trên server có trùng nhau hay không
                        // Nếu trùng nhau thì sẽ ko cần xác thực OTP và ngược lại
                        if (account.getDeviceId().equals(AppConfig.getDeviceID())){
                            // Lưu đối tượng account
                            AccountUtils.getInstance().setAccount(account);

                            // Mở màn hình chính
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            // Chuyển đến màn hình xác thực
                            startVerify(account);
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                edtPassword.setText("");
            }
        });
    }

    private void startVerify(Account account) {
        Intent intent = new Intent(this, XacthucActivity.class);
        intent.putExtra(Constants.KEY_ACCOUNT, account);
        intent.putExtra(Constants.KEY_TYPE,Constants.TYPE_LOGIN);
        startActivity(intent);
    }

    public void onClickRegister(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
