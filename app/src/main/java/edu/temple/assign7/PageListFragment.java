package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class PageListFragment extends Fragment implements Serializable {

    private PageListInterface browserActivity;

    private ListView listView;
    private ArrayList<PageViewerFragment> pages;

    public PageListFragment() { }

    public static PageListFragment newInstance(ArrayList<PageViewerFragment> pages) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KeyUtils.PAGES_ARG_KEY, pages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            pages = (ArrayList) getArguments().getSerializable(KeyUtils.PAGES_ARG_KEY);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageListInterface)
            browserActivity = (PageListInterface) context;
        else
            throw new RuntimeException("You must implement PageListInterface before attaching this fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page_list, container, false);

        listView = root.findViewById(R.id.listView);
        listView.setAdapter(new PageListAdapter(getActivity(), pages));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                browserActivity.pageSelected(position);
            }
        });

        return root;
    }

    public void notifyWebsitesChanged() {
        if (listView != null)
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    interface PageListInterface extends Serializable {
        void pageSelected(int position);
    }
}