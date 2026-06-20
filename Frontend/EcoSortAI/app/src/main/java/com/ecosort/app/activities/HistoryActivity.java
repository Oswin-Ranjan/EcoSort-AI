package com.ecosort.app.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ecosort.app.R;
import com.ecosort.app.utils.HistoryManager;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvHistory;
    private MaterialButton btnClearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        tvHistory = findViewById(R.id.tvHistory);
        btnClearHistory = findViewById(R.id.btnClearHistory);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Sustainability History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadHistory();

        btnClearHistory.setOnClickListener(v -> {

            HistoryManager.clearHistory(this);

            tvHistory.setText(
                    "📚 No Query History Yet\n\n" +
                            "Ask sustainability questions in EcoSort AI and your interactions will appear here."
            );
        });
    }

    private void loadHistory() {

        List<String> history =
                HistoryManager.getHistory(this);

        if (history.isEmpty()) {

            tvHistory.setText(
                    "📖\n\nNo Query History Yet\n\n" +
                            "Ask sustainability questions in EcoSort AI and your interactions will appear here."
            );

            return;
        }

        StringBuilder builder =
                new StringBuilder();

        int queryNumber = 1;

        for (int i = history.size() - 1; i >= 0; i--) {

            builder.append(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
            );

            builder.append("📝 Query #")
                    .append(queryNumber++)
                    .append("\n\n");

            builder.append(history.get(i));

            builder.append("\n\n");
        }

        tvHistory.setText(builder.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return true;
    }
}