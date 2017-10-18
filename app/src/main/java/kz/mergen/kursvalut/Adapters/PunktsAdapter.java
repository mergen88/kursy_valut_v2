package kz.mergen.kursvalut.Adapters;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.Models.KursLocation;
import kz.mergen.kursvalut.Models.PunktModel;
import kz.mergen.kursvalut.R;
import kz.mergen.kursvalut.Ui.MapsActivity;
import kz.mergen.kursvalut.Utils.ParserUtils;

/**
 * Created by arman on 30.09.17.
 */

public class PunktsAdapter extends ArrayAdapter<PunktModel> {

    private ViewHolder holder;
    private ArrayList<PunktModel> punktModels;
    private LayoutInflater layoutInflater;
    public PunktsAdapter(Context context, ArrayList<PunktModel> punktModels){
        super(context, R.layout.punkt_item);
        this.punktModels = punktModels;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public void loadNewData(ArrayList<PunktModel> punktModels){
        this.punktModels = punktModels;
        notifyDataSetChanged();
    }
    public void addData(ArrayList<PunktModel> punktModels){
        this.punktModels.addAll(punktModels);
        notifyDataSetChanged();
    }
    @Nullable
    @Override
    public PunktModel getItem(int position) {
        return punktModels.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.punkt_item,parent,false);
            holder.name_item = (TextView)convertView.findViewById(R.id.punkt_name_item);
            holder.address_item = (TextView)convertView.findViewById(R.id.punkt_address_item);
            holder.distance_view = (TextView)convertView.findViewById(R.id.distance_view);
            holder.currency_pager = (ViewPager)convertView.findViewById(R.id.currency_pager);
            holder.address_item.setSelected(true);
            holder.name_item.setText(getItem(position).getExchanger_name());
            holder.address_item.setText(getItem(position).getAddress());

            CurrencyPagerAdapter currencyPagerAdapter = new CurrencyPagerAdapter(getContext(), App.getInstance().parserUtils.getCurrencyModelsKursKZ(getItem(position).getCurrencys()));
            holder.currency_pager.setAdapter(currencyPagerAdapter);
            Location location = new Location("");
            location.setLongitude(76.793991);
            location.setLatitude(43.171860);
            //Location location = App.getInstance().settingsHelper.getMyPos(getContext());
        if(location!=null){
            Location punkt_location = new Location("");
            punkt_location.setLatitude(getItem(position).getLatitude());
            punkt_location.setLongitude(getItem(position).getLongitude());
            holder.distance_view.setText((int)(punkt_location.distanceTo(location)/1000)+" км");
            holder.distance_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsActivity.showMap(getContext(),new KursLocation(getItem(position).getLatitude(),getItem(position).getLongitude(),getItem(position).getExchanger_name()));
                }
            });
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return punktModels.size();
    }

    public void sortByLocation(){
        Collections.sort(punktModels, new Comparator<PunktModel>() {
            @Override
            public int compare(PunktModel o1, PunktModel o2) {
                Location location = new Location("");
                location.setLongitude(76.793991);
                location.setLatitude(43.171860);
                Location l1 = App.getInstance().getLocation("",o1.getLatitude(),o1.getLongitude());
                Location l2 = App.getInstance().getLocation("",o2.getLatitude(),o2.getLongitude());
                return l1.distanceTo(location)>l2.distanceTo(location) ? 1 : l1.distanceTo(location)==l2.distanceTo(location) ? 0 : -1;
            }
        });
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView name_item;
        public TextView address_item;
        public ViewPager currency_pager;
        public TextView distance_view;
    }
}
