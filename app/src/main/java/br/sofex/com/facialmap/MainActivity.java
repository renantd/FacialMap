package br.sofex.com.facialmap;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Login.doTaskFotoUserCod;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskListLogin;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskLogout;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskSelectByCod;
import br.sofex.com.facialmap.AsynkTask.Login.doTaskUpdateUserAccount;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetListPaciente;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskListFotoPaciente;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroCheckRow;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetDataByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetFotoByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskSelectSharedPreference;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskTotalPacientes;
import br.sofex.com.facialmap.Estatisticas.Estatisticas;
import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Paciente.PacienteMenu;
import br.sofex.com.facialmap.Sofex.AboutFacialMap;
import br.sofex.com.facialmap.Sofex.AboutSofex;
import br.sofex.com.facialmap.Sofex.SofexSolucoes;
import br.sofex.com.facialmap.Util.Util;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


     //TODO: Variaveis do Tipo Android(Button,TextView ...)
     //TODO: Encapsulamento - Private / Protected / Public
     private ImageView             imgview_tab;
     private View                  headerView;
     private NavigationView        navigationView;
     private AutoCompleteTextView  AC_Paciente;
     private Button                BtnPaciente;
     private Button                BtnMapeamento;
     private Button                BtnEstatisticas;
     private Bitmap                bit;
     private TextView              SelectedPacient;
     private TextView              Txt_UsuarioLogado;
     private TextView              Txt_UsuarioEmailLogado;
     private DrawerLayout          drawer;
     private AlertDialog           alerta;

     //TODO: Variaveis do Tipo Java
     SharedPreferences    sharedPreferences;
     String               MyPREFERENCES = "MyPrefs" ;
     String               Selectedpaciente = "";
     Long                 CodUser;


    //TODO: Instanciar classes
     Mensagem mensagem  = new Mensagem(MainActivity.this);
     Util     util      = new Util(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);


        //TODO: findViewById
        Txt_UsuarioLogado        = findViewById(R.id.Txt_UsuarioLogado);
        Txt_UsuarioEmailLogado   = findViewById(R.id.Txt_UsuarioEmailLogado);
        AC_Paciente              = findViewById(R.id.AC_Paciente);
        SelectedPacient          = findViewById(R.id.TxtUserLogged);
        BtnPaciente              = findViewById(R.id.BtnPaciente);
        BtnMapeamento            = findViewById(R.id.BtnMapeamento);
        BtnEstatisticas          = findViewById(R.id.BtnEstatisticas);


        //TODO: Vaiaveis de de sessão (Shared Preferences)
        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);
        CodUser = sharedPreferences.getLong("CodUser",0);
        SelectedPacient.setText(Selectedpaciente);


        List<String> ListNomes = new ArrayList<>();
        try { ListNomes = new doTaskCadastroCheckRow(MainActivity.this,2).execute().get();}
        catch (ExecutionException ee)   {ee.printStackTrace(); mensagem.ErrorMsg("Fatal Error :"+ee);}
        catch (InterruptedException ie) {ie.printStackTrace(); mensagem.ErrorMsg("Fatal Error :"+ie);}

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_autocompletview, R.id.txtview_autocomplete, ListNomes);
        AC_Paciente.setAdapter(adapter);
        AC_Paciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {CarregarPaciente(AC_Paciente.getText().toString());}
        });


        List<String> list1 = null;
        try {
            list1 = new doTaskGetListPaciente(MainActivity.this,1).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] array = new String[list1.size()];
        list1.toArray(array);


        int[] flags = new int[]{R.drawable.logotipo};

        List<byte[]> list2 = null;
        try {
            list2 = new doTaskListFotoPaciente(MainActivity.this).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        headerView                  = navigationView.getHeaderView(0);
        TextView navUsername        = headerView.findViewById(R.id.Txt_UsuarioLogado);
        TextView navUsernameLogin   = headerView.findViewById(R.id.Txt_UsuarioLoginLogado);
        TextView navUsernameSenha   = headerView.findViewById(R.id.Txt_UsuarioSenhaLogado);
        TextView navUserEmail       = headerView.findViewById(R.id.Txt_UsuarioEmailLogado);

        navUsername.setText(sharedPreferences.getString("Nome",null));
        navUsernameLogin.setText(sharedPreferences.getString("Login",null));
        navUsernameSenha.setText(sharedPreferences.getString("Senha",null));
        navUserEmail.setText(sharedPreferences.getString("Email",null));
        navigationView.setNavigationItemSelectedListener(this);

        /* headerView = navigationView.getHeaderView(0);
        TextView navUsernameLogin = headerView.findViewById(R.id.Txt_UsuarioLoginLogado);
        navUsernameLogin.setText(sharedPreferences.getString("Login",null));

        headerView = navigationView.getHeaderView(0);
        TextView navUsernameSenha = headerView.findViewById(R.id.Txt_UsuarioSenhaLogado);
        navUsernameSenha.setText(sharedPreferences.getString("Senha",null));

        TextView navUserEmail = headerView.findViewById(R.id.Txt_UsuarioEmailLogado);
        navUserEmail.setText(sharedPreferences.getString("Email",null));
        navigationView.setNavigationItemSelectedListener(this);*/
        byte[] b1; Bitmap bitmap1 = null;
        try {
          b1 = new doTaskFotoUserCod(MainActivity.this,CodUser).execute().get();
          bitmap1 = BitmapFactory.decodeByteArray(b1, 0, b1.length);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImageView imgPerfil = headerView.findViewById(R.id.Imaguser);
        bit = bitmap1;
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(),bitmap1);
        roundedBitmapDrawable.setCircular(true);
        imgPerfil.setImageDrawable(roundedBitmapDrawable);


        //TODO: Eventos dos Botões
        final Integer[] CountPaciente = {0};
        BtnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           try {
              CountPaciente[0] = new doTaskTotalPacientes(MainActivity.this).execute().get();
           } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
           }
           Intent intent = new Intent(MainActivity.this, PacienteMenu.class);
           startActivity(intent);
            }
        });

        BtnMapeamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CountPaciente[0] = new doTaskTotalPacientes(MainActivity.this).execute().get();
                 } catch (ExecutionException e) {
                    e.printStackTrace();
                 } catch (InterruptedException e) {
                   e.printStackTrace();
                }
                if(CountPaciente[0] < 1){
                   mensagem.ErrorMsg("Nenhum Paciente Cadastrado ! ");
                 }else{
                    if(Selectedpaciente.equalsIgnoreCase("Nenhum Paciente Selecionado")){
                        mensagem.ErrorMsg("Nenhum Paciente Selecionado ! ");
                    }else{
                        Intent intent = new Intent(MainActivity.this, Menu_Mapeamento.class);
                        startActivity(intent);
                    }
                  }
                }
        });

        BtnEstatisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           try {
               CountPaciente[0] = new doTaskTotalPacientes(MainActivity.this).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           if(CountPaciente[0] < 1){
                mensagem.ErrorMsg("Nenhum Paciente Cadastrado ! ");
             }else{
                Intent intent = new Intent(MainActivity.this, Estatisticas.class);
                startActivity(intent);
             }
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.MinhaConta) {
            //UserDetails();
            Minha_Conta(bit,CodUser);
        }
        else if (id == R.id.Sofex) {
            CallAboutSofex();
        } else if (id == R.id.SofexSolutions) {
            CallServicosSofex();
        } else if (id == R.id.SofexFacilMap) {
            CallAboutFacialMap();
        }
        else if (id == R.id.Deslogar) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void CallAboutSofex() {
        Intent intent = new Intent(getApplicationContext(), AboutSofex.class);
        startActivity(intent);
    }
    public void CallServicosSofex() {
        Intent intent = new Intent(getApplicationContext(), SofexSolucoes.class);
        startActivity(intent);
    }
    public void CallAboutFacialMap() {
        Intent intent = new Intent(getApplicationContext(), AboutFacialMap.class);
        startActivity(intent);
    }

    public  void logout(){
        /*SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Main_Logged.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(Main_Logged.this, MainActivity.class);
        startActivity(intent);*/
        doTaskLogout taskLogout = new doTaskLogout(MainActivity.this);
        taskLogout.execute();
    }

    private void Minha_Conta(final Bitmap bit, final Long CodUser) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.minha_conta, null);

        final TextView TxtNomeUserLabel = view.findViewById(R.id.TxtNomeUserLabel);
        final TextView TxtLoginUserLabel = view.findViewById(R.id.TxtLoginUserLabel);
        final TextView TxtSenhaUserLabel = view.findViewById(R.id.TxtSenhaUserLabel);
        final TextView TxtEmailUserLabel = view.findViewById(R.id.TxtEmailUserLabel);

        String Nomeuser = ""; String Loginuser = ""; String Senhauser = ""; String Emailuser = "";
        try {
            Nomeuser = new doTaskSelectByCod(MainActivity.this,CodUser,1).execute().get();
            Loginuser = new doTaskSelectByCod(MainActivity.this,CodUser,2).execute().get();
            Senhauser = new doTaskSelectByCod(MainActivity.this,CodUser,3).execute().get();
            Emailuser = new doTaskSelectByCod(MainActivity.this,CodUser,4).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TxtNomeUserLabel.setText(new doTaskSelectByCod(MainActivity.this,CodUser,1).execute().get());
            TxtLoginUserLabel.setText(new doTaskSelectByCod(MainActivity.this,CodUser,2).execute().get());
            TxtSenhaUserLabel.setText(new doTaskSelectByCod(MainActivity.this,CodUser,3).execute().get());
            TxtEmailUserLabel.setText(new doTaskSelectByCod(MainActivity.this,CodUser,4).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final EditText EditNomeUpdate = view.findViewById(R.id.EditNomeUpdate);
        final EditText EditLoginUpdate = view.findViewById(R.id.EditLoginUpdate);
        final EditText EditPasswordUpdate = view.findViewById(R.id.EditPasswordUpdate);
        final EditText EditEmailUpdate = view.findViewById(R.id.EditEmailUpdate);

        EditNomeUpdate.setHintTextColor(Color.parseColor("#000000"));
        EditLoginUpdate.setHintTextColor(Color.parseColor("#000000"));
        EditPasswordUpdate.setHintTextColor(Color.parseColor("#000000"));
        EditEmailUpdate.setHintTextColor(Color.parseColor("#000000"));

        ImageButton ImgBtnUpdateFoto = view.findViewById(R.id.ImgBtnUpdateFoto);

        Button BtnUpdateNomeUser = view.findViewById(R.id.BtnUpdateNomeUser);
        Button BtnUpdateLoginUser = view.findViewById(R.id.BtnUpdateLoginUser);
        Button BtnUpdateSenhaUser = view.findViewById(R.id.BtnUpdateSenhaUser);
        Button BtnUpdateEmailUser = view.findViewById(R.id.BtnUpdateEmailUser);
        BtnUpdateNomeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditNomeUpdate.getText().toString().length() <= 2 ){
                    mensagem.ErrorMsg("Nome do Usuário é muito curto !");
                }else{
                    doTaskUpdateUserAccount taskUpdate = new doTaskUpdateUserAccount(MainActivity.this,1,EditNomeUpdate.getText().toString(),CodUser,
                    EditNomeUpdate,EditLoginUpdate,EditPasswordUpdate,EditEmailUpdate,TxtNomeUserLabel,TxtLoginUserLabel,TxtSenhaUserLabel,TxtEmailUserLabel);
                    taskUpdate.execute(); //RefreshbasicInfoAccount();
                    try {
                        //.setText(new doTaskSelectByCod(MainActivity.this,CodUser,1).execute().get());
                        EditNomeUpdate.setText(new doTaskSelectByCod(MainActivity.this,CodUser,1).execute().get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        BtnUpdateLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> listLogin = new ArrayList<>();
                try {
                    listLogin = new doTaskListLogin(MainActivity.this).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(EditLoginUpdate.getText().toString().isEmpty()){
                    mensagem.ErrorMsg("Login em Branco !");
                }else{
                    if(listLogin.contains(EditLoginUpdate.getText().toString())){
                        mensagem.ErrorMsg("Login já existe !");
                    }else{
                        //usuarioDatabaseAdapter.UpdateLoginUserByNome(EditLoginUpdate.getText().toString(),CodUser);
                        doTaskUpdateUserAccount taskUpdate = new doTaskUpdateUserAccount(MainActivity.this,2,EditLoginUpdate.getText().toString(),CodUser,
                        EditNomeUpdate,EditLoginUpdate,EditPasswordUpdate,EditEmailUpdate,TxtNomeUserLabel,TxtLoginUserLabel,TxtSenhaUserLabel,TxtEmailUserLabel);
                        taskUpdate.execute(); RefreshbasicInfoAccount();
                    }
                }
            }
        });
        BtnUpdateSenhaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditPasswordUpdate.getText().toString().isEmpty()){
                    mensagem.ErrorMsg("Senha em Branco !");
                }else{
                    if(util.validarsenha(EditPasswordUpdate.getText().toString()) == true){
                        doTaskUpdateUserAccount taskUpdate = new doTaskUpdateUserAccount(MainActivity.this,3,EditPasswordUpdate.getText().toString(),CodUser,
                        EditNomeUpdate,EditLoginUpdate,EditPasswordUpdate,EditEmailUpdate,TxtNomeUserLabel,TxtLoginUserLabel,TxtSenhaUserLabel,TxtEmailUserLabel);
                        taskUpdate.execute(); RefreshbasicInfoAccount();
                    }else{
                        mensagem.ErrorMsg("Senha Inválida !\nA senha de atender os\nseguintes requesitos:" +
                                "\n\nAo menos 1 Caracter Especial:\n(@$%*?_-?&)\nAo menos 1 Letra Maiúscula\nAo menos 1 Letra Minuscula" +
                                "\nAo menos 8 Caracteres");
                    }
                }
            }
        });
        BtnUpdateEmailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditEmailUpdate.getText().toString().isEmpty()){
                    mensagem.ErrorMsg("Email em Branco !");
                }else{
                    if(util.isValidEmail(EditEmailUpdate.getText().toString()) == true){
                        doTaskUpdateUserAccount taskUpdate = new doTaskUpdateUserAccount(MainActivity.this,4,EditEmailUpdate.getText().toString(),CodUser,
                        EditNomeUpdate,EditLoginUpdate,EditPasswordUpdate,EditEmailUpdate,TxtNomeUserLabel,TxtLoginUserLabel,TxtSenhaUserLabel,TxtEmailUserLabel);
                        taskUpdate.execute(); RefreshbasicInfoAccount();
                    }else{
                        mensagem.ErrorMsg("Email Inválido !");
                    }
                }
            }
        });

        ImageView imgPerfil = view.findViewById(R.id.ImgPerfilAccount);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(),bit);
        roundedBitmapDrawable.setCircular(true);
        imgPerfil.setImageDrawable(roundedBitmapDrawable);

        Button BtnCPCpfClose = view.findViewById(R.id.BtnUpdatePerfilClose);
        BtnCPCpfClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });


        EditNomeUpdate.setText(Nomeuser);
        EditLoginUpdate.setText(Loginuser);
        EditPasswordUpdate.setText(Senhauser);
        EditEmailUpdate.setText(Emailuser);


       final String NomeuserAux = Nomeuser; final String LoginuserAux = Loginuser; final String SenhauserAux = Senhauser; final String EmailuserAux = Emailuser;


        EditNomeUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if(hasfocus){
                    EditNomeUpdate.setText(null);
                }else{
                    EditNomeUpdate.setText(NomeuserAux);
                }
            }
        });
        EditLoginUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if(hasfocus){
                    EditLoginUpdate.setText(null);
                }else{
                    EditLoginUpdate.setText(LoginuserAux);
                }
            }
        });
        EditPasswordUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if(hasfocus){
                    EditPasswordUpdate.setText(null);
                }else{
                    EditPasswordUpdate.setText(SenhauserAux);
                }
            }
        });
        EditEmailUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if(hasfocus){
                    EditEmailUpdate.setText(null);
                }else{
                    EditEmailUpdate.setText(EmailuserAux);
                }
            }
        });

        builder.setView(view);
        //define um botão como positivo

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    public void RefreshbasicInfoAccount(){
        TextView navUsername = headerView.findViewById(R.id.Txt_UsuarioLogado);
        try {
            navUsername.setText(new doTaskSelectByCod(MainActivity.this,CodUser,1).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView navUserelogin = headerView.findViewById(R.id.Txt_UsuarioEmailLogado);
        try {
            navUserelogin.setText(new doTaskSelectByCod(MainActivity.this,CodUser,2).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void CarregarPaciente(String Nome) {

        final boolean[] b = {false};
        final String[] x = {null};
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.carregar_paciente,null);
        //definimos para o botão do layout um clickListener

        byte[] foto = null;
        ImageView img_perfilSelectPac = view.findViewById(R.id.img_perfilSelectPac);
        try {
            foto = new doTaskCadastroGetFotoByNome(MainActivity.this, Nome).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bitmap bit1 = BitmapFactory.decodeByteArray(foto,0,foto.length);
        img_perfilSelectPac.setImageBitmap(bit1);

        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17;
        tv1  =  view.findViewById(R.id.Pac_NomeFoundSelectPac);   tv6  =  view.findViewById(R.id.DataNasc_PacFoundSelectPac);   tv11 =  view.findViewById(R.id.CEP_PacFoundSelectPac);
        tv2  =  view.findViewById(R.id.Doc_PacFoundSelectPac);    tv7  =  view.findViewById(R.id.Sexo_PacFoundSelectPac);       tv12 =  view.findViewById(R.id.End_PacFoundSelectPac);
        tv3  =  view.findViewById(R.id.Doc_ValueFoundSelectPac);  tv8  =  view.findViewById(R.id.TelFixo_PacFoundSelectPac);    tv13 =  view.findViewById(R.id.Comp_PacFoundSelectPac);
        tv4  =  view.findViewById(R.id.Nasc_PacFoundSelectPac);   tv9  =  view.findViewById(R.id.TelCel_PacFoundSelectPac);     tv14 =  view.findViewById(R.id.Num_PacFoundSelectPac);
        tv5  =  view.findViewById(R.id.Etnia_PacFoundSelectPac);  tv10 =  view.findViewById(R.id.Email_PacFoundSelectPac);      tv15 =  view.findViewById(R.id.Bairro_PacFoundSelectPac);
        tv16 =  view.findViewById(R.id.Munic_PacFoundSelectPac);  tv17 =  view.findViewById(R.id.Estado_PacFoundSelectPac);

        try {
            tv1.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,1).execute().get());
            tv2.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,2).execute().get());
            tv3.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,3).execute().get());
            tv4.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,4).execute().get());
            tv5.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,5).execute().get());

            tv6.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,6).execute().get());
            tv7.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,7).execute().get());
            tv8.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,8).execute().get());
            tv9.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,9).execute().get());
            tv10.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,10).execute().get());

            tv11.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,11).execute().get());
            tv12.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,12).execute().get());
            tv13.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,13).execute().get());
            tv14.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,14).execute().get());
            tv15.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,15).execute().get());
            tv16.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,16).execute().get());
            tv17.setText(new doTaskCadastroGetDataByNome(MainActivity.this , Nome,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        Button BtnLoadSelectPac = view.findViewById(R.id.BtnLoadSelectPac);
        BtnLoadSelectPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long long1 = sharedPreferences.getLong("CodUser",0);
                //Toast.makeText(MainActivity.this, "long1 "+long1, Toast.LENGTH_SHORT).show();
                String str = ""; String PacienteCarregado = "";
                try {
                    str = new doTaskSelectSharedPreference(MainActivity.this ,CodUser, AC_Paciente.getText().toString()).execute().get();
                    //PacienteCarregado = new doTaskCarregarPaciente(MainActivity.this ,str).execute().get();
                    //Toast.makeText(MainActivity.this, "PacienteCarregado "+str+" CodUser : "+CodUser, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PacienteSelecionado", str);
                    editor.commit();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);
                SelectedPacient.setText(Selectedpaciente);
                //SelectedPacient.setTextColor(Color.parseColor("#23A4CA"));
                AC_Paciente.setText(null);
                alerta.dismiss();
            }
        });

        Button BtnFecharSelectPac = view.findViewById(R.id.BtnFecharSelectPac);
        BtnFecharSelectPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           AC_Paciente.setText(null);
           alerta.dismiss();
            }
        });

        builder.setView(view);
        //define um botão como positivo
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    public static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

}
