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

            getSupportActionBar().setTitle("Query History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadHistory();

        btnClearHistory.setOnClickListener(v -> {

            HistoryManager.clearHistory(this);

            tvHistory.setText(
                    "📚 No Queries Yet\n\n" +
                            "Start asking sustainability questions to build your history."
            );
        });
    }

    private void loadHistory() {

        List<String> history =
                HistoryManager.getHistory(this);

        if (history.isEmpty()) {

            tvHistory.setText(
                    "📚 No Queries Yet\n\n" +
                            "Start asking sustainability questions to build your history."
            );

            return;
        }

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0; i < history.size(); i++) {

            builder.append("━━━━━━━━━━━━━━━━━━━━━━\n\n");

            builder.append("Query #")
                    .append(i + 1)
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