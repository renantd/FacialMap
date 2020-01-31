package br.sofex.com.facialmap.AsynkTask.Paciente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import br.sofex.com.facialmap.Entidades.Paciente;
import br.sofex.com.facialmap.R;

public class doTaskCadastroPaciente  extends AsyncTask<String, Void, Boolean> {

    private Activity activity;
    private ProgressDialog dialog;

    private AlertDialog alerta;
    private Context mContext;
    private String CEP;

    private String Nome;
    private String Documento;
    private String DocumentoValor;
    private String Nacionalidade;
    private String Etnia;
    private String DataDeNascimento;
    private String SexoDoPaciente;
    private String TelefoneFixo;
    private String TelefoneCelular;
    private String Email;
    private String Cep;
    private String Endereco;
    private String Complemento;
    private Integer Numero;
    private String Bairro;
    private String Municipio;
    private String Estado;
    private String NomePaciente;
    private String CaminhoDaFoto;
    private Long CodUsuario;
    byte[] Foto;
    String NomeDaFoto;
    Long CodUsuarioFK = 0l;
    Long CodPacienteFK = 0l;


    public doTaskCadastroPaciente(Context context,String NomePaciente , String Documento , String DocumentoValor, String Nacionalidade ,
        String Etnia , String DataDeNascimento, String SexoDoPaciente,String TelefoneFixo , String TelefoneCelular , String Email, String Cep ,
        String Endereco , String Complemento , Integer  Numero, String Bairro, String  Municipio, String Estado,byte[] Foto , String NomeDaFoto){

        mContext = context;
        this.NomePaciente = NomePaciente;
        this.Documento = Documento;
        this.DocumentoValor = DocumentoValor;
        this.Nacionalidade = Nacionalidade;
        this.Etnia = Etnia;
        this.DataDeNascimento = DataDeNascimento;
        this.SexoDoPaciente = SexoDoPaciente;
        this.TelefoneFixo = TelefoneFixo;
        this.TelefoneCelular = TelefoneCelular;
        this.Email = Email;
        this.Cep = Cep;
        this.Endereco = Endereco;
        this.Complemento = Complemento;
        this.Numero = Numero;
        this.Bairro = Bairro;
        this.Municipio = Municipio;
        this.Estado = Estado;
        this.CaminhoDaFoto  = CaminhoDaFoto;
        this.Foto = Foto;
        this.NomeDaFoto = NomeDaFoto;
    }

    @Override
    protected void onPreExecute() {
        //msg1();
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +Thread.currentThread().getName());
        Loading();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        /*List<String> ListDocValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetDocumentoConteudo();
        List<String> ListNomePacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetNomePacienteList();
        List<String> ListFotoPacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetFotoPacienteList();
        List<String> ListFotoNomePacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetFotoNomePacienteList();*/
        for(Long x : FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetListCods()){
          if(x == 1){ CodUsuarioFK = 1l; CodPacienteFK = 1l;} else if(x > 1){ CodUsuarioFK = x; CodPacienteFK = x;}
        }

        Paciente paciente = new Paciente(NomePaciente ,Documento ,DocumentoValor, Nacionalidade ,Etnia , DataDeNascimento,
        SexoDoPaciente,TelefoneFixo ,TelefoneCelular ,Email, Cep ,Endereco , Complemento , Numero, Bairro, Municipio,
        Estado,Foto , NomeDaFoto,CodUsuarioFK);
        FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().insertPaciente(paciente);
        return  true;
        /*if(ListDocValue.contains(DocumentoValor)){return false;
        }else{

            for(Long x : FacialMapRoomDatabase.getDatabaseMapa(mContext).usuarioDao().GetListCods()){
              if(x == 1){ CodUsuarioFK = 1l; CodPacienteFK = 1l;} else if(x > 1){ CodUsuarioFK = x; CodPacienteFK = x;}
            }

            Paciente paciente = new Paciente(NomePaciente ,Documento ,DocumentoValor, Nacionalidade ,Etnia , DataDeNascimento,
            SexoDoPaciente,TelefoneFixo ,TelefoneCelular ,Email, Cep ,Endereco , Complemento , Numero, Bairro, Municipio, Estado,Foto , NomeDaFoto,CodUsuarioFK);
            FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().insertPaciente(paciente);

            return true;
        }*/

    }

    @Override
    protected void onPostExecute(Boolean result) {
        alerta.dismiss();
    }

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
