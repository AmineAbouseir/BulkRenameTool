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

import abouseir.amine.bulkrenametool.renamePatterns.RenamePattern;
import abouseir.amine.bulkrenametool.sortingPatterns.SortingPattern;

public class PreviewFragment {
    private Button save;
    private TextView message_preview_fragment;
    private ListView previewList;
    private String[] listName = new String[0];
    private String[] listPath = new String[0];
    private String[] listNewName = new String[0];
    private RenamePattern pattern;
    private SortingPattern sort;
    private Activity activity;

    public View getView(LayoutInflater inflater, ViewGroup tabLayout, Context context, Activity activity, String[] listName, String[] listPath, SortingPattern sort, RenamePattern pattern) {
        View rootView = inflater.inflate(R.layout.fragment_preview, tabLayout, false);
        save = rootView.findViewById(R.id.save);
        message_preview_fragment = rootView.findViewById(R.id.message_preview_fragment);
        previewList = rootView.findViewById(R.id.previewList);
        ArrayList<String[]> arrayList = sort.apply(listName, listPath);
        this.listName = arrayList.get(0);
        this.listPath = arrayList.get(1);
        this.listNewName = pattern.apply(this.listName);
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
        this.activity = activity;
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
