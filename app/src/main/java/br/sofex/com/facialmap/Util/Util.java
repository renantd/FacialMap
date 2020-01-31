package br.sofex.com.facialmap.Util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.sofex.com.facialmap.Mensagem.Mensagem;

public class Util extends AppCompatActivity{

    Context mContext;
    public Util(Context context){
        this.mContext = context;
    }
    Mensagem mensagem = new Mensagem(Util.this);

    public void setTitleBarApp(String title, ActionBar  actionBar,Context context){
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textView);
    }
    public Intent CallActivity(Context context, Class MyClass){

        if(context != null || MyClass != null){
            Intent intent = new Intent(context,MyClass);
            return intent;
        }else return null;

    }
    public void ClickCallIntent(Button Btn1, final  Context context , final Class DestClass){
        if(context != null || DestClass != null){
            Btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DestClass);
                    context.startActivity(intent);
                }
            });
        } else{
            mensagem.ErrorMsg("Error Dev:\n Context ou Class de destino nulos");
        }

    }

    /*TODO: FUNÇÃO PARA OBTER O DIRETÓRIO REAL DE UM URI*/
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

    /*TODO: FUNÇÃO DE CRIAR DO PACIENTE*/
    public String CriarPastaPaciente(String NomePaciente,Context context){
        /*String filePath = null;
        File rootPath = new File(Cadastrar.this.getFilesDir(),"/Files");
        if (!rootPath.exists()) {

            // TODO: Cria apasta no diretório -> /data/data/br.sofex.com.pacientedb/files/Files/Images
            File DB = new File(rootPath, "/Mapa Facial/Paciente "+NomePaciente+"/FotoPerfil/");
            DB.mkdirs();
            filePath = DB.getAbsolutePath();
            return filePath;
        }
        else {
            Toast.makeText(Cadastrar.this, "Erro em criar a pasta , Pasta já existe ",Toast.LENGTH_LONG).show();
            return null;
        }*/

        String filePath = null;
        File rootPath = new File(context.getFilesDir()+"/Files/Mapa Facial/Paciente "+NomePaciente+"/FotoPerfil/");
        if (!rootPath.exists()) {
            // TODO: Cria apasta no diretório -> /data/data/br.sofex.com.pacientedb/files/Files/Images
            File DB = new File(context.getFilesDir()+"/Files/Mapa Facial/Paciente "+NomePaciente+"/FotoPerfil/");
            DB.mkdirs();
            filePath = DB.getAbsolutePath();
            return filePath;
        }
        else {
            mensagem.ErrorMsg("Erro em criar a pasta , Pasta já existe ");
            return null;
        }

    }

    /*TODO: FUNÇÃO DE CONVERTER ARQUIVO PARA ARRAY DE BYTE*/
    public byte[] FileImageTobyteArray(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis= new FileInputStream(f);
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

    /*TODO: FUNÇÃO PARA SALVAR IMAGEM EM UM DIRETÓRIO ESCOLHIDO*/
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

    /*TODO: FUNÇÃO PARA COPIAR ARQUIVOS */
    public void copyFromTo(File src, File dst) throws IOException {
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

    /*TODO: FUNÇÃO DE RETORNO DE STRING*/
    private String GetFileNameIntent(Intent data,Context context){
        Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;
        String finalName = "";

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    finalName = displayName;
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
            finalName = displayName;
        }
        return  finalName;
    }

    /*TODO: FUNÇÃO DE RETORNO DE BYTE ARRAY*/
    private byte[] BitmapToByteArray(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /*TODO: FUNÇÃO DE CONVERSÃO DE BYTE ARRAY TO BITMAP*/
    private void  ByteArrayToBitmap(byte[] bit ) {
        try {
            //Create directory..
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            Date date = new Date();
            String str = dateFormat.format(date);

            ContextWrapper c = new ContextWrapper(this);
            //Toast.makeText(this, c.getFilesDir().getPath()+"/Mapa Facial/Banco De Dados/Nome Paciente/"+str, Toast.LENGTH_LONG).show();
            String pathDest = c.getFilesDir().getPath()+"/Mapa Facial/Banco De Dados/Nome Paciente/"+str;
            Toast.makeText(this, c.getFilesDir().getPath()+"/Mapa Facial/Banco De Dados/Nome Paciente/"+str, Toast.LENGTH_LONG).show();

            //Create file..
            File file = new File(pathDest + File.separator + "Teste.png");
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            out.write(bit);
            out.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*TODO: FUNÇÃO DE CRIAR PASTAS*/
    public void createfolder(Context context){
        File rootPath = new File(context.getFilesDir(),"/Files");
        if (!rootPath.exists()) {
            // TODO: Cria apasta no diretório -> /data/data/br.sofex.com.pacientedb/files/Files/Images
            File DB = new File(rootPath, "/Mapa Facial/Paciente/FotoPerfil");
            DB.mkdirs();
            Toast.makeText(context, "Pasta criada com sucesso ! ",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Erro em criar a pasta , Pasta já existe ",Toast.LENGTH_LONG).show();
        }

    }


    /*TODO: FUNÇÃO DE RODAR BITMAP*/
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /*TODO: FUNÇÃO DE CHECAR FORMATO DA DATA*/
    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }

    /*TODO: FUNÇÃO PARA VALIDAR O RENAVAM*/
    public boolean validarRenavam(String renavam){
        // Pegando como exemplo o renavam = 639884962

        // Completa com zeros a esquerda se for no padrao antigo de 9 digitos
        // renavam = 00639884962
        if(renavam.matches("^([0-9]{9})$")){
            renavam = "00" + renavam;
        }

        // Valida se possui 11 digitos pos formatacao
        if(!renavam.matches("[0-9]{11}")){
            return false;
        }

        // Remove o digito (11 posicao)
        // renavamSemDigito = 0063988496
        String renavamSemDigito = renavam.substring(0, 10);

        // Inverte os caracteres (reverso)
        // renavamReversoSemDigito = 69488936
        String renavamReversoSemDigito = new StringBuffer(renavamSemDigito).reverse().toString();

        int soma = 0;

        // Multiplica as strings reversas do renavam pelos numeros multiplicadores
        // para apenas os primeiros 8 digitos de um total de 10
        // Exemplo: renavam reverso sem digito = 69488936
        // 6, 9, 4, 8, 8, 9, 3, 6
        // * * * * * * * *
        // 2, 3, 4, 5, 6, 7, 8, 9 (numeros multiplicadores - sempre os mesmos [fixo])
        // 12 + 27 + 16 + 40 + 48 + 63 + 24 + 54
        // soma = 284
        for (int i = 0; i < 8; i++) {
            Integer algarismo = Integer.parseInt(renavamReversoSemDigito.substring(i, i + 1));
            Integer multiplicador = i + 2;
            soma += algarismo * multiplicador;
        }

        // Multiplica os dois ultimos digitos e soma
        soma += Character.getNumericValue(renavamReversoSemDigito.charAt(8)) * 2;
        soma += Character.getNumericValue(renavamReversoSemDigito.charAt(9)) * 3;

        // mod11 = 284 % 11 = 9 (resto da divisao por 11)
        int mod11 = soma % 11;

        // Faz-se a conta 11 (valor fixo) - mod11 = 11 - 9 = 2
        int ultimoDigitoCalculado = 11 - mod11;

        // ultimoDigito = Caso o valor calculado anteriormente seja 10 ou 11, transformo ele em 0
        // caso contrario, eh o proprio numero
        ultimoDigitoCalculado = (ultimoDigitoCalculado >= 10 ? 0 : ultimoDigitoCalculado);

        // Pego o ultimo digito do renavam original (para confrontar com o calculado)
        int digitoRealInformado = Integer.valueOf(renavam.substring(renavam.length()-1, renavam.length()));

        // Comparo os digitos calculado e informado
        if(ultimoDigitoCalculado == digitoRealInformado){
            return true;
        }
        return false;
    }

    /*TODO: FUNÇÕES JSON DE CONSUMO DE SERVIÇO*/
    public   String JsonGetLogradouro(String cep) {
        String LogradouroAux = cep.replaceAll("<logradouro>", "");
        String Logradouro = LogradouroAux.substring(0, LogradouroAux.indexOf("</logradouro>\n"));
        return Logradouro;
    }
    public   String JsonGetBairro(String cep) {
        String BairroAux =  cep.substring(cep.indexOf("<bairro>") + 8);
        String Bairro = BairroAux.substring(0, BairroAux.indexOf("</bairro>\n")-1);
        return Bairro;
    }
    public   String JsonGetCidade(String cep) {
        String CidadeAux =  cep.substring(cep.indexOf("<cidade>") + 8);
        String Cidade = CidadeAux.substring(0, CidadeAux.indexOf("</cidade>\n"));
        return Cidade;
    }
    public  String JsonGetEstado(String cep) {
        String EstadoAux =  cep.substring(cep.indexOf("<estado>") + 8);
        String Estado = EstadoAux.substring(0, EstadoAux.indexOf("</estado>\n"));
        return Estado;
    }
    /*TODO: FIM ---------------- */

    /*TODO: FUNÇÕES*/
    // caso for digitado qualquer sequencia de numéro e o serviço não encontrar nada
    // ele ira retornar o valor do cep digitado.
    // Esta função verifica se o primeiro caracter do json xml de retorno for número, o cep não existe ,
    // então é invalido , caso contrário é valido
    public  String Cep_CheckReturnTeste(String cepJsonXlm) {

        String checkResult ="";
        String check = "";
        String jsoncheck = JsonGetLogradouro(cepJsonXlm).toLowerCase();
        for(int i=0; i < jsoncheck.length();i++){
            char c = jsoncheck.charAt(i);
            if(i >= 0 && !Character.isDigit(c)){
                check = JsonGetLogradouro(cepJsonXlm);
            }else{
                check = JsonGetLogradouro(cepJsonXlm);
            }
        }
        checkResult = check;
        return  checkResult;
    }


    /*TODO: FUNÇÕES DE TELEFONES*/
    public static boolean CelularIsValid(String Celular){
        String CelularAux1 = Celular.replace("(", "");
        String CelularAux2 = CelularAux1.replace(")", "");
        String CelularAux3 = CelularAux2.replace(" ", "");
        String CelularAux4 = CelularAux3.replace("-", "");
        Integer count = 0;
        Integer count2 = 0;
        for (int i = 0; i < CelularAux4.length(); i++) {
            char c = CelularAux4.charAt(i);
            if (i == 2) {
                count2 = Integer.parseInt(c + "");
            }
            if (i == 3) {
                count = Integer.parseInt(c + "");
            }
        }
        return count >= 7 && count2 == 9;
    }
    public Boolean DDDisValid(String DDD){
        String Aux = "";
        for(int i = 0; i < DDD.length();i++){
            char c = DDD.charAt(i);
            System.out.println("Count "+i+" Value :"+c);
            if(i >= 1 && i < 3){
                Aux = Aux + c;
            }
        }
        if(Integer.parseInt(Aux) < 11 ){
            return false;
        }else{
            return true;
        }
    }
    public static boolean TelFixoIsValid(String Telefone){
        String CelularAux1 = Telefone.replace("(", "");
        String CelularAux2 = CelularAux1.replace(")", "");
        String CelularAux3 = CelularAux2.replace(")", "");
        String CelularAux4 = CelularAux3.replace(" ", "");
        Integer count = 0;
        for(int i=0; i < CelularAux4.length();i++){
            char c = CelularAux4.charAt(i);
            if(i == 2){
                count = Integer.parseInt(c+"");
            }
        }
        return count == 3 || count == 4;
    }
    /*TODO: FIM ---------------- */

    /*TODO: FUNÇÕES DE CEP*/
    public static boolean Cep_CheckReturn(String cepJsonXlm) {
        return !Character.isDigit(cepJsonXlm.charAt(12));
    }
    public static boolean Cep_isValid(String cep) {
        String CepAux = cep.replaceAll("-", "");
        String pattern = "[0-9]{5}[0-9]{3}";

        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(CepAux);
        boolean isMatch = matcher.find();

        boolean b = isMatch == true;
        return b;

    }
    /*TODO: FIM ---------------- */

    /*TODO: FUNÇÕES DE EMAIL*/
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static boolean Email_isValid(String email) {
        String email2 = email+"/";
        Boolean b = false;
        if(email2.contains("@")){
            String EmailAux = email2.substring(email2.indexOf("@"), email2.indexOf("/"));
            if( EmailAux.equalsIgnoreCase("@gmail.com") || EmailAux.equalsIgnoreCase("@outlook.com") || EmailAux.equalsIgnoreCase("@live.com") || EmailAux.equalsIgnoreCase("@yahoo.com.br")){
                b = true;
            }
        } else{b = false;}
        return b;
    }
    /*TODO: FIM ---------------- */

    public static boolean isValidEmailserver(String email){
        if(email.toLowerCase().contains("gmail") ||
                email.toLowerCase().contains("outlook") ||
                email.toLowerCase().contains("live") ||
                email.toLowerCase().contains("yahoo")){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isValidNome(String Input){
        Integer count = 0;
        for(int i=0; i < Input.length();i++){
            char c = Input.charAt(i);
            if(Character.isDigit(c) ||
                    Character.valueOf(c).equals('!')||
                    Character.valueOf(c).equals('@')||
                    Character.valueOf(c).equals('$')||
                    Character.valueOf(c).equals('%')||
                    Character.valueOf(c).equals('*')||
                    Character.valueOf(c).equals('?')||
                    Character.valueOf(c).equals('_')||
                    Character.valueOf(c).equals('-')||
                    Character.valueOf(c).equals('?')||
                    Character.valueOf(c).equals('&')){
                count++;
            }

        }
        if(count >= 1){
            return false;
        }else{
            return true;
        }

    }
    public boolean validarsenha(String senha){
        Integer upper = 0;
        Integer lower = 0;
        Integer digit = 0;
        Integer charEspecial = 0;
        for(int i=0; i < senha.length();i++){
            char c = senha.charAt(i);
            if(Character.isUpperCase(c)){
                upper++;
            }
            if(Character.isLowerCase(c)){
                lower++;
            }
            if(Character.isDigit(c)){
                digit++;
            }
            if(Character.valueOf(c).equals('!')||
                    Character.valueOf(c).equals('@')||
                    Character.valueOf(c).equals('$')||
                    Character.valueOf(c).equals('%')||
                    Character.valueOf(c).equals('*')||
                    Character.valueOf(c).equals('?')||
                    Character.valueOf(c).equals('_')||
                    Character.valueOf(c).equals('-')||
                    Character.valueOf(c).equals('?')||
                    Character.valueOf(c).equals('&')){
                charEspecial++;
            }
        }
        if(upper < 1 || lower < 1  || digit < 1 || charEspecial < 1 || senha.length() < 8 ){
            return false;
        }else{
            return true;
        }
    }


}
