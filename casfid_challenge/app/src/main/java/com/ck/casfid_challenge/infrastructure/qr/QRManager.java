package com.ck.casfid_challenge.infrastructure.qr;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.presentation.qr.UserQRScannerActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import android.graphics.Bitmap;

public class QRManager {

    public static Bitmap generateQRCodeFromUser(User user, int size) {
        String vCard = generateVCard(user);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(vCard, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateVCard(User user) {
        return "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:" + user.fullName +"\n" +
                "TEL:" + user.phone + "\n" +
                "EMAIL:" + user.email + "\n" +
                "END:VCARD";
    }

    public static void startQRScanWithCamera(Activity activity) {
        Intent intent = new Intent(activity, UserQRScannerActivity.class);
        activity.startActivity(intent);
    }

    public static Intent buildInsertContactIntentFromQR(String content) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        if (content != null && content.startsWith("BEGIN:VCARD")) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, extractField(content, "FN:"));
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, extractField(content, "TEL:"));
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, extractField(content, "EMAIL:"));
        }

        return intent;
    }

    private static String extractField(String vcard, String key) {
        for (String line : vcard.split("\n")) {
            if (line.startsWith(key)) {
                return line.replace(key, "").trim();
            }
        }
        return "";
    }
}

