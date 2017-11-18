package abouseir.amine.bulkrenametool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Amine Abouseir on 06/09/17.
 */

public class FilesFragment {
    public static final String SELF_DIR_NAME = "self";
    public static final String EMULATED_DIR_NAME = "emulated";
    public static final String EMULATED_DIR_KNOX = "knox-emulated";
    public static final String SDCARD0_DIR_NAME = "sdcard0";
    public static final String CONTAINER = "container";
    private final static int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 13;
    private static final String ERROR = "error";
    private Button fileBrowser;
    private TextView message_files_fragment;
    private ListView filesList;
    private String[] listName = new String[0];
    private String[] listPath = new String[0];
    private String[] listFullPath = new String[0];
    private View view;
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;

    public View getView(LayoutInflater inflater, ViewGroup tabLayout, Context context, Activity activity) {
        View rootView = inflater.inflate(R.layout.fragment_files, tabLayout, false);
        fileBrowser = rootView.findViewById(R.id.fileBrowser);
        message_files_fragment = rootView.findViewById(R.id.message_files_fragment);
        filesList = rootView.findViewById(R.id.filesList);
        if (listPath.length > 0)
            message_files_fragment.setText(context.getString(R.string.selected_files_message, listPath.length));
        else {
            message_files_fragment.setText("No file selected");
        }
        FilesListAdapter filesAdapter = new FilesListAdapter(listName, listFullPath, context);
        filesList.setAdapter(filesAdapter);
        fileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
            }
        });
        this.view = rootView;
        this.inflater = inflater;
        this.activity = activity;
        this.context = context;
        return rootView;
    }

    private void askPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            Toast.makeText(context, "Storage permission is needed to to display files list", Toast.LENGTH_SHORT).show();
        } else {
            selectDrive();
        }
    }

    private void selectDrive() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customView = inflater.inflate(R.layout.dialog_storage, null, false);
        builder.setView(customView);
        ListView list = customView.findViewById(R.id.storage_list_view);
        ArrayList<String> storagesNameList = new ArrayList<>();
        final ArrayList<String> storagesPathList = new ArrayList<>();

        File storageDir = new File("/storage");
        String internalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        File[] volumeList = storageDir.listFiles();

        storagesNameList.add("Internal Storage");
        storagesPathList.add(internalStoragePath);

        for (File f : volumeList) {
            if (!f.getName().equals(SELF_DIR_NAME)
                    && !f.getName().equals(EMULATED_DIR_KNOX)
                    && !f.getName().equals(EMULATED_DIR_NAME)
                    && !f.getName().equals(SDCARD0_DIR_NAME)
                    && !f.getName().equals(CONTAINER)) {
                storagesNameList.add(f.getName());
                storagesPathList.add(f.getAbsolutePath());
            }
        }
        StorageChooserListAdapter nameAdapter = new StorageChooserListAdapter(storagesNameList, storagesPathList, context, true);
        list.setAdapter(nameAdapter);
        final AlertDialog dialog = builder.create();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFiles(storagesPathList.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void selectFiles(String directory) {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(directory);
        FilePickerDialog dialog = new FilePickerDialog(context, properties);
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                updateList(files);
            }
        });
        dialog.show();
        int width = view.getResources().getDisplayMetrics().widthPixels;
        int height = (int) (view.getResources().getDisplayMetrics().heightPixels * 0.95);
        dialog.getWindow().setLayout(width, height);
    }

    private void updateList(String[] files) {
        int size = files.length;
        listName = new String[size];
        listPath = new String[size];
        listFullPath = files;
        for (int i = 0; i < size; i++) {
            int j = files[i].lastIndexOf("/");
            listName[i] = files[i].substring(j + 1);
            listPath[i] = files[i].substring(0, j);
        }
        if (listPath.length > 0)
            message_files_fragment.setText(context.getString(R.string.selected_files_message, listPath.length));
        else {
            message_files_fragment.setText("No file selected");
        }
        FilesListAdapter filesAdapter = new FilesListAdapter(listName, listFullPath, context);
        filesList.setAdapter(filesAdapter);
        RenameActivity.listName = listName;
        RenameActivity.listPath = listPath;
        RenameActivity.listFullPath = listFullPath;
    }

    public String[] getListName() {
        return listName;
    }

    public String[] getListPath() {
        return listPath;
    }
}
