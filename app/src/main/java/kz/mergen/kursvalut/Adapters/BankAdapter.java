package kz.mergen.kursvalut.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kz.mergen.kursvalut.Models.BankModel;
import kz.mergen.kursvalut.R;

/**
 * Created by arman on 12.10.17.
 */

public class BankAdapter extends ArrayAdapter<BankModel>{

    private ArrayList<BankModel> bankModels;
    private LayoutInflater layoutInflater;
    private ViewHolder holder;

    public BankAdapter(@NonNull Context context, ArrayList<BankModel> bankModels) {
        super(context, R.layout.bank_item);
        this.bankModels = bankModels;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public void loadNewData(ArrayList<BankModel> bankModels){
        this.bankModels = bankModels;
    }
    @Nullable
    @Override
    public BankModel getItem(int position) {
        return bankModels.get(position);
    }

    @Override
    public int getCount() {
        return bankModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.bank_item, parent, false);
        holder = new ViewHolder();
        holder.currency_name = (TextView)convertView.findViewById(R.id.currency_view);
        holder.currency_money = (TextView)convertView.findViewById(R.id.sale_view);
        holder.currency_changed = (TextView)convertView.findViewById(R.id.change_view);
        holder.date_changed = (TextView)convertView.findViewById(R.id.date_view);

        holder.currency_name.setText(getItem(position).getValuta());
        holder.currency_money.setText(holder.currency_money.getText().toString().replace("%",getItem(position).getMoney()));
        if(getItem(position).getMoney_change().contains("+")){
            holder.currency_changed.setTextColor(Color.RED);
        } else {
            holder.currency_changed.setTextColor(Color.GREEN);
        }
        holder.currency_changed.setText(getItem(position).getMoney_change());
        holder.date_changed.setText(holder.date_changed.getText().toString().replace("%",getItem(position).getDate_change()));
        return convertView;
    }

    static class ViewHolder{
        TextView currency_money;
        TextView currency_changed;
        TextView date_changed;
        TextView currency_name;
    }
}
