package br.sofex.com.facialmap.Paciente.Procurar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Paciente.EditorPaciente;
import br.sofex.com.facialmap.R;

public class Procurar_Paciente extends AppCompatActivity {

    //EditText EditPesqPaciente;
    Button BtnTelaInicial,BtnVoltar;
    AutoCompleteTextView EditPesqPaciente;

    int flags[] = {R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes};

    List<String> Nomes1;
    ArrayAdapter<String> adaptador;

    // Array of strings for ListView Title

    String[] Nomes2 = new String[]{
       "Judith Melnick", "Evelyn Harper", "Rose Lynskey", "Chelsea Christine",
       "Joana Melnick 2", "Cristine Harper 2", "Berta Lynskey 2", "Celeste Christine 2",
    };


    int[] listviewImage = new int[]{
       R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes,
       R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes, R.drawable.molde_antes,
    };


    String[] fixo = new String[]{
       "(31) 3282-5377", "(31) 3082-4206", "(31) 3282-5377", "(31) 3825-0797",
       "(31) 3991-2442", "(31) 3701-2192", "(31) 3876-2293", "(31) 3458-6405",
    };

    String[] celular = new String[]{
       "(31) 98282-5377", "(31) 98082-4206", "(31) 99282-5377", "(31) 99825-0797",
       "(31) 99991-2442", "(31) 99701-2192", "(31) 99876-2293", "(31) 99458-6405",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar__paciente);

        BtnTelaInicial = findViewById(R.id.BtnTelaInicial);
        BtnVoltar = findViewById(R.id.BtnVoltar);

        BtnTelaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Procurar_Paciente.this, MainActivity.class);
           startActivity(intent);
            }
        });

        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Procurar_Paciente.this, EditorPaciente.class);
           startActivity(intent);
            }
        });

        EditPesqPaciente = findViewById(R.id.EditPesqPaciente);
        EditPesqPaciente.setHint("Digite um paciente para pesquisar");
        EditPesqPaciente.setTextColor(Color.BLACK);
        // Criação de um ArrayAdapter, para que possa mostrar
        // a lista de sugestões para o usuário
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Nomes2);
        EditPesqPaciente.setAdapter(adapter);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("retorno_listview_title", Nomes2[i]);
            hm.put("retorno_telfixo", fixo[i]);
            hm.put("retorno_telcelular", celular[i]);
            hm.put("retorno_listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"retorno_listview_image", "retorno_listview_title","retorno_telfixo" , "retorno_telcelular"};
        int[] to = {R.id.retorno_listview_image, R.id.retorno_listview_item_title, R.id.retorno_telfixo,R.id.retorno_telcelular};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_listar, from, to);
        ListView androidListView = (ListView) findViewById(R.id.LV_RETORNO);
        androidListView.setAdapter(simpleAdapter);

    }
}
