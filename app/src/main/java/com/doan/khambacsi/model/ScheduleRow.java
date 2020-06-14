package com.doan.khambacsi.model;

public class ScheduleRow {
    private String time;
    private boolean checked;

    public ScheduleRow(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
