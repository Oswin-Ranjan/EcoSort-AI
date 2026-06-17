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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        setupQuickActions();
        setupEcoTip();
        setupHistoryButton();
        setupAskButton();

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

                                tvSource.setText(
                                        "📄 " + source
                                );

                                tvAnswer.setText(answer);

                                String historyEntry =
                                        "Question:\n"
                                                + question
                                                + "\n\nSource:\n"
                                                + source
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

                            tvSource.setText("Connection Error");

                            tvAnswer.setText(
                                    t.getMessage()
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

        return source
                .replace(".txt", "")
                .replace(".pdf", "")
                .replace("_", " ");
    }
}