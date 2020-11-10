package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowserControlFragment extends Fragment {

    BrowserControl parentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControl)
            parentActivity = (BrowserControl) context;
        else
            throw new RuntimeException("Must implement BrowserControl interface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_browser_control, container, false);

        root.findViewById(R.id.new_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addNewPage();
            }
        });

        return root;
    }

    interface BrowserControl {
        // add a new page when pressed
        void addNewPage();
    }
}