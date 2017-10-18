package kz.mergen.kursvalut.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.mergen.kursvalut.App;
import kz.mergen.kursvalut.R;

/**
 * Created by arman on 12.10.17.
 */

public abstract class BaseFragment extends Fragment {

    protected App app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = App.getInstance();
    }

    public abstract void initData();





}
