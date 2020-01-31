package br.sofex.com.facialmap.Paciente;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Paciente.Procurar.Listar_Pacientes;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Paciente_Encontrado extends AppCompatActivity {

    Button BtnEditPacPrinc,BtnEditPacVolta,BtnComparar;

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
    Util util = new Util(Paciente_Encontrado.this);
    //DataBaseDAO pacienteDAO = new DataBaseDAO(Paciente_Encontrado.this);

    /*TODO: Variáveis do tipo AlertDialog*/
    ListView lv_itemsUpdate;
    RelativeLayout RL_Cod_PacFound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_encontrado);

        util.setTitleBarApp("Facial Map - Resultado da Pesquisa",getSupportActionBar(), Paciente_Encontrado.this);
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
            Intent intent = new Intent(Paciente_Encontrado.this, MainActivity.class);
            startActivity(intent);
            }
        });
        BtnBackPacFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(Paciente_Encontrado.this, Listar_Pacientes.class);
              startActivity(intent);
            }
        });

        /*CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));
        Bitmap bit = BitmapFactory.decodeByteArray(pacienteDAO.getFotoByID(CodPaciente), 0, pacienteDAO.getFotoByID(CodPaciente).length);
        img_perfilFoundListar.setImageBitmap(bit);
        Pac_NomeFoundListar.setText(pacienteDAO.getNomePacienteByID(CodPaciente));
        Doc_PacFoundListar.setText(pacienteDAO.getDocumentoByID(CodPaciente));
        Doc_ValueFoundListar.setText(pacienteDAO.getDocumentoConteudoByID(CodPaciente));
        Nasc_PacFoundListar.setText(pacienteDAO.getNacionalidadeByID(CodPaciente));
        Etnia_PacFoundListar.setText(pacienteDAO.getEtniaByID(CodPaciente));
        DataNasc_PacFoundListar.setText(pacienteDAO.getDataNascimentoByID(CodPaciente));
        Sexo_PacFoundListar.setText(pacienteDAO.getSexoByID(CodPaciente));
        TelFixo_PacFoundListar.setText(pacienteDAO.getTelefoneFixoByID(CodPaciente));
        TelCel_PacFoundListar.setText(pacienteDAO.getTelefoneCelularByID(CodPaciente));
        Email_PacFoundListar.setText(pacienteDAO.getEmailByID(CodPaciente));
        CEP_PacFoundListar.setText(pacienteDAO.getCepByID(CodPaciente));
        End_PacFoundListar.setText(pacienteDAO.getEnderecoByID(CodPaciente));
        Comp_PacFoundListar.setText(pacienteDAO.getComplementoByID(CodPaciente));
        Num_PacFoundListar.setText(pacienteDAO.getNumeroByID(CodPaciente));
        Bairro_PacFoundListar.setText(pacienteDAO.getBairroByID(CodPaciente));
        Munic_PacFoundListar.setText(pacienteDAO.getMunicipioByID(CodPaciente));
        Estado_PacFoundListar.setText(pacienteDAO.getEstadoByID(CodPaciente));*/

    }

}
