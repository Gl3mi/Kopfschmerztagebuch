package at.htlkaindorf.kopfschmerztagebuch.bl;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;

import at.htlkaindorf.kopfschmerztagebuch.R;

public class Operator implements View.OnClickListener {
    private final Context context;
    private final ArrayList<Integer> langList;
    private final String[] items;
    private final boolean[] selectedItems;
    private final TextView textView;
    private final String check;

    public Operator(Context context, ArrayList<Integer> langList, @NonNull String[] items, TextView textView, String check) {
        this.context = context;
        this.langList = langList;
        this.items = items;
        this.selectedItems = new boolean[items.length];
        this.textView = textView;
        this.check = check;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // set title
        switch (check) {
            case "kindOfPain":
                builder.setTitle("Schmerzart");
                break;
            case "painArea":
                builder.setTitle("Schmerzbereich");
                break;
            case "symptoms":
                builder.setTitle("Beigleitsymptome");
                break;
            default:
                builder.setTitle("");
                break;
        }

        // set dialog non cancelable
        builder.setCancelable(false);

        if (check.equals("kindOfPain")) {
            builder.setSingleChoiceItems(items, -1, (dialog, which) -> {
                langList.clear();
                langList.add(which);
            });
        } else {
            if(check.equals("painArea")){
                ImageView image = new ImageView(v.getContext());
                image.setImageResource(R.mipmap.ic_koepfe);
                image.setScaleX(3f);
                image.setScaleY(3f);
                builder.setView(image);
            }
            builder.setMultiChoiceItems(items, selectedItems, (dialog, which, isChecked) -> {
                if (isChecked) {
                    langList.add(which);
                    Collections.sort(langList);
                } else {
                    langList.remove(Integer.valueOf(which));
                }
            });
        }

        builder.setPositiveButton("Ok", (dialog, which) -> {
            StringBuilder stringBuilder = new StringBuilder();

            if (check.equals("kindOfPain") && !langList.isEmpty()) {
                stringBuilder.append(items[langList.get(0)]);
                langList.clear();
            } else {
                for (int i = 0; i < langList.size(); i++) {
                    // concat array value
                    stringBuilder.append(items[langList.get(i)]);
                    // check condition
                    if (i != langList.size() - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ");
                    }
                }
            }
            textView.setText(stringBuilder.toString());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        if (!check.equals("kindOfPain")) {
            builder.setNeutralButton("Clear All", (dialog, which) -> {
                for (int i = 0; i < selectedItems.length; i++) {
                    // remove all selection
                    selectedItems[i] = false;
                    // clear language list
                    langList.clear();
                    // clear text view value
                    textView.setText("");
                }
            });
        }
        builder.show();
    }
}
