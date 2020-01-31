package br.sofex.com.facialmap.AsynkTask.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;

public class doTaskNumberUser extends AsyncTask<Void,Void, Integer> {

    Context mContext;

    public doTaskNumberUser(Context context){this.mContext = context;}

    private ProgressDialog dialog;
    protected void onPreExecute() {
        Log.e("AsyncTask", "Antes de executar ");
        dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Realizando pesquisa...", true, false);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Integer Count = FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetNumberUser();
        return Count;
    }

    @Override
    protected void onPostExecute(Integer str) {
        Log.e("HttpService", "Terminado ");
        if (dialog.isShowing()) {dialog.dismiss();}
    }

}
