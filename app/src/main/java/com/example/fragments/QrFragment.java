package com.example.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application_chat.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class QrFragment extends Fragment  {

        private TextView scannedTextView;
        private Button scan;

        public QrFragment() {
            // Required empty public constructor
        }


        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_qr, container, false);
            scannedTextView = view.findViewById(R.id.textQr);

            scan = view.findViewById(R.id.Scan);
            scannedTextView.setBackground(null);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = IntentIntegrator.forSupportFragment(QrFragment.this);
                    integrator.setOrientationLocked(false);
                    integrator.setPrompt("Scan QR code");
                    integrator.setBeepEnabled(false);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                    integrator.setCameraId(0);  // Use a specific camera of the device
                    integrator.initiateScan();
                }
            });

            return view;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String scannedContent = result.getContents();
                    if (scannedContent.startsWith("http")) {
                        scannedTextView.setText(null);
                        scannedTextView.setBackground(null);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedContent));
                        startActivity(browserIntent);
                    } else {
                        scannedTextView.setText(scannedContent);
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }