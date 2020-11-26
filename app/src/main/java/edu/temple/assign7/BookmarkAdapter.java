package edu.temple.assign7;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter implements Serializable {

    Context context;

    // list of page titles that
    // will be displayed
    ArrayList<String> titles;

    // urls corresponding to
    // to the page titles
    ArrayList<String> urls;

    public BookmarkAdapter(Context context, ArrayList<String> titles, ArrayList<String> urls) {
        this.context = context;
        this.titles = titles;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(KeyUtils.BOOKMARK_TITLE_KEY, titles.get(position));
        bundle.putString(KeyUtils.BOOKMARK_URL_KEY, urls.get(position));
        return bundle;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bookmark_item, parent, false);
        }

        TextView titleText = convertView.findViewById(R.id.title);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);

        titleText.setText(titles.get(position));
        titleText.setOnClickListener(v -> sendUrl(position));

        deleteButton.setOnClickListener(v -> new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.dialog, titles.get(position)))
                .setPositiveButton(R.string.ok,((dialog, which) -> delete(position)))
                .setNegativeButton(R.string.cancel, null)
                .show());

        return convertView;
    }

    private void sendUrl(int position) {
        Intent response = new Intent();
        response.putExtra(KeyUtils.URL_RESULT_KEY, urls.get(position));

        BookmarkActivity activity = (BookmarkActivity) context;

        activity.setResult(Activity.RESULT_OK, response);
        activity.finish();
    }

    public void delete(int position) {
        context.getSharedPreferences(KeyUtils.FILE_KEY, 0)
                .edit()
                .remove(titles.get(position))
                .apply();
        deleteEntry(position);
    }

    private void deleteEntry(int position) {
        titles.remove(position);
        urls.remove(position);
        notifyDataSetChanged();
    }
}