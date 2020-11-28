package edu.temple.assign7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import android.widget.Toast;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlInterface,
        PageViewerFragment.PageViewerInterface, BrowserControlFragment.BrowserControlInterface,
        PagerFragment.PagerInterface, PageListFragment.PageListInterface {

    FragmentManager fm;

    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;

    ArrayList<PageViewerFragment> pages;

    boolean listMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            pages = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(KeyUtils.PAGES_KEY);
        else
            pages = new ArrayList<>();

        fm = getSupportFragmentManager();

        listMode = findViewById(R.id.page_list) != null;

        Fragment tmpFragment;

        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tmpFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) tmpFragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.page_viewer)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) tmpFragment;
        else {
            pagerFragment = PagerFragment.newInstance(pages);
            fm.beginTransaction()
                    .add(R.id.page_viewer, pagerFragment)
                    .commit();
        }

        if (listMode) {
            if ((tmpFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                pageListFragment = (PageListFragment) tmpFragment;
            else {
                pageListFragment = PageListFragment.newInstance(pages);
                fm.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KeyUtils.PAGES_KEY, pages);
    }

    private void clearIdentifiers() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        pageControlFragment.updateUrl("");
    }

    // Notify all observers of collections
    private void notifyWebsitesChanged() {
        pagerFragment.notifyWebsitesChanged();
        if (listMode)
            pageListFragment.notifyWebsitesChanged();
    }

    @Override
    public void go(String url) {
        if (pages.size() > 0)
            pagerFragment.go(url);
        else {
            pages.add(PageViewerFragment.newInstance(url));
            notifyWebsitesChanged();
            pagerFragment.showPage(pages.size() - 1);
        }
    }

    @Override
    public void back() {
        pagerFragment.back();
    }

    @Override
    public void forward() {
        pagerFragment.forward();
    }

    @Override
    public void updateUrl(String url) {
        if (url != null && url.equals(pagerFragment.getCurrentUrl())) {
            pageControlFragment.updateUrl(url);
            // Update the ListView in the PageListFragment - results in updated titles
            notifyWebsitesChanged();
        }
    }

    @Override
    public void updateTitle(String title) {
        if (title != null && title.equals(pagerFragment.getCurrentTitle()) && getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        notifyWebsitesChanged();
    }

    @Override
    public void newPage() {
        // Add page to list
        pages.add(new PageViewerFragment());
        // Update all necessary views
        notifyWebsitesChanged();
        // Display the newly created page
        pagerFragment.showPage(pages.size() - 1);
        // Clear the displayed URL in PageControlFragment and title in the activity
        clearIdentifiers();
    }

    @Override
    public void savePage() {
        if (pages.size() > 0) {
            String title = pagerFragment.getCurrentTitle();
            String url = pagerFragment.getCurrentUrl();

            if (title != null && url != null && !title.isEmpty() && !url.isEmpty()) {
                getSharedPreferences(KeyUtils.FILE_KEY, 0)
                        .edit()
                        .putString(title, url)
                        .apply();
                Toast.makeText(BrowserActivity.this, getString(R.string.confirmation), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showBookmarks() {
        startActivityForResult(new Intent(BrowserActivity.this, BookmarkActivity.class), KeyUtils.LAUNCH_BOOKMARK_ACTIVITY);
    }

    @Override
    public void pageSelected(int position) {
        pagerFragment.showPage(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == KeyUtils.LAUNCH_BOOKMARK_ACTIVITY && resultCode == Activity.RESULT_OK) {
            Bundle extras = null;

            if (data != null)
                extras = data.getExtras();

            String url = "";

            if (extras != null)
                url = extras.getString(KeyUtils.URL_RESULT_KEY);

            // display the corresponding
            // url in the current page
            go(url);
        }
    }
}