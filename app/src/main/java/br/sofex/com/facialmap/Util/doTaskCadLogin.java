package br.sofex.com.facialmap.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class doTaskCadLogin extends AsyncTask<Void,Void,Boolean> {

    private String name;
    private String username;
    private String passord;
    private String passord2;
    private String email;

    private Context mContext;

    public doTaskCadLogin(Context context, String name, String login, String password, String password2, String email){
        mContext = context;
        this.name = name;
        this.username = login;
        this.passord = password;
        this.passord2 = password2;
        this.email = email;
    }

    private ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        //msg1();
        Log.e("AsyncTask", "Antes de executar ");
        dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Realizando pesquisa...", true, false);
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i("AsyncTask", "Executando: ");
        if(this.name.equalsIgnoreCase("r") &&
           this.username.equalsIgnoreCase("rcs") &&
           this.passord.equalsIgnoreCase("1") &&
           this.passord2.equalsIgnoreCase("1") &&
           this.email.equalsIgnoreCase("w")
        ){return true;}
        else{return false;}
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.e("HttpService", "Terminado ");
        if (dialog.isShowing()) {dialog.dismiss();}
    }



}
