package br.sofex.com.facialmap.AsynkTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.sofex.com.facialmap.R;

//TODO: AsyncTask<Parâmetros,Progresso,Resultado>
public class AsyncTaskLoadCep extends AsyncTask<String, Void, Boolean>{

    private Activity activity;
    private ProgressDialog dialog;

    private AlertDialog alerta;
    private Context mContext;
    private String JsonXmlRetorno;

    private EditText Edit_End;
    private EditText Edit_Bairro;
    private EditText Edit_EndMuni;
    private EditText Edit_EndEstado;


    public AsyncTaskLoadCep(Context context){
        mContext = context;
    }

    public AsyncTaskLoadCep(Context context, String JsonXmlRetorno, EditText Edit_End, EditText Edit_Bairro, EditText Edit_EndMuni, EditText Edit_EndEstado){
        mContext = context;
        this.JsonXmlRetorno = JsonXmlRetorno;
        this.Edit_End = Edit_End;
        this.Edit_Bairro = Edit_Bairro;
        this.Edit_EndMuni = Edit_EndMuni;
        this.Edit_EndEstado = Edit_EndEstado;
    }

    @Override
    protected void onPreExecute() {
        //msg1();
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +
        Thread.currentThread().getName());
        Loading();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if(Cep_CheckReturn(JsonXmlRetorno) == true){
            return true;
        }else{
            return false;
        }
        //return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        alerta.dismiss();
        if(result == true){
            Edit_End.setText(JsonGetLogradouro(JsonXmlRetorno));
            Edit_Bairro.setText(JsonGetBairro(JsonXmlRetorno));
            Edit_EndMuni.setText(JsonGetCidade(JsonXmlRetorno));
            Edit_EndEstado.setText(JsonGetEstado(JsonXmlRetorno));
        }else{
            Edit_End.setText(null);
            Edit_Bairro.setText(null);
            Edit_EndMuni.setText(null);
            Edit_EndEstado.setText(null);
            //mensagem.ErrorMsg(" O cep informado é inválido");
        }
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
    public static boolean Cep_CheckReturn(String cepJsonXlm) {
        return !Character.isDigit(cepJsonXlm.charAt(12));
    }
    /*TODO: FUNÇÕES JSON DE CONSUMO DE SERVIÇO*/
    public   String JsonGetLogradouro(String cep) {
        String LogradouroAux = cep.replaceAll("<logradouro>", "");
        String Logradouro = LogradouroAux.substring(0, LogradouroAux.indexOf("</logradouro>\n"));
        return Logradouro;
    }
    public   String JsonGetBairro(String cep) {
        String BairroAux =  cep.substring(cep.indexOf("<bairro>") + 8);
        String Bairro = BairroAux.substring(0, BairroAux.indexOf("</bairro>\n"));
        return Bairro;
    }
    public   String JsonGetCidade(String cep) {
        String CidadeAux =  cep.substring(cep.indexOf("<cidade>") + 8);
        String Cidade = CidadeAux.substring(0, CidadeAux.indexOf("</cidade>\n"));
        return Cidade;
    }
    public   String  JsonGetEstado(String cep) {
        String EstadoAux =  cep.substring(cep.indexOf("<estado>") + 8);
        String Estado = EstadoAux.substring(0, EstadoAux.indexOf("</estado>\n"));
        return Estado;
    }
    private void Loading() {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        //LayoutInflater li = getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        //inflamos o layout alerta.xml na view
        View view = inflater.inflate(R.layout.loading_verificando, null);
        //definimos para o botão do layout um clickListener


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
