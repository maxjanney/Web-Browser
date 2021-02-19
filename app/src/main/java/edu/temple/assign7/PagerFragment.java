package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    private PagerInterface browserActivity;
    private ViewPager viewPager;
    private ArrayList<PageViewerFragment> pages;

    public static PagerFragment newInstance(ArrayList<PageViewerFragment> pages) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(KeyUtils.PAGES_ARG_KEY, pages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PagerInterface) {
            browserActivity = (PagerInterface) context;
        } else {
            throw new RuntimeException("You must implement PagerInterface to attach this fragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pages = (ArrayList) getArguments().getSerializable(KeyUtils.PAGES_ARG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = root.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
                if (pages.contains(object)) {
                    return pages.indexOf(object);
                } else {
                    return POSITION_NONE;
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                browserActivity.updateUrl((pages.get(position)).getUrl());
                browserActivity.updateTitle((pages.get(position)).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        return root;
    }

    public void notifyWebsitesChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public void showPage(int index) {
        viewPager.setCurrentItem(index);
    }

    public void go(String url) {
        getPageViewer(viewPager.getCurrentItem()).go(url);
    }

    public void back() {
        getPageViewer(viewPager.getCurrentItem()).back();
    }

    public void forward() {
        getPageViewer(viewPager.getCurrentItem()).forward();
    }

    public String getCurrentUrl() {
        if (pages.size() > 0) {
            return getPageViewer(viewPager.getCurrentItem()).getUrl();
        } else {
            return null;
        }
    }

    public String getCurrentTitle() {
        if (pages.size() > 0) {
            return getPageViewer(viewPager.getCurrentItem()).getTitle();
        } else {
            return null;
        }
    }

    private PageViewerFragment getPageViewer(int position) {
        return (PageViewerFragment) ((FragmentStatePagerAdapter) viewPager.getAdapter()).getItem(position);
    }

    interface PagerInterface {
        void updateUrl(String url);
        void updateTitle(String title);
    }
}