package edu.temple.assign7;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class PageControlFragment extends Fragment {

    private TextView urlTextView;

    private PageControlInterface browserActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageControlInterface) {
            browserActivity = (PageControlInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page_control, container, false);

        urlTextView = root.findViewById(R.id.urlTextView);

        final ImageButton goButton = root.findViewById(R.id.goButton);
        final ImageButton backButton = root.findViewById(R.id.backButton);
        final ImageButton forwardButton = root.findViewById(R.id.forwardButton);

        View.OnClickListener controlOcl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(goButton)) {
                    browserActivity.go(formatUrl(urlTextView.getText().toString()));
                } else if (v.equals(backButton)) {
                    browserActivity.back();
                } else if (v.equals(forwardButton)) {
                    browserActivity.forward();
                }
            }
        };

        goButton.setOnClickListener(controlOcl);
        backButton.setOnClickListener(controlOcl);
        forwardButton.setOnClickListener(controlOcl);

        return root;
    }

    public void updateUrl(String url) {
        urlTextView.setText(url);
    }

    private String formatUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        } else {
            return url;
        }
    }

    interface PageControlInterface {
        void go(String url);
        void back();
        void forward();
    }
}
