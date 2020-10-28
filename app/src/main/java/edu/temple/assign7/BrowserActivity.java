package edu.temple.assign7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.BrowserButtons {

    private PageViewerFragment pageViewer;
    private PageControlFragment pageControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {   // create new
            pageViewer = new PageViewerFragment();
            pageControl = new PageControlFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControl)
                    .add(R.id.page_viewer, pageViewer)
                    .commit();
        } else {    // restore
            pageViewer = (PageViewerFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pageViewer");
            pageControl = (PageControlFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pageControl");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "pageViewer", pageViewer);
        getSupportFragmentManager().putFragment(outState, "pageControl", pageControl);
    }

    @Override
    public void loadUrl(String url) {
        pageViewer.loadUrl(url);
    }

    @Override
    public void goBack() {
        pageViewer.goBack();
    }

    @Override
    public void goForward() {
        pageViewer.goForward();
    }
}