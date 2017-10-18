package kz.mergen.kursvalut.Ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kz.mergen.kursvalut.Adapters.BankAdapter;
import kz.mergen.kursvalut.R;

public class BankFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView data_list;
    private BankAdapter bankAdapter;
    public boolean isShown;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initData() {
        bankAdapter.loadNewData(app.databaseHelper.getBank(getContext()));
        bankAdapter.notifyDataSetChanged();
    }
    private void initBank(){
        bankAdapter = new BankAdapter(getContext(),app.databaseHelper.getBank(getContext()));
        data_list.setAdapter(bankAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isShown = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isShown = false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        data_list = (ListView)view.findViewById(R.id.data_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            data_list.setNestedScrollingEnabled(true);
        }
        initBank();
        return view;
    }

}
