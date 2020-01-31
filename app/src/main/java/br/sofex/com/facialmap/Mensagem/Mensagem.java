package br.sofex.com.facialmap.Mensagem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.sofex.com.facialmap.AsynkTask.Login.doTaskUsuarioInsert;
import br.sofex.com.facialmap.Login.CodigoRecuperacao;
import br.sofex.com.facialmap.Login.Login;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;

public class Mensagem {

    Context mContext;
    private AlertDialog alerta;
    public Mensagem(Context context){
        mContext = context;
    }
    CodigoRecuperacao cdRecover = new CodigoRecuperacao();


    public void MsgCreate(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.create_layout, null);
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
    public void SucessMsgClickRedirect(String SucessMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.sucess_redirect, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_SucessOKRedirec);
        textdialog.setText(SucessMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);


        Button BtnSucessOKRedirec = view.findViewById(R.id.BtnSucessOKRedirec);
        BtnSucessOKRedirec.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
             Intent intent = new Intent(mContext,MainActivity.class);
             mContext.startActivity(intent);
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
    public void ErrorMsgRedirect(String ErroMsg, final Context context, final Class destClass){

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
                //alerta.dismiss();
                Intent intent = new Intent(context, destClass);
                context.startActivity(intent);
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
    public void MsgImg(Bitmap bit){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        ImageView img1 = new ImageView(mContext);
        img1.setImageBitmap(bit);
        builder.setView(img1);
        builder.setPositiveButton("Dispensar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
    public void SucessMsgCad(final String Nome, final String Login, final String Senha, final String Email, final byte[] File, final String FileNome,final Long FileTamanho,Bitmap bit){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.msg_cad_user, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog1 = view.findViewById(R.id.tv_NomeCadSuc);
        textdialog1.setText(Nome);

        TextView textdialog2 = view.findViewById(R.id.tv_LoginCadSuc);
        textdialog2.setText(Login);

        TextView textdialog3 = view.findViewById(R.id.tv_SenhaCadSuc);
        textdialog3.setText(Senha);

        TextView textdialog4 = view.findViewById(R.id.tv_EmailCadSuc);
        textdialog4.setText(Email);

        ImageView img1 = view.findViewById(R.id.imgUserCad);
        img1.setImageBitmap(bit);

        TextView textdialog5 = view.findViewById(R.id.tv_FotoNome);
        textdialog5.setText(FileNome);

        TextView textdialog6 = view.findViewById(R.id.tv_FotoTamanho);
        textdialog6.setText(FileTamanho.toString()+" KB");

        Button Btn1 = view.findViewById(R.id.Btn_SalvarCadUser);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            doTaskUsuarioInsert userInsert = new doTaskUsuarioInsert(mContext,Nome,Login,Senha,Email,File,FileNome);
            userInsert.execute();
            alerta.dismiss();
           ConfSendCodEmail(Email);
            }
        });
        Button Btn2 = view.findViewById(R.id.Btn_CancelCadUser);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    public void ConfSendCodEmail(final String Email){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.enviar_codigo, null);
        //definimos para o botão do layout um clickListener

        Button Btn1 = view.findViewById(R.id.BtnConfCanCod);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alerta.dismiss();
                Intent intent = new Intent(mContext, Login.class);
                mContext.startActivity(intent);
            }
        });
        Button Btn2 = view.findViewById(R.id.BtnConfSendCod);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + Email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Facial Maps - Código de Recuperação");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Código de Recuperação : " +cdRecover.GenCodRecover());
                alerta.dismiss();
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);

                try {
                    mContext.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    ErrorMsg("Nenhum cliente de e-mail instalado.");
                }

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
