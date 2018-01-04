package abouseir.amine.bulkrenametool.Patterns;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import abouseir.amine.bulkrenametool.PatternsFragment;
import abouseir.amine.bulkrenametool.R;

public class PrefixPattern extends Pattern {

    private String prefix;
    private String name = "Add Prefix";

    public PrefixPattern(String prefix) {
        this.prefix = prefix;
    }

    public static void patternDialog(Context context, LayoutInflater inflater, final PatternsFragment patternsFragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_prefix_suffix, null, false);
        final EditText content = customView.findViewById(R.id.text);
        content.setHint("Prefix");
        builder.setView(customView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String prefix = String.valueOf(content.getText());
                        if (!prefix.equals("")) {
                            dialog.dismiss();
                            patternsFragment.addPattern(new PrefixPattern((prefix)));
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        TextView title = customView.findViewById(R.id.dialog_title);
        title.setText("Type the prefix");
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public ArrayList<String[]> apply(String[] listNewName, String[] listPath, String[] listName) {
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = this.prefix + listNewName[i];
        }
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(listNewName);
        arrayList.add(listPath);
        arrayList.add(listName);
        return arrayList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Prefix: " + prefix;
    }

}
