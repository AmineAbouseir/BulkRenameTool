package abouseir.amine.bulkrenametool;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import abouseir.amine.bulkrenametool.Patterns.Pattern;


public class PreviewFragment {
    private Button save;
    private TextView message_preview_fragment;
    private ListView previewList;
    private String[] listName = new String[0];
    private String[] listPath = new String[0];
    private String[] listNewName = new String[0];
    private View view;
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;

    public View getView(LayoutInflater inflater, ViewGroup tabLayout, Context context, Activity activity, String[] listName, String[] listPath, ArrayList<Pattern> patterns) {
        View rootView = inflater.inflate(R.layout.fragment_preview, tabLayout, false);
        save = rootView.findViewById(R.id.save);
        message_preview_fragment = rootView.findViewById(R.id.message_preview_fragment);
        previewList = rootView.findViewById(R.id.previewList);
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(listName.clone());
        arrayList.add(listPath);
        arrayList.add(listName);
        for (Pattern pattern : patterns) {
            arrayList = pattern.apply(arrayList.get(0), arrayList.get(1), arrayList.get(2));
        }
        this.listNewName = arrayList.get(0);
        this.listPath = arrayList.get(1);
        this.listName = arrayList.get(2);
        if (this.listName.length > 0)
            message_preview_fragment.setText(context.getString(R.string.selected_files_message, listName.length));
        else {
            message_preview_fragment.setText("No file selected");
        }
        FilesListAdapter filesAdapter = new FilesListAdapter(this.listNewName, this.listName, context);
        previewList.setAdapter(filesAdapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChange();
            }
        });
        this.view = rootView;
        this.inflater = inflater;
        this.activity = activity;
        this.context = context;
        return rootView;
    }

    private void applyChange() {
        for (int i = 0; i < listPath.length; i++) {
            File oldfile = new File(listPath[i] + "/" + listName[i]);
            File newfile = new File(listPath[i] + "/" + listNewName[i]);
            oldfile.renameTo(newfile);
        }
        activity.finish();
    }
}
