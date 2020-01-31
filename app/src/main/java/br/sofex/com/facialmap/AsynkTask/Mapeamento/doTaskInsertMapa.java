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

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;
import br.sofex.com.facialmap.Entidades.Mapa;
import br.sofex.com.facialmap.R;

public class doTaskInsertMapa extends AsyncTask<String, Void, Void> {

    private AlertDialog alerta;
    private Context mContext;
    FacialMapRoomDatabase faceDatabase;
    String DataCriacao;
    String HoraCriacao;
    String TipoDispositivo;
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


    public doTaskInsertMapa(Context context,String DataCriacao,String  HoraCriacao, String TipoDispositivo,byte[]  FotoPath,String FotoNome,
       byte[] FotoMapeada,String FotoMapeadaNome,byte[] FotoFinal,String FotoFinalNome,byte[] FotoVertical,String FotoVerticalNome,
       byte[] FotoHorizontal,String FotoHorizontalNome){

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
    }

    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +Thread.currentThread().getName());Loading();
    }

    @Override
    protected Void doInBackground(String... params) {

        /* for(Long x : FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetCodPacienteList()){
          if(x == 1){ CodPacienteFK = 1l; CodMapaFK = 1l;} else if(x > 1){ CodPacienteFK = x; CodMapaFK = x;}
        }
        Mapa mapa = new Mapa(DataCriacao,HoraCriacao,TipoDispositivo,FotoMapaInicial,FotoMapaInicialNome,
        FotoMapaMapeada,FotoMapaMapeadaNome,FotoMapaFinal,FotoMapaFinalNome,FotoVertical,FotoVerticalNome,
        FotoHorizontal,FotoHorizontalNome,CodMapaFK);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).mapaDao().insertMapa(mapa);*/
        for(Long x : FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetCodPacienteList()){
          if(x == 1){ CodPacienteFK = 1l; CodMapaFK = 1l;} else if(x > 1){ CodPacienteFK = x; CodMapaFK = x;}
        }
        Mapa mapa = new Mapa(DataCriacao,HoraCriacao,TipoDispositivo,FotoPath,FotoNome,
        FotoMapeada,FotoMapeadaNome,FotoFinal,FotoFinalNome,FotoVertical,FotoVerticalNome,
        FotoHorizontal,FotoHorizontalNome,CodMapaFK);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).mapaDao().insertMapa(mapa);

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
