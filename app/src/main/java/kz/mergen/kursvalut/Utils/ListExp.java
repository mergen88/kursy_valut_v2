package kz.mergen.kursvalut.Utils;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

/**
 * Created by arman on 01.10.17.
 */

public class ListExp extends ExpandableListView {
    public ListExp(Context context) {
        super(context);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        super.setOnGroupCollapseListener(onGroupCollapseListener);
    }
}
