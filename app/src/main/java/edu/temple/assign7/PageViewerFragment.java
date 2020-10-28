package edu.temple.assign7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class PageViewerFragment extends Fragment {

    private WebView myWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        myWebView = root.findViewById(R.id.web_view);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        return root;
    }

    public void loadUrl(String url) {
        myWebView.loadUrl(url);
    }

    public void goBack() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        }
    }

    public void goForward() {
        if (myWebView.canGoForward()) {
            myWebView.goForward();
        }
    }
}