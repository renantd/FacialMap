package br.sofex.com.facialmap.Paciente.Cadastro;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.br.facialmap.Mask.MaskCPF;
import br.sofex.com.br.facialmap.Mask.MaskTelefone;
import br.sofex.com.facialmap.AsynkTask.AsyncTaskCheckCep;
import br.sofex.com.facialmap.AsynkTask.AsyncTaskLoadCep;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroCheckFoto;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroCheckRow;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroPaciente;
import br.sofex.com.facialmap.BuildConfig;
import br.sofex.com.facialmap.Json.JsonCep;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Paciente.PacienteMenu;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.MaskEdit;
import br.sofex.com.facialmap.Util.Util;
import br.sofex.com.facialmap.Util.Validacoes;
import pub.devrel.easypermissions.EasyPermissions;

public class Cadastro_Paciente extends AppCompatActivity {

    Util util = new Util(Cadastro_Paciente.this);
    Button BtnTelaPrincipal,BtnVoltar,BtnSalvarPaciente;

    /*TODO: Declaração de Variáveis  de Botão*/
    Button BtnSaveDoc,BtnSelFoto,BtnLoadCep,BtnCPSalvarPaciente,
    BtnCPVoltarMenu,BtnCPTelaPrincipal;

    /*TODO: Declaração de Variáveis  de Spinner(Combo Box Android)*/
    Spinner Spin_Doc,Spin_Nac,Spin_Etnia;

    /*TODO: Declaração de Variáveis  de EditText*/
    EditText Edit_NomePac,Edit_DtNasc,Edit_TelFixo,Edit_TelCel,Edit_CadPacEmail,
    Edit_CepEnd,Edit_End,Edit_EndNum,Edit_EndComp,Edit_Bairro,Edit_EndMuni,Edit_EndEstado;

    /*TODO: Declaração de Variáveis  de Table Row*/
    TableRow TL_DocResult;
    TableRow TR_ShowFotoPerfil1;

    /*TODO: Declaração de Variáveis  de TextView*/
    TextView doc_escolhido,doc_value,tv_NomeFilePac1,tv_SizeFilePac1;

    /*TODO: Declaração de Variáveis  de RadioButton e RadioGroup*/
    RadioButton RB_Masc,RB_Fem;
    RadioGroup RadioSexo;

    /*TODO: Declaração de Variáveis  de Contexto*/
    Context mContext;

    /*TODO: Declaração de Variáveis  de ImageView*/
    ImageView imgFP_Pac1;

    /*TODO: Variáveis do tipo TextWatcher*/
    private TextWatcher cpfMask;

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog alerta;

    /*TODO: Variáveis do tipo Integer*/
    private static final int READ_REQUEST_CODE = 300;
    static final int REQUEST_TAKE_PHOTO = 1;

    /*TODO: Variáveis do tipo Bitmap*/
    private Bitmap bitmap;

    /*TODO: Variáveis do tipo URI*/
    private Uri capturedImageUri;


    /*TODO: Variáveis do tipo Bitmap*/
    private Bitmap bitmap2;

    /*TODO: Declaração de Variáveis*/
    /*TODO: Variáveis do tipo String*/
    private String list = "";
    private String rg_escolhido;
    private String cpf_escolhido;
    private String passaporte_escolhido;
    private String cnh_escolhido;
    private String CPFResult;
    private String RGResult;
    private String Carteira_Trabalho_Result;
    private String Titulo_Eleitor_Result;
    private String Passaporte_Result;
    private String CNH_Result;
    private String ValueDialog;
    private String selectedImagePath;
    private String DocumentoSelect = null;
    private String DocumentoValue = null;
    private String selectedPath;
    private String FilePathCorrigido;
    private String NomePaciente= "";
    private String DtNascValido = null;
    private Date DataNascFinal = null;
    private String capturedFileName = null;
    private String SexoSelecionado = "";
    private byte[] FotoDB = null;

