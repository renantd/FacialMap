package br.sofex.com.facialmap.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.R;

public class RecoverLogin extends AppCompatActivity {

    EditText lPassword,luseremail;
    WebView webview_Info;
    TextView info_recover;
    Button BtnRecvLogin,BtnVoltRecvLogin;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_login);


        String info = "*O código de segurança encontra-se no seu e-mail , cadastrado no aplicativo do Facial Map*";
        info_recover = findViewById(R.id.info_recover);
        info_recover.setText(info);
        info_recover.setHintTextColor(Color.BLACK);
        BtnRecvLogin = findViewById(R.id.BtnRecvLogin);
        BtnVoltRecvLogin = findViewById(R.id.BtnVoltRecvLogin);



        luseremail = findViewById(R.id.Lostuseremail);
        luseremail.setHintTextColor(Color.BLACK);

        lPassword = findViewById(R.id.Lostpassword);
        lPassword.setHintTextColor(Color.BLACK);

        BtnRecvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!luseremail.getText().toString().isEmpty() && !lPassword.getText().toString().isEmpty()){
            } else{ msg1("E-mail ou código de segurança em branco !");}
            }
        });
        BtnVoltRecvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(RecoverLogin.this, Login.class);
            startActivity(intent);
            }
        });

        setTitle("Facial Map - Recuperar Login");
    }

    public void setTitle(String title){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void msg1(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(RecoverLogin.this);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(RecoverLogin.this);

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

            }
        });

        alertadd.setView(view);
        TextView title = new TextView(this);
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
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

}
