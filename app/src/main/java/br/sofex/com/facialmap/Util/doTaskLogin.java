package br.sofex.com.facialmap.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.sofex.com.facialmap.R;

public class doTaskLogin extends AsyncTask<Void,Void,Boolean> {

    private String username;
    private String passord;
    private Context mContext;
    private AlertDialog alerta;

    public doTaskLogin(Context context, String login, String password){
        mContext = context;
        this.username = login;
        this.passord = password;
    }

    private ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        //msg1();
        Log.e("AsyncTask", "Antes de executar ");
        //dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Realizando pesquisa...", true, false);
        Loading("Por favor aguarde, processando...");
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i("AsyncTask", "Executando: ");
        if(this.username.equalsIgnoreCase("R") && this.passord.equalsIgnoreCase("1")){
            Message("OK");
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.e("HttpService", "Terminado ");
        if (alerta.isShowing()) {
            delay();
        }
    }

    private void delay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alerta.dismiss();
            }
        }, 40);
    }

    private void Loading(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.loading_custom, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        ProgressBar progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setIndeterminate(true);


        alertadd.setView(view);
        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    private void Message(String Mensagem){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.sucess, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_sucess);
        textdialog.setText(Mensagem);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setView(view);
        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

}
