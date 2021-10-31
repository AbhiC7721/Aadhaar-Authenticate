package com.pointo.myaadharverifier;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import java.nio.ByteBuffer;

import static android.graphics.ImageFormat.YUV_420_888;
import static android.graphics.ImageFormat.YUV_422_888;
import static android.graphics.ImageFormat.YUV_444_888;

public class ImageAnalyser implements ImageAnalysis.Analyzer {

    Context context;
    ProcessCameraProvider processCameraProvider;

    public ImageAnalyser(Context context, ProcessCameraProvider processCameraProvider){
        this.context=context;
        this.processCameraProvider=processCameraProvider;
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {

        //check for image format compatibility

        if(image.getFormat()== YUV_420_888 || image.getFormat() == YUV_422_888 || image.getFormat() == YUV_444_888) {
            //convert image to byte[]
            ByteBuffer byteBuffer=image.getPlanes()[0].getBuffer();
            byte[] imageData=new byte[byteBuffer.capacity()];
            byteBuffer.get(imageData);

            //create a PlanarYUVLuminanceSource object
            //then pass it to a HybridBinarizer object to generate a BinaryBitmap
            //so that ZXing can analyse

            PlanarYUVLuminanceSource source=new PlanarYUVLuminanceSource(imageData,
                    image.getWidth(),
                    image.getHeight(),
                    0,0,
                    image.getWidth(), image.getHeight(),
                    false);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            try{
                Result result= new QRCodeMultiReader().decode(binaryBitmap);
                Intent intent=new Intent(context, QRCodeFoundActivity.class);
                intent.putExtra("qr",result.getText());

                Log.d("status","QR-->"+result.getText());
                processCameraProvider.unbindAll();
                context.startActivity(intent);
                image.close();

            }
            catch(Exception e){

            }

            image.close();
        }
    }
}
