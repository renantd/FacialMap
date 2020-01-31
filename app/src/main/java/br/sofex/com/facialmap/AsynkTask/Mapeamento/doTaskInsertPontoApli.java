package br.sofex.com.facialmap.AsynkTask.Mapeamento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;
import br.sofex.com.facialmap.Entidades.Aplicacao;
import br.sofex.com.facialmap.Entidades.Ponto;
import br.sofex.com.facialmap.R;

public class doTaskInsertPontoApli  extends AsyncTask<String, Void, Void> {


    private AlertDialog alerta;
    private Context mContext;
    FacialMapRoomDatabase faceDatabase;
    String DataCriacao;
    String  HoraCriacao;
    String TipoDispositivo;
    Long FkMapa;
    String NomePonto;
    Integer  CoordenataX;
    Integer CoordenataY;
    List<String> ListPontos = new ArrayList<>();
    List<String> ListAplicacoes = new ArrayList<>();
    Long CodMapFK = 0l;
    Long CodPontoFK = 0l;
    String NomeToxina = "";
    String FabricanteToxina = "";
    String DistribuidoraToxina = "";
    Integer TamanhoFrascoToxina = 0;
    String SoroFisiologico = "";
    Integer VolumeAplicacao = 0;
    Integer DiametroAgulha = 0;
    Integer ComprimentoAgulha = 0;
    String DataValidade = "";
    String LoteToxina = "";
    byte[] FotoPath = null;
    String FotoNome = "";
    byte[] FotoMapeada = null;
    String FotoMapeadaNome = "";
    byte[] FotoFinal = null;
    String FotoFinalNome = "";
    byte[] FotoVertical = null;
    String FotoVerticalNome = "";
    byte[] FotoHorizontal = null;
    String FotoHorizontalNome = "";

    Long CodPacienteFK = 0l;
    Long CodMapaFK = 0l;


    public doTaskInsertPontoApli(Context context,String NomePonto,Integer CoordenataX,Integer CoordenataY,String Nome_Toxina,String FabricanteToxina,
    String DistribuidoraToxina,Integer TamanhoFrascoToxina,String SoroFisiologico,Integer  VolumeAplicacao,
    Integer DiametroAgulha,Integer ComprimentoAgulha, String DataValidade, String LoteToxina){

        this.mContext =            context;
        this.DataCriacao =         DataCriacao;
        this.HoraCriacao =         HoraCriacao;
        this.FotoPath =            FotoPath;
        this.FotoNome =            FotoNome;
        this.FotoMapeada =         FotoMapeada;
        this.FotoMapeadaNome =     FotoMapeadaNome;
        this.FotoFinal =           FotoFinal;
        this.FotoFinalNome =       FotoFinalNome;
        this.FotoVertical =        FotoVertical;
        this.FotoVerticalNome =    FotoVerticalNome;
        this.FotoHorizontal =      FotoHorizontal;
        this.FotoHorizontalNome =  FotoHorizontalNome;
        this.TipoDispositivo =     TipoDispositivo;
        this.NomePonto =           NomePonto;
        this.CoordenataX =         CoordenataX;
        this.CoordenataY =         CoordenataY;
        this.NomeToxina =          Nome_Toxina;
        this.FabricanteToxina =    FabricanteToxina;
        this.DistribuidoraToxina = DistribuidoraToxina;
        this.TamanhoFrascoToxina = TamanhoFrascoToxina;
        this.SoroFisiologico =     SoroFisiologico;
        this.VolumeAplicacao =     VolumeAplicacao;
        this.DiametroAgulha =      DiametroAgulha;
        this.ComprimentoAgulha =   ComprimentoAgulha;
        this.DataValidade =        DataValidade;
        this.LoteToxina =          LoteToxina;
    }

    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +Thread.currentThread().getName());Loading();
    }

    @Override
    protected Void doInBackground(String... params) {


        for(Long x : FacialMapRoomDatabase.getDatabaseMapa(mContext).mapaDao().GetListCods()){
            if(x == 1){ CodMapFK = 1l; CodPontoFK = 1l;}
            else if(x > 1){ CodMapFK = x; CodPontoFK = x;}
        }
        /*for(Long x : MapaRoomDatabase.getDatabaseMapa(mContext).pontoDao().GetListCods()){
            if(x == 1){ CodPontoFK = 1l; }
            else if(x > 1){ CodPontoFK = x; }
        }*/

        Ponto ponto = new Ponto(NomePonto,CoordenataX,CoordenataY,CodMapFK);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).pontoDao().insertPonto(ponto);


        Aplicacao aplicacao = new Aplicacao(NomeToxina, FabricanteToxina,DistribuidoraToxina,TamanhoFrascoToxina, SoroFisiologico,
        VolumeAplicacao, DiametroAgulha, ComprimentoAgulha, DataValidade, LoteToxina, CodPontoFK);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).aplicacaoDao().insertAplicacao(aplicacao);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) { alerta.dismiss();}

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
    public void ErrorMsg(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

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
        TextView title = new TextView(mContext);
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
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#23A4CA"));
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    public void WarningMsg(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

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
        TextView title = new TextView(mContext);
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
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#23A4CA"));
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    public void SucessMsg(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.sucess, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_sucess);
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
        TextView title = new TextView(mContext);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.parseColor("#23A4CA"));
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);

        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#23A4CA"));
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }
    public String NomePonto(String ListaRow){
        return ListaRow.substring(0, ListaRow.indexOf("X"));
    }
    public String GetPontoCoordX(String ListaRow){
        String NomePonto = ListaRow.substring(0, ListaRow.indexOf("X"));
        return ListaRow.substring(NomePonto.length()+1, ListaRow.indexOf(" P"));
    }
    public String GetPontoCoordY(String ListaRow){
        String PontoYCut = ListaRow.substring(0, ListaRow.indexOf("Y")+1);
        String x = ListaRow.replace(PontoYCut, "");
        return x;
    }


}
