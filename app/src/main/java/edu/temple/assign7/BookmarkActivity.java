package edu.temple.assign7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class BookmarkActivity extends AppCompatActivity {

    ArrayList<String> titles, urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        titles = new ArrayList<>();
        urls = new ArrayList<>();

        loadTitlesAndUrls();

        ListView bookmarks = findViewById(R.id.bookmarks);

        bookmarks.setAdapter(new BookmarkAdapter(BookmarkActivity.this, titles, urls));
    }

    private void loadTitlesAndUrls() {
        SharedPreferences preferences = getSharedPreferences(KeyUtils.FILE_KEY, 0);

        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            titles.add(entry.getKey());
            urls.add(entry.getValue().toString());
        }
    }
}