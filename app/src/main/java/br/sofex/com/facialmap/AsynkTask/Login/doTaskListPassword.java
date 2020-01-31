package br.sofex.com.facialmap.AsynkTask.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;

public class doTaskListPassword extends AsyncTask<Void,Void, List<String>> {

    Context mContext;

    public doTaskListPassword(Context context){this.mContext = context;}

    private ProgressDialog dialog;
    protected void onPreExecute() {
        Log.e("AsyncTask", "Antes de executar ");
        dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Realizando pesquisa...", true, false);
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> listpassword;
        listpassword = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetUsuarioSenha();
        return listpassword;
    }

    @Override
    protected void onPostExecute( List<String> str) {
        Log.e("HttpService", "Terminado ");
        if (dialog.isShowing()) {dialog.dismiss();}
    }

}
