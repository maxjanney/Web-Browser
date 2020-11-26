package edu.temple.assign7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.Serializable;

public class BrowserControlFragment extends Fragment implements Serializable {

    private BrowserControlInterface browserActivity;

    public BrowserControlFragment() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlInterface)
            browserActivity = (BrowserControlInterface) context;
        else
            throw new RuntimeException("Cannot manage windows if interface BrowserControlInterface not implemented");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_browser_control, container, false);

        final ImageButton newPageButton = root.findViewById(R.id.newPageButton);
        final ImageButton savePageButton = root.findViewById(R.id.savePageButton);
        final ImageButton bookmarksButton = root.findViewById(R.id.bookmarksButton);

        View.OnClickListener browserControlClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(newPageButton))
                    browserActivity.newPage();
                else if (v.equals(savePageButton))
                    browserActivity.savePage();
                else if (v.equals(bookmarksButton))
                    browserActivity.showBookmarks();
            }
        };

        newPageButton.setOnClickListener(browserControlClickListener);
        savePageButton.setOnClickListener(browserControlClickListener);
        bookmarksButton.setOnClickListener(browserControlClickListener);

        return root;
    }

    interface BrowserControlInterface extends Serializable {
        void newPage();
        void savePage();
        void showBookmarks();
    }
}