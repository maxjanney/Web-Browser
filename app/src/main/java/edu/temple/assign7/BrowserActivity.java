package edu.temple.assign7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControl,
        PageViewerFragment.PageViewer, BrowserControlFragment.BrowserControl {

    // list of current pages
    private PageControlFragment pageControl;
    private PagerFragment pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating for first time
        if (savedInstanceState == null) {
            pager = new PagerFragment();
            pageControl = new PageControlFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControl)
                    .add(R.id.page_display, pager)
                    .add(R.id.browser_control, new BrowserControlFragment())
                    .commit();
        } else {
            pager = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pager");
            pageControl = (PageControlFragment) getSupportFragmentManager().getFragment(savedInstanceState, "pageControl");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "pager", pager);
        getSupportFragmentManager().putFragment(outState, "pageControl", pageControl);
    }

    @Override
    public void updateUrl(String url) {
        pageControl.setUrl(url);
    }

    @Override
    public void loadUrl(String url) {
        pager.currentPage().loadUrl(url);
    }

    @Override
    public void goBack() {
        pager.currentPage().goBack();
    }

    @Override
    public void goForward() {
        pager.currentPage().goForward();
    }

    @Override
    public void addNewPage() {
        pager.addNewPage();
        pageControl.setUrl("");
    }
}