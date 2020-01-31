package br.sofex.com.facialmap.AsynkTask.Login;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;
import br.sofex.com.facialmap.R;

public class doTaskUpdateUserAccount  extends AsyncTask<Void,Void,Void> {

    private Integer Option = 0 ;
    private String NewValueDB;
    private Long CodUsuario;
    private Context mContext;
    private AlertDialog alerta;

    private String  NomeUser;
    private String  LoginUser;
    private String  SenhaUser;
    private String  EmailUser;

    TextView TxtNomeUserLabel;
    TextView TxtLoginUserLabel;
    TextView TxtSenhaUserLabel;
    TextView TxtEmailUserLabel;

    EditText EditNomeUpdate;
    EditText EditLoginUpdate;
    EditText EditPasswordUpdate;
    EditText EditEmailUpdate;

    public doTaskUpdateUserAccount(Context context, Integer Option, String NewValueDB, Long CodUsuario,EditText EditNomeUpdate, EditText EditLoginUpdate, EditText EditPasswordUpdate, EditText EditEmailUpdate,
                                   TextView TxtNomeUserLabel, TextView TxtLoginUserLabel, TextView TxtSenhaUserLabel, TextView TxtEmailUserLabel){
        mContext = context;
        this.Option = Option;
        this.NewValueDB = NewValueDB;
        this.CodUsuario = CodUsuario;

        this.TxtNomeUserLabel = TxtNomeUserLabel;
        this.TxtLoginUserLabel = TxtLoginUserLabel;
        this.TxtSenhaUserLabel = TxtSenhaUserLabel;
        this.TxtEmailUserLabel = TxtEmailUserLabel;

        this.EditNomeUpdate = EditNomeUpdate;
        this.EditLoginUpdate = EditLoginUpdate;
        this.EditPasswordUpdate = EditPasswordUpdate;
        this.EditEmailUpdate = EditEmailUpdate;
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
    protected Void doInBackground(Void... voids) {
        NomeUser  = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetNomeUserByCod(this.CodUsuario);
        LoginUser = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetLoginUserByCod(this.CodUsuario);
        SenhaUser = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetSenhaUserByCod(this.CodUsuario);
        EmailUser = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetEmailUserByCod(this.CodUsuario);
        switch(Option){
            case 1:FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().UpdateNomeUserByCod(this.NewValueDB,this.CodUsuario); break;
            case 2:FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().UpdateLoginUserByCod(this.NewValueDB,this.CodUsuario);break;
            case 3:FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().UpdateSenhaUserByCod(this.NewValueDB,this.CodUsuario);break;
            case 4:FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().UpdateEmailUserByCod(this.NewValueDB,this.CodUsuario);break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.e("HttpService", "Terminado ");
        if (alerta.isShowing()) {
            delay();
            TxtNomeUserLabel.setText(NomeUser);
            TxtLoginUserLabel.setText(LoginUser);
            TxtSenhaUserLabel.setText(SenhaUser);
            TxtEmailUserLabel.setText(EmailUser);

            EditNomeUpdate.setText(NomeUser);
            EditLoginUpdate.setText(LoginUser);
            EditPasswordUpdate.setText(SenhaUser);
            EditEmailUpdate.setText(EmailUser);
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
