package br.sofex.com.facialmap.Paciente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Paciente.Alterar.Alterar_Paciente;
import br.sofex.com.facialmap.Paciente.Cadastro.Cadastro_Paciente;
import br.sofex.com.facialmap.Paciente.Excluir.Excluir_Paciente;
import br.sofex.com.facialmap.Paciente.Procurar.Listar_Pacientes;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class EditorPaciente extends AppCompatActivity {

    ImageButton BtnEditPacSearch;
    Button BtnEditPacVoltar,BtnCadPaciente,BtnUpdatePaciente,BtnListPaciente,BtnDeletePaciente;
    EditText EditSearchPac;
    Util util = new Util(EditorPaciente.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_paciente);

        util.setTitleBarApp("Facial Map - Menu Paciente",getSupportActionBar(),EditorPaciente.this);
        EditSearchPac = findViewById(R.id.EditSearchPac);
        BtnEditPacVoltar = findViewById(R.id.BtnEditPacVoltar);
        BtnEditPacSearch = findViewById(R.id.BtnEditPacSearch);
        BtnCadPaciente = findViewById(R.id.BtnCadPaciente);
        BtnUpdatePaciente = findViewById(R.id.BtnUpdatePaciente);
        BtnListPaciente = findViewById(R.id.BtnListPaciente);
        BtnDeletePaciente = findViewById(R.id.BtnDeletePaciente);

        BtnEditPacVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(util.CallActivity(EditorPaciente.this, MainActivity.class));
          // Intent intent = new Intent(EditorPaciente.this,MainActivity.class);
          // startActivity(intent);
            }
        });
        BtnCadPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(util.CallActivity(EditorPaciente.this, Cadastro_Paciente.class));
            }
        });
        BtnUpdatePaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(util.CallActivity(EditorPaciente.this, Alterar_Paciente.class));
            }
        });
        BtnListPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(util.CallActivity(EditorPaciente.this, Listar_Pacientes.class));
            }
        });
        BtnDeletePaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(util.CallActivity(EditorPaciente.this, Excluir_Paciente.class));
            }
        });

    }
}
