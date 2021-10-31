package com.pointo.myaadhar.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

    private int size;
    private Bitmap bitmap;
    public static QRCodeGenerator qrCodeGenerator;

    public static QRCodeGenerator getInstance(){        // singleton pattern
        if(qrCodeGenerator==null)
            qrCodeGenerator = new QRCodeGenerator();

        return qrCodeGenerator;
    }

    public Bitmap generateQRCode(String text){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        size = 512;
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text,BarcodeFormat.QR_CODE, size, size);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++){
                    if(bitMatrix.get(i,j))
                        bitmap.setPixel(i,j,Color.BLACK);
                    else
                        bitmap.setPixel(i,j,Color.WHITE);
                }

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}