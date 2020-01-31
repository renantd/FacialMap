package br.sofex.com.facialmap.AsynkTask.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;

public class doTaskLogar extends AsyncTask<Void,Void,Boolean> {

    private String username;
    private String passord;
    private Context mContext;
    private AlertDialog alerta;
    List<String> Nomes;
    List<String> Logins;
    List<String> Passwords;
    byte[] Foto;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;


    public doTaskLogar(Context context,String Login){
        mContext = context;
        this.username = Login;
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

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String encodedImage = Base64.encodeToString(FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetFotoUserByLogin(this.username), Base64.DEFAULT);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("CodUser", FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetCodUserByLogin(this.username));
        editor.putString("Nome", FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetNomeUserByLogin(this.username));
        editor.putString("Login", FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetLoginUserByLogin(this.username));
        editor.putString("Senha", FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetSenhaUserByLogin(this.username));
        editor.putString("Email", FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetEmailUserByLogin(this.username));
        editor.putString("PacienteSelecionado", "Nenhum Paciente Selecionado");
        editor.putString("FotoUsuario",encodedImage);
        editor.commit();

        Intent intent = new Intent(mContext, MainActivity.class);
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

}
