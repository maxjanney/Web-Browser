package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {

    private BrowserControlInterface browserActivity;

    public BrowserControlFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BrowserControlInterface) {
            browserActivity = (BrowserControlInterface) context;
        } else {
            throw new RuntimeException("Cannot manage windows if interface not BrowserControlInterface implemented");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_browser_control, container, false);

        final ImageButton newPageButton = root.findViewById(R.id.newPageButton);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(newPageButton))
                    browserActivity.newPage();
            }
        };

        newPageButton.setOnClickListener(ocl);

        return root;
    }

    interface BrowserControlInterface {
        void newPage();
    }
}