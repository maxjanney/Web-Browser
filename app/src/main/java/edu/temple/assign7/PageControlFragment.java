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

    private EditText url;
    private BrowserButtons parentLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserButtons) {
            parentLayout = (BrowserButtons) context;
        } else {
            throw new RuntimeException("Activity must implement SearchURL interface");
        }
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_page_control, container, false);

        url = root.findViewById(R.id.url);
        root.findViewById(R.id.go).setOnClickListener((v) -> parentLayout.loadUrl(url.getText().toString()));
        root.findViewById(R.id.back).setOnClickListener((v) -> parentLayout.goBack());
        root.findViewById(R.id.next).setOnClickListener((v) -> parentLayout.goForward());

        return root;
    }

    interface BrowserButtons {
        // load the url in a WebView
        void loadUrl(String url);

        // go back in the WebView
        void goBack();

        // go forward in the WebView
        void goForward();
    }
}