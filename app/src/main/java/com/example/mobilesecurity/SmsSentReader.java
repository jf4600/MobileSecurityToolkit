package com.example.mobilesecurity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

public class SmsSentReader {
    public static String getSentSms(Context context) {
        StringBuilder smsBuilder = new StringBuilder();
        Uri uri = Telephony.Sms.Sent.CONTENT_URI;

        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, Telephony.Sms.DEFAULT_SORT_ORDER)) {
            if (cursor != null && cursor.moveToFirst()) {
                int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
                int dateIndex = cursor.getColumnIndex(Telephony.Sms.DATE);

                int count = 0;
                do {
                    String address = cursor.getString(addressIndex);
                    String body = cursor.getString(bodyIndex);
                    long date = cursor.getLong(dateIndex);

                    smsBuilder.append("To: ").append(address)
                            .append("\nMessage: ").append(body)
                            .append("\nDate: ").append(date)
                            .append("\n\n");

                    count++;
                } while (cursor.moveToNext() && count < 10); // Limit to 10
            } else {
                smsBuilder.append("No sent SMS messages found.");
            }
        } catch (SecurityException e) {
            smsBuilder.append("Permission denied or SMS access blocked.");
        } catch (Exception e) {
            smsBuilder.append("Error reading SMS: ").append(e.getMessage());
        }

        return smsBuilder.toString();
    }
}
