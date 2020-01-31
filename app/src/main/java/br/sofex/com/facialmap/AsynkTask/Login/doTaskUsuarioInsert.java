package br.sofex.com.facialmap.AsynkTask.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.sofex.com.facialmap.DataBase.FacialMapRoomDatabase;
import br.sofex.com.facialmap.Entidades.Usuario;

public class doTaskUsuarioInsert   extends AsyncTask<Void,Void,Void> {

    Context mContext;
    String Nome_Usuario = "";
    String Login = "";
    String Senha = "";
    String Email = "";
    byte[] FotoUsuario = null;
    String NomeFotoUsuario = "";


    private ProgressDialog dialog;
    protected void onPreExecute() {
        Log.e("AsyncTask", "Antes de executar ");
        dialog = ProgressDialog.show(mContext, "Por favor aguarde", " Inserindo os Dados...", true, false);
    }

   public doTaskUsuarioInsert(Context context, String Nome_Usuario, String Login,String Senha, String Email, byte[] FotoUsuario, String NomeFotoUsuario){
       this.mContext =          context;
       this.Nome_Usuario =      Nome_Usuario;
       this.Login =             Login;
       this.Senha =             Senha;
       this.Email =             Email;
       this.FotoUsuario =       FotoUsuario;
       this.NomeFotoUsuario =   NomeFotoUsuario;
   }

    @Override
    protected Void doInBackground(Void... voids) {

        Usuario usuario = new Usuario( Nome_Usuario, Login, Senha, Email, FotoUsuario, NomeFotoUsuario);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().insertUuario(usuario);


        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        Log.e("HttpService", "Terminado ");
        if (dialog.isShowing()) {dialog.dismiss();}
    }
}
