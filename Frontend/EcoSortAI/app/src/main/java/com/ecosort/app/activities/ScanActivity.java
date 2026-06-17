package com.ecosort.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ecosort.app.R;
import com.google.android.material.button.MaterialButton;

public class ScanActivity extends AppCompatActivity {

    private ImageView imagePreview;

    private TextView tvResult;
    private TextView tvRecommendation;

    private MaterialButton btnCapture;

    private MaterialButton btnBattery;
    private MaterialButton btnPlastic;
    private MaterialButton btnEWaste;
    private MaterialButton btnOrganic;

    private static final int CAMERA_PERMISSION_CODE = 100;

    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initializeViews();
        setupCameraLauncher();
        setupCaptureButton();
        setupCategoryButtons();
    }

    private void initializeViews() {

        imagePreview = findViewById(R.id.imagePreview);

        tvResult = findViewById(R.id.tvResult);
        tvRecommendation = findViewById(R.id.tvRecommendation);

        btnCapture = findViewById(R.id.btnCapture);

        btnBattery = findViewById(R.id.btnBattery);
        btnPlastic = findViewById(R.id.btnPlastic);
        btnEWaste = findViewById(R.id.btnEWaste);
        btnOrganic = findViewById(R.id.btnOrganic);
    }

    private void setupCameraLauncher() {

        cameraLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if (result.getResultCode() == RESULT_OK
                                    && result.getData() != null) {

                                Bundle extras =
                                        result.getData().getExtras();

                                Bitmap imageBitmap =
                                        (Bitmap) extras.get("data");

                                imagePreview.setImageBitmap(
                                        imageBitmap
                                );

                                tvResult.setText(
                                        "✅ Image Captured Successfully"
                                );
                            }
                        });
    }

    private void setupCaptureButton() {

        btnCapture.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        CAMERA_PERMISSION_CODE
                );

            } else {

                openCamera();
            }
        });
    }

    private void openCamera() {

        Intent cameraIntent =
                new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );

        cameraLauncher.launch(
                cameraIntent
        );
    }

    private void setupCategoryButtons() {

        btnBattery.setOnClickListener(v -> {

            tvResult.setText("🔋 Battery Detected");

            tvRecommendation.setText(
                    "• Do not throw batteries into household bins.\n\n" +
                            "• Take them to an authorized battery recycling center.\n\n" +
                            "• Batteries contain hazardous chemicals that can contaminate soil and water."
            );
        });

        btnPlastic.setOnClickListener(v -> {

            tvResult.setText("♻️ Plastic Waste");

            tvRecommendation.setText(
                    "• Rinse plastic containers before recycling.\n\n" +
                            "• Separate recyclable and non-recyclable plastics.\n\n" +
                            "• Dispose through local recycling programs."
            );
        });

        btnEWaste.setOnClickListener(v -> {

            tvResult.setText("💻 E-Waste");

            tvRecommendation.setText(
                    "• Never dispose electronics with regular trash.\n\n" +
                            "• Use certified e-waste collection centers.\n\n" +
                            "• Remove personal data before disposal."
            );
        });

        btnOrganic.setOnClickListener(v -> {

            tvResult.setText("🍃 Organic Waste");

            tvRecommendation.setText(
                    "• Compost food scraps whenever possible.\n\n" +
                            "• Organic waste can be converted into nutrient-rich compost.\n\n" +
                            "• Avoid mixing organic waste with plastics."
            );
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                openCamera();
            }
        }
    }
}