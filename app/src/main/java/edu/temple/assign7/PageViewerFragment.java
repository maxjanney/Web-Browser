package edu.temple.assign7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ServiceWorkerWebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;

public class PageViewerFragment extends Fragment implements Parcelable {

    private static final int MAX_TITLE_LEN = 30;

    WebView webView;
    private PageViewerInterface browserActivity;

    private String url;

    public static PageViewerFragment newInstance(String url) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KeyUtils.URL_ARG_KEY, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageViewerInterface)
            browserActivity = (PageViewerInterface) context;
        else
            throw new RuntimeException("You must implement PageViewerInterface to attach this fragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            url = getArguments().getString(KeyUtils.URL_ARG_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = root.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                browserActivity.updateUrl(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                browserActivity.updateTitle(webView.getTitle());
            }
        });

        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
        else {
            if (url != null)
                webView.loadUrl(url);
            else
                browserActivity.updateUrl("");
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    public void go(String url) {
        webView.loadUrl(url);
    }

    public void back() {
        webView.goBack();
    }

    public void forward() {
        webView.goForward();
    }

    public String getTitle() {
        String title;
        if (webView != null) {
            title = webView.getTitle();
            if (title == null || title.isEmpty())
                return webView.getUrl();
            else
                return shorten(title);
        } else
            return "Blank Page";
    }

    private String shorten(String title) {
        if (title.length() > MAX_TITLE_LEN)
            title = title.substring(0, MAX_TITLE_LEN);
        return title;
    }

    public String getUrl() {
        if (webView != null)
            return webView.getUrl();
        else
            return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    interface PageViewerInterface {
        void updateUrl(String url);
        void updateTitle(String title);
    }
}
