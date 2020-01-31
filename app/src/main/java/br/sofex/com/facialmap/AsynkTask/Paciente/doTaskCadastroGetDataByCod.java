package br.sofex.com.facialmap.AsynkTask.Paciente;

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
import br.sofex.com.facialmap.R;

public class doTaskCadastroGetDataByCod extends AsyncTask<String, Void, String> {

    private AlertDialog alerta;
    private Context mContext;
    private Long CodPaciente;
    private Integer Option;


    public doTaskCadastroGetDataByCod(Context context,Long CodPaciente,Integer Option){
        mContext = context;
        this.Option =  Option;
        this.CodPaciente = CodPaciente;
    }

    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +Thread.currentThread().getName());Loading();
    }

    @Override
    protected String doInBackground(String... params) {

        String Str = "";
        switch(Option){
            case 1:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetNomePacienteByCod(this.CodPaciente);
                break;
            }
            case 2:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetDocumentoByCod(this.CodPaciente);
                break;
            }
            case 3:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetDocumentoConteudoPacienteByCod(this.CodPaciente);
                break;
            }
            case 4:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetNacionalidadeByCod(this.CodPaciente);
                break;
            }
            case 5:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetEtniaByCod(this.CodPaciente);
                break;
            }
            case 6:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetDataNascByCod(this.CodPaciente);
                break;
            }
            case 7:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetSexoByCod(this.CodPaciente);
                break;
            }
            case 8:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetTelFixoByCod(this.CodPaciente);
                break;
            }
            case 9:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetTelCelularByCod(this.CodPaciente);
                break;
            }
            case 10:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetEmailByCod(this.CodPaciente);
                break;
            }
            case 11:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetCepByCod(this.CodPaciente);
                break;
            }
            case 12:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetEnderecoByCod(this.CodPaciente);
                break;
            }
            case 13:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetComplementoByCod(this.CodPaciente);
                break;
            }
            case 14:
            {
                Str = String.valueOf(FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetNumeroByCod(this.CodPaciente));
                break;
            }
            case 15:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetBairroByCod(this.CodPaciente);
                break;
            }
            case 16:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetMunicipioByCod(this.CodPaciente);
                break;
            }
            case 17:
            {
                Str = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetEstadoByCod(this.CodPaciente);
                break;
            }

        }

        return  Str;
    }

    @Override
    protected void onPostExecute(String result) { alerta.dismiss();}

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

}
