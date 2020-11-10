package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {

    private EditText urlEditText;
    private ImageButton goButton, backButton, nextButton;
    private PageControl parentLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageControl) 
            parentLayout = (PageControl) context;
        else 
            throw new RuntimeException("Activity must implement PageControl interface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_control, container, false);
        urlEditText = root.findViewById(R.id.url);
        goButton = root.findViewById(R.id.go);
        backButton = root.findViewById(R.id.back);
        nextButton = root.findViewById(R.id.next);

        View.OnClickListener onClick = ((v -> {
            if (v.equals(goButton))
                parentLayout.loadUrl(fixUrl(urlEditText.getText().toString()));
            else if (v.equals(backButton))
                parentLayout.goBack();
            else if (v.equals(nextButton))
                parentLayout.goForward();
        }));

        goButton.setOnClickListener(onClick);
        backButton.setOnClickListener(onClick);
        nextButton.setOnClickListener(onClick);

        return root;
    }

    public void setUrl(String url) {
        urlEditText.setText(url);
    }

    private String fixUrl(String url) {
        if (!(url.startsWith("https://") || url.startsWith("http://")))
            url = "https://" + url;
        return url;
    }

    interface PageControl {
        // load the url in a WebView
        void loadUrl(String url);

        // go back in the WebView
        void goBack();

        // go forward in the WebView
        void goForward();
    }
}
