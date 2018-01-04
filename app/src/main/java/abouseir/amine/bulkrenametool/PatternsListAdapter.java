package abouseir.amine.bulkrenametool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import abouseir.amine.bulkrenametool.Patterns.Pattern;

/**
 * Adapter Class that extends {@link BaseAdapter} that is
 * used to populate {@link ListView} with file info.
 */
public class PatternsListAdapter extends BaseAdapter {
    private String[] list1;
    private String[] list2;
    private Context context;

    public PatternsListAdapter(ArrayList<Pattern> patterns, Context context) {
        int n = patterns.size();
        this.list1 = new String[n];
        this.list2 = new String[n];
        for (int i = 0; i < n; i++) {
            list1[i] = patterns.get(i).getName();
            list2[i] = patterns.get(i).getDescription();
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return list2.length;
    }

    @Override
    public Object getItem(int i) {
        return list2[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    @SuppressWarnings("deprecation")
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.file_list_item, viewGroup, false);
        TextView name = view.findViewById(R.id.fname);
        TextView path = view.findViewById(R.id.fpath);
        ImageView type_icon = view.findViewById(R.id.image_type);
        type_icon.setImageResource(R.mipmap.ic_type_file);
        name.setText(list1[i]);
        path.setText(list2[i]);
        return view;
    }
}
