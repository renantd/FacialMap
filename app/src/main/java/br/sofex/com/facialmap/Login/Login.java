package br.sofex.com.facialmap.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.Apresentacao1;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskListLogin;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskListPassword;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskLogar;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskNumberUser;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Login extends AppCompatActivity {

    TextView LostPass,DataLogin,tv_dicapass;
    Button BtnCadUser,BtnLogarUser;
    EditText username,password;
    private AlertDialog alerta;
    Mensagem message = new Mensagem(Login.this);
    Util util = new Util(Login.this);
    RelativeLayout RL_CadLogin,RL_LoginUser;
    Integer numberAttemp = 0;
    LinearLayout LinDicaPass;
    Activity activity;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        DataLogin = findViewById(R.id.DataLogin);
        RL_LoginUser = findViewById(R.id.RL_LoginUser);
        RL_CadLogin = findViewById(R.id.RL_CadLogin);
        tv_dicapass = findViewById(R.id.tv_dicapass);
        LinDicaPass = findViewById(R.id.LinDicaPass);
        LinDicaPass.setVisibility(View.GONE);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Login.this.MODE_PRIVATE);
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(d);
        DataLogin.setText(formattedDate);

        //TODO: checa se o numero de usuario é maior que um (Usuário ja cadastrado)
        if(checkExistsUser() == true){
            RL_CadLogin.setVisibility(View.GONE);
            RL_LoginUser.setVisibility(View.VISIBLE);
            username.setEnabled(true);
            password.setEnabled(true);
        }else{
            RL_CadLogin.setVisibility(View.VISIBLE);
            RL_LoginUser.setVisibility(View.GONE);
            username.setEnabled(false);
            password.setEnabled(false);
        }


        util.setTitleBarApp("Facial Map - Logar no aplicativo",getSupportActionBar(),Login.this);
        BtnLogarUser = findViewById(R.id.BtnLogarUser);
        BtnLogarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

               List<String> Login = new ArrayList<>();
               List<String> Senhas = new ArrayList<>();

               try {
                   Login =  new doTaskListLogin(Login.this).execute().get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               try {
                   Senhas =  new doTaskListPassword(Login.this).execute().get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

                //TODO: Teste
               username.setText("RenanCS"); password.setText("Aa!xvt5qweasd");

               if(Login.contains(username.getText().toString()) && Senhas.contains(password.getText().toString())){

                   doTaskLogar tasklogin = new doTaskLogar(Login.this,username.getText().toString());
                   tasklogin.execute();

               }else{message.ErrorMsg(" Nenhum registro encontrado \n com os dados informados !");}

                  /*try {
                        List<String> Logins = new doTaskListLogin(Login.this).execute().get();
                        List<String> Senhas = new doTaskListPassword(Login.this).execute().get();
                        if(Logins.contains(username.getText().toString()) && Senhas.contains(password.getText().toString())){
                          Intent intent = new Intent(Login.this, MainActivity.class);
                          startActivity(intent);
                          overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }else{
                          message.WarningMsg("Login não encontrado. Por favor tente novamente");
                          password.setText("");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.e("FacialMap"," Error1 : "+e);
                        message.ErrorMsg(e.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e("FacialMap"," Error2 : "+e);
                        message.ErrorMsg(e.toString());
                    }*/
                  //Intent intent = new Intent(Login.this, MainActivity.class);
                  //startActivity(intent);
                }else{
              message.ErrorMsg("Login em branco. Por favor ,\n informe os dados necessários");
           }
           numberAttemp++;
           //TODO: Mostra Parte da senha (como no windows)
            if(numberAttemp >= 3 ){
              showDicaPassword();
            }
           Log.e("App1","Result: "+numberAttemp);
            }
        });

        LostPass = findViewById(R.id.LostPass);
        LostPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Login.this, RecoverLogin.class);
           startActivity(intent);
            }
        });

        BtnCadUser = findViewById(R.id.BtnCadUser);
        BtnCadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          Intent intent = new Intent(Login.this, CadastroLogin.class);
          startActivity(intent);
            }
        });

    }

    private Boolean checkExistsUser(){
        Integer row = 0;
        try {
          row = new doTaskNumberUser(Login.this).execute().get();
          Log.e("APP1","Row "+row);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(row >= 1){return true;}
        else{return false;}
    }

    private void showDicaPassword(){
        try {
            List<String > row = new doTaskListPassword(Login.this).execute().get();
            LinDicaPass.setVisibility(View.VISIBLE); String x1 = "";
            for(String x : row){
              for(int i=0; i < x.length();i++){
                    char c = x.charAt(i);
                    if(i <= (x.length()/3) ){x1 = x1 + c;}
                }
            }
            tv_dicapass.setText(x1+" . . . ");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Loading(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(Login.this);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(Login.this);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.loading_custom, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);


        alertadd.setView(view);
        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    @Override
    public void onBackPressed() {
       Log.e("App1","Pressed: ");
       Intent intent = new Intent(Login.this, Apresentacao1.class);
       startActivity(intent);
    }


}
