package edu.temple.assign7;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {

    private WebView myWebView;
    private PageViewer parentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageViewer)
            parentActivity = (PageViewer) context;
        else 
            throw new RuntimeException("Activity must implement PageViewer interface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        myWebView = root.findViewById(R.id.web_view);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
               if (url != null)
                   parentActivity.updateUrl(url);
           }
        });

        if (savedInstanceState != null) 
            myWebView.restoreState(savedInstanceState);
            
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
        myWebView.goBack();
    }

    public void goForward() {
        myWebView.goForward();
    }

    interface PageViewer {
        // update any views using the new url
        void updateUrl(String url);
    }
}
