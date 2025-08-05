package com.example.mobilesecurity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.ClipData;

public class ClipboardReader {
    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                CharSequence text = clipData.getItemAt(0).getText();
                if (text != null) {
                    return text.toString();
                }
            }
        }
        return "Clipboard is empty or inaccessible";
    }
}
