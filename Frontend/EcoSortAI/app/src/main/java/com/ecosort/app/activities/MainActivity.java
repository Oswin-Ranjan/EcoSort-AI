package com.ecosort.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecosort.app.R;
import com.ecosort.app.models.QueryRequest;
import com.ecosort.app.models.QueryResponse;
import com.ecosort.app.network.ApiService;
import com.ecosort.app.network.RetrofitClient;
import com.ecosort.app.utils.HistoryManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnHistory;
    private MaterialButton btnAsk;

    private MaterialButton btnBattery;
    private MaterialButton btnPlastic;
    private MaterialButton btnEWaste;
    private MaterialButton btnCompost;

    private TextInputEditText etQuestion;

    private TextView tvAnswer;
    private TextView tvSource;
    private TextView tvEcoTip;

    private LinearProgressIndicator progressBar;

    private FloatingActionButton fabCamera;

    private LinearProgressIndicator confidenceBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        setupQuickActions();
        setupEcoTip();
        setupHistoryButton();
        setupAskButton();

        String question =
                getIntent().getStringExtra(
                        "question"
                );

        if (question != null) {

            etQuestion.setText(
                    question
            );

            etQuestion.post(() ->
                    btnAsk.performClick()
            );
        }

        fabCamera.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ScanActivity.class
                    );

            startActivity(intent);
        });
    }

    private void initializeViews() {

        btnHistory = findViewById(R.id.btnHistory);
        btnAsk = findViewById(R.id.btnAsk);

        btnBattery = findViewById(R.id.btnBattery);
        btnPlastic = findViewById(R.id.btnPlastic);
        btnEWaste = findViewById(R.id.btnEWaste);
        btnCompost = findViewById(R.id.btnCompost);

        etQuestion = findViewById(R.id.etQuestion);

        tvAnswer = findViewById(R.id.tvAnswer);
        tvSource = findViewById(R.id.tvSource);
        tvEcoTip = findViewById(R.id.tvEcoTip);

        progressBar = findViewById(R.id.progressBar);

        fabCamera = findViewById(R.id.fabCamera);

        confidenceBar = findViewById(R.id.confidenceBar);
        confidenceBar.setVisibility(View.GONE);
    }

    private void setupHistoryButton() {

        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            HistoryActivity.class
                    );

            startActivity(intent);
        });
    }

    private void setupAskButton() {

        btnAsk.setOnClickListener(v -> {

            String question =
                    etQuestion.getText()
                            .toString()
                            .trim();

            if (question.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Please enter a question",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            tvSource.setText("Searching Knowledge Base...");
            tvAnswer.setText("Generating recommendation...");

            QueryRequest request =
                    new QueryRequest(question);

            ApiService apiService =
                    RetrofitClient.getInstance()
                            .create(ApiService.class);

            apiService.askQuestion(request)
                    .enqueue(new Callback<QueryResponse>() {

                        @Override
                        public void onResponse(
                                Call<QueryResponse> call,
                                Response<QueryResponse> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                String source =
                                        formatSourceName(
                                                response.body().getSource()
                                        );

                                String answer =
                                        response.body().getAnswer();

                                int confidence =
                                        response.body().getConfidence();

                                if (confidence >= 90) {

                                    confidenceBar.setIndicatorColor(
                                            getColor(R.color.primary_green)
                                    );

                                } else if (confidence >= 70) {

                                    confidenceBar.setIndicatorColor(
                                            getColor(android.R.color.holo_orange_dark)
                                    );

                                } else {

                                    confidenceBar.setIndicatorColor(
                                            getColor(android.R.color.holo_red_dark)
                                    );
                                }

                                confidenceBar.setVisibility(View.VISIBLE);
                                confidenceBar.setProgressCompat(
                                        confidence,
                                        true
                                );

                                tvSource.setText(
                                        "📄 " + source +
                                                "\n🎯 Confidence: " +
                                                confidence + "%"
                                );

                                tvAnswer.setText(answer);

                                String historyEntry =
                                        "Question:\n"
                                                + question
                                                + "\n\nSource:\n"
                                                + source
                                                + "\nConfidence:\n"
                                                + confidence + "%"
                                                + "\n\nAnswer:\n"
                                                + answer;

                                HistoryManager.saveHistory(
                                        MainActivity.this,
                                        historyEntry
                                );

                            } else {

                                tvSource.setText("Error");

                                tvAnswer.setText(
                                        "Unable to retrieve response from server."
                                );
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<QueryResponse> call,
                                Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            confidenceBar.setVisibility(View.GONE);

                            tvSource.setText(
                                    "⚠️ AI Service Unavailable"
                            );

                            tvAnswer.setText(
                                    "The AI service is taking longer than expected.\n\n" +
                                            "Please try again in a few moments."
                            );
                        }
                    });
        });
    }

    private void setupQuickActions() {

        btnBattery.setOnClickListener(v ->
                etQuestion.setText(
                        "How should I dispose of a used battery?"
                ));

        btnPlastic.setOnClickListener(v ->
                etQuestion.setText(
                        "How do I recycle plastic bottles?"
                ));

        btnEWaste.setOnClickListener(v ->
                etQuestion.setText(
                        "How should I dispose of an old charger?"
                ));

        btnCompost.setOnClickListener(v ->
                etQuestion.setText(
                        "Can food waste be composted?"
                ));
    }

    private void setupEcoTip() {

        String[] tips = {

                "🌱 Compost food waste whenever possible.",
                "♻️ Clean plastic bottles before recycling.",
                "🔋 Never throw batteries into household bins.",
                "💻 Recycle old electronics responsibly.",
                "🌍 Small sustainable actions create big impact."
        };

        int random =
                (int) (Math.random() * tips.length);

        tvEcoTip.setText(
                tips[random]
        );
    }

    private String formatSourceName(String source) {

        switch (source) {

            case "Battery_Disposal_Guide.pdf":
                return "🔋 Battery Disposal Guide";

            case "Plastic_Recycling_Guide.pdf":
                return "♻️ Plastic Recycling Guide";

            case "E_Waste_Management.pdf":
                return "💻 E-Waste Management Guide";

            case "Composting_Guide.pdf":
                return "🍃 Composting Guide";

            case "Waste_Segregation_Guide.pdf":
                return "🗑️ Waste Segregation Guide";

            default:
                return source
                        .replace(".pdf", "")
                        .replace("_", " ");
        }
    }
}