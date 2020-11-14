package edu.temple.assign7;

import android.os.Bundle;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PagerFragment extends Fragment {

    private static final String KEY_PAGES = "savedPages";

    ViewPager viewPager;
    ArrayList<PageViewerFragment> pages;

    public static PagerFragment newInstance(ArrayList<PageViewerFragment> pages) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PAGES, pages);
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle;
        if ((bundle = getArguments()) != null) {
            pages = (ArrayList<PageViewerFragment>) bundle.getSerializable(KEY_PAGES);
            pages.add(new PageViewerFragment());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = root.findViewById(R.id.viewpager);

        if (savedInstanceState != null) {
            pages = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(KEY_PAGES);
        }

        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return pages.get(position);
            }

            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                if (pages.contains(object))
                    return pages.indexOf(object);
                else
                    return POSITION_NONE;
            }
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PAGES, pages);
    }

    public void loadUrl(String url) {
        pages.get(viewPager.getCurrentItem()).myWebView.loadUrl(url);
    }

    public void goBack() {
        pages.get(viewPager.getCurrentItem()).myWebView.goBack();
    }

    public void goForward() {
        pages.get(viewPager.getCurrentItem()).myWebView.goForward();
    }

    public void addNewPage() {
        pages.add(new PageViewerFragment());
        Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
        viewPager.setCurrentItem(pages.size() - 1);
    }
}