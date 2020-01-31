package br.sofex.com.facialmap.Mapeamento.Carregar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetListDataHoraByNP;
import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;
import pub.devrel.easypermissions.EasyPermissions;

public class Upload_Foto_Mapa extends AppCompatActivity {

    Button BtnVoltar,BtnUpload;
    ImageView img_foto;
    private static final int READ_REQUEST_CODE = 300;
    private Bitmap bitmap;
    private Uri capturedImageUri;
    private String selectedImagePath;
    Util util = new Util(Upload_Foto_Mapa.this);
    Mensagem mensagem = new Mensagem(Upload_Foto_Mapa.this);
    Spinner SpinDataUpFotoMapa,SpinHoraUpFotoMapa;
    TextView MapaUpSelectPac;

    //TODO: Variaveis do Tipo Java
    SharedPreferences    sharedPreferences;
    String               MyPREFERENCES = "MyPrefs" ;
    String               Selectedpaciente = "";
    Long                 CodUser = 0l;

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog     alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remap__upload);

        util.setTitleBarApp("Facial Map - Upload de foto",getSupportActionBar(), Upload_Foto_Mapa.this);

        BtnVoltar           = findViewById(R.id.BtnVoltar);
        BtnUpload           = findViewById(R.id.Btn_Remap_Upload);
        img_foto            = findViewById(R.id.img_foto);
        SpinDataUpFotoMapa  = findViewById(R.id.SpinDataUpFotoMapa);
        SpinHoraUpFotoMapa  = findViewById(R.id.SpinHoraUpFotoMapa);
        MapaUpSelectPac     = findViewById(R.id.MapaUpSelectPac);
        util.ClickCallIntent(BtnVoltar,Upload_Foto_Mapa.this, Menu_Mapeamento.class);

        //TODO: Vaiaveis de de sessão (Shared Preferences)
        sharedPreferences   = getSharedPreferences(MyPREFERENCES,0);
        Selectedpaciente    = sharedPreferences.getString("PacienteSelecionado",null);
        CodUser             = sharedPreferences.getLong("CodUser",0);
        MapaUpSelectPac.setText(Selectedpaciente);


        List<String> ListData = new ArrayList<>();
        List<String> ListHora = new ArrayList<>();

        try {ListData = new doTaskGetListDataHoraByNP(Upload_Foto_Mapa.this,1).execute().get();}
        catch (ExecutionException e) { e.printStackTrace();}
        catch (InterruptedException e) {e.printStackTrace();}

        try {ListHora = new doTaskGetListDataHoraByNP(Upload_Foto_Mapa.this,2).execute().get();}
        catch (ExecutionException e) { e.printStackTrace();}
        catch (InterruptedException e) {e.printStackTrace();}

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_fm, R.id.sp_item_center1, ListData);
        SpinDataUpFotoMapa.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_custom_fm, R.id.sp_item_center1, ListHora);
        SpinHoraUpFotoMapa.setAdapter(adapter2);

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          if (EasyPermissions.hasPermissions(Upload_Foto_Mapa.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    /*
                    * The modern action is ACTION_GET_CONTENT, which is much better supported,

                        ACTION_PICK :

                        Activity Action: Pick an item from the data, returning what was selected.

                        Input: getData() is URI containing a directory of data (vnd.android.cursor.dir/*) from which to pick an item.

                        Output: The URI of the item that was picked.

                        Constant Value: "android.intent.action.PICK"

                        Difference :-

                        Activity Action: Allow the user to select a particular kind of data and return it.

                        This is different than ACTION_PICK in that here we just say what kind of data is desired,
                        not a URI of existing data from which the user can pick.

                        A ACTION_GET_CONTENT could allow the user to create the data as it runs (for example taking a picture or recording a sound),
                        let them browse over the web and download the desired data, etc.
                    *
                     */
                    File rootPath = new File(getFilesDir(), "/Facial Map");
                    if (!rootPath.exists()) {
                        mensagem.MsgCreate("Pasta interna não existe, criando agora . . .");
                        createfolder();
                        //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        startActivityForResult(i, 1);
                    }
                    else{
                      mensagem.ErrorMsg("Erro em criar a pasta . Pasta do aplicativo já existe");
                    }

                } else {
            //If permission is not present request for the same.
            EasyPermissions.requestPermissions(Upload_Foto_Mapa.this, getString(R.string.read_file), READ_REQUEST_CODE,
            Manifest.permission.READ_EXTERNAL_STORAGE);}
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            capturedImageUri = data.getData();
            //Toast.makeText(getApplicationContext()," capturedImageUri :" + data.getData().toString(), Toast.LENGTH_LONG).show();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                selectedImagePath = getRealPathFromURIPath(capturedImageUri, Upload_Foto_Mapa.this);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capturedImageUri);
                    //Toast.makeText(getApplicationContext()," Width :" +  bitmap.getWidth() + " Height " +  bitmap.getHeight(), Toast.LENGTH_LONG).show();
                    //Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 400, true);

                      //img_foto.setImageBitmap(bitmap);
                      //ByteArrayToBitmap(BitmapToByteArray(bitmap));

                    ShowUploadedImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else Toast.makeText(getApplicationContext()," Sem permissão !", Toast.LENGTH_LONG).show();

        }else {
            //Toast.makeText(getApplicationContext()," Error :" + requestCode + " / " + resultCode + " / " + data, Toast.LENGTH_LONG).show();
            Log.e("App" , " requestCode :" + requestCode);
            Log.e("App" , " resultCode :" + resultCode);
            Log.e("App" , " data :" + data);
        }

    }
    private byte[] BitmapToByteArray(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void  ByteArrayToBitmap(byte[] bit ) {
        try {
            //Create directory..
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            Date date = new Date();
            String str = dateFormat.format(date);

            ContextWrapper c = new ContextWrapper(this);
            //Toast.makeText(this, c.getFilesDir().getPath()+"/Mapa Facial/Banco De Dados/Nome Paciente/"+str, Toast.LENGTH_LONG).show();
            String pathDest = c.getFilesDir().getPath()+"/Facial Map/Banco De Dados/Nome Paciente/"+str;
            Toast.makeText(this, c.getFilesDir().getPath()+"/Facial Map/Banco De Dados/Nome Paciente/"+str, Toast.LENGTH_LONG).show();

            //Create file..
            File file = new File(pathDest + File.separator + "Teste.png");
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            out.write(bit);
            out.close();

        }
        catch (FileNotFoundException e) {
            mensagem.ErrorMsg("Erro de arquivo :\n"+e);
            e.printStackTrace();
        }
        catch (IOException e) {
            mensagem.ErrorMsg("Erro excessão :\n"+e);
            e.printStackTrace();
        }
    }

    /**
     * Returns the actual path of the file in the file system
     *
     * @param contentURI
     * @param activity
     * @return
     */
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
    public void createfolder(){

        File rootPath = new File(getFilesDir(), "/Facial Map");
        if (!rootPath.exists()) {

            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            Date date = new Date();
            String str = dateFormat.format(date);

            File DB = new File(rootPath, "Banco De Dados/Nome Paciente/"+str);
            DB.mkdirs();
            Toast.makeText(Upload_Foto_Mapa.this, "Pasta criada com sucesso ! ",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(Upload_Foto_Mapa.this, "Erro em criar a pasta ",Toast.LENGTH_LONG).show();
        }
    }

    public void ShowUploadedImage( Bitmap bit){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.show_foto,null);
        //definimos para o botão do layout um clickListener

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(d);
        Log.e("APP","Data: "+formattedDate);

        TextView Txt_Data = view.findViewById(R.id.DataPreviewDialog);
        Txt_Data.setText(formattedDate);

        final ImageView ImgShowZoom = view.findViewById(R.id.show_img1);
        ImgShowZoom.setImageBitmap(bit);

        Button BtnZoomImgClose = view.findViewById(R.id.BtnPreviewCancel);
        BtnZoomImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              alerta.dismiss();
            }
        });

        Button BtnPreviewUploadSave = view.findViewById(R.id.BtnPreviewSave);
        BtnPreviewUploadSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //TODO : Gravar no Banco de Dados
                img_foto.setImageBitmap(bitmap);
                ByteArrayToBitmap(BitmapToByteArray(bitmap));
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

}
