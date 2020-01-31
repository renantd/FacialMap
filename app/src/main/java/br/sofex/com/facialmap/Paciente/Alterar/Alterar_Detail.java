package br.sofex.com.facialmap.Paciente.Alterar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import br.sofex.com.br.facialmap.Mask.MaskCPF;
import br.sofex.com.br.facialmap.Mask.MaskTelefone;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetDataByCod;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetFotoByCod;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskUpdateFotoByCod;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateCelularPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateCepPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateDataNascimentoPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateDocumentoPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateEmailPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateEtiniaPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateFixoPaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateNascionalidadePaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateNomePaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdatePaciente;
import br.sofex.com.facialmap.AsynkTask.Update.Paciente.doTaskUpdateSexoPaciente;
import br.sofex.com.facialmap.Json.JsonCep;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;
import br.sofex.com.facialmap.Util.Validacoes;
import pub.devrel.easypermissions.EasyPermissions;

public class Alterar_Detail extends AppCompatActivity {

    Button BtnBackHomePacFound,BtnBackPacFound,BtnUPFotoPC,BtnUPAll_Pac;
    ImageView img_perfilFound;
    TextView Pac_NomeFound;
    TextView Doc_PacFound;
    TextView Doc_ValueFound;
    TextView Nasc_PacFound;
    TextView Etnia_PacFound;
    TextView DataNasc_PacFound;
    TextView Sexo_PacFound;
    TextView TelFixo_PacFound;
    TextView TelCel_PacFound;
    TextView Email_PacFound;
    TextView CEP_PacFound;
    TextView End_PacFound;
    TextView Comp_PacFound;
    TextView Num_PacFound;
    TextView Bairro_PacFound;
    TextView Munic_PacFound;
    TextView Estado_PacFound;
    TextView doc_value;
    TextView doc_escolhido;
    /*TODO: Declaração de Variáveis  de Table Row*/
    TableRow TL_DocResult;
    TableRow TR_ShowFotoPerfil;


    private int SELECT_IMAGE = 1;
    private String capturedFileName = "";
    private Uri capturedImageUri = null;
    private Bitmap bitmap = null;
    private String DocumentoTipo = null;

    private boolean resultrespost = false;
    private String FinalPassaporteValue ="";
    private String selectedImagePath;
    private String DocumentoSelect = null;
    private String DocumentoValue = null;
    private String selectedPath;
    private String FilePathCorrigido;
    private String NomePaciente= "";
    private String DtNascValido = null;
    private String SexoSelecionado = null;
    private String filepathUpdate = null;

    Mensagem mensagem = new Mensagem(Alterar_Detail.this);
    Validacoes valid = new Validacoes(Alterar_Detail.this);
    Long CodPaciente;
    String Doc;
    String DocValue;

    Button BtnPac_NomeFound,BtnDoc_PacFound,BtnNasc_PacFound,BtnEtnia_PacFound,BtnDataNasc_PacFound,BtnSexo_PacFound,BtnTelFixo_PacFound,BtnTelCel_PacFound,BtnEmail_PacFound,BtnCEP_PacFound;
    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog alerta;

    /*TODO: importação de classes internas*/
    Util util = new Util(Alterar_Detail.this);
    Validacoes validacoes = new Validacoes(Alterar_Detail.this);
    //DataBaseDAO pacienteDAO = new DataBaseDAO(Alterar_Detail.this);

