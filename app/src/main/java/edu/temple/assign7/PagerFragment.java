package edu.temple.assign7;

import android.os.Bundle;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = root.findViewById(R.id.viewpager);

        if (savedInstanceState != null) {
            pages = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable(KEY_PAGES);
        } else {
            pages = new ArrayList<>();
            pages.add(new PageViewerFragment());
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
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PAGES, pages);
    }

    public void addNewPage() {
        pages.add(new PageViewerFragment());
        Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
        viewPager.setCurrentItem(pages.size() - 1);
    }

    public PageViewerFragment currentPage() {
        return (PageViewerFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
    }
}