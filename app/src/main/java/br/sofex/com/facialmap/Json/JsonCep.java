package br.sofex.com.facialmap.Json;

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
import android.widget.TextView;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import br.sofex.com.facialmap.R;

public class JsonCep extends AsyncTask<Void, Void, String> {

    private Activity activity;
    private ProgressDialog dialog;

    private AlertDialog alerta;
    private Context mContext;
    private final String cep;
    public JsonCep(String cep) {
        this.cep = cep;
    }

    public JsonCep(Context context, String cep) {
        mContext = context;
        this.cep = cep;
    }

    @Override
    protected void onPreExecute() {
        Loading();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String x = null;
        try {
            //x = "Rua :"+getEndereco(this.cep)+"Bairro :"+getBairro(this.cep)+"Cidade :"+getCidade(this.cep)+"Estado :"+getUF(this.cep)+"/";
            x = "<logradouro>"+getEndereco(this.cep)+"</logradouro>\n" +
                 "<bairro>"+getBairro(this.cep)+"</bairro>\n" +
                 "<cidade>"+getCidade(this.cep)+"</cidade>\n" +
                 "<estado>"+getUF(this.cep)+"</estado>\n";

            Log.e("App"," retorno Thread : "+x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    @Override
    protected void onPostExecute(String result) {
        alerta.dismiss();
    }

    public String getEndereco(String CEP) throws IOException {

        //***************************************************
        try{

            Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP) .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
            for (Element urlEndereco : urlPesquisa) {
                return urlEndereco.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getBairro(String CEP) throws IOException {

        //***************************************************
        try{

            Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
                    .timeout(120000)
                    .get();
            Elements urlPesquisa = doc.select("td:gt(1)");
            for (Element urlBairro : urlPesquisa) {
                return urlBairro.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getCidade(String CEP) throws IOException {

        //***************************************************
        try{

            Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
                    .timeout(120000)
                    .get();
            Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
            for (Element urlCidade : urlPesquisa) {
                return urlCidade.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getUF(String CEP) throws IOException {

        //***************************************************
        try{

            Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/"+CEP)
                    .timeout(120000)
                    .get();
            Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
            for (Element urlUF : urlPesquisa) {
                return urlUF.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
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
