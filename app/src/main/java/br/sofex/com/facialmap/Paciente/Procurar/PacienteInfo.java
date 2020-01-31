package br.sofex.com.facialmap.Paciente.Procurar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetDataByCod;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetFotoByCod;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mascara.Mask;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class PacienteInfo  extends AppCompatActivity {

    Button BtnBackHomePacFound,BtnBackPacFound,BtnUPFotoPC,BtnUPAll_Pac;
    ImageView img_perfilFoundListar;
    TextView Pac_NomeFoundListar;
    TextView Doc_PacFoundListar;
    TextView Doc_ValueFoundListar;
    TextView Nasc_PacFoundListar;
    TextView Etnia_PacFoundListar;
    TextView DataNasc_PacFoundListar;
    TextView Sexo_PacFoundListar;
    TextView TelFixo_PacFoundListar;
    TextView TelCel_PacFoundListar;
    TextView Email_PacFoundListar;
    TextView CEP_PacFoundListar;
    TextView End_PacFoundListar;
    TextView Comp_PacFoundListar;
    TextView Num_PacFoundListar;
    TextView Bairro_PacFoundListar;
    TextView Munic_PacFoundListar;
    TextView Estado_PacFoundListar;
    private Long CodPaciente;
    private boolean resultrespost = false;
    private int SELECT_IMAGE = 1;

    private String selectedImagePath;
    private String DocumentoSelect = null;
    private String DocumentoValue = null;
    private String selectedPath;
    private String FilePathCorrigido;
    private String NomePaciente= "";
    private String DtNascValido = null;
    private String capturedFileName = "";
    private String SexoSelecionado = null;
    private Uri capturedImageUri = null;
    private Bitmap bitmap = null;

    Button BtnPac_NomeFound,BtnDoc_PacFound,BtnNasc_PacFound,BtnEtnia_PacFound,BtnDataNasc_PacFound,BtnSexo_PacFound,BtnTelFixo_PacFound,BtnTelCel_PacFound,
            BtnEmail_PacFound,BtnCEP_PacFound;
    //BtnEnd_PacFound,BtnComp_PacFound,BtnNum_PacFound,BtnBairro_PacFound,BtnMunic_PacFound,BtnEstado_PacFound

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog alerta;

    /*TODO: importação de classes internas*/
    Util util = new Util(PacienteInfo.this);
    //DataBaseDAO pacienteDAO = new DataBaseDAO(PacienteInfo.this);

    /*TODO: Variáveis do tipo AlertDialog*/
    ListView lv_itemsUpdate;
    RelativeLayout RL_Cod_PacFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_encontrado);

        //TODO: Button
        BtnBackHomePacFound = findViewById(R.id.BtnBackHomePacFound);
        BtnBackPacFound = findViewById(R.id.BtnBackPacFound);
        BtnUPFotoPC = findViewById(R.id.BtnUPFotoPC);
        BtnUPAll_Pac = findViewById(R.id.BtnUPAll_Pac);

        //TODO: TextView
        img_perfilFoundListar = findViewById(R.id.img_perfilFoundListar);
        Pac_NomeFoundListar = findViewById(R.id.Pac_NomeFoundListar);
        Doc_PacFoundListar = findViewById(R.id.Doc_PacFoundListar);
        Doc_ValueFoundListar = findViewById(R.id.Doc_ValueFoundListar);
        Nasc_PacFoundListar = findViewById(R.id.Nasc_PacFoundListar);
        Etnia_PacFoundListar = findViewById(R.id.Etnia_PacFoundListar);
        DataNasc_PacFoundListar = findViewById(R.id.DataNasc_PacFoundListar);
        Sexo_PacFoundListar = findViewById(R.id.Sexo_PacFoundListar);
        TelFixo_PacFoundListar = findViewById(R.id.TelFixo_PacFoundListar);
        TelCel_PacFoundListar = findViewById(R.id.TelCel_PacFoundListar);
        Email_PacFoundListar = findViewById(R.id.Email_PacFoundListar);
        CEP_PacFoundListar = findViewById(R.id.CEP_PacFoundListar);
        End_PacFoundListar = findViewById(R.id.End_PacFoundListar);
        Comp_PacFoundListar = findViewById(R.id.Comp_PacFoundListar);
        Num_PacFoundListar = findViewById(R.id.Num_PacFoundListar);
        Bairro_PacFoundListar = findViewById(R.id.Bairro_PacFoundListar);
        Munic_PacFoundListar = findViewById(R.id.Munic_PacFoundListar);
        Estado_PacFoundListar = findViewById(R.id.Estado_PacFoundListar);


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

        BtnBackHomePacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PacienteInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
        BtnBackPacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PacienteInfo.this, Listar_Pacientes.class);
                startActivity(intent);
            }
        });

        CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));

        byte[] FotoThread = null;
        try {
           FotoThread = new doTaskCadastroGetFotoByCod(PacienteInfo.this,CodPaciente).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bit = BitmapFactory.decodeByteArray(FotoThread, 0, FotoThread.length);
        img_perfilFoundListar.setImageBitmap(bit);
        try {
            Pac_NomeFoundListar.setText(        new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,1).execute().get());
            Doc_PacFoundListar.setText(         new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,2).execute().get());
            Doc_ValueFoundListar.setText(       new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,3).execute().get());
            Nasc_PacFoundListar.setText(        new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,4).execute().get());
            Etnia_PacFoundListar.setText(       new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,5).execute().get());
            DataNasc_PacFoundListar.setText(    new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,6).execute().get());
            Sexo_PacFoundListar.setText(        new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,7).execute().get());
            TelFixo_PacFoundListar.setText(     new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,8).execute().get());
            TelCel_PacFoundListar.setText(      new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,9).execute().get());
            Email_PacFoundListar.setText(       new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,10).execute().get());
            CEP_PacFoundListar.setText(         new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,11).execute().get());
            End_PacFoundListar.setText(         new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,12).execute().get());
            Comp_PacFoundListar.setText(        new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,13).execute().get());
            Num_PacFoundListar.setText(         new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,14).execute().get());
            Bairro_PacFoundListar.setText(      new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,15).execute().get());
            Munic_PacFoundListar.setText(       new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,16).execute().get());
            Estado_PacFoundListar.setText(      new doTaskCadastroGetDataByCod(PacienteInfo.this,CodPaciente,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String PreencherData() {

        EditText editcpf;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Titulo");

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Facial Map - Info \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        builder.setCustomTitle(title);
        builder.setMessage("\nDigite a data de pesquisa : ");

        final EditText input = new EditText(PacienteInfo.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        builder.setView(input);
        //cpfMask = Mask.insert("##/##/####", input);
        //input.addTextChangedListener(cpfMask);
        //input.addTextChangedListener(Mask.insertCPF("###.###.###-##", input));
        input.addTextChangedListener(Mask.insertCPF("##/##/####", input));

        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.hide();
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        return input.getText().toString();
    }

}
