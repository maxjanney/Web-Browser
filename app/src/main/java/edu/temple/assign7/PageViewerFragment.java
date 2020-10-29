package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {

    private WebView myWebView;
    private UpdateUrl parentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UpdateUrl) {
            parentActivity = (UpdateUrl) context;
        } else {
            throw new RuntimeException("Must implement SetURL interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        myWebView = root.findViewById(R.id.web_view);
        if (savedInstanceState != null) {
            myWebView.restoreState(savedInstanceState);
        }
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                parentActivity.updateUrl(request.getUrl().toString());
                return false;
            }
        });
        myWebView.getSettings().setJavaScriptEnabled(true);

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        myWebView.saveState(outState);
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

    interface UpdateUrl {
        // update any views using the new url
        void updateUrl(String url);
    }
}