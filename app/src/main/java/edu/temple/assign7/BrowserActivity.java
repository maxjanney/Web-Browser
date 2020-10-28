package edu.temple.assign7;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.SearchURL {

    private PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment pageControlFragment = new PageControlFragment();
        pageViewerFragment = new PageViewerFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .add(R.id.page_viewer, pageViewerFragment)
                .commit();
    }

    @Override
    public void loadUrl(String url) {
        pageViewerFragment.loadUrl(url);
    }

    @Override
    public void goBack() {
        pageViewerFragment.goBack();
    }

    @Override
    public void goForward() {
        pageViewerFragment.goForward();
    }
}