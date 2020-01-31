package br.sofex.com.facialmap.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.sofex.com.facialmap.R;

public class doTaskCall_Intent extends AsyncTask<Void,Void,Boolean> {

    private String username;
    private String passord;
    private Context mContext;
    private AlertDialog alerta;
    private Object ob;

    public doTaskCall_Intent(Context context, Class classe){
        mContext = context;
        this.ob = classe;
    }

    @Override
    protected void onPreExecute() {
        //msg1();
        Log.e("AsyncTask", "Antes de executar ");
        //dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Realizando pesquisa...", true, false);
        Loading("Por favor aguarde , processando...");
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i("AsyncTask", "Executando: ");
        if(mContext != null && ob != null){
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
        }, 10);
    }

    private boolean Loading(String ErroMsg){

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


        alertadd.setView(view);
        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        return true;
    }

}
