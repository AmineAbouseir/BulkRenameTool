package abouseir.amine.bulkrenametool;

import android.content.Context;
import android.graphics.Typeface;
import android.os.StatFs;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class StorageChooserListAdapter extends BaseAdapter {

    public static final int OVERVIEW_STORAGE_TEXT_INDEX = 3;
    public static final int OVERVIEW_INDICATOR_INDEX = 4;
    public static final int OVERVIEW_MEMORYBAR_INDEX = 5;
    private static int memoryPercentile;
    private List<String> storagesNameList;
    private List<String> storagesPathList;
    private Context mContext;
    private boolean shouldShowMemoryBar;
    private ProgressBar memoryBar;
    private int[] scheme;


    public StorageChooserListAdapter(List<String> storagesNameList, List<String> storagesPathList, Context mContext, boolean shouldShowMemoryBar) {
        this.storagesNameList = storagesNameList;
        this.storagesPathList = storagesPathList;
        this.mContext = mContext;
        this.shouldShowMemoryBar = shouldShowMemoryBar;
        this.scheme = mContext.getResources().getIntArray(R.array.default_light);
    }

    @Override
    public int getCount() {
        return storagesNameList.size();
    }

    @Override
    public Object getItem(int i) {
        return storagesPathList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public long getTotalMemorySize(String path) {
        File file = new File(path);
        StatFs stat = new StatFs(file.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public long getAvailableMemorySize(String path) {
        File file = new File(path);
        StatFs stat = new StatFs(file.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KiB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MiB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GiB";
                    size /= 1024;
                }
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        memoryPercentile = -1;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.row_storage, viewGroup, false);

        //for animation set current position to provide animation delay
        TextView storageName = rootView.findViewById(R.id.storage_name);
        TextView memoryStatus = rootView.findViewById(R.id.memory_status);
        memoryBar = rootView.findViewById(R.id.memory_bar);

        // new scaled memorybar - following the new google play update!
        memoryBar.setScaleY(2f);

        final SpannableStringBuilder str = new SpannableStringBuilder(storagesNameList.get(i) + " (" + formatSize(getTotalMemorySize(storagesPathList.get(i))) + ")");

        str.setSpan(new StyleSpan(Typeface.ITALIC), getSpannableIndex(str), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String availableText = mContext.getString(R.string.text_freespace, formatSize(getAvailableMemorySize(storagesPathList.get(i))));
        storageName.setText(str);

        storageName.setTextColor(scheme[OVERVIEW_STORAGE_TEXT_INDEX]);
        memoryStatus.setText(availableText);

        memoryStatus.setTextColor(scheme[OVERVIEW_INDICATOR_INDEX]);
        DrawableCompat.setTint(memoryBar.getProgressDrawable(), scheme[OVERVIEW_MEMORYBAR_INDEX]);

        memoryPercentile = getPercentile(storagesPathList.get(i));

        // THE ONE AND ONLY MEMORY BAR
        if (shouldShowMemoryBar && memoryPercentile != -1) {
            memoryBar.setMax(100);
            memoryBar.setProgress(memoryPercentile);
            runMemorybarAnimation(i);
        } else {
            memoryBar.setVisibility(View.GONE);
        }

        return rootView;

    }

    private void runMemorybarAnimation(int pos) {
        MemorybarAnimation animation = new MemorybarAnimation(memoryBar, 0, memoryPercentile);
        animation.setDuration(500);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());

        if (pos > 0) {
            animation.setStartOffset(300);
        }

        memoryBar.startAnimation(animation);
    }

    /**
     * return the spannable index of character '('
     *
     * @param str SpannableStringBuilder to apply typeface changes
     * @return index of '('
     */
    private int getSpannableIndex(SpannableStringBuilder str) {
        return str.toString().indexOf("(") + 1;
    }

    /**
     * calculate percentage of memory left for memorybar
     *
     * @param path use same statfs
     * @return integer value of the percentage with amount of storage used
     */
    private int getPercentile(String path) {
        int percent = -1;

        long availableMem = getAvailableMemorySize(path);
        long totalMem = getTotalMemorySize(path);

        if (totalMem > 0)
            percent = (int) (100 - ((availableMem * 100) / totalMem));
        return percent;
    }

    /**
     * remove KiB,MiB,GiB text that we got from MemoryUtil.getAvailableMemorySize() &
     * MemoryUtil.getTotalMemorySize()
     *
     * @param size String in the format of user readable string, with MB, GiB .. suffix
     * @return integer value of the percentage with amount of storage used
     */
    private long getMemoryFromString(String size) {
        long mem;

        if (size.contains("MiB")) {
            mem = Integer.parseInt(size.replace(",", "").replace("MiB", ""));
        } else if (size.contains("GiB")) {
            mem = Integer.parseInt(size.replace(",", "").replace("GiB", ""));
        } else {
            mem = Integer.parseInt(size.replace(",", "").replace("KiB", ""));
        }

        Log.d("TAG", "Memory:" + mem);
        return mem;
    }
}
