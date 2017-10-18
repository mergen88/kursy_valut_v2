package kz.mergen.kursvalut.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kz.mergen.kursvalut.Models.CurrencyModel;
import kz.mergen.kursvalut.R;

/**
 * Created by arman on 19.10.17.
 */

public class CurrencyPagerAdapter extends PagerAdapter {

    private View view;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CurrencyModel> currencyModels;

    public CurrencyPagerAdapter(Context context, ArrayList<CurrencyModel> currencyModels) {
        this.context = context;
        this.currencyModels = currencyModels;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int m = currencyModels.size()%3;
        int s = currencyModels.size()/3;
        System.out.println("m = "+m);
        if(m==0){
            return s;
        } else {
            return s+1;
        }
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = inflater.inflate(R.layout.currency_list_item,container,false);
        ViewHolder holder = new ViewHolder();
        CurrencyModel currencyModel = null;

        holder.currency_1_1 = (TextView)view.findViewById(R.id.curview_1_1);
        holder.currency_1_2 = (TextView)view.findViewById(R.id.curview_1_2);
        holder.currency_2_1 = (TextView)view.findViewById(R.id.curview_2_1);
        holder.currency_2_2 = (TextView)view.findViewById(R.id.curview_2_2);
        holder.currency_3_1 = (TextView)view.findViewById(R.id.curview_3_1);
        holder.currency_3_2 = (TextView)view.findViewById(R.id.curview_3_2);
        holder.currency_name_1 = (TextView)view.findViewById(R.id.currency_name_1);
        holder.currency_name_2 = (TextView)view.findViewById(R.id.currency_name_2);
        holder.currency_name_3 = (TextView)view.findViewById(R.id.currency_name_3);


        if((position*3)<currencyModels.size()){
            holder.currency_name_1.setText(currencyModels.get((position*3)).getCurrency_name());
            holder.currency_1_1.setText(String.valueOf(currencyModels.get((position*3)).getCurrency_pokupka()));
            holder.currency_1_2.setText(String.valueOf(currencyModels.get((position*3)).getCurrency_prodaja()));
        }
        if((position*3+1)<currencyModels.size()){
            holder.currency_name_2.setText(currencyModels.get((position*3)+1).getCurrency_name());
            holder.currency_2_1.setText(String.valueOf(currencyModels.get((position*3)+1).getCurrency_pokupka()));
            holder.currency_2_2.setText(String.valueOf(currencyModels.get((position*3)+1).getCurrency_prodaja()));
        }
        if((position*3+2)<currencyModels.size()){
            holder.currency_name_3.setText(currencyModels.get((position*3)+2).getCurrency_name());
            holder.currency_3_1.setText(String.valueOf(currencyModels.get((position*3)+2).getCurrency_pokupka()));
            holder.currency_3_2.setText(String.valueOf(currencyModels.get((position*3)+2).getCurrency_prodaja()));
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((LinearLayout) object);
    }
    private static class ViewHolder{
        TextView currency_1_1;
        TextView currency_1_2;
        TextView currency_2_1;
        TextView currency_2_2;
        TextView currency_3_1;
        TextView currency_3_2;
        TextView currency_name_1;
        TextView currency_name_2;
        TextView currency_name_3;
    }
}
