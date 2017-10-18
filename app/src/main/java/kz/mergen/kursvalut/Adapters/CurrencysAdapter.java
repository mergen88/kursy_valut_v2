package kz.mergen.kursvalut.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import kz.mergen.kursvalut.Models.CurrencyModel;
import kz.mergen.kursvalut.R;

/**
 * Created by arman on 02.10.17.
 */

public class CurrencysAdapter extends RecyclerView.Adapter<CurrencysAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CurrencyModel> currencyModels;
    private String dateChange;
    public CurrencysAdapter(ArrayList<CurrencyModel> currencyModels, String dateChange){
        this.currencyModels = currencyModels;
        this.dateChange = dateChange;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.currency_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyModel currencyModel = currencyModels.get(position);
        holder.currency_name.setText(currencyModel.getCurrency_name());
        holder.currency_pokupka.setText(currencyModel.getCurrency_pokupka()+" тг.");
        holder.currency_prodaja.setText(currencyModel.getCurrency_prodaja()+" тг.");
        holder.currency_date.setText(holder.currency_date.getText().toString().replace("%",dateChange));
    }

    @Override
    public int getItemCount() {
        return currencyModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView currency_name;
        public TextView currency_pokupka;
        public TextView currency_prodaja;
        public TextView currency_date;
        public ViewHolder(View itemView) {
            super(itemView);
            currency_name = (TextView)itemView.findViewById(R.id.currency_name);
            currency_pokupka = (TextView)itemView.findViewById(R.id.currency_pokupka);
            currency_prodaja = (TextView)itemView.findViewById(R.id.currency_prodaja);
            currency_date = (TextView)itemView.findViewById(R.id.date_change);
        }
    }
}
