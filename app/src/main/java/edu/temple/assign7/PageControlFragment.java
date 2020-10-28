package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PageControlFragment extends Fragment {

    private SearchURL parentLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SearchURL) {
            parentLayout = (SearchURL) context;
        } else {
            throw new RuntimeException("Activity must implement SearchURL interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_page_control, container, false);
        final EditText url = root.findViewById(R.id.url);

        root.findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLayout.loadUrl(url.getText().toString());
            }
        });

        root.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLayout.goBack();
            }
        });

        root.findViewById(R.id.next).setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                parentLayout.goForward();
            }
        });

        return root;
    }

    interface SearchURL {
        // load the url in a WebView
        void loadUrl(String url);

        // go back in the WebView
        void goBack();

        // go forward in the WebView
        void goForward();
    }
}