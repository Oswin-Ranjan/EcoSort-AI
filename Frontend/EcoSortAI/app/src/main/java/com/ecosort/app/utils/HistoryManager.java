package com.ecosort.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    public static final String PREF_NAME = "ecosort_history";
    public static final String KEY_HISTORY = "history";

    public static void saveHistory(
            Context context,
            String historyEntry) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE);

        Gson gson = new Gson();

        List<String> history =
                getHistory(context);

        history.add(0, historyEntry);

        String json =
                gson.toJson(history);

        prefs.edit()
                .putString(KEY_HISTORY, json)
                .apply();
    }

    public static List<String> getHistory(
            Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE);

        String json =
                prefs.getString(
                        KEY_HISTORY,
                        null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();

        Type type =
                new TypeToken<List<String>>() {
                }.getType();

        return gson.fromJson(
                json,
                type);
    }

    public static void clearHistory(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE);

        prefs.edit()
                .remove(KEY_HISTORY)
                .apply();
    }
}