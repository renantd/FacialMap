package br.sofex.com.facialmap.AsynkTask.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.sofex.com.facialmap.Login.Login;
import br.sofex.com.facialmap.R;

public class doTaskLogout extends AsyncTask<Void,Void,Boolean> {

    private Context mContext;
    private AlertDialog alerta;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public doTaskLogout(Context context){
        mContext = context;
    }

    private ProgressDialog dialog;
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
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(mContext, Login.class);
        mContext.startActivity(intent);
        return true;
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
        }, 1000);
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

}
