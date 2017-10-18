package kz.mergen.kursvalut.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kz.mergen.kursvalut.Models.CityModel;
import kz.mergen.kursvalut.R;

/**
 * Created by arman on 30.09.17.
 */

public class CitysAdapter extends BaseAdapter {
    private String[] citys;
    private Context context;
    private LayoutInflater layoutInflater;
    public CitysAdapter(Context context,String[] citys){
        this.context = context;
        this.citys = citys;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return citys.length;
    }

    @Override
    public String getItem(int position) {
        return citys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if (citys[position].contains("%")){
            return false;
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(citys[position].contains("%")){
            convertView = layoutInflater.inflate(R.layout.country_view_header,parent,false);
            TextView textView = (TextView)convertView.findViewById(R.id.country_header);
            textView.setText(citys[position].replace("%",""));
        } else {
            convertView = layoutInflater.inflate(R.layout.city_view,parent,false);
            TextView textView = (TextView)convertView.findViewById(R.id.city_text);
            textView.setText(citys[position]);
        }


        return convertView;
    }
}
