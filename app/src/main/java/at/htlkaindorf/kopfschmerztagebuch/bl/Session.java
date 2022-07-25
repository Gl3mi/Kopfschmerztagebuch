package at.htlkaindorf.kopfschmerztagebuch.bl;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class Session {
    private final SharedPreferences sharedPreferences;
    private final String MYPREFS = "myprefs";
    private final Gson gson = new Gson();
    private final SharedPreferences.Editor editor;

    public Session(@NonNull Context context) {
        this.sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void putString(List<Entry> entries) {
        String json = gson.toJson(entries);
        editor.putString("data", json).apply();
    }

    public List<Entry> getEntries(String key) {
        if (!sharedPreferences.getString(key, " ").equals("")) {
            String json = sharedPreferences.getString(key, " ");
            Type type = new TypeToken<List<Entry>>() {
            }.getType();

            return gson.fromJson(json, type);
        } else {
            List<Entry> placeholder = new ArrayList<>();
            placeholder.add(new Entry("Schmerzart", "", 0, "", "",
                    "", "", "", "", false));
            return placeholder;
        }
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}
