package kz.mergen.kursvalut.Models;

import android.location.Location;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by arman on 30.09.17.
 */

public class PunktModel {
    private String exchanger_name;
    private String currencys_json;
    private String work_time;
    private String address;
    private String telnumber;
    private double latitude;
    private double longitude;
    public String date_change;




    public PunktModel(String exchanger_name, String currencys_json, String work_time, String address, String telnumber, double latitude, double longitude, String date_change) {
        this.exchanger_name = exchanger_name;
        this.currencys_json = currencys_json;
        this.work_time = work_time;
        this.address = address;
        this.telnumber = telnumber;
        this.date_change = date_change;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getExchanger_name() {
        return exchanger_name;
    }

    public String getDate_change() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.parseLong(date_change+"000")));
        return calendar.get(Calendar.DAY_OF_MONTH)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.YEAR)+"г.";
    }

    public String getCurrencys(){
        return currencys_json;
    }

    public String getWork_time() {
        return work_time;
    }

    public String getAddress() {
        return address;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public String getCurrencys_json() {
        return currencys_json;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
