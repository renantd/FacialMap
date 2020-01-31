package br.sofex.com.facialmap.Util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.sofex.com.facialmap.Mensagem.Mensagem;

public class CapturePhoto {

    Context mContext;
    private Uri capturedImageUri;
    ImageView img1;

    public CapturePhoto(Context context){
        this.mContext = context;
    }


    public void SaveFotoFix(Uri capturedImageUri, String Destino, String NomeArquivoExtencao){
        Bitmap bitmap = null; Bitmap scaledBitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), capturedImageUri);
            //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

            File file  = new File(Destino + NomeArquivoExtencao);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata); fos.flush(); fos.close();

            //Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            scaledBitmap = Bitmap.createScaledBitmap(bit, bit.getWidth(), bit.getHeight(), true);
            saveBitmap(scaledBitmap,file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Bitmap UploadedFileToBitmap(Uri capturedImageUri, String Destino, String NomeArquivoExtencao){
        Bitmap bitmap = null; Bitmap scaledBitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), capturedImageUri);
            //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

            File file  = new File(Destino + NomeArquivoExtencao);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata); fos.flush(); fos.close();

            //Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            scaledBitmap = Bitmap.createScaledBitmap(bit, bit.getWidth(), bit.getHeight(), true);
            saveBitmap(scaledBitmap,file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }
    public Bitmap UploadedFileToBitmapResize(Uri capturedImageUri, String Destino, String NomeArquivoExtencao){
        Bitmap bitmap = null; Bitmap scaledBitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), capturedImageUri);
            //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

            File file  = new File(Destino + NomeArquivoExtencao);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata); fos.flush(); fos.close();

            //Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            scaledBitmap = Bitmap.createScaledBitmap(bit, 1133, 1280, true);
            saveBitmap(scaledBitmap,file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }
    public File UploadedFileResize(Uri capturedImageUri, String Destino, String NomeArquivoExtencao, int Largura , int Altura){
        Bitmap bitmap = null; Bitmap scaledBitmap = null; File file2 = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), capturedImageUri);
            //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

            File file  = new File(Destino + NomeArquivoExtencao);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata); fos.flush(); fos.close();

            //Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            scaledBitmap = Bitmap.createScaledBitmap(bit, Largura, Altura, true);
            saveBitmap(scaledBitmap,file.getAbsolutePath());
            file2 = file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file2;
    }

    public String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        String realPath = "";
        if (cursor == null) {
            realPath = contentURI.getPath();
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            realPath = cursor.getString(idx);
        }
        if (cursor != null) {
            cursor.close();
        }

        return realPath;
    }
    public Bitmap UploadedFileToBitmapCamera(Uri capturedImageUri, String Destino, String NomeArquivoExtencao){
        Bitmap bitmap = null; Bitmap scaledBitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), capturedImageUri);
            //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

            File file  = new File(Destino + NomeArquivoExtencao);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata); fos.flush(); fos.close();

            //Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
            Bitmap bit = FixOrientatioCameraCustom(Uri.fromFile(new File(file.getAbsolutePath())));
            scaledBitmap = Bitmap.createScaledBitmap(bit, bit.getWidth(), bit.getHeight(), true);
            saveBitmap(scaledBitmap,file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }
    public Bitmap FixOrientatioImage(final Uri selectedImageUri) {
        Mensagem mensagem = new Mensagem(mContext);
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedImageUri);
            ExifInterface exif = new ExifInterface(selectedImageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;

                default:
                    angle = 0;
                    break;
            }

            Matrix mat = new Matrix();

            if (angle == 0 && bm.getWidth() > bm.getHeight()) {
                mat.postRotate(270);
                if(bm.getWidth() >= 3840 && bm.getHeight() >= 2160){
                    mat.postRotate(90);
                }if(bm.getWidth() >= 2560 && bm.getHeight() >= 1444){
                    Log.e("App" , " Foto :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+(bm.getWidth() % bm.getHeight()));
                    if(bm.getWidth() / bm.getHeight() != 0){
                        mat.postRotate(angle);
                    }
                }else{

                    if(bm.getWidth() / bm.getHeight() != 0){
                        mat.postRotate(-180);
                        Log.e("App" , " Foto2 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+(bm.getWidth() % bm.getHeight()));
                    }
                    else if(bm.getWidth() % 4 == 0 &&  bm.getHeight() % 3 == 0){
                        mat.postRotate(-270);
                        Log.e("App" , " Foto3 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+(bm.getWidth() % bm.getHeight()));
                    }
                }
            }
            else{
                if(angle == 0 && bm.getWidth() == bm.getHeight()){
                    mat.postRotate(270);
                    Log.e("App" , " teste12 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+bm.getWidth() % bm.getHeight());
                }
                else{
                    Log.e("App" , " teste123 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+bm.getWidth() % bm.getHeight());
                    mat.postRotate(angle);
                }

            }

            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);

        }
        catch (IOException e) {
            mensagem.ErrorMsg("Error :"+e);
            Log.e("", "-- Error in setting image");
        }
        catch (OutOfMemoryError oom) {
            mensagem.ErrorMsg("Error :"+oom);
            Log.e("", "-- OOM Error in setting image");
        }
        catch (NullPointerException npe) {
            mensagem.ErrorMsg("Error :"+npe);
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }
    public Bitmap FixOrientatioCameraCustom(final Uri selectedImageUri) {
        Mensagem mensagem = new Mensagem(mContext);
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedImageUri);
            ExifInterface exif = new ExifInterface(selectedImageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;

                default:
                    angle = 90;
                    break;
            }

            Matrix mat = new Matrix();

            if (angle == 0 && bm.getWidth() > bm.getHeight()) {
                mat.postRotate(angle);
            }
            else{
                if(angle == 0 && bm.getWidth() == bm.getHeight()){
                    mat.postRotate(270);
                    Log.e("App" , " teste12 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+bm.getWidth() % bm.getHeight());
                }
                else{
                    Log.e("App" , " teste123 :" + angle+ " getWidth "+bm.getWidth()+ " getHeight "+bm.getHeight()+" Teste :"+bm.getWidth() % bm.getHeight());
                    mat.postRotate(angle);
                }

            }

            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);

        }
        catch (IOException e) {
            mensagem.ErrorMsg("Error :"+e);
            Log.e("", "-- Error in setting image");
        }
        catch (OutOfMemoryError oom) {
            mensagem.ErrorMsg("Error :"+oom);
            Log.e("", "-- OOM Error in setting image");
        }
        catch (NullPointerException npe) {
            mensagem.ErrorMsg("Error :"+npe);
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }
    public File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public  Uri getImageUri(Activity inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
