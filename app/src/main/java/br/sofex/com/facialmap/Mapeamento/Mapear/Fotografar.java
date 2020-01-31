package br.sofex.com.facialmap.Mapeamento.Mapear;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.sofex.com.facialmap.BuildConfig;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Preview.PreviewCamera;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;
import pub.devrel.easypermissions.EasyPermissions;

public class Fotografar extends AppCompatActivity {

    Button BtnFotografar, BtnVoltar, BtnMapProximo, BtnMapVoltarPosFoto;
    LinearLayout Lin1, LinPosFoto1;
    ImageView FotoResult;
    private static final int READ_REQUEST_CODE = 200;
    static final int REQUEST_IMGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int CAM_REQUEST = 1;
    String mCurrentPhotoPath;
    private String pictureFilePath;
    private Bitmap mImageBitmap;

    Util util = new Util(Fotografar.this);
    Mensagem mensagem = new Mensagem(Fotografar.this);
    private AlertDialog alerta;
    String FileName = "";
    String FilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapear);

        /*TODO: importação de classes internas*/
        util.setTitleBarApp("Facial Map - Fotografar Paciente", getSupportActionBar(), Fotografar.this);
        FotoResult = findViewById(R.id.FotoResult);

        Lin1 = findViewById(R.id.Lin1);
        LinPosFoto1 = findViewById(R.id.LinPosFoto1);
        LinPosFoto1.setVisibility(View.GONE);

        BtnMapProximo = findViewById(R.id.BtnMapProximo);
        BtnMapVoltarPosFoto = findViewById(R.id.BtnMapVoltarPosFoto);
        BtnFotografar = findViewById(R.id.BtnMapearFotografar);
        BtnVoltar = findViewById(R.id.BtnMapearVoltar);
        BtnFotografar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //check if app has permission to access the external storage.
          if (EasyPermissions.hasPermissions(Fotografar.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Intent intent = new Intent(Fotografar.this, PreviewCamera.class);
                    startActivity(intent);
                } else {
                    //If permission is not present request for the same.
                    EasyPermissions.requestPermissions(Fotografar.this, "Este aplicativo precisa acessar a câmera para prosseguir . Por favor autorize o acesso a câmera.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                }
            }
        });
        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Fotografar.this, Menu_Mapeamento.class);
            startActivity(intent);
            }
        });
        BtnMapVoltarPosFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            LinPosFoto1.setVisibility(View.GONE);
            Lin1.setVisibility(View.VISIBLE);
            FotoResult.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logotipo));
            Intent intent = new Intent(Fotografar.this, MainActivity.class);
            startActivity(intent);
            }
        });
        BtnMapProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String Foto = getIntent().getStringExtra("FotoSalva");
            String FotoNome = getIntent().getStringExtra("FotoSalvaNome");
            Log.e("App1", "FotoSalva123 :" + FotoNome);
            if (Foto != null) {
                    Intent intent = new Intent(Fotografar.this, Marcar_Pontos.class);
                    intent.putExtra("FotoCorreta", Foto);
                    intent.putExtra("FotoNome", FotoNome);
                    startActivity(intent);
                } else {
                    mensagem.ErrorMsg("Foto Inválida !");
                }
            }
        });

    }

    private void sendTakePictureIntent() {

        //CriarPasta();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(cameraIntent, REQUEST_IMGE_CAPTURE);

            File pictureFile = null;
            try {
                //pictureFile = getPictureFile();
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Photo file can't be created, please try again . Error: " + ex, Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "br.sofex.com.mapdb.fileprovider", pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMGE_CAPTURE);

            }
        }
    }

    // storage/self/primary/Android/data/br.sofex.com.fotografar/files/Pictures
    /*Local Android Emulador -> Pasta/Imagens*/
    /*Local Android S8 -> Meus Arquivos/Amrmazenamento Interno/Android/data/br.com.sofex.fotografar/files/Pictures */
    private File getPictureFile() throws IOException {
        File image = null;
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        //String pictureFile = "ZOFTINO_" + timeStamp;
        String pictureFile = "Imagem_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir.exists()) {
            image = File.createTempFile(pictureFile, ".jpg", storageDir);
            pictureFilePath = image.getAbsolutePath();
        } else {
            storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File f = new File(storageDir, "");
            f.mkdirs();
            image = File.createTempFile(pictureFile, ".jpg", storageDir);
            pictureFilePath = image.getAbsolutePath();
        }

        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // now, you have permission go ahead
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Fotografar.this,
                    Manifest.permission.READ_CALL_LOG)) {
                EasyPermissions.requestPermissions(Fotografar.this, "Este aplicativo precisa acessar a câmera para prosseguir . Por favor autorize o acesso a câmera.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                // now, user has denied permission permanently!
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Voçê negou o acesso ao aplicativo.\n" +
                        "Você precissa aprovar a(s) permissão(ôes)", Snackbar.LENGTH_LONG).setAction("Alterar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                });
                snackbar.show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (requestCode == CAM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                //image.setImageBitmap(bitmap);
                /*
                 * The Android Camera application encodes the photo in the return Intent delivered to onActivityResult()
                 * as a small Bitmap in the extras, under the key "data".
                 */
                //dispatchTakePictureIntent();
                File imgFile = new File(pictureFilePath);
                if (imgFile.exists()) {
                    SaveImagePreview(imgFile);
                    /*if(mFile.exists()){
                      mensagem.ErrorMsg("Erro em remover a pasta !");
                    }else{
                      mensagem.SucessMsg("Imagem Capturada com sucesso !");
                    }*/
                    //FaceDetector(bitmap);
                } else {
                    mensagem.ErrorMsg("Error em criar a foto");
                }
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getFilesDir();
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        FileOutputStream out = new FileOutputStream(image);
        out.close();

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //Toast.makeText(this, " Path : "+mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
        Log.v("App", " Path1 " + mCurrentPhotoPath);
        return image;
    }

    /*TODO: FUNÇÃO DE SALVAR BITMAP EM ARQUIVO DE IMAGEM*/
    private File saveBitmap(Bitmap bitmap, String path) {
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

    /*TODO: FUNÇÃO DE RETORNO DE BITMAP PARA PORTRAIT*/
    private Bitmap FixOrientatioImage(final Uri selectedImageUri) {
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

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

            if (angle == 0 && bm.getWidth() > bm.getHeight())
                mat.postRotate(270);
            else
                mat.postRotate(angle);

            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);

        } catch (IOException e) {
            Log.e("", "-- Error in setting image");
        } catch (OutOfMemoryError oom) {
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }

    /*TODO: FUNÇÃO PARA COPIAR ARQUIVOS */
    public static void copyFromTo(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public void deleteFolder(File folder) throws IOException {
        if (folder.isDirectory()) {
            for (File ct : folder.listFiles()) {
                deleteFolder(ct);
            }
        }
        if (!folder.delete()) {
            throw new FileNotFoundException("Unable to delete: " + folder);
        }
    }

    private void SaveImagePreview(final File fileimage) {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.show_foto, null);
        //definimos para o botão do layout um clickListener
        final ImageView img1 = view.findViewById(R.id.show_img1);

        //TODO: Rotaciona o arquivo de foto  para a orientação correta, e passa para o bitmap
        final Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(fileimage.getAbsolutePath())));

        //TODO: Salva o arquivo corrigido para pasta /storage/self/primary/Android/data/br.sofex.com.mapdb/files/Pictures
        saveBitmap(bit, fileimage.getAbsolutePath());
        img1.setImageBitmap(bit);

        Button BtnPreviewSave = view.findViewById(R.id.BtnPreviewSave);
        BtnPreviewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Copia o arquivo corrigido para pasta /data/data/br.sofex.com.mapdb/cache
                try {
                    File from = fileimage;
                    File to = new File("/data/data/br.sofex.com.facialmap/cache", "Preview.jpg");
                    copyFromTo(from, to);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //TODO: Deleta a pasta com a imagem selecionada
                File mFile = new File("/data/data/br.sofex.com.facialmap/cache" + "Preview.jpg");
                try {
                    deleteFolder(mFile);
                } catch (IOException e) {
                    Toast.makeText(Fotografar.this, "Unable to delete folder", Toast.LENGTH_SHORT).show();
                }
                if (mFile.exists()) {
                    mensagem.ErrorMsg("Erro em remover a pasta !");
                } else {
                    mensagem.SucessMsg("Imagem Salva com sucesso !");
                }
                FotoResult.setImageBitmap(bit);
                alerta.dismiss();
            }
        });
        Button BtnPreviewCancel = view.findViewById(R.id.BtnPreviewCancel);
        BtnPreviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File mFile = new File("/data/data/br.sofex.com.facialmap/cache/Preview.jpg");
                Log.e("App1", "Path :" + mFile);
                if (mFile.exists()) {
                    try {
                        deleteFolder(mFile);
                        alerta.dismiss();
                    } catch (IOException e) {
                        Toast.makeText(Fotografar.this, "Unable to delete folder", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mensagem.SucessMsg("Pasta de preview não existe !");
                    alerta.dismiss();
                }
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String Foto = getIntent().getStringExtra("FotoSalva");
        if (Foto != null) {
            Bitmap bit = BitmapFactory.decodeFile(Foto);
            FotoResult.setImageBitmap(bit);
            LinPosFoto1.setVisibility(View.VISIBLE);
            Lin1.setVisibility(View.GONE);
        } else {
            LinPosFoto1.setVisibility(View.GONE);
            Lin1.setVisibility(View.VISIBLE);
        }
    }
}
