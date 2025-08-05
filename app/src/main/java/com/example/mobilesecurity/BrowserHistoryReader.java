package com.example.mobilesecurity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;

public class BrowserHistoryReader {
    public static String getBrowserHistory(Context context) {
        StringBuilder historyBuilder = new StringBuilder();

        Uri uri = Uri.parse("content://browser/bookmarks");
        String[] projection = new String[]{"title", "url", "date"};

        try (Cursor cursor = context.getContentResolver().query(uri, projection,
                "bookmark = 0", null, "date DESC")) {
            if (cursor != null && cursor.moveToFirst()) {
                int titleIdx = cursor.getColumnIndex("title");
                int urlIdx = cursor.getColumnIndex("url");

                int count = 0;
                do {
                    String title = cursor.getString(titleIdx);
                    String url = cursor.getString(urlIdx);
                    historyBuilder.append("• ").append(title).append(" — ").append(url).append("\n");
                    count++;
                    if (count >= 50) break; // Limit to 50 entries
                } while (cursor.moveToNext());
            } else {
                historyBuilder.append("No accessible browser history found.\n");
            }
        } catch (Exception e) {
            historyBuilder.append("Error accessing browser history: ").append(e.getMessage()).append("\n");
        }

        return historyBuilder.toString();
    }
}
