package com.doan.khambacsi.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.khambacsi.R;
import com.doan.khambacsi.adapter.DatlichhenAdapter;
import com.doan.khambacsi.apisetup.DataService;
import com.doan.khambacsi.apisetup.RetrofitConfig;
import com.doan.khambacsi.config.Constants;
import com.doan.khambacsi.model.Hospital;
import com.doan.khambacsi.model.ScheduleRow;
import com.doan.khambacsi.utils.AccountUtils;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatlichhenActivity<MainActivity> extends AppCompatActivity {

    private Hospital hospital;
    private Toolbar toolbar;
    private Spinner spDoctor,spRoom;
    private RecyclerView recyclerSchedule;
    private DatePicker datePicker;
    private ArrayList<ScheduleRow> arraySchdule = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datlichhen);

        initView();
        initData();
        initRecycler();
        setupDatePicker();
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        // Lấy ra năm - tháng - ngày hiện tại
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH) + 1;
        int day = calendar.get(calendar.DAY_OF_MONTH);

        // Khởi tạo sự kiện lắng nghe khi DatePicker thay đổi
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(DatlichhenActivity.this, dayOfMonth+"-"+monthOfYear+"-"+year, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initRecycler() {
        recyclerSchedule.setHasFixedSize(true);
        recyclerSchedule.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));
        DatlichhenAdapter scheduleAdapter = new DatlichhenAdapter(arraySchdule);
        recyclerSchedule.setAdapter(scheduleAdapter);
    }

    private void initData() {
        hospital = (Hospital) getIntent().getSerializableExtra(Constants.KEY_HOSPITAL);
        addArrayTime();
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void addArrayTime() {
        arraySchdule.add(new ScheduleRow("08:00 - 09:00"));
        arraySchdule.add(new ScheduleRow("09:00 - 10:00"));
        arraySchdule.add(new ScheduleRow("10:00 - 11:00"));
        arraySchdule.add(new ScheduleRow("13:00 - 14:00"));
        arraySchdule.add(new ScheduleRow("14:00 - 15:00"));
        arraySchdule.add(new ScheduleRow("15:00 - 16:00"));
        arraySchdule.add(new ScheduleRow("16:00 - 17:00"));
    }

    private void initView() {
        spDoctor = findViewById(R.id.spDoctor);
        spRoom = findViewById(R.id.spRoom);
        toolbar = findViewById(R.id.toolbar);
        recyclerSchedule = findViewById(R.id.recyclerSchedule);
        datePicker = findViewById(R.id.datePicker);
    }

    public void onClickSchedule(View view) {
        String phoneNumber = AccountUtils.getInstance().getAccount().getPhoneNumber();
        String doctor = spDoctor.getSelectedItem().toString();
        String room = spRoom.getSelectedItem().toString();
        String time = getTimeSelected();
        String hospitalName = hospital.getName();
        String position = hospital.getPosition();

        if (TextUtils.isEmpty(time)){
            Toast.makeText(this, "Vui lòng chọn thời gian", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<String> call = RetrofitConfig.getInstance().create(DataService.class).addSchedule(phoneNumber,
                doctor,
                time,
                room,
                hospitalName,
                position);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    if (response.body() != null){
                        if (response.body().equals("Success")){
                            Toast.makeText(DatlichhenActivity.this, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(DatlichhenActivity.this, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(DatlichhenActivity.this, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(DatlichhenActivity.this, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DatlichhenActivity.this, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTimeSelected() {
        for (ScheduleRow scheduleRow : arraySchdule){
            if (scheduleRow.isChecked()){
                return scheduleRow.getTime();
            }
        }
        return null;
    }
}
