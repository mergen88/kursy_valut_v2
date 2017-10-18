package kz.mergen.kursvalut.Ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import kz.mergen.kursvalut.Adapters.PunktsAdapter;
import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.R;

public class PunktsFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView data_list;
    private PunktsAdapter punktsAdapter;
    public boolean isShown;
    private boolean isScrolled = true;
    private int offset = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isShown = true;
    }

    public void sortByLocation(){
        if(punktsAdapter!=null){
            punktsAdapter.sortByLocation();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        isShown = false;
    }

    @Override
    public void initData() {
       // currencyAdapter.loadNewData(App.getInstance().databaseHelper.getPunkts(getContext()));
        punktsAdapter.notifyDataSetChanged();
    }
    private void loadNewData(){
        offset+=5;
        if(punktsAdapter!=null){
            punktsAdapter.addData(App.getInstance().databaseHelper.getPunkts(getContext(),100,offset));
            //isScrolled = true;
        }
    }
    private void initPunkts(){
        punktsAdapter = new PunktsAdapter(getContext(), App.getInstance().databaseHelper.getPunkts(getContext(),100,offset));
        data_list.setAdapter(punktsAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_data, container, false);
        data_list = (ListView) view.findViewById(R.id.data_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            data_list.setNestedScrollingEnabled(false);
        }
        data_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(isScrolled&&totalItemCount!=0) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        isScrolled = false;
                        //loadNewData();
                    }
                }

            }
        });
        initPunkts();
        return view;
    }




}