    /*TODO: Variáveis do tipo AlertDialog*/
    ListView lv_itemsUpdate;
    RelativeLayout RL_Cod_PacFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_detail);

        //TODO: Button
        BtnBackHomePacFound = findViewById(R.id.BtnBackHomePacFound);
        BtnBackPacFound = findViewById(R.id.BtnBackPacFound);
        BtnUPFotoPC = findViewById(R.id.BtnUPFotoPC);
        BtnUPAll_Pac = findViewById(R.id.BtnUPAll_Pac);

        //TODO: TextView
        img_perfilFound = findViewById(R.id.img_perfilFound);
        Pac_NomeFound = findViewById(R.id.Pac_NomeFound);
        Doc_PacFound = findViewById(R.id.Doc_PacFound);
        Doc_ValueFound = findViewById(R.id.Doc_ValueFound);
        Nasc_PacFound = findViewById(R.id.Nasc_PacFound);
        Etnia_PacFound = findViewById(R.id.Etnia_PacFound);
        DataNasc_PacFound = findViewById(R.id.DataNasc_PacFound);
        Sexo_PacFound = findViewById(R.id.Sexo_PacFound);
        TelFixo_PacFound = findViewById(R.id.TelFixo_PacFound);
        TelCel_PacFound = findViewById(R.id.TelCel_PacFound);
        Email_PacFound = findViewById(R.id.Email_PacFound);
        CEP_PacFound = findViewById(R.id.CEP_PacFound);
        End_PacFound = findViewById(R.id.End_PacFound);
        Comp_PacFound = findViewById(R.id.Comp_PacFound);
        Num_PacFound = findViewById(R.id.Num_PacFound);
        Bairro_PacFound = findViewById(R.id.Bairro_PacFound);
        Munic_PacFound = findViewById(R.id.Munic_PacFound);
        Estado_PacFound = findViewById(R.id.Estado_PacFound);

        /*TODO: findViewById de TextView*/
        doc_escolhido = findViewById(R.id.CadPacDocLabel);
        doc_value = findViewById(R.id.CadPacDocValue);


        //TODO: Button
        BtnPac_NomeFound = findViewById(R.id.BtnPac_NomeFound);
        BtnDoc_PacFound = findViewById(R.id.BtnDoc_PacFound);
        BtnNasc_PacFound = findViewById(R.id.BtnNasc_PacFound);
        BtnEtnia_PacFound = findViewById(R.id.BtnEtnia_PacFound);
        BtnDataNasc_PacFound = findViewById(R.id.BtnDataNasc_PacFound);
        BtnSexo_PacFound = findViewById(R.id.BtnSexo_PacFound);
        BtnTelFixo_PacFound = findViewById(R.id.BtnTelFixo_PacFound);
        BtnTelCel_PacFound = findViewById(R.id.BtnTelCel_PacFound);
        BtnEmail_PacFound = findViewById(R.id.BtnEmail_PacFound);
        BtnCEP_PacFound = findViewById(R.id.BtnCEP_PacFound);
        /*BtnEnd_PacFound = findViewById(R.id.BtnEnd_PacFound);
        BtnComp_PacFound = findViewById(R.id.BtnComp_PacFound);
        BtnNum_PacFound = findViewById(R.id.BtnNum_PacFound);
        BtnBairro_PacFound = findViewById(R.id.BtnBairro_PacFound);
        BtnMunic_PacFound = findViewById(R.id.BtnMunic_PacFound);
        BtnEstado_PacFound = findViewById(R.id.BtnEstado_PacFound);*/

        //String CodPaciente = getIntent().getExtras().getString("CodPaciente");
        CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));

        /*Bitmap bit = BitmapFactory.decodeByteArray(pacienteDAO.getFotoByID(CodPaciente), 0, pacienteDAO.getFotoByID(CodPaciente).length);
        img_perfilFound.setImageBitmap(bit);
        Pac_NomeFound.setText(pacienteDAO.getNomePacienteByID(CodPaciente));
        Doc_PacFound.setText(pacienteDAO.getDocumentoByID(CodPaciente));
        Doc_ValueFound.setText(pacienteDAO.getDocumentoConteudoByID(CodPaciente));
        Nasc_PacFound.setText(pacienteDAO.getNacionalidadeByID(CodPaciente));
        Etnia_PacFound.setText(pacienteDAO.getEtniaByID(CodPaciente));
        DataNasc_PacFound.setText(pacienteDAO.getDataNascimentoByID(CodPaciente));
        Sexo_PacFound.setText(pacienteDAO.getSexoByID(CodPaciente));
        TelFixo_PacFound.setText(pacienteDAO.getTelefoneFixoByID(CodPaciente));
        TelCel_PacFound.setText(pacienteDAO.getTelefoneCelularByID(CodPaciente));
        Email_PacFound.setText(pacienteDAO.getEmailByID(CodPaciente));
        CEP_PacFound.setText(pacienteDAO.getCepByID(CodPaciente));
        End_PacFound.setText(pacienteDAO.getEnderecoByID(CodPaciente));
        Comp_PacFound.setText(pacienteDAO.getComplementoByID(CodPaciente));
        Num_PacFound.setText(pacienteDAO.getNumeroByID(CodPaciente));
        Bairro_PacFound.setText(pacienteDAO.getBairroByID(CodPaciente));
        Munic_PacFound.setText(pacienteDAO.getMunicipioByID(CodPaciente));
        Estado_PacFound.setText(pacienteDAO.getEstadoByID(CodPaciente));*/

        //TODO: Button Events
        BtnPac_NomeFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateNome(CodPaciente);
            }
        });
        BtnDoc_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherDocumento(CodPaciente);
            }
        });
        BtnNasc_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherNacionalidade(CodPaciente);
            }
        });
        BtnEtnia_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateEtinia(CodPaciente);
            }
        });
        BtnDataNasc_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateDataNascimento(CodPaciente);    }
        });
        BtnSexo_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateSexo(CodPaciente);
            }
        });
        BtnTelFixo_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateTelFixo(CodPaciente);
            }
        });
        BtnTelCel_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateTelCelular(CodPaciente);    }
        });

        BtnUPFotoPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
            }
        });

        BtnUPAll_Pac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateTodosCampos(CodPaciente);
            }
        });
        BtnEmail_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateEmail(CodPaciente);
            }
        });
        BtnCEP_PacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {PreencherUpdateCep(CodPaciente);
            }
        });

        BtnBackHomePacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Alterar_Detail.this, MainActivity.class);
            startActivity(intent);
            }
        });
        BtnBackPacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Alterar_Detail.this,Alterar_Paciente.class);
           startActivity(intent);
            }
        });


        CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));

        byte[] FotoThread = null;
        try {
            FotoThread = new doTaskCadastroGetFotoByCod(Alterar_Detail.this,CodPaciente).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bit = BitmapFactory.decodeByteArray(FotoThread, 0, FotoThread.length);
        img_perfilFound.setImageBitmap(bit);
        try {
            Pac_NomeFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,1).execute().get());
            Doc_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,2).execute().get());
            Doc_ValueFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,3).execute().get());
            Nasc_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,4).execute().get());
            Etnia_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,5).execute().get());
            DataNasc_PacFound.setText(    new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,6).execute().get());
            Sexo_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,7).execute().get());
            TelFixo_PacFound.setText(     new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,8).execute().get());
            TelCel_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,9).execute().get());
            Email_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,10).execute().get());
            CEP_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,11).execute().get());
            End_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,12).execute().get());
            Comp_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,13).execute().get());
            Num_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,14).execute().get());
            Bairro_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,15).execute().get());
            Munic_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,16).execute().get());
            Estado_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //TODO: Upadate Nome
    private void ConfirmarAlteracao() {

        final String[] x = {null};
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.confirmar_alteracao,null);
        //definimos para o botão do layout um clickListener


        Button BtnConfUPCancel = view.findViewById(R.id.BtnConfUPCancel);
        BtnConfUPCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnConfUPSave = view.findViewById(R.id.BtnConfUPSave);
        BtnConfUPSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultrespost = true;
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

    /*TODO: preencher o Documento*/
    private void PreencherUpdateNome(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_nome_paciente, null);
        //definimos para o botão do layout um clickListener

        final EditText EditUPNomePac = view.findViewById(R.id.EditUPNomePac);


        String str = "";
        try {
            str = new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodigoPaciente,1).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("App1","Nome : "+"/data/data/br.sofex.com.facialmap/files/Files/Mapa Facial/Paciente "+str+"/FotoPerfil/");

        Button BtnUPNomePacSave = view.findViewById(R.id.BtnUPNomePacSave);
        Button BtnUPNomePacCancel = view.findViewById(R.id.BtnUPNomePacCancel);
        BtnUPNomePacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(validacoes.Nome_isValid(EditUPNomePac.getText().toString()) == true){

               String str = "";
               try {
                   str = new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodigoPaciente,1).execute().get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               File oldFolder = new File("/data/data/br.sofex.com.facialmap/files/Files/Mapa Facial/Paciente "+str);
               File newFolder = new File("/data/data/br.sofex.com.facialmap/files/Files/Mapa Facial/Paciente "+EditUPNomePac.getText().toString());
               oldFolder.renameTo(newFolder);

               try {
                   doTaskUpdateNomePaciente TaskUpdate = new doTaskUpdateNomePaciente(Alterar_Detail.this,EditUPNomePac.getText().toString(),CodigoPaciente);
                   TaskUpdate.execute().get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

                /* File oldFolder = new File(Alterar_Detail.this.getFilesDir()+"/Files/Mapa Facial/Paciente "+str+"/FotoPerfil/");
               File newFolder = new File(Alterar_Detail.this.getFilesDir()+"/Files/Mapa Facial/Paciente "+EditUPNomePac.getText().toString()+"/FotoPerfil/");
               oldFolder.renameTo(newFolder);*/

               updateInfoPaciente(CodigoPaciente);
               mensagem.SucessMsg("Nome alterado com sucesso !");
               alerta.dismiss();
            }
           /*if(!EditUPNomePac.getText().toString().isEmpty()){
                  if(EditUPNomePac.getText().toString().length() > 2){
                      String x = pacienteDAO.UpdateNomePacienteByID(EditUPNomePac.getText().toString(),CodigoPaciente);
                      updateInfoPaciente(CodigoPaciente);
                      mensagem.SucessMsg("Nome alterado com sucesso !");
                      alerta.dismiss();
                  }else{
                      mensagem.ErrorMsg("Nome Extremamente pequeno,\nO nome deve ter pelo menos,\n três caracteres");
                      EditUPNomePac.setText(null);
                  }
                }else{
                    mensagem.ErrorMsg("Nome em Branco !");
                }*/
            }
        });
        BtnUPNomePacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: preencher o Documento*/
    private void PreencherDocumento(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_documento, null);
        //definimos para o botão do layout um clickListener

        final Spinner Spin_UpPacMsg = view.findViewById(R.id.Spin_UpPacMsg);

        /*TODO: Spinner , Adpters e Arrays de Spinners*/
        /*TODO: Array de Documentos*/
        String[] Documentos = new String[]{"Documento","CPF", "RG", "Carteira de Trabalho", "Título de Eleitor" , "Passaporte" , "CNH" };
        ArrayAdapter<String> adpterDocumentos = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Documentos);
        adpterDocumentos.setDropDownViewResource(R.layout.spinner_custom_center);


        /*TODO: SetAdapter*/
        Spin_UpPacMsg.setAdapter(adpterDocumentos);
        Spin_UpPacMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_UpPacMsg.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_UpPacMsg.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        //Spin_Doc.setSelection(0);
        /*TODO: Fim -------- */

        final EditText EditUPDocPac = view.findViewById(R.id.EditUPNomePac);
        final String[] Documento = {null};

        final Boolean[] b = {false};
        Spin_UpPacMsg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(Spin_UpPacMsg.getSelectedItemPosition() != 0 ){
                    EditUPDocPac.setEnabled(true);
                    b[0] = true;
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 1 ){
                        EditUPDocPac.setInputType(InputType.TYPE_CLASS_NUMBER);
                        EditUPDocPac.addTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        DocumentoTipo = "CPF";
                        EditUPDocPac.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 2 ){
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        DocumentoTipo = "RG";
                    }
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 3 ){
                        EditUPDocPac.addTextChangedListener(MaskCPF.insertCPF("#######", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("####-####-####-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        DocumentoTipo = "Carteira de Trabalho";
                        EditUPDocPac.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 4 ){
                        EditUPDocPac.addTextChangedListener(MaskCPF.insertCPF("####-####-####-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        DocumentoTipo = "Título de Eleitor";
                    }
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 5 ) {
                        //EditUPDocPac.addTextChangedListener(MaskCPF.insertCPF("##.######", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("####-####-####-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        DocumentoTipo = "Passaporte";
                    }
                    if(Spin_UpPacMsg.getSelectedItemPosition() == 6 ){
                        EditUPDocPac.addTextChangedListener(MaskCPF.insertCPF("###########", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("####-####-####-##", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("##.######.##-#", EditUPDocPac));
                        EditUPDocPac.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", EditUPDocPac));
                        EditUPDocPac.setInputType(InputType.TYPE_CLASS_NUMBER);
                        DocumentoTipo = "CNH";
                    }
                }else{
                    EditUPDocPac.setInputType(InputType.TYPE_CLASS_TEXT);
                    EditUPDocPac.setEnabled(false);
                    DocumentoTipo = "inválido";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button BtnUPPacCancel = view.findViewById(R.id.BtnUPPacCancel);
        BtnUPPacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUPPacSave = view.findViewById(R.id.BtnUPPacSave);
        BtnUPPacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!EditUPDocPac.getText().toString().isEmpty()){
                    //Log.e("Doc","Doc : "+EditUPDocPac.getText().toString());
                    if(DocumentoTipo != "inválido"){
                        if(DocumentoTipo == "CPF"){
                            try {
                                doTaskUpdateDocumentoPaciente TaskUpdate = new doTaskUpdateDocumentoPaciente(Alterar_Detail.this,"CPF",EditUPDocPac.getText().toString(),CodigoPaciente);
                                TaskUpdate.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            updateInfoPaciente(CodigoPaciente);
                            mensagem.SucessMsg("Documento Alterado com sucesso !");
                            alerta.dismiss();
                        }
                        if(DocumentoTipo == "RG"){
                            try {
                                doTaskUpdateDocumentoPaciente TaskUpdate = new doTaskUpdateDocumentoPaciente(Alterar_Detail.this,"CPF",EditUPDocPac.getText().toString(),CodigoPaciente);
                                TaskUpdate.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            updateInfoPaciente(CodigoPaciente);
                            mensagem.SucessMsg("Documento Alterado com sucesso !");
                            alerta.dismiss();
                        }
                        if(DocumentoTipo == "Carteira de Trabalho"){
                            try {
                                doTaskUpdatePaciente TaskUpdate = new doTaskUpdatePaciente(Alterar_Detail.this,"Carteira de Trabalho","","",
                                        "","","","","","","",2,CodigoPaciente);
                                doTaskUpdatePaciente TaskUpdate2 = new doTaskUpdatePaciente(Alterar_Detail.this,EditUPDocPac.getText().toString(),"","",
                                        "","","","","","","",3,CodigoPaciente);
                                TaskUpdate.execute().get(); TaskUpdate2.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            updateInfoPaciente(CodigoPaciente);
                            mensagem.SucessMsg("Documento Alterado com sucesso !");
                            alerta.dismiss();
                        }
                        if(DocumentoTipo == "Título de Eleitor"){
                            try {
                                doTaskUpdatePaciente TaskUpdate = new doTaskUpdatePaciente(Alterar_Detail.this,"Título de Eleitor","","",
                                        "","","","","","","",2,CodigoPaciente);
                                doTaskUpdatePaciente TaskUpdate2 = new doTaskUpdatePaciente(Alterar_Detail.this,EditUPDocPac.getText().toString(),"","",
                                        "","","","","","","",3,CodigoPaciente);
                                TaskUpdate.execute().get(); TaskUpdate2.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            updateInfoPaciente(CodigoPaciente);
                            mensagem.SucessMsg("Documento Alterado com sucesso !");
                            alerta.dismiss();
                        }
                        if(DocumentoTipo == "Passaporte"){
                            if(validacoes.Documento_isValid(EditUPDocPac.getText().toString()) == true){
                                try {
                                    doTaskUpdatePaciente TaskUpdate = new doTaskUpdatePaciente(Alterar_Detail.this,"Passaporte","","",
                                            "","","","","","","",2,CodigoPaciente);
                                    doTaskUpdatePaciente TaskUpdate2 = new doTaskUpdatePaciente(Alterar_Detail.this,EditUPDocPac.getText().toString(),"","",
                                            "","","","","","","",3,CodigoPaciente);
                                    TaskUpdate.execute().get(); TaskUpdate2.execute().get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                updateInfoPaciente(CodigoPaciente);
                                mensagem.SucessMsg("Documento Alterado com sucesso !");
                                alerta.dismiss();
                            }else{
                                mensagem.ErrorMsg("Número do passaporte é inválido");
                            }
                        }
                        if(DocumentoTipo == "CNH"){
                            try {
                                doTaskUpdatePaciente TaskUpdate = new doTaskUpdatePaciente(Alterar_Detail.this,"CNH","","",
                                        "","","","","","","",2,CodigoPaciente);
                                doTaskUpdatePaciente TaskUpdate2 = new doTaskUpdatePaciente(Alterar_Detail.this,EditUPDocPac.getText().toString(),"","",
                                        "","","","","","","",3,CodigoPaciente);
                                TaskUpdate.execute().get(); TaskUpdate2.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mensagem.SucessMsg("Documento Alterado com sucesso !");
                            updateInfoPaciente(CodigoPaciente);
                            alerta.dismiss();
                        }
                    }else{
                        mensagem.ErrorMsg("Tipo de Documento Inválido !");
                        Spin_UpPacMsg.setSelection(0);
                        EditUPDocPac.setText(null);
                    }
                }
                else{
                    if(b[0] == false){
                        mensagem.ErrorMsg("Tipo de documento inválido !");
                        EditUPDocPac.setText(null);
                    }else{
                        mensagem.ErrorMsg("Valor do documento em branco !");
                        EditUPDocPac.setText(null);
                    }

                }
            }

        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Nacionalidade*/
    private void PreencherNacionalidade(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_nascionalidade, null);
        //definimos para o botão do layout um clickListener

        final Spinner Spin_UPNac_Pac = view.findViewById(R.id.Spin_UPNac_Pac);

        /*TODO: Spinner , Adpters e Arrays de Spinners*/
        /*TODO: Array de Documentos*/
        String[] Nacionalidades = new String[]{"Escolha um País","Alemanha","Argentina ","Austrália","Bélgica","Bolívia","Brasil","Canadá","Chile","Colômbia","Bélgica ","Bolívia ","Coréia do Sul",
         "Croácia","Dinamarca","Equador","Espanha","Estados Unidos","Finlândia","França","Grécia","Holanda","Inglaterra","Irlanda ","Itália","Japão","México","Paraguai","Peru","Estados Unidos",
         "Portugal","Reino Unido","Rússia","Uruguai","Venezuela"};
        ArrayAdapter<String> adpterNacionalidades = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Nacionalidades);
        adpterNacionalidades.setDropDownViewResource(R.layout.spinner_custom_center);


        /*TODO: SetAdapter*/
        Spin_UPNac_Pac.setAdapter(adpterNacionalidades);
        Spin_UPNac_Pac.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_UPNac_Pac.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_UPNac_Pac.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        //Spin_Doc.setSelection(0);
        /*TODO: Fim -------- */

        Spin_UPNac_Pac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(Spin_UPNac_Pac.getSelectedItemPosition() != 0 ){
                  Spin_UPNac_Pac.getSelectedItem().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button BtnUPNascPacCancel = view.findViewById(R.id.BtnUPNascPacCancel);
        BtnUPNascPacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              alerta.dismiss();
            }
        });

        Button BtnUPNascPacSave = view.findViewById(R.id.BtnUPNascPacSave);
        BtnUPNascPacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(Spin_UPNac_Pac.getSelectedItemPosition() != 0 ){

                try {
                    doTaskUpdateNascionalidadePaciente TaskUpdate = new doTaskUpdateNascionalidadePaciente(Alterar_Detail.this,
                    Spin_UPNac_Pac.getSelectedItem().toString(),CodigoPaciente);
                    TaskUpdate.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateInfoPaciente(CodigoPaciente);
                mensagem.SucessMsg("Nascionalidade Alterado com sucesso !");
                alerta.dismiss();
            }else{
                    mensagem.ErrorMsg("Nacionalide inválida !");
                }
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Etnia*/
    private void PreencherUpdateEtinia(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_etinia, null);
        //definimos para o botão do layout um clickListener

        final Spinner Spin_UPEtinia_Pac = view.findViewById(R.id.Spin_UPEtinia_Pac);

        /*TODO: Spinner , Adpters e Arrays de Spinners*/
        /*TODO: Array de Documentos*/
        String[] Etnias = new String[]{"Escolha a Etnia","Caucasiano(a)","Afro-descendente","Latino/Hispânico(a)","Asiático(a)","Pardo(a)/Mulato(a)","Cafuzo(a)"};
        ArrayAdapter<String> adpterEtnia = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Etnias);
        adpterEtnia.setDropDownViewResource(R.layout.spinner_custom_center);


        Spin_UPEtinia_Pac.setAdapter(adpterEtnia);
        Spin_UPEtinia_Pac.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_UPEtinia_Pac.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_UPEtinia_Pac.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        //Spin_Doc.setSelection(0);
        /*TODO: Fim -------- */


        Button BtnUPEtiniaPacCancel = view.findViewById(R.id.BtnUPEtiniaPacCancel);
        BtnUPEtiniaPacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        Button BtnUPEtiniaPacSave = view.findViewById(R.id.BtnUPEtiniaPacSave);
        BtnUPEtiniaPacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Spin_UPEtinia_Pac.getSelectedItemPosition() != 0 ){

                    try {
                        doTaskUpdateEtiniaPaciente TaskUpdate = new doTaskUpdateEtiniaPaciente(Alterar_Detail.this,
                        Spin_UPEtinia_Pac.getSelectedItem().toString(),CodigoPaciente);
                        TaskUpdate.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    updateInfoPaciente(CodigoPaciente);
                    mensagem.SucessMsg("Etinia Alterada com sucesso !");
                    alerta.dismiss();
                }else{
                    mensagem.ErrorMsg("Etinia inválida !");
                }
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Data de Nascimento*/
    private void PreencherUpdateDataNascimento(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_data_nascimento, null);
        //definimos para o botão do layout um clickListener

        final EditText Edit_UPDataNasc_Pac = view.findViewById(R.id.Edit_UPDataNasc_Pac);
        Edit_UPDataNasc_Pac.addTextChangedListener(MaskCPF.insertCPF("##/##/####", Edit_UPDataNasc_Pac));

        Button BtnUPDataNascPacCancel = view.findViewById(R.id.BtnUPDataNascPacCancel);
        BtnUPDataNascPacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUPDataNascPacSave = view.findViewById(R.id.BtnUPDataNascPacSave);
        BtnUPDataNascPacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!Edit_UPDataNasc_Pac.getText().toString().isEmpty()){
               if( validacoes.check_isValidDate(Edit_UPDataNasc_Pac.getText().toString()) == true){

                   try {
                       doTaskUpdateDataNascimentoPaciente TaskUpdate = new doTaskUpdateDataNascimentoPaciente(Alterar_Detail.this,
                       Edit_UPDataNasc_Pac.getText().toString(),CodigoPaciente);
                       TaskUpdate.execute().get();
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   updateInfoPaciente(CodigoPaciente);
                   mensagem.SucessMsg("Data de nascimento \n cadastrado com sucesso");
                   alerta.dismiss();
               }
            }
            //alerta.dismiss();
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Sexo do Paciente*/
    private void PreencherUpdateSexo(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_sexo, null);
        //definimos para o botão do layout um clickListener


        final RadioGroup RadioGroupUPSexoPac = view.findViewById(R.id.RadioGroupUPSexoPac);
        final RadioButton RadioUPSexoPacMasculino = view.findViewById(R.id.RadioUPSexoPacMasculino);
        RadioButton RadioUPSexoPacFeminino = view.findViewById(R.id.RadioUPSexoPacFeminino);

        Button BtnUPSexoPacCancel = view.findViewById(R.id.BtnUPSexoPacCancel);
        BtnUPSexoPacCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUPSexoPacSave = view.findViewById(R.id.BtnUPSexoPacSave);
        BtnUPSexoPacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RadioUPSexoPacMasculino.isChecked()){

                    try {
                        doTaskUpdateSexoPaciente TaskUpdate = new doTaskUpdateSexoPaciente(Alterar_Detail.this,
                        "Masculino",CodigoPaciente);
                        TaskUpdate.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mensagem.SucessMsg("Sexo do paciente \n alterado com sucesso");
                    updateInfoPaciente(CodigoPaciente);
                    alerta.dismiss();
                }else{

                    try {
                        doTaskUpdateSexoPaciente TaskUpdate = new doTaskUpdateSexoPaciente(Alterar_Detail.this,
                        "Feminino",CodigoPaciente);
                        TaskUpdate.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mensagem.SucessMsg("Sexo do paciente \n alterado com sucesso");
                    updateInfoPaciente(CodigoPaciente);
                    alerta.dismiss();
                }
                alerta.dismiss();
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Telefone Fixo*/
    private void PreencherUpdateTelFixo(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_tel_fixo, null);
        //definimos para o botão do layout um clickListener

        final EditText EditUPTelFixoPac = view.findViewById(R.id.EditUPTelFixoPac);
        //EditUPTelFixoPac.addTextChangedListener(MaskTelefone.insert(EditUPTelFixoPac));
        EditUPTelFixoPac.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditUPTelFixoPac.addTextChangedListener(MaskCPF.insertCPF("(##)####-####", EditUPTelFixoPac));


        Button BtnUP_TFPC_Close = view.findViewById(R.id.BtnUP_TFPC_Close);
        BtnUP_TFPC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUP_TFPC_Save = view.findViewById(R.id.BtnUP_TFPC_Save);
        BtnUP_TFPC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(!EditUPTelFixoPac.getText().toString().isEmpty()){
                    // TODO: Regex Telefone Fixo : ^(?:\(?([1-9][0-9])\)?\s?)?(?:((?:3\d|[2-9])\d{2})\-?(\d{4}))$
                    final String emailPattern = "^(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:3\\d|[2-9])\\d{2})\\-?(\\d{4}))$";
                    if (EditUPTelFixoPac.getText().toString().matches(emailPattern) || EditUPTelFixoPac.getText().toString().equals("(00)0000-0000"))
                    {
                        if(EditUPTelFixoPac.getText().toString().length() < 13){

                            mensagem.ErrorMsg("Número de Telefone inválido");

                        } else{
                            if(EditUPTelFixoPac.getText().toString().equals("(00)0000-0000")){

                                try {
                                    doTaskUpdateFixoPaciente TaskUpdate = new doTaskUpdateFixoPaciente(Alterar_Detail.this,
                                            "N/A",CodigoPaciente);
                                    TaskUpdate.execute().get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                updateInfoPaciente(CodigoPaciente);
                                mensagem.SucessMsg("Telefone fixo alterado com sucesso !");
                                alerta.dismiss();
                            }else{

                                try {
                                    doTaskUpdateFixoPaciente TaskUpdate = new doTaskUpdateFixoPaciente(Alterar_Detail.this,
                                            EditUPTelFixoPac.getText().toString(),CodigoPaciente);
                                    TaskUpdate.execute().get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                updateInfoPaciente(CodigoPaciente);
                                mensagem.SucessMsg("Telefone fixo alterado com sucesso !");
                                alerta.dismiss();
                            }
                        }

                    }
                    else
                    {
                        mensagem.ErrorMsg("Número de Telefone Inválido !");
                    }
                }else{
              mensagem.ErrorMsg("Número de Telefone em branco");
           }
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Telefone Celular*/
    private void PreencherUpdateTelCelular(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_tel_celular, null);
        //definimos para o botão do layout um clickListener

        final EditText EditUPTelCelPac = view.findViewById(R.id.EditUPTelCelPac);
        EditUPTelCelPac.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditUPTelCelPac.addTextChangedListener(MaskCPF.insertCPF("(##)#####-####", EditUPTelCelPac));

        Button BtnUP_TCelPC_Close = view.findViewById(R.id.BtnUP_TCelPC_Close);
        BtnUP_TCelPC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUP_TCelPC_Save = view.findViewById(R.id.BtnUP_TCelPC_Save);
        BtnUP_TCelPC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditUPTelCelPac.getText().toString().isEmpty()){
                    if(EditUPTelCelPac.getText().toString().length() < 14){

                        mensagem.ErrorMsg("Número de Telefone inválido");

                    } else{
                        // TODO: Regex Telefone Celular :^(?:(?:\+|00)?(55)\s?)?(?:\(?([1-9][0-9])\)?\s?)?(?:((?:9\d|[2-9])\d{3})\-?(\d{4}))$
                        final String emailPattern = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";
                        if (EditUPTelCelPac.getText().toString().matches(emailPattern))
                        {

                            try {
                                doTaskUpdateCelularPaciente TaskUpdate = new doTaskUpdateCelularPaciente(Alterar_Detail.this,
                                EditUPTelCelPac.getText().toString(),CodigoPaciente);
                                TaskUpdate.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            updateInfoPaciente(CodigoPaciente);
                            mensagem.SucessMsg("Telefone Celular alterado com sucesso !");
                            alerta.dismiss();
                        }
                        else
                        {
                            mensagem.ErrorMsg("Número de Telefone Inválido !");
                        }
                    }

                }else{
                    mensagem.ErrorMsg("Número de Telefone em branco");
                }
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Email*/
    private void PreencherUpdateEmail(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_email, null);
        //definimos para o botão do layout um clickListener

        final EditText EditUPEmailPac = view.findViewById(R.id.EditUPEmailPac);

        Button BtnUP_EmailPC_Close = view.findViewById(R.id.BtnUP_EmailPC_Close);
        BtnUP_EmailPC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              alerta.dismiss();
            }
        });
        Button BtnUP_EmailPC_Save = view.findViewById(R.id.BtnUP_EmailPC_Save);
        BtnUP_EmailPC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditUPEmailPac.getText().toString().isEmpty()){
                    if(validacoes.Email_isValid(EditUPEmailPac.getText().toString())){

                        try {
                            doTaskUpdateEmailPaciente TaskUpdate = new doTaskUpdateEmailPaciente(Alterar_Detail.this,
                            EditUPEmailPac.getText().toString(),CodigoPaciente);
                            TaskUpdate.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        updateInfoPaciente(CodigoPaciente);
                        mensagem.SucessMsg("E-mail alterado com sucesso !");
                        alerta.dismiss();

                    } else{
                        mensagem.ErrorMsg("E-mail inválido");
                    }

                }else{
                    mensagem.ErrorMsg("E-mail em branco");
                }
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Cep*/
    private void PreencherUpdateCep(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_cep, null);
        //definimos para o botão do layout um clickListener

        Button BtnUPCep_Load = view.findViewById(R.id.BtnUPCep_Load);
        final EditText Edit_Cep_UP_Pac = view.findViewById(R.id.Edit_Cep_UP_Pac);
        Edit_Cep_UP_Pac.addTextChangedListener(MaskCPF.insertCPF("#####-###", Edit_Cep_UP_Pac));

        /*String[] EstadoSiglas = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        String[] Estado = {
        "Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
        "Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul",
        "Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro",
        "Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina",
        "São Paulo","Sergipe","Tocantins"};*/

        final EditText Edit_End_UP_Pac = view.findViewById(R.id.Edit_End_UP_Pac);
        final EditText Edit_Comp_UP_Pac = view.findViewById(R.id.Edit_Comp_UP_Pac);
        final EditText Edit_Num_UP_Pac = view.findViewById(R.id.Edit_Num_UP_Pac);
        final EditText Edit_Bairro_UP_Pac = view.findViewById(R.id.Edit_Bairro_UP_Pac);
        final EditText Edit_Munic_UP_Pac = view.findViewById(R.id.Edit_Munic_UP_Pac);
        final EditText Edit_Estado_UP_Pac = view.findViewById(R.id.Edit_Estado_UP_Pac);

        BtnUPCep_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacoes.Cep_isValid(Edit_Cep_UP_Pac.getText().toString()) == true){
                    String str2 = Edit_Cep_UP_Pac.getText().toString().replace("-", "");
                    try {
                        String retorno = new JsonCep(Alterar_Detail.this,str2).execute().get();
                        if(retorno != null){
                            if(validacoes.Cep_CheckReturn(retorno) == true){
                                Edit_End_UP_Pac.setText(validacoes.JsonGetLogradouro(retorno));
                                Edit_Bairro_UP_Pac.setText(validacoes.JsonGetBairro(retorno));
                                Edit_Munic_UP_Pac.setText(validacoes.JsonGetCidade(retorno));
                                Edit_Estado_UP_Pac.setText(validacoes.JsonGetEstado(retorno));
                            }else{
                                Edit_End_UP_Pac.setText(null);
                                Edit_Bairro_UP_Pac.setText(null);
                                Edit_Munic_UP_Pac.setText(null);
                                Edit_Estado_UP_Pac.setText(null);
                                mensagem.ErrorMsg(" O CEP informado é inválido");
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }else{mensagem.ErrorMsg(" O cep informado é inválido");}
            }
        });

        Button BtnUP_CepPC_Close = view.findViewById(R.id.BtnUP_CepPC_Close);
        BtnUP_CepPC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        Button BtnUP_CepPC_Save = view.findViewById(R.id.BtnUP_CepPC_Save);
        BtnUP_CepPC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Edit_Cep_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_End_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_Comp_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_Num_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_Bairro_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_Munic_UP_Pac.getText().toString().isEmpty() &&
                    !Edit_Estado_UP_Pac.getText().toString().isEmpty()){
                    try {
                        if(validacoes.Cep_isValid(Edit_Cep_UP_Pac.getText().toString())){
                            String retorno = new JsonCep(Alterar_Detail.this,Edit_Cep_UP_Pac.getText().toString()).execute().get();
                            if(retorno != null){
                                if(validacoes.Cep_CheckReturn(retorno) == true){
                                    Log.e("App1"," - "+validacoes.JsonGetLogradouro(retorno)+" "+validacoes.JsonGetBairro(retorno)+" "+validacoes.JsonGetCidade(retorno)+" "+validacoes.JsonGetEstado(retorno));
                                    if(validacoes.JsonGetLogradouro(retorno).toLowerCase().contains(Edit_End_UP_Pac.getText().toString().toLowerCase()) &&
                                       validacoes.JsonGetBairro(retorno).toLowerCase().contains(Edit_Bairro_UP_Pac.getText().toString().toLowerCase()) &&
                                       validacoes.JsonGetCidade(retorno).toLowerCase().contains(Edit_Munic_UP_Pac.getText().toString().toLowerCase()) &&
                                       validacoes.JsonGetEstado(retorno).toLowerCase().contains(Edit_Estado_UP_Pac.getText().toString().toLowerCase())){

                                        try {
                                            doTaskUpdateCepPaciente TaskUpdate = new doTaskUpdateCepPaciente(Alterar_Detail.this,
                                            Edit_Cep_UP_Pac.getText().toString(),Edit_End_UP_Pac.getText().toString(),Edit_Comp_UP_Pac.getText().toString(),Edit_Num_UP_Pac.getText().toString(),
                                            Edit_Bairro_UP_Pac.getText().toString(),Edit_Munic_UP_Pac.getText().toString(),Edit_Estado_UP_Pac.getText().toString(),CodigoPaciente);
                                            TaskUpdate.execute().get();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        updateInfoPaciente(CodigoPaciente);
                                        mensagem.SucessMsg("Cep alterado com sucesso !");
                                        alerta.dismiss();

                                    } else{
                                        Log.e("App1"," Logradouro CEP "+validacoes.JsonGetLogradouro(retorno)+" Logradouro Digitado : "+Edit_End_UP_Pac.getText().toString());
                                        Log.e("App1"," Bairro CEP "+validacoes.JsonGetBairro(retorno)+" Bairro Digitado  : "+Edit_Bairro_UP_Pac.getText().toString());
                                        Log.e("App1"," Cidade CEP "+validacoes.JsonGetCidade(retorno)+" Cidade Digitado : "+Edit_Munic_UP_Pac.getText().toString());
                                        Log.e("App1"," Estado CEP "+validacoes.JsonGetEstado(retorno)+" Estado Digitado : "+Edit_Estado_UP_Pac.getText().toString());
                                        mensagem.ErrorMsg(" O(s) dado(s) informado(s)\nnão confere(m) com , os dados do CEP\n nos correios");
                                    }
                                }
                            }else{mensagem.ErrorMsg("Não houve retorno, na pesquisa do CEP !");}
                        } else{mensagem.ErrorMsg(" O Cep Digitado é inválido ! ");}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                   /*pacienteDAO.UpdateCepPacienteByID(
                   Edit_Cep_UP_Pac.getText().toString(),
                   Edit_End_UP_Pac.getText().toString(),
                   Edit_Comp_UP_Pac.getText().toString(),
                   Edit_Num_UP_Pac.getText().toString(),
                   Edit_Bairro_UP_Pac.getText().toString(),
                   Edit_Munic_UP_Pac.getText().toString(),
                   Edit_Estado_UP_Pac.getText().toString(),
                   CodigoPaciente);*/
                }else{
                    mensagem.ErrorMsg("Todos os campos devem ser preenchidos !");
                }
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar Cep*/
    private void PreencherUpdateFoto(final Long CodigoPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_cep, null);
        //definimos para o botão do layout um clickListener

        Button BtnUPCep_Load = view.findViewById(R.id.BtnUPCep_Load);
        final EditText Edit_Cep_UP_Pac = view.findViewById(R.id.Edit_Cep_UP_Pac);
        Edit_Cep_UP_Pac.addTextChangedListener(MaskCPF.insertCPF("#####-###", Edit_Cep_UP_Pac));

        /*String[] EstadoSiglas = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        String[] Estado = {
        "Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
        "Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul",
        "Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro",
        "Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina",
        "São Paulo","Sergipe","Tocantins"};*/

        final EditText Edit_End_UP_Pac = view.findViewById(R.id.Edit_End_UP_Pac);
        final EditText Edit_Comp_UP_Pac = view.findViewById(R.id.Edit_Comp_UP_Pac);
        final EditText Edit_Num_UP_Pac = view.findViewById(R.id.Edit_Num_UP_Pac);
        final EditText Edit_Bairro_UP_Pac = view.findViewById(R.id.Edit_Bairro_UP_Pac);
        final EditText Edit_Munic_UP_Pac = view.findViewById(R.id.Edit_Munic_UP_Pac);
        final EditText Edit_Estado_UP_Pac = view.findViewById(R.id.Edit_Estado_UP_Pac);

        BtnUPCep_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacoes.Cep_isValid(Edit_Cep_UP_Pac.getText().toString()) == true){
                    String str2 = Edit_Cep_UP_Pac.getText().toString().replace("-", "");
                    try {

                        String retorno = new JsonCep(str2).execute().get();
                        if(retorno != null){
                            if(validacoes.Cep_CheckReturn(retorno) == true){
                                Edit_End_UP_Pac.setText(validacoes.JsonGetLogradouro(retorno));
                                Edit_Bairro_UP_Pac.setText(validacoes.JsonGetBairro(retorno));
                                Edit_Munic_UP_Pac.setText(validacoes.JsonGetCidade(retorno));
                                Edit_Estado_UP_Pac.setText(validacoes.JsonGetEstado(retorno));
                            }else{
                                Edit_End_UP_Pac.setText(null);
                                Edit_Bairro_UP_Pac.setText(null);
                                Edit_Munic_UP_Pac.setText(null);
                                Edit_Estado_UP_Pac.setText(null);
                                mensagem.ErrorMsg(" O CEP informado é inválido");
                            }

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }else{mensagem.ErrorMsg(" O cep informado é inválido");}
            }
        });

        Button BtnUP_CepPC_Close = view.findViewById(R.id.BtnUP_CepPC_Close);
        BtnUP_CepPC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUP_CepPC_Save = view.findViewById(R.id.BtnUP_CepPC_Save);
        BtnUP_CepPC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Edit_Cep_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_End_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_Comp_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_Num_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_Bairro_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_Munic_UP_Pac.getText().toString().isEmpty() &&
                        !Edit_Estado_UP_Pac.getText().toString().isEmpty()){

                    try {

                        if(validacoes.Cep_isValid(Edit_Cep_UP_Pac.getText().toString())){
                            String retorno = new JsonCep(Edit_Cep_UP_Pac.getText().toString()).execute().get();
                            if(retorno != null){
                                if(validacoes.Cep_CheckReturn(retorno) == true){
                                    Log.e("App1"," - "+validacoes.JsonGetLogradouro(retorno)+" "+validacoes.JsonGetBairro(retorno)+" "+validacoes.JsonGetCidade(retorno)+" "+validacoes.JsonGetEstado(retorno));

                                    if(validacoes.JsonGetLogradouro(retorno).toLowerCase().contains(Edit_End_UP_Pac.getText().toString().toLowerCase()) &&
                                            validacoes.JsonGetBairro(retorno).toLowerCase().contains(Edit_Bairro_UP_Pac.getText().toString().toLowerCase()) &&
                                            validacoes.JsonGetCidade(retorno).toLowerCase().contains(Edit_Munic_UP_Pac.getText().toString().toLowerCase()) &&
                                            validacoes.JsonGetEstado(retorno).toLowerCase().contains(Edit_Estado_UP_Pac.getText().toString().toLowerCase())){

                                       /* pacienteDAO.UpdateCepPacienteByID(
                                                Edit_Cep_UP_Pac.getText().toString(),
                                                validacoes.JsonGetLogradouro(retorno),
                                                Edit_Comp_UP_Pac.getText().toString(),
                                                Edit_Num_UP_Pac.getText().toString(),
                                                validacoes.JsonGetBairro(retorno),
                                                validacoes.JsonGetCidade(retorno),
                                                validacoes.JsonGetEstado(retorno),
                                                CodigoPaciente);*/

                                        updateInfoPaciente(CodigoPaciente);
                                        mensagem.SucessMsg("Cep alterado com sucesso !");
                                        alerta.dismiss();

                                    } else{
                                        Log.e("App1"," Logradouro CEP "+validacoes.JsonGetLogradouro(retorno)+" Logradouro Digitado : "+Edit_End_UP_Pac.getText().toString());
                                        Log.e("App1"," Bairro CEP "+validacoes.JsonGetBairro(retorno)+" Bairro Digitado  : "+Edit_Bairro_UP_Pac.getText().toString());
                                        Log.e("App1"," Cidade CEP "+validacoes.JsonGetCidade(retorno)+" Cidade Digitado : "+Edit_Munic_UP_Pac.getText().toString());
                                        Log.e("App1"," Estado CEP "+validacoes.JsonGetEstado(retorno)+" Estado Digitado : "+Edit_Estado_UP_Pac.getText().toString());
                                        mensagem.ErrorMsg(" O(s) dado(s) informado(s)\nnão confere(m) com , os dados do CEP\n nos correios");
                                    }
                                }
                            }else{
                                mensagem.ErrorMsg("Não houve retorno, na pesquisa do CEP !");
                            }
                        } else{
                            mensagem.ErrorMsg(" O Cep Digitado é inválido ! ");
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                   /*pacienteDAO.UpdateCepPacienteByID(
                   Edit_Cep_UP_Pac.getText().toString(),
                   Edit_End_UP_Pac.getText().toString(),
                   Edit_Comp_UP_Pac.getText().toString(),
                   Edit_Num_UP_Pac.getText().toString(),
                   Edit_Bairro_UP_Pac.getText().toString(),
                   Edit_Munic_UP_Pac.getText().toString(),
                   Edit_Estado_UP_Pac.getText().toString(),
                   CodigoPaciente);*/

                }else{
                    mensagem.ErrorMsg("Todos os campos devem ser preenchidos !");
                }
            }
        });

        builder.setView(view);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    /*TODO: Atualizar todos os campos*/
    private void PreencherUpdateTodosCampos(final Long CodigoPaciente) {

        final String[] DocType = {""};
        final String[] DocValue = {""};
        final String[] SexoEscolhido = {""};

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.update_paciente_fields, null);
        //definimos para o botão do layout um clickListener

        final TableRow TL_DocResult = view.findViewById(R.id.TL_DocResult);
        TL_DocResult.setVisibility(View.GONE);

        final TextView CadPacDocLabelUpdate = view.findViewById(R.id.CadPacDocLabelUpdate);
        final EditText CadPacDocValueUpdate = view.findViewById(R.id.CadPacDocValueUpdate);


        //TODO : EditText
        final EditText Edit_NomePacUpdate = view.findViewById(R.id.Edit_NomePacUpdate);
        final EditText Edit_DtNascUpdate = view.findViewById(R.id.Edit_DtNascUpdate);

        final EditText Edit_TelFixoUpdate = view.findViewById(R.id.Edit_TelFixoUpdate);
        final EditText Edit_TelCelUpdate = view.findViewById(R.id.Edit_TelCelUpdate);
        final EditText Edit_CadPacEmailUpdate = view.findViewById(R.id.Edit_CadPacEmailUpdate);

        final EditText Edit_CepEndUpdate = view.findViewById(R.id.Edit_CepEndUpdate);
        final EditText Edit_EndUpdate = view.findViewById(R.id.Edit_EndUpdate);
        final EditText Edit_EndCompUpdate = view.findViewById(R.id.Edit_EndCompUpdate);
        final EditText Edit_EndNumUpdate = view.findViewById(R.id.Edit_EndNumUpdate);
        final EditText Edit_BairroUpdate = view.findViewById(R.id.Edit_BairroUpdate);
        final EditText Edit_EndMuniUpdate = view.findViewById(R.id.Edit_EndMuniUpdate);
        final EditText Edit_EndEstadoUpdate = view.findViewById(R.id.Edit_EndEstadoUpdate);

        //TODO : RadioGroup,RadioButton
        final RadioGroup RadioSexoUpdate = view.findViewById(R.id.RadioSexoUpdate);
        final RadioButton RB_MascUpdate = view.findViewById(R.id.RB_MascUpdate);
        final RadioButton RB_FemUpdate = view.findViewById(R.id.RB_FemUpdate);


        final Spinner Spin_Doc = view.findViewById(R.id.Spin_DocUpdate);
        final Spinner Spin_Nac = view.findViewById(R.id.Spin_NacUpdate);
        final Spinner Spin_Etnia = view.findViewById(R.id.Spin_EtniaUpdate);

        /*TODO: Spinner , Adpters e Arrays de Spinners*/
        /*TODO: Array de Documentos*/
        String[] Documentos = new String[]{"Documento","CPF", "CTPS", "Título de Eleitor" , "Passaporte" , "CNH" };
        ArrayAdapter<String> adpterDocumentos = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Documentos);
        adpterDocumentos.setDropDownViewResource(R.layout.spinner_custom_center);

        /*TODO: Array de Nacionalidades*/
        String[] Nacionalidades = new String[]{"Escolha um País","Alemanha","Argentina ","Austrália","Bélgica","Bolívia","Brasil","Canadá","Chile","Colômbia","Bélgica ",
                "Bolívia ","Coréia do Sul","Croácia","Dinamarca","Equador","Espanha","Estados Unidos","Finlândia","França","Grécia","Holanda","Inglaterra","Irlanda ","Itália",
                "Japão","México","Paraguai","Peru","Estados Unidos","Portugal","Reino Unido","Rússia","Uruguai","Venezuela"};
        ArrayAdapter<String> adpterNacionalidades = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Nacionalidades);
        adpterNacionalidades.setDropDownViewResource(R.layout.spinner_custom_center);

        /*TODO: Array de etnias*/
        String[] Etnias = new String[]{"Escolha a Etnia","Caucasiano(a)","Afro-descendente","Latino/Hispânico(a)","Asiático(a)","Pardo(a)/Mulato(a)","Cafuzo(a)"};
        ArrayAdapter<String> adpterEtnia = new ArrayAdapter<>(Alterar_Detail.this,R.layout.spinner_custom_center,R.id.sp_item_center,Etnias);
        adpterEtnia.setDropDownViewResource(R.layout.spinner_custom_center);

        /*TODO: SetAdapter*/
        Spin_Doc.setAdapter(adpterDocumentos);
        Spin_Doc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Doc.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Doc.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        //Spin_Doc.setSelection(0);
        final Boolean[] b = {false};
        Spin_Doc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(Spin_Doc.getSelectedItemPosition() != 0 ){
                    TL_DocResult.setVisibility(View.VISIBLE);
                    CadPacDocValueUpdate.setText(null);
                    b[0] = true;
                    if(Spin_Doc.getSelectedItemPosition() == 1 ){
                        DocType[0] = "CPF";
                        //PreencherUPALLDocumento("CPF");
                        CadPacDocLabelUpdate.setText("CPF");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocValueUpdate.setHint("CPF - EX: 999.999.999-99");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocValueUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setInputType(InputType.TYPE_CLASS_TEXT);

                    }
                    /*if(Spin_Doc.getSelectedItemPosition() == 2 ){
                        DocType[0] = "RG";
                        CadPacDocLabelUpdate.setText("RG");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocValueUpdate.setHint("Digite o RG :");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocValueUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setInputType(InputType.TYPE_CLASS_TEXT);

                        DocumentoTipo = "RG";
                    }*/
                    if(Spin_Doc.getSelectedItemPosition() == 2 ){
                        DocType[0] = "CTPS";
                        CadPacDocLabelUpdate.setText("CTPS");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocLabelUpdate.setHint("N/S: 9999999");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocLabelUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocLabelUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocLabelUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);

                        DocumentoTipo = "CTPS";
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 3 ){
                        DocType[0] = "Título de Eleitor";
                        CadPacDocLabelUpdate.setText("Título de\nEleitor");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocValueUpdate.setHint("Núm.insc : Ex 9999.9999.9999");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocValueUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);

                        DocumentoTipo = "Título de Eleitor";
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 4 ) {
                        DocType[0] = "Passaporte";
                        CadPacDocLabelUpdate.setText("Passaporte");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocValueUpdate.setHint("Núm.Passaporte: Ex: AA999999");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocValueUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setInputType(InputType.TYPE_CLASS_TEXT);

                        DocumentoTipo = "Passaporte";
                    }
                    if(Spin_Doc.getSelectedItemPosition() == 5 ){
                        DocType[0] = "CNH";
                        CadPacDocLabelUpdate.setText("CNH");
                        //CadPacDocValueUpdate.setText(Doc);
                        CadPacDocValueUpdate.setHint("RENAVAM - Ex: 999999999999");
                        CadPacDocValueUpdate.setText(null);
                        CadPacDocValueUpdate.setTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setHintTextColor(Color.parseColor("#000000"));
                        CadPacDocValueUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);

                        DocumentoTipo = "CNH";
                    }
                }else{
                    TL_DocResult.setVisibility(View.GONE);
                    DocumentoTipo = "inválido";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Spin_Nac.setAdapter(adpterNacionalidades);
        Spin_Nac.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Nac.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Nac.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        Spin_Nac.setSelection(6);


        Spin_Etnia.setAdapter(adpterEtnia);
        Spin_Etnia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spin_Etnia.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spin_Etnia.getBackground().setColorFilter(getResources().getColor(R.color.bluepsico), PorterDuff.Mode.SRC_ATOP);
        Spin_Etnia.setSelection(1);
        /*TODO: Fim -------- */

        // TODO: EditText Events
        Edit_DtNascUpdate.setHint("Data de Nascimento");
        Edit_DtNascUpdate.setTextColor(Color.parseColor("#000000"));
        Edit_DtNascUpdate.setHintTextColor(Color.parseColor("#000000"));
        Edit_DtNascUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);
        Edit_DtNascUpdate.addTextChangedListener(MaskCPF.insertCPF("##/##/####", Edit_DtNascUpdate));

        //Edit_TelFixoUpdate.addTextChangedListener(MaskCPF.insertCPF("(##)####-####", Edit_TelFixoUpdate));
        Edit_TelFixoUpdate.addTextChangedListener(MaskTelefone.insert(Edit_TelFixoUpdate));
        Edit_TelFixoUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);

        Edit_TelCelUpdate.addTextChangedListener(MaskCPF.insertCPF("(##)#####-####", Edit_TelCelUpdate));
        Edit_TelCelUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);

        //TODO: CEP events
        Edit_CepEndUpdate.addTextChangedListener(MaskCPF.insertCPF("#####-###", Edit_CepEndUpdate));
        Edit_CepEndUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);
        Edit_EndNumUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);


        //TODO : RadioGroup,RadioButton Events
        RadioSexoUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.RB_MascUpdate:
                        SexoEscolhido[0] = "Masculino";
                        break;
                    case R.id.RB_FemUpdate:
                        SexoEscolhido[0] = "Feminino";
                        break;
                }
            }
        });


        Button BtnLoadCepUpdate = view.findViewById(R.id.BtnLoadCepUpdate);
        BtnLoadCepUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Edit_CepEndUpdate.getText().toString().isEmpty()){
                    if(Edit_CepEndUpdate.getText().toString().length() == 9){
                        if(validacoes.Cep_isValid(Edit_CepEndUpdate.getText().toString()) == true){
                            String str2 = Edit_CepEndUpdate.getText().toString().replace("-", "");
                            try {
                                String retorno = new JsonCep(Alterar_Detail.this,str2).execute().get();
                                if(retorno != null){
                                    if(validacoes.Cep_CheckReturn(retorno) == true){
                                        Edit_EndUpdate.setText(validacoes.JsonGetLogradouro(retorno));
                                        Edit_BairroUpdate.setText(validacoes.JsonGetBairro(retorno));
                                        Edit_EndMuniUpdate.setText(validacoes.JsonGetCidade(retorno));
                                        Edit_EndEstadoUpdate.setText(validacoes.JsonGetEstado(retorno));
                                    }else{
                                        Edit_EndUpdate.setText(null);
                                        Edit_BairroUpdate.setText(null);
                                        Edit_EndMuniUpdate.setText(null);
                                        Edit_EndEstadoUpdate.setText(null);
                                        mensagem.ErrorMsg(" O CEP informado é inválido");
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }else{mensagem.ErrorMsg(" O cep informado é inválido");}
                    }else{
                        mensagem.ErrorMsg("Cep Inválido !");
                    }
                }  else {
                    mensagem.ErrorMsg("CEP de pesquisa em branco !");
                }
            }
        });

        Button BtnUPVoltarMenu = view.findViewById(R.id.BtnUPVoltarMenu);
        BtnUPVoltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        Button BtnUPSalvarPaciente = view.findViewById(R.id.BtnUPSalvarPaciente);
        BtnUPSalvarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*EditText Edit_NomePacUpdate = view.findViewById(R.id.Edit_NomePacUpdate);
                EditText Edit_DtNascUpdate = view.findViewById(R.id.Edit_DtNascUpdate);

                EditText Edit_TelFixoUpdate = view.findViewById(R.id.Edit_TelFixoUpdate);
                EditText Edit_TelCelUpdate = view.findViewById(R.id.Edit_TelCelUpdate);
                EditText Edit_CadPacEmailUpdate = view.findViewById(R.id.Edit_CadPacEmailUpdate);

                EditText Edit_CepEndUpdate = view.findViewById(R.id.Edit_CepEndUpdate);
                EditText Edit_EndUpdate = view.findViewById(R.id.Edit_EndUpdate);
                EditText Edit_EndCompUpdate = view.findViewById(R.id.Edit_EndCompUpdate);
                EditText Edit_EndNumUpdate = view.findViewById(R.id.Edit_EndNumUpdate);
                EditText Edit_BairroUpdate = view.findViewById(R.id.Edit_BairroUpdate);
                EditText Edit_EndMuniUpdate = view.findViewById(R.id.Edit_EndMuniUpdate);
                EditText Edit_EndEstadoUpdate = view.findViewById(R.id.Edit_EndEstadoUpdate);*/

                if(valid.ValidarRegistroUpdate(
                        valid.ValidarDadosPessoais(Edit_NomePacUpdate.getText().toString(),Spin_Doc.getSelectedItem().toString(),CadPacDocValueUpdate.getText().toString(),Spin_Nac.getSelectedItem().toString(),
                                Spin_Etnia.getSelectedItem().toString(),Edit_DtNascUpdate.getText().toString(),SexoEscolhido[0]),

                        valid.ValidarDadosDeContato(Edit_TelFixoUpdate.getText().toString(),Edit_TelCelUpdate.getText().toString(),Edit_CadPacEmailUpdate.getText().toString()),
                        valid.ValidarDadosEndereco(Edit_CepEndUpdate.getText().toString(),Edit_EndUpdate.getText().toString(),Edit_EndCompUpdate.getText().toString(),

                                Edit_EndNumUpdate.getText().toString(),Edit_BairroUpdate.getText().toString(),Edit_EndMuniUpdate.getText().toString(),Edit_EndEstadoUpdate.getText().toString())) == true) {

                    /*try {Boolean checkCad = new AsyncTaskUpdatePaciente(Alterar_Detail.this,Edit_NomePacUpdate.getText().toString(), Spin_Doc.getSelectedItem().toString(),
                            CadPacDocValueUpdate.getText().toString(), Spin_Nac.getSelectedItem().toString(), Spin_Etnia.getSelectedItem().toString(),Edit_DtNascUpdate.getText().toString(), SexoEscolhido[0],
                            Edit_TelFixoUpdate.getText().toString(), Edit_TelCelUpdate.getText().toString(), Edit_CadPacEmailUpdate.getText().toString(),
                            Edit_CepEndUpdate.getText().toString(), Edit_EndUpdate.getText().toString(), Edit_EndCompUpdate.getText().toString(), Integer.valueOf(Edit_EndNumUpdate.getText().toString()),
                            Edit_BairroUpdate.getText().toString(), Edit_EndMuniUpdate.getText().toString(), Edit_EndEstadoUpdate.getText().toString(),CodigoPaciente).execute().get();

                        if(checkCad == true) {
                            Toast.makeText(Alterar_Detail.this, "Registro Salvo", Toast.LENGTH_SHORT).show();}

                        else{Toast.makeText(Alterar_Detail.this, "Erro no Registro Salvo", Toast.LENGTH_SHORT).show();}

                    }catch (ExecutionException e) {e.printStackTrace();

                    }catch (InterruptedException e) {e.printStackTrace();}*/

                }else{Toast.makeText(Alterar_Detail.this, "Erro ao Realizar o Update !", Toast.LENGTH_SHORT).show(); }

                //alerta.dismiss();
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }




    /*TODO: Mostrar Imgem selecionada*/
    private void SelectedImageUpdate(final Bitmap bit, String Filename, final Long CodPaciente) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.selected_image_updade, null);
        //definimos para o botão do layout um clickListener
        final Bitmap[] bit2 = {null};

        final ImageView ImgSelectedUP = view.findViewById(R.id.ImgSelectedUP);

        final File file  = new File("/data/data/br.sofex.com.facialmap/cache/"+"Captured.jpg");
        try {

            file.createNewFile();
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: Rotaciona o arquivo de foto  para a orientação correta, e passa para o bitmap
        bit2[0] = FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath())));
        //TODO: Salva o arquivo corrigido
        util.saveBitmap(bit2[0],file.getAbsolutePath());

        ImgSelectedUP.setImageBitmap(FixOrientatioImage(Uri.fromFile(new File(file.getAbsolutePath()))));
        TextView FileName_UpPac = view.findViewById(R.id.FileName_UpPac);
        FileName_UpPac.setText(Filename);

        Button BtnUP_FP_Close = view.findViewById(R.id.BtnUP_FP_Close);
        BtnUP_FP_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button BtnUP_FP_Save = view.findViewById(R.id.BtnUP_FP_Save);
        BtnUP_FP_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturedFileName != null){
                    try {
                        File from = file.getAbsoluteFile();
                        String NomePaciente = "";
                        try {
                            NomePaciente = new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,1).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        File to = new File(Alterar_Detail.this.getFilesDir()+"/Files/Mapa Facial/Paciente "+NomePaciente+"/FotoPerfil/",capturedFileName);
                        util.copyFromTo(from,to);
                        filepathUpdate = to.getAbsolutePath();

                        Bitmap bmp = BitmapFactory.decodeFile(to.getAbsolutePath());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        try {
                            doTaskUpdateFotoByCod taskUpdate = new doTaskUpdateFotoByCod(Alterar_Detail.this,byteArray,CodPaciente);
                            taskUpdate.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateInfoPaciente(CodPaciente);
                        mensagem.SucessMsg("Foto de perfil, alterado com sucesso !");

                        byte[] FotoThread = null;
                        try {
                            FotoThread = new doTaskCadastroGetFotoByCod(Alterar_Detail.this,CodPaciente).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Bitmap bit = BitmapFactory.decodeByteArray(FotoThread, 0, FotoThread.length);
                        img_perfilFound.setImageBitmap(bit);
                        try {
                            Pac_NomeFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,1).execute().get());
                            Doc_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,2).execute().get());
                            Doc_ValueFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,3).execute().get());
                            Nasc_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,4).execute().get());
                            Etnia_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,5).execute().get());
                            DataNasc_PacFound.setText(    new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,6).execute().get());
                            Sexo_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,7).execute().get());
                            TelFixo_PacFound.setText(     new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,8).execute().get());
                            TelCel_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,9).execute().get());
                            Email_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,10).execute().get());
                            CEP_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,11).execute().get());
                            End_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,12).execute().get());
                            Comp_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,13).execute().get());
                            Num_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,14).execute().get());
                            Bairro_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,15).execute().get());
                            Munic_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,16).execute().get());
                            Estado_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,17).execute().get());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        alerta.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            capturedImageUri = data.getData();
            capturedFileName = GetFileNameIntent(data);
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), capturedImageUri);

                    //TableRow TR_ShowFotoPerfil = findViewById(R.id.TR_ShowFotoPerfil);
                    //TR_ShowFotoPerfil.setVisibility(View.VISIBLE);
                    int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);
                    SelectedImageUpdate(bitmap,capturedFileName,CodPaciente);
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

    private void  updateInfoPaciente(Long CodPaciente){
        CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));
        /*Bitmap bit = BitmapFactory.decodeByteArray(pacienteDAO.getFotoByID(CodPaciente), 0, pacienteDAO.getFotoByID(CodPaciente).length);
        img_perfilFound.setImageBitmap(bit);
        Pac_NomeFound.setText(pacienteDAO.getNomePacienteByID(CodPaciente));
        Doc_PacFound.setText(pacienteDAO.getDocumentoByID(CodPaciente));
        Doc_ValueFound.setText(pacienteDAO.getDocumentoConteudoByID(CodPaciente));
        Nasc_PacFound.setText(pacienteDAO.getNacionalidadeByID(CodPaciente));
        Etnia_PacFound.setText(pacienteDAO.getEtniaByID(CodPaciente));
        DataNasc_PacFound.setText(pacienteDAO.getDataNascimentoByID(CodPaciente));
        Sexo_PacFound.setText(pacienteDAO.getSexoByID(CodPaciente));
        TelFixo_PacFound.setText(pacienteDAO.getTelefoneFixoByID(CodPaciente));
        TelCel_PacFound.setText(pacienteDAO.getTelefoneCelularByID(CodPaciente));
        Email_PacFound.setText(pacienteDAO.getEmailByID(CodPaciente));
        CEP_PacFound.setText(pacienteDAO.getCepByID(CodPaciente));
        End_PacFound.setText(pacienteDAO.getEnderecoByID(CodPaciente));
        Comp_PacFound.setText(pacienteDAO.getComplementoByID(CodPaciente));
        Num_PacFound.setText(pacienteDAO.getNumeroByID(CodPaciente));
        Bairro_PacFound.setText(pacienteDAO.getBairroByID(CodPaciente));
        Munic_PacFound.setText(pacienteDAO.getMunicipioByID(CodPaciente));
        Estado_PacFound.setText(pacienteDAO.getEstadoByID(CodPaciente));*/

        byte[] FotoThread = null;
        try {
            FotoThread = new doTaskCadastroGetFotoByCod(Alterar_Detail.this,CodPaciente).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bit = BitmapFactory.decodeByteArray(FotoThread, 0, FotoThread.length);
        img_perfilFound.setImageBitmap(bit);
        try {
            Pac_NomeFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,1).execute().get());
            Doc_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,2).execute().get());
            Doc_ValueFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,3).execute().get());
            Nasc_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,4).execute().get());
            Etnia_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,5).execute().get());
            DataNasc_PacFound.setText(    new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,6).execute().get());
            Sexo_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,7).execute().get());
            TelFixo_PacFound.setText(     new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,8).execute().get());
            TelCel_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,9).execute().get());
            Email_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,10).execute().get());
            CEP_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,11).execute().get());
            End_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,12).execute().get());
            Comp_PacFound.setText(        new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,13).execute().get());
            Num_PacFound.setText(         new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,14).execute().get());
            Bairro_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,15).execute().get());
            Munic_PacFound.setText(       new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,16).execute().get());
            Estado_PacFound.setText(      new doTaskCadastroGetDataByCod(Alterar_Detail.this,CodPaciente,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //TODO: Preenchimento de documento
    private void PreencherUPALLDocumento(String Title) {
        String tipoDoc = "";
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_update_todos_campos_documento, null);
        //definimos para o botão do layout um clickListener
        TextView TV_UPALL_TITTLE = view.findViewById(R.id.TV_UPALL_TITTLE);
        TV_UPALL_TITTLE.setText("Facial Map - "+Title);

        final EditText Edit_UPALL_DOC = view.findViewById(R.id.Edit_UPALL_DOC);

        if(Title.equals("CPF")){
            Edit_UPALL_DOC.setHint("Digite o CPF :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_NUMBER);

            Edit_UPALL_DOC.addTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));

            tipoDoc = "CPF";
            DocumentoTipo = "CPF";
        }
        else if(Title.equals("RG")){
            Edit_UPALL_DOC.setHint("Digite o RG :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_TEXT);

            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));

            tipoDoc = "RG";
            DocumentoTipo = "RG";
            Doc = Edit_UPALL_DOC.getText().toString();
        }
        else if(Title.equals("Carteira de Trabalho")){
            Edit_UPALL_DOC.setHint("Digite o número de série :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.addTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_NUMBER);

            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));

            tipoDoc = "CTPS";
            DocumentoTipo = "CTPS";
            Doc = Edit_UPALL_DOC.getText().toString();
        }
        else if(Title.equals("Título de Eleitor")){
            Edit_UPALL_DOC.setHint("Digite o número da inscrição :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.addTextChangedListener(MaskCPF.insertCPF("####-####-####-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_NUMBER);


            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));

            tipoDoc = "Título de Eleitor";
            DocumentoTipo = "Título de Eleitor";
            Doc = Edit_UPALL_DOC.getText().toString();
        }
        else if(Title.equals("Passaporte")){
            Edit_UPALL_DOC.setHint("Digite o número do passaporte :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_TEXT);

            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));

            tipoDoc = "Passaporte";
            DocumentoTipo = "Passaporte";
            Doc = Edit_UPALL_DOC.getText().toString();
        }
        else if(Title.equals("CNH")){
            Edit_UPALL_DOC.setHint("Digite o RENAVAM da CNH: :");
            Edit_UPALL_DOC.setTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.setHintTextColor(Color.parseColor("#000000"));
            Edit_UPALL_DOC.addTextChangedListener(MaskCPF.insertCPF("###########", Edit_UPALL_DOC));
            Edit_UPALL_DOC.setInputType(InputType.TYPE_CLASS_NUMBER);

            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("###.###.###-##", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#######", Edit_UPALL_DOC));
            Edit_UPALL_DOC.removeTextChangedListener(MaskCPF.insertCPF("#### #### #### ##", Edit_UPALL_DOC));

            tipoDoc = "CNH";
            DocumentoTipo = "CNH";
            Doc = Edit_UPALL_DOC.getText().toString();
        }

        Button Edit_UPALL_CANCEL = view.findViewById(R.id.Edit_UPALL_CANCEL);
        Edit_UPALL_CANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        final String x = tipoDoc;
        Button Edit_UPALL_SAVE = view.findViewById(R.id.Edit_UPALL_SAVE);
        Edit_UPALL_SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(x.equals("CPF")){
                    DocumentoTipo = "CPF";
                    Doc = Edit_UPALL_DOC.getText().toString();
                }
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
                cursor = Alterar_Detail.this.getContentResolver().query(uri, null, null, null, null);
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

}
