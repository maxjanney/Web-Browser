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
import android.widget.ListView;

import java.util.ArrayList;

// pass a list of Strings to be displayed as
public class PageListFragment extends Fragment {

    ListView page_titles;
    ArrayList<String> titles;
    PageListInterface parentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageListInterface)
            parentActivity = (PageListInterface) context;
        else
            throw new RuntimeException("Must implement PageListInterface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_page_list, container, false);
        page_titles = root.findViewById(R.id.page_titles);

        page_titles.setAdapter(new ArrayAdapter<String>((Context) parentActivity, android.R.layout.simple_list_item_1, titles));
        page_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.changePage(position);
            }
        });
        return root;
    }

    interface PageListInterface {
        void changePage(int position);
    }
}