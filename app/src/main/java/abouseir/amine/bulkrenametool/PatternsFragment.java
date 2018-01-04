package abouseir.amine.bulkrenametool;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import abouseir.amine.bulkrenametool.Patterns.Pattern;
import abouseir.amine.bulkrenametool.Patterns.PrefixPattern;
import abouseir.amine.bulkrenametool.Patterns.SuffixPattern;


public class PatternsFragment {
    private Button addPattern;
    private TextView message_patterns_fragment;
    private ListView patternsList;
    private ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    private View view;
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;
    private RenameActivity.PlaceholderFragment placeholderFragment;

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    public View getView(LayoutInflater inflater, ViewGroup tabLayout, Context context, Activity activity) {
        View rootView = inflater.inflate(R.layout.fragment_patterns, tabLayout, false);
        addPattern = rootView.findViewById(R.id.addPattern);
        message_patterns_fragment = rootView.findViewById(R.id.message_patterns_fragment);
        patternsList = rootView.findViewById(R.id.patternsList);
        if (this.patterns.size() > 0)
            message_patterns_fragment.setText(context.getString(R.string.selected_patterns_message, patterns.size()));
        else {
            message_patterns_fragment.setText("No pattern selected");
        }
        PatternsListAdapter patternsAdapter = new PatternsListAdapter(this.patterns, context);
        patternsList.setAdapter(patternsAdapter);
        addPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPattern();
            }
        });
        this.view = rootView;
        this.inflater = inflater;
        this.activity = activity;
        this.context = context;
        RenameActivity.patterns = patterns;
        return rootView;
    }

    private void selectPattern() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_pattern, null, false);
        builder.setView(customView);
        ListView list = customView.findViewById(R.id.pattern_list_view);
        String[] choicesList = new String[]{"Add Rename Pattern", "Add Sort Pattern", "Load a saved Pattern"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, choicesList);
        list.setAdapter(adapter);
        final AlertDialog dialog = builder.create();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (position == 0)
                    selectRenamePattern();
                else if (position == 1)
                    selectSortPattern();
                else if (position == 2)
                    loadSavedPattern();
            }
        });
        dialog.show();
    }

    private void selectRenamePattern() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_pattern, null, false);
        builder.setView(customView);
        TextView title = customView.findViewById(R.id.dialog_title);
        title.setText("Choose a rename pattern");
        ListView list = customView.findViewById(R.id.pattern_list_view);
        String[] choicesList = new String[]{"Add Numbers", "Add Prefix", "Add Suffix", " Replace Sequence"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, choicesList);
        list.setAdapter(adapter);
        final AlertDialog dialog = builder.create();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (position == 0)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 1)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 2)
                    SuffixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 3)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
            }
        });
        dialog.show();
    }

    private void selectSortPattern() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_pattern, null, false);
        builder.setView(customView);
        TextView title = customView.findViewById(R.id.dialog_title);
        title.setText("Choose a sort pattern");
        ListView list = customView.findViewById(R.id.pattern_list_view);
        String[] choicesList = new String[]{"Sort by Name", "Sort by Date", "Sort by Type", " Sort by Size"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, choicesList);
        list.setAdapter(adapter);
        final AlertDialog dialog = builder.create();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (position == 0)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 1)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 2)
                    SuffixPattern.patternDialog(context, inflater, PatternsFragment.this);
                else if (position == 3)
                    PrefixPattern.patternDialog(context, inflater, PatternsFragment.this);
            }
        });
        dialog.show();
    }

    private void loadSavedPattern() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_pattern, null, false);
        builder.setView(customView);
        TextView title = customView.findViewById(R.id.dialog_title);
        title.setText("Choose a saved pattern");
        ListView list = customView.findViewById(R.id.pattern_list_view);
        String[] choicesList = new String[]{"Add Rename Pattern", "Add Sort Pattern", "Load a saved Pattern"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, choicesList);
        list.setAdapter(adapter);
        final AlertDialog dialog = builder.create();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateList() {
        if (patterns.size() > 0)
            message_patterns_fragment.setText(context.getString(R.string.selected_patterns_message, patterns.size()));
        else {
            message_patterns_fragment.setText("No pattern selected");
        }
        PatternsListAdapter patternsAdapter = new PatternsListAdapter(patterns, context);
        patternsList.setAdapter(patternsAdapter);
        RenameActivity.patterns = patterns;
    }

    public void addPattern(Pattern pattern) {
        this.patterns.add(pattern);
        updateList();
    }
}
