package com.ecosort.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

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

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                                Bundle extras = result.getData().getExtras();

                                Bitmap imageBitmap = (Bitmap) extras.get("data");

                                imagePreview.setImageBitmap(imageBitmap);

                                detectWaste(imageBitmap);
                            }
        });
    }

    private void setupCaptureButton() {

        btnCapture.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
            else {
                openCamera();
            }
        });
    }

    private void openCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void setupCategoryButtons() {

        btnBattery.setOnClickListener(v -> {

            tvResult.setText("🔋 Battery Detected");
            tvRecommendation.setText("• Do not throw batteries into household bins.\n\n" + "• Take them to an authorized battery recycling center.\n\n" + "• Batteries contain hazardous chemicals that can contaminate soil and water.");
        });

        btnPlastic.setOnClickListener(v -> {

            tvResult.setText("♻️ Plastic Waste");
            tvRecommendation.setText("• Rinse plastic containers before recycling.\n\n" + "• Separate recyclable and non-recyclable plastics.\n\n" + "• Dispose through local recycling programs.");
        });

        btnEWaste.setOnClickListener(v -> {

            tvResult.setText("💻 E-Waste");
            tvRecommendation.setText("• Never dispose electronics with regular trash.\n\n" + "• Use certified e-waste collection centers.\n\n" + "• Remove personal data before disposal.");
        });

        btnOrganic.setOnClickListener(v -> {

            tvResult.setText("🍃 Organic Waste");
            tvRecommendation.setText("• Compost food scraps whenever possible.\n\n" + "• Organic waste can be converted into nutrient-rich compost.\n\n" + "• Avoid mixing organic waste with plastics.");
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private void detectWaste(Bitmap bitmap) {

        InputImage image = InputImage.fromBitmap(bitmap, 0);

        ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
                .process(image)
                .addOnSuccessListener(labels -> {

                    if (labels.isEmpty()) {

                        tvResult.setText("⚠️ Unable to Identify Waste");

                        tvRecommendation.setText(
                                "Try capturing a clearer image."
                        );

                        return;
                    }

                    ImageLabel bestLabel = labels.get(0);

                    String label =
                            bestLabel.getText();

                    String lowerLabel =
                            label.toLowerCase();

                    Intent intent =
                            new Intent(
                                    ScanActivity.this,
                                    MainActivity.class
                            );

                    String question;

                    /* Battery */

                    if (lowerLabel.contains("battery")) {

                        tvResult.setText(
                                "🔋 Battery Detected"
                        );

                        tvRecommendation.setText(
                                "🔋 Battery detected.\n\nFetching AI recommendation..."
                        );

                        question =
                                "How should I dispose of a used battery?";
                    }

                    /* Plastic */

                    else if (lowerLabel.contains("plastic")
                            || lowerLabel.contains("bottle")
                            || lowerLabel.contains("container")
                            || lowerLabel.contains("package")
                            || lowerLabel.contains("packaging")) {

                        tvResult.setText(
                                "♻️ Plastic Waste Detected"
                        );

                        tvRecommendation.setText(
                                "♻️ Plastic waste detected.\n\nFetching AI recommendation..."
                        );

                        question =
                                "How do I recycle plastic bottles?";
                    }

                    /* Organic */

                    else if (lowerLabel.contains("food")
                            || lowerLabel.contains("fruit")
                            || lowerLabel.contains("vegetable")) {

                        tvResult.setText(
                                "🍃 Organic Waste Detected"
                        );

                        tvRecommendation.setText(
                                "🍃 Organic waste detected.\n\nFetching AI recommendation..."
                        );

                        question =
                                "Can food waste be composted?";
                    }

                    /* E-Waste */

                    else if (lowerLabel.contains("phone")
                            || lowerLabel.contains("mobile")
                            || lowerLabel.contains("smartphone")
                            || lowerLabel.contains("laptop")
                            || lowerLabel.contains("computer")
                            || lowerLabel.contains("keyboard")
                            || lowerLabel.contains("mouse")
                            || lowerLabel.contains("charger")
                            || lowerLabel.contains("earphone")
                            || lowerLabel.contains("earbud")
                            || lowerLabel.contains("headphone")
                            || lowerLabel.contains("electronic")
                            || lowerLabel.contains("electronics")
                            || lowerLabel.contains("device")
                            || lowerLabel.contains("technology")) {

                        tvResult.setText(
                                "💻 E-Waste Detected"
                        );

                        tvRecommendation.setText(
                                "💻 " + label +
                                        " detected.\n\nFetching AI recommendation..."
                        );

                        question =
                                "How should I dispose of a "
                                        + label
                                        + "?";
                    }

                    /* Unknown */

                    else {

                        tvResult.setText(
                                "🤖 Detected: " + label
                        );

                        tvRecommendation.setText(
                                "🤖 " + label +
                                        " detected.\n\nFetching AI recommendation..."
                        );

                        question =
                                "How should I dispose of a "
                                        + label
                                        + "?";
                    }

                    intent.putExtra(
                            "question",
                            question
                    );

                    new Handler().postDelayed(() -> {

                        startActivity(intent);

                    }, 1500);
                })

                .addOnFailureListener(e -> {

                    tvResult.setText(
                            "⚠️ Detection Failed"
                    );

                    tvRecommendation.setText(
                            "Unable to analyze the image.\n\nPlease try capturing a clearer photo."
                    );
                });
    }
}