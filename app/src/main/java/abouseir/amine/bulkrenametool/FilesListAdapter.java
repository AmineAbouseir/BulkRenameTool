package abouseir.amine.bulkrenametool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Adapter Class that extends {@link BaseAdapter} that is
 * used to populate {@link ListView} with file info.
 */
public class FilesListAdapter extends BaseAdapter {
    private String[] list1;
    private String[] list2;
    private Context context;

    public FilesListAdapter(String[] list1, String[] list2, Context context) {
        this.list1 = list1;
        this.list2 = list2;
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
