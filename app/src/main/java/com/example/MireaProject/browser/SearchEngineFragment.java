package com.example.MireaProject.browser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.MireaProject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchEngineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchEngineFragment extends Fragment {

    EditText inputUrl;
    WebView webView;
    Button searchButton, forwardButton, backButton, refreshButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchEngineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchEngineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchEngineFragment newInstance(String param1, String param2) {
        SearchEngineFragment fragment = new SearchEngineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_search_engine, container, false);

        inputUrl = (EditText) inflatedView.findViewById(R.id.editTextURL);
        webView = (WebView) inflatedView.findViewById(R.id.webView);
        searchButton = (Button) inflatedView.findViewById(R.id.buttonSearch);
        forwardButton = (Button) inflatedView.findViewById(R.id.buttonForward);
        backButton = (Button) inflatedView.findViewById(R.id.buttonBack);
        refreshButton = (Button) inflatedView.findViewById(R.id.buttonRefresh);


        webView.setWebViewClient(new MyClient());


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.loadUrl("http://www.google.com");


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = inputUrl.getText().toString();

                if (!url.startsWith("http://")) {
                    url = "http://" + url;
                }
                webView.loadUrl(url);

            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward())
                    webView.goForward();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack())
                    webView.goBack();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        return inflatedView;
    }
}