    public void setList(String list) {this.list = list;}
    public void Cadastrar(Context context){this.mContext = context;}
    public void Cadastrar(){}
    Mensagem mensagem = new Mensagem(Cadastro_Paciente.this);
    Validacoes valid = new Validacoes(Cadastro_Paciente.this);
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__paciente_1);

        BtnTelaPrincipal = findViewById(R.id.BtnCPTelaPrincipal);
        BtnVoltar = findViewById(R.id.BtnCPVoltarMenu);
        BtnSalvarPaciente = findViewById(R.id.BtnCPSalvarPaciente);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Cadastro_Paciente.this.MODE_PRIVATE);

        util.setTitleBarApp("Facial Map - Cadastro de Paciente",getSupportActionBar(), Cadastro_Paciente.this);
        util.ClickCallIntent(BtnTelaPrincipal, Cadastro_Paciente.this, MainActivity.class);
        util.ClickCallIntent(BtnVoltar, Cadastro_Paciente.this, PacienteMenu.class);
        /*TODO: importação de classes internas*/

        //TODO: INTÂNCIA OS COMPONENTES CRIADOS NO XML
        /*TODO: findViewById de Button*/
        BtnSaveDoc = findViewById(R.id.BtnCPSalvarPaciente);
        BtnSelFoto = findViewById(R.id.BtnSelFoto);
        BtnCPSalvarPaciente = findViewById(R.id.BtnCPSalvarPaciente);
        //BtnCPVoltarMenu = findViewById(R.id.BtnCPVoltarMenu);
        //BtnCPTelaPrincipal = findViewById(R.id.BtnCPTelaPrincipal);

        /*TODO: findViewById de Imageview*/
        imgFP_Pac1 = findViewById(R.id.imgFP_Pac1);

        /*TODO: findViewById de TableRow*/
        TR_ShowFotoPerfil1 =findViewById(R.id.TR_ShowFotoPerfil1);

        /*TODO: findViewById de Spinner*/
        Spin_Doc = findViewById(R.id.Spin_Doc);
        Spin_Nac = findViewById(R.id.Spin_Nac);
        Spin_Etnia = findViewById(R.id.Spin_Etnia);

        /*TODO: findViewById de Radio Button e Group*/
        RB_Masc = findViewById(R.id.RB_Masc);
        RB_Fem = findViewById(R.id.RB_Fem);
        RadioSexo = findViewById(R.id.RadioSexo);

        /*TODO: findViewById de EditText*/
        Edit_NomePac = findViewById(R.id.Edit_NomePac);
        Edit_DtNasc = findViewById(R.id.Edit_DtNasc);
        Edit_TelFixo = findViewById(R.id.Edit_TelFixo);
        Edit_TelCel = findViewById(R.id.Edit_TelCel);
        Edit_CadPacEmail = findViewById(R.id.Edit_CadPacEmail);
        Edit_CepEnd = findViewById(R.id.Edit_CepEnd);
        Edit_End = findViewById(R.id.Edit_End);
        Edit_EndNum = findViewById(R.id.Edit_EndNum);
        Edit_EndComp = findViewById(R.id.Edit_EndComp);
        Edit_EndMuni = findViewById(R.id.Edit_EndMuni);
        Edit_EndEstado = findViewById(R.id.Edit_EndEstado);
        Edit_Bairro = findViewById(R.id.Edit_Bairro);
        BtnLoadCep = findViewById(R.id.BtnLoadCep);

        /*TODO: findViewById de TextView*/
        doc_escolhido = findViewById(R.id.CadPacDocLabel);
        doc_value = findViewById(R.id.CadPacDocValue);
        tv_NomeFilePac1 = findViewById(R.id.tv_NomeFilePac1);
        tv_SizeFilePac1 = findViewById(R.id.tv_SizeFilePac1);

        /*TODO: findViewById de TableLayout*/
        TL_DocResult = findViewById(R.id.TL_DocResult);
        /*TODO: ---------------------*/


        /*TODO: Esconder e mostrar Views*/
        TR_ShowFotoPerfil1.setVisibility(View.GONE);
        TL_DocResult.setVisibility(View.GONE);

        /*TODO: Spinner , Adpters e Arrays de Spinners*/
        /*TODO: Array de Documentos*/
        String[] Documentos = new String[]{
        "Documento","CPF", "Carteira de Trabalho", "Título de Eleitor" , "Passaporte" , "CNH" };
        ArrayAdapter<String> adpterDocumentos = new ArrayAdapter<>(Cadastro_Paciente.this,R.layout.spinner_documento_center,R.id.sp_item_doc_center,Documentos);
        adpterDocumentos.setDropDownViewResource(R.layout.spinner_documento_center);

        /*TODO: Array de Nacionalidades*/
        String[] Nacionalidades = new String[]{
        "Escolha um País","Alemanha","Argentina ","Austrália","Bélgica","Bolívia","Brasil","Canadá","Chile","Colômbia","Bélgica ","Bolívia ","Coréia do Sul",
        "Croácia","Dinamarca","Equador","Espanha","Estados Unidos","Finlândia","França","Grécia","Holanda","Inglaterra","Irlanda ","Itália","Japão","México",
        "Paraguai","Peru","Estados Unidos","Portugal","Reino Unido","Rússia","Uruguai","Venezuela"};
        ArrayAdapter<String> adpterNacionalidades = new ArrayAdapter<>(Cadastro_Paciente.this,R.layout.spinner_custom_center,R.id.sp_item_center,Nacionalidades);
        adpterNacionalidades.setDropDownViewResource(R.layout.spinner_custom_center);

        /*TODO: Array de etnias*/
        String[] Etnias = new String[]{
        "Escolha a Etnia","Caucasiano(a)","Afro-descendente","Latino/Hispânico(a)","Asiático(a)","Pardo(a)/Mulato(a)","Cafuzo(a)"};
        ArrayAdapter<String> adpterEtnia = new ArrayAdapter<>(Cadastro_Paciente.this,R.layout.spinner_custom_center,R.id.sp_item_center,Etnias);
        adpterEtnia.setDropDownViewResource(R.layout.spinner_custom_center);

        /*TODO: SetAdapter*/
        Spin_Doc.setAdapter(adpterDocumentos);
        Spin_Doc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Doc.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Doc.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);


        Spin_Nac.setAdapter(adpterNacionalidades);
        Spin_Nac.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Nac.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Nac.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);


        Spin_Etnia.setAdapter(adpterEtnia);
        Spin_Etnia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Etnia.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Etnia.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        /*TODO: Fim -------- */


        /*TODO: Button Events*/
        BtnSaveDoc.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View view){}
        });
        BtnSelFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EasyPermissions.hasPermissions(Cadastro_Paciente.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i, 1);
                    /*TR_ShowFotoPerfil.setVisibility(View.VISIBLE);

                    //bitmap decode from drawable folder
                    Resources res = Cadastro_Paciente.this.getResources();
                    int id = R.drawable.logotipo;
                    Bitmap bitDefault = BitmapFactory.decodeResource(res, id);

                    imgFP_Pac.setImageBitmap(bitDefault);
                    tv_NomeFilePac.setText(null);
                    tv_SizeFilePac.setText(null);*/

                }
                else {
                    EasyPermissions.requestPermissions(Cadastro_Paciente.this,"Este aplicativo precisa acessar o armazenamento. Por favor autorize o acesso.",
                    READ_REQUEST_CODE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        BtnLoadCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(!Edit_CepEnd.getText().toString().isEmpty()){
                    String str = Edit_CepEnd.getText().toString();
                    try {
                        Boolean CheckCep = new AsyncTaskCheckCep(Cadastro_Paciente.this,str).execute().get();
                        if(CheckCep == true){
                            String str2 = str.replace("-", "");
                            try {
                                String retorno = new JsonCep(Cadastro_Paciente.this,str2).execute().get();
                                Boolean CheckReturn = new AsyncTaskLoadCep(Cadastro_Paciente.this,retorno,Edit_End,Edit_Bairro,Edit_EndMuni,Edit_EndEstado).execute().get();
                                if(CheckReturn == false){
                                    mensagem.ErrorMsg("O cep informado não existe");
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }else{
                            mensagem.ErrorMsg(" O cep informado é inválido");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        /*TODO: ******** */
        /*TODO:  ****** */
        /*TODO:   **** */
        /*TODO:    ** */
        BtnCPSalvarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(valid.ValidarRegistro(
               valid.ValidarDadosPessoais(Edit_NomePac.getText().toString(),DocumentoSelect,DocumentoValue,Spin_Nac.getSelectedItem().toString(),Spin_Etnia.getSelectedItem().toString(),
               Edit_DtNasc.getText().toString(),SexoSelecionado),

               valid.ValidarDadosDeContato(Edit_TelFixo.getText().toString(),Edit_TelCel.getText().toString(),Edit_CadPacEmail.getText().toString()),

               valid.ValidarDadosEndereco(Edit_CepEnd.getText().toString(),Edit_End.getText().toString(),Edit_EndComp.getText().toString(),Edit_EndNum.getText().toString(),
               Edit_Bairro.getText().toString(),Edit_EndMuni.getText().toString(),Edit_EndEstado.getText().toString()),

               valid.ValidarDadosDePerfil(FilePathCorrigido,FotoDB,capturedFileName)) == true){

                Log.e("App1","Cod User Save: "+sharedPreferences.getLong("CodUser",0));
                    try {

                        List<String> ListDocValue = new doTaskCadastroCheckRow(Cadastro_Paciente.this,1).execute().get();
                        List<String> ListNomePacienteValue = new doTaskCadastroCheckRow(Cadastro_Paciente.this,2).execute().get();
                        List<byte[]> ListFotoPacienteValue = new doTaskCadastroCheckFoto(Cadastro_Paciente.this).execute().get();
                        List<String> ListFotoNomePacienteValue = new doTaskCadastroCheckRow(Cadastro_Paciente.this,3).execute().get();

                        for(String x : ListNomePacienteValue){
                            Log.e("App1", "Value 123123: "+x+" - "+Edit_NomePac.getText().toString());
                        }

                        if(ListDocValue.contains(DocumentoValue) || ListNomePacienteValue.contains(Edit_NomePac.getText().toString()) ||
                                ListFotoPacienteValue.contains(FotoDB) || ListFotoNomePacienteValue.contains(capturedFileName)){

                            if(ListDocValue.contains(DocumentoValue)){

                                mensagem.ErrorMsg("Documento já Cadastrado");
                                //Toast.makeText(mContext, "Documento já Cadastrado", Toast.LENGTH_SHORT).show();

                            }else if(ListNomePacienteValue.contains(NomePaciente)){

                                mensagem.ErrorMsg("Nome já Cadastrado");

                            }
                            else if(ListFotoPacienteValue.contains(FotoDB)){

                                mensagem.ErrorMsg("Foto já Cadastrado");

                            }
                            else if(ListFotoNomePacienteValue.contains(capturedFileName)){

                                mensagem.ErrorMsg("Foto com este nome já está Cadastrado");

                            }

                        }else{
                               Boolean checkCad = new doTaskCadastroPaciente(Cadastro_Paciente.this,
                               Edit_NomePac.getText().toString(),DocumentoSelect,DocumentoValue,Spin_Nac.getSelectedItem().toString(),Spin_Etnia.getSelectedItem().toString(),Edit_DtNasc.getText().toString(),SexoSelecionado,
                               Edit_TelFixo.getText().toString(),Edit_TelCel.getText().toString(),Edit_CadPacEmail.getText().toString(),Edit_CepEnd.getText().toString(),Edit_End.getText().toString(),Edit_EndComp.getText().toString(),
                               Integer.parseInt(Edit_EndNum.getText().toString()),Edit_Bairro.getText().toString(),Edit_EndMuni.getText().toString(),Edit_EndEstado.getText().toString(),FotoDB,capturedFileName).execute().get();
                            if(checkCad == true){
                                Edit_NomePac.setText(null); Spin_Doc.setSelection(0); Spin_Nac.setSelection(0);
                                Spin_Etnia.setSelection(0); Edit_DtNasc.setText(null); RadioSexo.clearCheck();
                                Edit_TelFixo.setText(null); Edit_TelCel.setText(null); Edit_CadPacEmail.setText(null);

                                Edit_CepEnd.setText(null); Edit_End.setText(null); Edit_EndComp.setText(null);
                                Edit_EndNum.setText(null); Edit_Bairro.setText(null); Edit_EndMuni.setText(null); Edit_EndEstado.setText(null);
                                TR_ShowFotoPerfil1.setVisibility(View.GONE);
                                mensagem.SucessMsg("Registro Salvo");
                            }else{
                                mensagem.ErrorMsg("Documento já Cadastrado !");
                                //Toast.makeText(Cadastro_Paciente.this, "Erro no Registro do Cadastro", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }else{Toast.makeText(Cadastro_Paciente.this, "Erro ao Cadastrar !", Toast.LENGTH_SHORT).show(); }

            }
        });
        /*TODO: Fim -------- */


        /*TODO: Spinner Events*/
        Spin_Doc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(Spin_Doc.getSelectedItemPosition() != 0 ){
                    if(Spin_Doc.getSelectedItemPosition() == 1 ){
                        PreencherCpf();
                    }

                    if(Spin_Doc.getSelectedItemPosition() == 2 ){
                        PreencherCarteiraTrabalho();
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 3 ){
                        PreencherTituloEleitor();
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 4 ){
                        PreencherPassaporte();
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 5 ){
                        PreencherCNH();
                    }
                }else{
                    doc_escolhido.setText(null);
                    doc_value.setText(null);
                    TL_DocResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*TODO: EditText Events*/
        Edit_NomePac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {if(Edit_NomePac != null || !Edit_NomePac.getText().toString().isEmpty()){NomePaciente = Edit_NomePac.getText().toString();}}
        });

        /*TODO: Adciona as mascaras  para os campos de Telefone , CEP e Data de Nascimento*/
        Edit_TelFixo.addTextChangedListener(MaskTelefone.insert(Edit_TelFixo));
        Edit_TelCel.addTextChangedListener(MaskTelefone.insert(Edit_TelCel));
        Edit_CepEnd.addTextChangedListener(MaskEdit.mask(Edit_CepEnd, MaskEdit.FORMAT_CEP));
        Edit_DtNasc.addTextChangedListener(MaskEdit.mask(Edit_DtNasc, MaskEdit.FORMAT_DATE));

        /*TODO: EditText Data de Nascimento Events*/
        Edit_DtNasc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {if(!hasfocus){if(!Edit_DtNasc.getText().toString().isEmpty()){
                if(!Edit_DtNasc.getText().toString().isEmpty()){
                  DtNascValido = Edit_DtNasc.getText().toString();
                }
            }}}
        });
        /*TODO: Fim -------- */

        /*TODO: RadioButton, RadioGroup*/
        RadioSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
          switch(checkedId){
                    case R.id.RB_Masc:
                        SexoSelecionado = "Masculino";
                        break;
                    case R.id.RB_Fem:
                        SexoSelecionado = "Feminino";
                        break;
                }
            }
        });
    }

    /*TODO: FUNÇÕES DE PERMISSÕES*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // now, you have permission go ahead
            Intent i = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(i, 1);

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Cadastro_Paciente.this,
                Manifest.permission.READ_CALL_LOG)) {
                EasyPermissions.requestPermissions(Cadastro_Paciente.this, "Este aplicativo precisa acessar o armazenamento interno. Por favor autorize o acesso ", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                  Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Voçê negou o acesso ao aplicativo.\n" +
                  "Você precissa aprovar a(s) permissão(ôes)", Snackbar.LENGTH_LONG).setAction("Alterar", new View.OnClickListener() {
                  @Override
                   public void onClick(View view) {
                   startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                 }
                });
              snackbar.show();
            }

        }
    }
    /*TODO: FIM ---------------- */

    /*TODO: FUNÇÕES DE RESULTADO DA CÂMERA*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            capturedImageUri = data.getData();
            capturedFileName = GetFileNameIntent(data);
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                selectedImagePath = util.getRealPathFromURIPath(capturedImageUri,Cadastro_Paciente.this);
                //FilePathFolderPac = CriarPastaPaciente(Edit_NomePac.getText().toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capturedImageUri);
                    if(capturedFileName != null){
                        if(!Edit_NomePac.getText().toString().isEmpty()){
                            //CriarPastaPaciente(Edit_NomePac.getText().toString());
                            //TODO: Cria um arquivo no formato jpg
                            File file  = new File("/data/data/br.sofex.com.facialmap/cache/"+"Captured.jpg");
                            file.createNewFile();
                            String FilePathFolderPac = null; Long FileSize = file.length();
                            try{
                                FilePathFolderPac = util.CriarPastaPaciente(Edit_NomePac.getText().toString(),Cadastro_Paciente.this);
                            } catch (NullPointerException nullEX) {
                                mensagem.ErrorMsg("A Pasta intena no Aplicativo do FacialMap\nPara este paciente já existe.");
                                nullEX.printStackTrace();
                            }

                            selectedPath = file.getAbsolutePath();
                            //Convert bitmap to byte array
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            byte[] bitmapdata = bos.toByteArray();

                            //write the bytes in file
                            //TODO: salva os bytes no arquivo , e fecha . Gerando um novo arquivo.
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(bitmapdata);
                            fos.flush(); fos.close();

                            //TODO: Rotaciona o arquivo de foto  para a orientação correta, e passa para o bitmap
                            Bitmap bit = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));

                            //TODO: Salva o arquivo corrigido
                            util.saveBitmap(bit,file.getAbsolutePath());
                            //FilePathCorrigido = file.getAbsolutePath();
                            if(capturedFileName != null){
                                if(FilePathFolderPac != null){
                                    File rootPath = new File(FilePathFolderPac);

                                    if(rootPath.exists()){
                                        //Toast.makeText(Cadastrar.this, "Existe", Toast.LENGTH_SHORT).show();
                                        // TODO: move a foto / 100%
                                        /* File from = file;
                                         File to = new File(FilePathFolderPac+"/",capturedFileName);
                                         from.renameTo(to);*/
                                        File from = file;
                                        File to = new File(FilePathFolderPac+"/",capturedFileName);
                                        util.copyFromTo(from,to);
                                        FotoDB = util.FileImageTobyteArray(to);
                                        FilePathCorrigido = to.getAbsolutePath();

                                    }else {
                                        //Toast.makeText(Cadastrar.this, " Não Existe ", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    mensagem.ErrorMsg("Não foi possivel criar a pasta,\n pois a mesma ja existe !");
                                }
                            }

                            TR_ShowFotoPerfil1.setVisibility(View.VISIBLE);
                            imgFP_Pac1.setImageBitmap(bit);
                            tv_NomeFilePac1.setText(capturedFileName);
                            tv_SizeFilePac1.setText((file.length())+" KB");
                        }else{mensagem.ErrorMsg("Não foi possível criar a pasta do paciente,\npois o campo do nome do paciente\n está em branco");}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else Toast.makeText(getApplicationContext()," Sem permissão !", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext()," Error :" + requestCode + " / " + resultCode + " / " + data, Toast.LENGTH_LONG).show();
            Log.e("App" , " requestCode :" + requestCode);
            Log.e("App" , " resultCode :" + resultCode);
            Log.e("App" , " data :" + data);
        }

    }
    /*TODO: FIM ---------------- */


    //TODO: Funções de preenchimento de campos
    /*TODO: preencher cpf*/
    private void PreencherCpf() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_cpf, null);
        //definimos para o botão do layout um clickListener
        final EditText EditCPFPac = view.findViewById(R.id.EditCPFPac);
        EditCPFPac.addTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditCPFPac));
        builder.setView(EditCPFPac);


        Button BtnCPCpfClose = view.findViewById(R.id.BtnCPCpfSave);
        BtnCPCpfClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spin_Doc.setSelection(0);
                alerta.dismiss();
            }
        });
        Button BtnCPCpfSave = view.findViewById(R.id.BtnCPCpfSave);
        BtnCPCpfSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(!EditCPFPac.getText().toString().isEmpty()){
              TL_DocResult.setVisibility(View.VISIBLE);
              doc_escolhido.setText("(CPF) :");
              doc_value.setText(EditCPFPac.getText().toString());
              DocumentoSelect = "CPF";
              DocumentoValue = EditCPFPac.getText().toString();
              alerta.dismiss();
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

    /*TODO: preencher RG // desativado por enquanto */
    private void PreencherRG() {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_rg, null);
        //definimos para o botão do layout um clickListener
        final EditText EditRGPac = view.findViewById(R.id.EditRGPac);
        builder.setView(EditRGPac);

        Button BtnCP_RG_Cancel = view.findViewById(R.id.BtnCP_RG_Save);
        BtnCP_RG_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnCP_RG_Save = view.findViewById(R.id.BtnCP_RG_Save);
        BtnCP_RG_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditRGPac.getText().toString().isEmpty()){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    doc_escolhido.setText("Documento (RG) :");
                    doc_value.setText(EditRGPac.getText().toString());
                    DocumentoSelect = "RG";
                    DocumentoValue = EditRGPac.getText().toString();
                    alerta.dismiss();
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

    /*TODO: preencher Carteira de Trabalho*/
    private void PreencherCarteiraTrabalho() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_carteira_trabalho, null);
        //definimos para o botão do layout um clickListener
        final EditText EditCarteiraTrabPac = view.findViewById(R.id.EditCarteiraTrabPac);
        EditCarteiraTrabPac.addTextChangedListener(MaskCPF.insertCPF("#######", EditCarteiraTrabPac));


        Button BtnCP_CT_Close = view.findViewById(R.id.BtnCP_CT_Close);
        BtnCP_CT_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Spin_Doc.setSelection(0);
           alerta.dismiss();
            }
        });

        Button BtnCP_CT_save = view.findViewById(R.id.BtnCP_CT_save);
        BtnCP_CT_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!EditCarteiraTrabPac.getText().toString().isEmpty()){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    doc_escolhido.setText("(CTPS) :");
                    doc_value.setText(EditCarteiraTrabPac.getText().toString());
                    DocumentoSelect = "CTPS";
                    DocumentoValue = EditCarteiraTrabPac.getText().toString();
                    alerta.dismiss();
                }
            }
        });

        builder.setView(EditCarteiraTrabPac);
        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: preencher Titulo de Eleitor*/
    private void PreencherTituloEleitor() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_titulo_eleitor, null);
        //definimos para o botão do layout um clickListener
        final EditText EditTEPac = view.findViewById(R.id.EditTituloElePac);
        EditTEPac.addTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", EditTEPac));

        Button BtnCP_TE_Close = view.findViewById(R.id.BtnCP_TE_Close);
        BtnCP_TE_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spin_Doc.setSelection(0);alerta.dismiss();
            }
        });

        Button BtnCP_TE_save = view.findViewById(R.id.BtnCP_TE_save);
        BtnCP_TE_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditTEPac.getText().toString().isEmpty()){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    doc_escolhido.setText("(Tit. Eleitor) :");
                    doc_value.setText(EditTEPac.getText().toString());
                    DocumentoSelect = "Título de Eleitor";
                    DocumentoValue = EditTEPac.getText().toString();
                    alerta.dismiss();
                }
            }
        });

        builder.setView(EditTEPac);
        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: preencher Passaporte*/
    private void PreencherPassaporte() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_passaporte, null);
        //definimos para o botão do layout um clickListener
        final EditText EditPassaportePac = view.findViewById(R.id.EditPassaportePac);
        EditPassaportePac.addTextChangedListener(MaskCPF.insertCPF("##.######", EditPassaportePac));

        Button BtnCP_Passaporte_Close = view.findViewById(R.id.BtnCP_Passaporte_Close);
        BtnCP_Passaporte_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spin_Doc.setSelection(0);alerta.dismiss();
            }
        });

        Button BtnCP_Passaporte_save = view.findViewById(R.id.BtnCP_Passaporte_save);
        BtnCP_Passaporte_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditPassaportePac.getText().toString().isEmpty()){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    doc_escolhido.setText("(Passaporte) :");
                    doc_value.setText(EditPassaportePac.getText().toString());
                    DocumentoSelect = "Passaporte";
                    DocumentoValue = EditPassaportePac.getText().toString();
                    alerta.dismiss();
                }
            }
        });

        builder.setView(EditPassaportePac);
        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: preencher CNH*/
    private void PreencherCNH() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_cnh, null);
        //definimos para o botão do layout um clickListener
        final EditText EditCNHPac = view.findViewById(R.id.EditCarteiraTrabPac);
        EditCNHPac.addTextChangedListener(MaskCPF.insertCPF("###########", EditCNHPac));

        Button BtnCP_CNH_Close = view.findViewById(R.id.BtnCP_CNH_Close);
        BtnCP_CNH_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spin_Doc.setSelection(0);alerta.dismiss();
            }
        });

        Button BtnCP_CNH_save = view.findViewById(R.id.BtnCP_CNH_save);
        BtnCP_CNH_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditCNHPac.getText().toString().isEmpty()){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    doc_escolhido.setText("(CNH) :");
                    doc_value.setText(EditCNHPac.getText().toString());
                    DocumentoSelect = "CNH";
                    DocumentoValue = EditCNHPac.getText().toString();
                    alerta.dismiss();
                }
            }
        });

        builder.setView(EditCNHPac);
        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }
    //TODO: -------------------------- //

    /*TODO: FUNÇÃO PARA AJUSTAR A FOTO PARA A ORIENTAÇÃO CORRETA
     * TODO: INFELIZMENTE, AINDA NÂO CONSEGUI HERDA ESTA FUNÇÃO DE OUTRA CLASSE*/
    public Bitmap FixOrientatioImage(final Uri selectedImageUri) {
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            ExifInterface exif = new ExifInterface(selectedImageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;

                default:
                    angle = 0;
                    break;
            }

            Matrix mat = new Matrix();

            if (angle == 0 && bm.getWidth() > bm.getHeight())
                mat.postRotate(270);
            else
                mat.postRotate(angle);

            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);

        }
        catch (IOException e) {
            mensagem.ErrorMsg("Error :"+e);
            Log.e("", "-- Error in setting image");
        }
        catch (OutOfMemoryError oom) {
            mensagem.ErrorMsg("Error :"+oom);
            Log.e("", "-- OOM Error in setting image");
        }
        catch (NullPointerException npe) {
            mensagem.ErrorMsg("Error :"+npe);
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }

    /*TODO: FUNÇÃO PARA RETORNAR NOME DO AQUIVO PELO INTENT*/
    private String GetFileNameIntent(Intent data){
        Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;
        String finalName = "";

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = Cadastro_Paciente.this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    finalName = displayName;
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
            finalName = displayName;
        }
        return  finalName;
    }

    /*private Boolean checkExistsRow(String DocumentoValor,String NomePaciente ,byte[] foto , String NomeFoto){
        List<String> ListDocValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetDocumentoConteudo();
        List<String> ListNomePacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetNomePacienteList();
        List<byte[]> ListFotoPacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetFotoPacienteList();
        List<String> ListFotoNomePacienteValue = FacialMapRoomDatabase.getDatabaseMapa(mContext).pacienteDao().GetFotoNomePacienteList();

       *//* if(ListDocValue.contains(DocumentoValor) || ListNomePacienteValue.contains(NomePaciente) ||
                ListFotoPacienteValue.contains(foto) || ListFotoNomePacienteValue.contains(NomeFoto)){

            if(ListDocValue.contains(DocumentoValor)){

                //ErrorMsg("Documento já Cadastrado");
                Toast.makeText(mContext, "Documento já Cadastrado", Toast.LENGTH_SHORT).show();

            }else if(ListNomePacienteValue.contains(NomePaciente)){

                mensagem.ErrorMsg("Nome já Cadastrado");

            }
            else if(ListFotoPacienteValue.contains(foto)){

                mensagem.ErrorMsg("Foto já Cadastrado");

            }
            else if(ListFotoNomePacienteValue.contains(NomeFoto)){

                mensagem.ErrorMsg("Foto com este nome já está Cadastrado");

            }
            return true;

        }else{return false;}*//*

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume"," onResume loaded");

        String rg_escolhidoRet = getIntent().getStringExtra("rg_escolhidoRet");
        if(rg_escolhidoRet != null){
            doc_value.setText(rg_escolhidoRet);
        }

        String cpf_escolhidoRet = getIntent().getStringExtra("cpf_escolhidoRet");
        if(cpf_escolhidoRet != null){
            doc_value.setText(cpf_escolhidoRet);
        }

        String passaporte_escolhidoRet = getIntent().getStringExtra("passaporte_escolhidoRet");
        if(passaporte_escolhidoRet != null){
            doc_value.setText(passaporte_escolhidoRet);
        }

        String cnh_escolhidoRet = getIntent().getStringExtra("cnh_escolhidoRet");
        if(cnh_escolhidoRet != null){
            doc_value.setText(cnh_escolhidoRet);
        }

        String DocReturn = getIntent().getStringExtra("DocTypRet");
        if(DocReturn != null){
            int i = Integer.parseInt(DocReturn);
            Spin_Doc.setSelection(i);
            TL_DocResult.setVisibility(View.VISIBLE);
            //BtnDoc.setVisibility(View.VISIBLE);

        }

    }

}
