package br.sofex.com.facialmap.Login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.sofex.com.facialmap.BuildConfig;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;
import pub.devrel.easypermissions.EasyPermissions;

public class CadastroLogin extends AppCompatActivity {

    Button BtnCadVoltar,BtnSelectFoto,BtnCadUser,BtnLogar;
    EditText EditNameUser,EditLoginUser,EditPassUser,EditPass2User,EditEmailUser;
    private AlertDialog alerta;
    Mensagem message = new Mensagem(CadastroLogin.this);
    Util util = new Util(CadastroLogin.this);
    private Uri capturedImageUri; private String selectedImagePath;
    private Bitmap bitmap; File file2; File fileAux;
    RelativeLayout rlFoto; ImageView imgSelec;
    TextView NomeFotoSele,TamFotoSele,LabelPassStrong;
    private static final int READ_REQUEST_CODE = 300;
    String keyboardkey;
    Integer keyboardCount = 0;
    LinearLayout linpassstrong;
    ProgressBar progressBarPass;
    RelativeLayout LinProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_login);
        setTitle("Facial Map - Cadastro de Login");

        EditNameUser = findViewById(R.id.EditNameUser);
        EditLoginUser = findViewById(R.id.EditLoginUser);
        EditPassUser = findViewById(R.id.EditPassUser);
        EditPass2User = findViewById(R.id.EditPass2User);
        EditEmailUser = findViewById(R.id.EditEmailUser);
        imgSelec = findViewById(R.id.imgSelec);
        NomeFotoSele = findViewById(R.id.NomeFotoSele);
        TamFotoSele = findViewById(R.id.TamFotoSele);
        rlFoto = findViewById(R.id.rlFoto);
        rlFoto.setVisibility(View.GONE);
        BtnSelectFoto = findViewById(R.id.BtnSelectFoto);
        LinProgress = findViewById(R.id.LinProgress);
        LinProgress.setVisibility(View.GONE);

        progressBarPass = findViewById(R.id.progressBarPass);
        LabelPassStrong = findViewById(R.id.LabelPassStrong);
        linpassstrong = findViewById(R.id.linpassstrong);
        linpassstrong.setVisibility(View.GONE);

        BtnCadVoltar = findViewById(R.id.Btn_Login_Cad_Voltar);
        BtnCadUser = findViewById(R.id.Btn_Login_Cad_User);
        final Mensagem mensagem = new Mensagem(CadastroLogin.this);

        BtnCadVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          Intent intent = new Intent(CadastroLogin.this, Login.class);
          startActivity(intent);
            }
        });

        BtnCadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(!EditNameUser.getText().toString().isEmpty() && !EditLoginUser.getText().toString().isEmpty() &&
               !EditPassUser.getText().toString().isEmpty() && !EditPass2User.getText().toString().isEmpty() &&
               !EditEmailUser.getText().toString().isEmpty()){

                    /*try {Boolean data = new doTaskCadLogin(CadastroLogin.this,
                            EditNameUser.getText().toString(),
                            EditLoginUser.getText().toString(),
                            EditPassUser.getText().toString(),
                            EditPass2User.getText().toString(),
                            EditEmailUser.getText().toString()).execute().get();
                        if(data == true){
                            Intent intent = new Intent(CadastroLogin.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }else{
                            message.WarningMsg("Nenhum registro foi encontrado\n com os dados informados");
                            //MsgWarning("Nenhum registro foi encontrado\n com os dados informados");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.e("FacialMap"," Error1 : "+e);
                        message.ErrorMsg(""+e);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e("FacialMap"," Error2 : "+e);
                        message.ErrorMsg(""+e);
                    }*/

                    if(!EditNameUser.getText().toString().isEmpty() && !EditLoginUser.getText().toString().isEmpty() &&
                      !EditPassUser.getText().toString().isEmpty() && !EditPass2User.getText().toString().isEmpty() &&
                      !EditEmailUser.getText().toString().isEmpty() && file2 != null){

                        if(util.isValidNome(EditNameUser.getText().toString()) == true &&
                            util.validarsenha(EditPassUser.getText().toString()) == true &&
                            util.validarsenha(EditPass2User.getText().toString()) == true &&
                            util.isValidEmail(EditEmailUser.getText().toString()) == true){

                            if(util.isValidEmailserver(EditEmailUser.getText().toString()) == true &&
                                EditPassUser.getText().toString().equals(EditPass2User.getText().toString())){

                                  /*usuarioDatabaseAdapter.insertEntry(
                                   EditNameUser.getText().toString(),
                                   EditLoginUser.getText().toString(),
                                   EditPassUser.getText().toString(),
                                   EditEmailUser.getText().toString(),
                                   selectedPath);*/

                                try {
                                     mensagem.SucessMsgCad(
                                     EditNameUser.getText().toString(),
                                     EditLoginUser.getText().toString(),
                                     EditPassUser.getText().toString(),
                                     EditEmailUser.getText().toString(),
                                     fullyReadFileToBytes(file2),
                                     file2.getName(),
                                     file2.length(),
                                     bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                limparcampos();
                            }

                        }

                        //sendEmail("woazaki@gmail.com",GenCodRecover());
                    }else{ mensagem.ErrorMsg("Todos os campos são obrigatórios"); }

                    //Intent intent = new Intent(Login.this, MainActivity.class);
                    //startActivity(intent);
                }else{
               message.WarningMsg("Campo(s) em branco.\nPor favor ,\n informe o(s) dado(s) necessário(s)");
           }
            }
        });
        BtnSelectFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //check if app has permission to access the external storage.
           if (EasyPermissions.hasPermissions(CadastroLogin.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i, 1);

                } else {
                    //If permission is not present request for the same.
                    EasyPermissions.requestPermissions(CadastroLogin.this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        });

        /*TODO: Eventos*/
        EditNameUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {if(!hasfocus){
              if(!EditNameUser.getText().toString().isEmpty()){
                 if(util.isValidNome(EditNameUser.getText().toString()) == false){
                     mensagem.ErrorMsg("Número e caracteres especiais ,\nnão são permitos neste campo");
                     EditNameUser.setText(null);
                 }
              }
           }}
        });
        EditPassUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyboardCount++;
                linpassstrong.setVisibility(View.VISIBLE);
                LinProgress.setVisibility(View.VISIBLE);
                keyboardkey = EditPassUser.getText().toString();
                String x = "";
                if(!keyboardkey.equals(" ")){
                    x = x + keyboardkey.replace(" ",""); // Retirar spaço
                    //Toast.makeText(Cadastro_Login.this, "charSequence : "+x, Toast.LENGTH_SHORT).show();

                    if(keyboardCount > 0 && keyboardCount <= 5){
                        LabelPassStrong.setText("Fraca");
                        progressBarPass.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    }
                    else if(keyboardCount > 5 && keyboardCount <= 8){
                        LabelPassStrong.setText("Média");
                        progressBarPass.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                    }
                    else if(keyboardCount > 8){
                        LabelPassStrong.setText("Forte");
                        progressBarPass.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                    }else {
                        linpassstrong.setVisibility(View.GONE);
                        LinProgress.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(keyboardkey.length() > 0 && keyboardkey.length() <= 5){
                    LabelPassStrong.setText("Fraca");
                    progressBarPass.setBackgroundColor(Color.RED);
                }
                else if(keyboardkey.length() > 5 && keyboardkey.length() <= 8){
                    LabelPassStrong.setText("Média");
                    progressBarPass.setBackgroundColor(Color.YELLOW);
                }
                else if(keyboardkey.length() > 8){
                    LabelPassStrong.setText("Forte");
                    progressBarPass.setBackgroundColor(Color.GREEN);
                }else {
                    linpassstrong.setVisibility(View.GONE);
                    LinProgress.setVisibility(View.GONE);
                }

            }
        });
        EditPassUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
            if(!hasfocus){
                    if(!EditPassUser.getText().toString().isEmpty()){
                        if(util.validarsenha(EditPassUser.getText().toString()) == false){
                            mensagem.ErrorMsg("Senha inválida !\nA senha deverá conter pelo menos :\n\n" +
                            "1 caracter Maiúsculo\n1 caracter Minúsculo\n1 dígito numérico\n1 caracter especial\n( @ $ % * ? _ - & )\nPelo menos 8 caracteres");
                            linpassstrong.setVisibility(View.GONE);
                            LinProgress.setVisibility(View.GONE);
                            EditPassUser.setText(null);
                        }
                    }
                }
            }
        });
        EditPass2User.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
           if(!hasfocus){
              if(!EditPass2User.getText().toString().isEmpty()){
                  if(!EditPass2User.getText().toString().equals(EditPassUser.getText().toString())){
                       mensagem.ErrorMsg("As senha não coincidem ");
                       EditPassUser.setText(null);
                       EditPass2User.setText(null);
                    }
                 }
               }
            }
        });


    }

    public void setTitle(String title){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void MsgError(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(CadastroLogin.this);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(CadastroLogin.this);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.error, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        alertadd.setView(view);
        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);



        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    private void MsgWarning(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(CadastroLogin.this);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(CadastroLogin.this);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.warning, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        alertadd.setView(view);
        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);



        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    private void MsgGenSerialkey(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(CadastroLogin.this);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(CadastroLogin.this);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.error, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        alertadd.setView(view);
        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);

        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            capturedImageUri = data.getData();
            Toast.makeText(getApplicationContext()," capturedImageUri :" + data.getData().toString(), Toast.LENGTH_LONG).show();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                selectedImagePath = getRealPathFromURIPath(capturedImageUri, CadastroLogin.this);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capturedImageUri);
                    Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();

                    File file  = new File("/data/data/br.sofex.com.facialmap/cache/"+"Captured.jpg");
                    file.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush(); fos.close();

                    //TODO: Rotaciona o arquivo de foto  para a orientação correta, e passa para o bitmap
                    Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));

                    //TODO: Salva o arquivo corrigido
                    util.saveBitmap(bit,file.getAbsolutePath());
                    file2 = file;

                    /*ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
                    int degree = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
                    Toast.makeText(getApplicationContext()," degree :" +degree, Toast.LENGTH_LONG).show();

                    selectedPath = file.getAbsolutePath();
                    //Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();*/

                    ///Bitmap bitmapFinal = null;
                    //ByteArrayToBitmap2(BitmapToByteArray(bitmap));
                    // file = new File(filePath2);
                    //FixImageRotate(ByteArrayToBitmap2(BitmapToByteArray(bitmap)));
                    //imgSelec.setImageBitmap(myBitmap);

                    /*createfolder();
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "Captured01.jpg");
                    file.createNewFile();

                    //Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
                    int degree = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
                    Toast.makeText(this, "degree : "+degree, Toast.LENGTH_SHORT).show();
*/

                    /* float degrees = -225;//rotation degree
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);
                    bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);*/

                    /*Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,85,bos2);
                    //Funciona 100%
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(file.toString());
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                        bitmap2 = rotateBitmap(bitmap1, orientation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    fileAux = util.saveBitmap(bitmap,"/data/data/br.sofex.com.facialmap/cache/"+"Captured.jpg");
                    rlFoto.setVisibility(View.VISIBLE);
                    imgSelec.setImageBitmap(bitmap);
                    NomeFotoSele.setText(file2.getName());
                    TamFotoSele.setText((file2.length())+" KB");


                    /*createfolder();
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "Captured01.jpg");
                    file.createNewFile();

                    //Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100*//*ignored for PNG*//*, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();*/

                    /*//Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100*//*ignored for PNG*//*, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    FixImageRotate(file);*/

                    //Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 400, true);
                    //imageview.setImageBitmap(bitmap);
                    //mensagem.DialogImageUser(bitmap);
                    //imgSelec.setImageBitmap(myBitmap);

                    /*File file = null;
                    Bitmap bitmapFinal = null;
                    ByteArrayToBitmap2(BitmapToByteArray(bitmap));
                    file = new File(filePath2);
                    if(file != null){
                        if(file.exists()){
                            Log.e("App" , " file :" + file.getAbsolutePath());
                            Bitmap myBitmap2 = BitmapFactory.decodeFile(filePath2);
                            FixImageRotate(file);
                            imgSelec.setImageBitmap(myBitmap2);
                            *//*Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            FixImageRotate(file);
                            Bitmap myBitmap2 = BitmapFactory.decodeFile(filePath2);
                            mensagem.ShowImageUser(myBitmap2);

                            imgSelec.setImageBitmap(myBitmap2);
                            NomeFotoSele.setText(file.getName());
                            double fileSize = file.length();
                            TamFotoSele.setText(fileSize+" KB");*//*
                        }
                    }*/

                } catch (IOException e) {
                    MsgError("Error CadLoginIOException "+e);
                    e.printStackTrace();
                }
            }else Toast.makeText(getApplicationContext()," Sem permissão !", Toast.LENGTH_LONG).show();

        }else {
            MsgError("Nenhuma foto selecionada\n Por favor selecione uma foto.");
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(CadastroLogin.this,Manifest.permission.READ_CALL_LOG)) {
            EasyPermissions.requestPermissions(CadastroLogin.this, "Este aplicativo precisa acessar o armazenamento interno. Por favor autorize o acesso.",
            READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            // now, user has denied permission permanently!
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Voçê negou o acesso ao aplicativo.\n" +
                "Você precissa aprovar a(s) permissão(ôes)", Snackbar.LENGTH_LONG).setAction("Alterar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                }
            }); snackbar.show();
        }
    }
    /*TODO: FIM ---------------- */

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
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
    /*TODO: FUNÇÃO PARA AJUSTAR A FOTO PARA A ORIENTAÇÃO CORRETA
     * TODO: INFELIZMENTE, AINDA NÂO CONSEGUI HERDA ESTA FUNÇÃO DE OUTRA CLASSE*/
    public Bitmap FixOrientatioImage(final Uri selectedImageUri) {
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

        }
        catch (IOException e) {
            MsgError("Error :"+e);
            Log.e("", "-- Error in setting image");
        }
        catch (OutOfMemoryError oom) {
            MsgError("Error :"+oom);
            Log.e("", "-- OOM Error in setting image");
        }
        catch (NullPointerException npe) {
            MsgError("Error :"+npe);
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }
    byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis= new FileInputStream(f);;
        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        }  catch (IOException e){
            throw e;
        } finally {
            fis.close();
        }

        return bytes;
    }
    public void limparcampos(){
        EditNameUser.getText().clear();
        EditLoginUser.getText().clear();
        EditPassUser.getText().clear();
        EditPass2User.getText().clear();
        EditEmailUser.getText().clear();
        file2 = null;
    }
}
