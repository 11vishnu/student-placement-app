package com.example.placementapp.ui.resume;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.placementapp.ui.company.EditCompanyFragmentArgs;
import com.github.barteksc.pdfviewer.PDFView;
import com.example.placementapp.databinding.FragmentViewPdfBinding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewPdfFragment  extends Fragment {

    String urls;
    PDFView pdfView;
    ProgressDialog dialog;
    FragmentViewPdfBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewPdfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Firstly we are showing the progress
        // dialog when we are loading the pdf
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Loading..");
        dialog.show();

        // getting url of pdf using getItentExtra
        String url = ViewPdfFragmentArgs.fromBundle(getArguments()).getPdfUrl();

        new RetrivePdfStream().execute(url);

    }

    // Retrieving the pdf file using url
    @SuppressLint("StaticFieldLeak")
    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                // adding url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // if url connection response code is 200 means ok the execute
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            // if error return null
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {
            binding.pdfViewer.fromStream(inputStream).load();
            dialog.dismiss();
        }
    }
}

