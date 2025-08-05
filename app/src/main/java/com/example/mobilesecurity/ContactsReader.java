package com.example.mobilesecurity;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactsReader {
    public static String getContacts(Context context) {
        StringBuilder contactsBuilder = new StringBuilder();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(
                        cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactsBuilder.append("Name: ").append(name)
                        .append(", Number: ").append(number).append("\n");
            }
            cursor.close();
        } else {
            contactsBuilder.append("No contacts found or access denied.\n");
        }

        return contactsBuilder.toString();
    }
}
