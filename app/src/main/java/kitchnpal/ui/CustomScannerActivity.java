package kitchnpal.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import kitchnpal.kitchnpal.R;

/**
 * Created by Mandy on 2017-03-22.
 */

public class CustomScannerActivity extends AppCompatActivity {
        private CaptureManager capture;
        private DecoratedBarcodeView barcodeScannerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_barcode_scanner);

            barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);

            capture = new CaptureManager(this, barcodeScannerView);
            capture.initializeFromIntent(getIntent(), savedInstanceState);
            capture.decode();
        }

        @Override
        protected void onResume() {
            super.onResume();
            capture.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            capture.onPause();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            capture.onDestroy();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            capture.onSaveInstanceState(outState);
        }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }
}
