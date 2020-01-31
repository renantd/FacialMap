package br.sofex.com.facialmap.Preview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.sofex.com.facialmap.Mapeamento.Mapear.Marcar_Pontos;
import br.sofex.com.facialmap.R;

public class DesenharPonto extends View {

    Activity activity;
    Context mContext;
    private Paint paint;
    private List<Point> points = new ArrayList<>();
    List<Integer> pointX = new ArrayList<>();
    List<Integer> pointY = new ArrayList<>();
    List<Integer> pointX_Result = new ArrayList<>();
    List<Integer> pointY_Result = new ArrayList<>();
    public int count = 0; public int count2 = 0;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    Point p2 = new Point();
    public List<String> ListaPontosMarcados = new ArrayList<>();
    Marcar_Pontos marcar = new Marcar_Pontos();
    private AlertDialog alerta;
    private int CoordenadaXFinal = 0;
    private int CoordenadaYFinal = 0;
    // Main class instance
    private Marcar_Pontos marcarpontos;
    public Integer CountPontos = 0;

    public List<String> ListToxinaValores = new ArrayList<>();
    public DesenharPonto(Context context,Activity activity) {
        super(context);
        this.mContext = context;
        this.activity = activity;
        initView();
    }
    public DesenharPonto(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }
    public DesenharPonto(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.mContext = context;
        initView();
    }
    public DesenharPonto(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        this.mContext = context;
        initView();
    }
    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        ListaPontosMarcados.add("Selecione um ponto");
    }

    @Override
    public boolean onTouchEvent( MotionEvent m) {
        int pointerCount = m.getPointerCount();
        for (int i = 0; i < pointerCount; i++)
        {
            int action = m.getActionMasked();
            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                    count++;
                    Point p = new Point();
                    p.x = (int) m.getX(i);
                    p.y = (int) m.getY(i);
                    pointX.add((int) m.getX(i));
                    pointY.add((int) m.getY(i));

                    ListaPontosMarcados.add("P"+count+"X"+(int) m.getX(i)+" P"+count+"Y"+(int) m.getY(i));
                    Log.e("App1","Desenhar1 : "+(int) m.getX(i)+"Y : "+(int) m.getY(i));
                    list1.add("P"+count+"X"+(int) m.getX(i)+" P"+count+"Y"+(int) m.getY(i));
                    CountPontos = count;

                    points.add(p);
                break;
            }
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //int i = 0;
        count2 = 0;
        for (Point point : points) {
            //i++;
            count2 ++;
            canvas.drawCircle(point.x, point.y, 20, paint);
            paint.setTextSize(60);
            canvas.drawText("P"+count2,point.x-25, point.y-25,paint);
            invalidate();
            listPontos();
        }

    }
    public void DeletePoint(int x , int y){
        pointX_Result = pointX;
        pointY_Result = pointY;

        for(Integer x1 : pointX_Result){
            Log.e("App"," Original_X: "+pointX_Result);
        }
        for(Integer y1 : pointY_Result){
            Log.e("App"," Original_Y: "+pointY_Result);
        }
        if(pointX_Result.contains(x) && pointY_Result.contains(y)){
            //Log.e("AppX"," contentX : "+pointX_Result.indexOf(x));
            pointX_Result.remove(pointX.indexOf(x));
        }

        String i2 = "";
        for(String x1 : list1){
          if(x1.contains(x+"")){
             //int i = list1.indexOf(x1);
             i2 = list1.get(list1.indexOf(x1));
             Log.e("App"," list1 X: "+list1.indexOf(x1)+" List : "+list1.get(list1.indexOf(x1)));
          }
        }
        list1.remove(i2);
        Log.e("App"," list1 A: "+list1);

        int iAux = 0;
        for(String k : list1){
            iAux++;
            k.replace("P"+count2+"X","P"+iAux+"X");
            Log.e("App","iAux :"+iAux);
        }
        points.clear();
        int i = 0;
        for(String k : list1){
            i++;
            if(!k.equals("Selecione um ponto")){
              //Log.e("App","TesteX :"+RefactorCoordX(k)+" TesteY : "+RefactorCoordY(k));
              redrawPoints(RefactorCoordX(k),RefactorCoordY(k));
              //listPontos();
            }
        }Log.e("App","list1 :"+list1);
    }
    public void DeleteAllCanvas(){
        points.clear();
        invalidate();
    }
    public void ClearLista(){
        list1.clear();
        ListaPontosMarcados.clear();
    }
    public List<String> listPontos(){
        return list1;
    }
    public List<String> UpdatelistPontos(){return list2 = list1; }
    public Integer RefactorCoordX(String str){
        String str4 = "";
        try{
            String Pont_Cont = str.substring(0, str.indexOf("X")+1);
            String requiredString = str.replaceAll(Pont_Cont, "");
            str4 = requiredString.substring(0,  requiredString.indexOf(" "));
        }
        catch(NullPointerException e){
            Log.e("Error :","Error :"+e);
        }
        return Integer.parseInt(str4);
    }
    public Integer RefactorCoordY(String str){
        Integer x = Integer.parseInt(str.substring(str.lastIndexOf("Y") + 1));
        //Log.e("App123"," Y "+str);
        return x;
    }
    public void redrawPoints(int x , int y){
        Point p = new Point();
        p.x = x;
        p.y = y;
        points.add(p);
        invalidate();
    }
    public void inserirPonto(final Context context, final int CoordenadaX, final Integer CoordenadaY){
        final int[] count = {0};
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        //inflamos o layout alerta.xml na view
        View view = inflater.inflate(R.layout.inserir_ponto_rosto, null);
        //definimos para o botão do layout um clickListener

        final EditText Edit_Nome_Toxina = view.findViewById(R.id.Edit_Nome_Toxina);
        final EditText Edit_DT_Validade = view.findViewById(R.id.Edit_DT_Validade);
        final EditText Edit_Lote_Toxina = view.findViewById(R.id.Edit_Lote_Toxina);

        //TODO: Arrays
        String[] listMarcaToxina = {"Fabricante do Botox","BOTOX","XEOMIN","PROSIGNE","DYSPORT","BOTULIFT","BOTULIM"};
        String[] listDistribuidora = {"Distribuidora","ALLERGAN","BIOLAB","CRISTÁLIA","IPSEN","BERGAMO","BLAU"};
        String[] listConteudo_Frasco = {"Conteúdo do Frasco","50","100","200","300","500"};
        String[] listDiametroAgulhas = {"Diametro","30G","31G","32G","33G"};
        String[] listComprimentoAgulhas = {"Comprimento","4mm","6mm","8mm","13mm"};

        //final LinearLayout LinNested = view.findViewById(R.id.LinNested);

        //TODO: Spinner
        final Spinner SpinnerMarcaToxina = view.findViewById(R.id.SpinnerMarcaToxina);
        final Spinner Spinner_Distribuidora = view.findViewById(R.id.Spinner_Distribuidora);
        final Spinner Spinner_Conteudo_Frasco = view.findViewById(R.id.Spinner_Conteudo_Frasco);
        final Spinner SpinnerDiamAgulha = view.findViewById(R.id.SpinnerDiamAgulha);
        final Spinner SpinnerCompAgulha = view.findViewById(R.id.SpinnerCompAgulha);

        //TODO: Set Adpter dos Spinners
        ArrayAdapter<String> AdapterMarcaToxina = new ArrayAdapter<>(mContext,R.layout.center_item,R.id.center_item,listMarcaToxina);
        AdapterMarcaToxina.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerMarcaToxina.setAdapter(AdapterMarcaToxina);

        SpinnerMarcaToxina.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerMarcaToxina.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerMarcaToxina.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerMarcaToxina.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterDistribuidora = new ArrayAdapter<>(mContext,R.layout.center_item,R.id.center_item,listDistribuidora);
        AdapterDistribuidora.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        Spinner_Distribuidora.setAdapter(AdapterDistribuidora);

        Spinner_Distribuidora.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spinner_Distribuidora.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spinner_Distribuidora.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        Spinner_Distribuidora.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterConteudo_Frasco = new ArrayAdapter<>(mContext,R.layout.center_item,R.id.center_item,listConteudo_Frasco);
        AdapterConteudo_Frasco.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        Spinner_Conteudo_Frasco.setAdapter(AdapterConteudo_Frasco);

        Spinner_Conteudo_Frasco.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spinner_Conteudo_Frasco.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spinner_Conteudo_Frasco.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        Spinner_Conteudo_Frasco.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterDiametroAgulhas = new ArrayAdapter<>(mContext,R.layout.center_item,R.id.center_item,listDiametroAgulhas);
        AdapterDiametroAgulhas.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerDiamAgulha.setAdapter(AdapterDiametroAgulhas);

        SpinnerDiamAgulha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerDiamAgulha.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerDiamAgulha.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerDiamAgulha.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterComprimentoAgulhas = new ArrayAdapter<>(mContext,R.layout.center_item,R.id.center_item,listComprimentoAgulhas);
        AdapterComprimentoAgulhas.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerCompAgulha.setAdapter(AdapterComprimentoAgulhas);

        SpinnerCompAgulha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerCompAgulha.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerCompAgulha.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerCompAgulha.setSelection(0);
        //TODO: ----------------------------//

        Button Btn_InserirPontoMapClose = view.findViewById(R.id.Btn_InserirPontoMapClose);
        Btn_InserirPontoMapClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               DeletePoint(CoordenadaX,CoordenadaY);
               alerta.dismiss();
            }
        });

        Button Btn_InserirPontoMapSave = view.findViewById(R.id.Btn_InserirPontoMapSave);
        Btn_InserirPontoMapSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){
              count[0]++;
              //TODO: Linear Layout
              //UpdateListToxinas();
              Edit_Nome_Toxina.getText().toString();
              SpinnerMarcaToxina.getSelectedItem().toString();
              Spinner_Distribuidora.getSelectedItem().toString();
              Spinner_Conteudo_Frasco.getSelectedItem().toString();
              SpinnerDiamAgulha.getSelectedItem().toString();
              SpinnerCompAgulha.getSelectedItem().toString();
              Edit_DT_Validade.getText().toString();
              Edit_Lote_Toxina.getText().toString();


              ListToxinaValores.add(Edit_Nome_Toxina.getText().toString());
              ListToxinaValores.add(SpinnerMarcaToxina.getSelectedItem().toString());
              ListToxinaValores.add(Spinner_Distribuidora.getSelectedItem().toString());
              ListToxinaValores.add(Spinner_Conteudo_Frasco.getSelectedItem().toString());
              ListToxinaValores.add(SpinnerDiamAgulha.getSelectedItem().toString());
              ListToxinaValores.add(SpinnerCompAgulha.getSelectedItem().toString());
              ListToxinaValores.add(Edit_DT_Validade.getText().toString());
              ListToxinaValores.add(Edit_Lote_Toxina.getText().toString());

                Toast.makeText(mContext, "Dados :\n"+
                Edit_Nome_Toxina.getText().toString()+"\n"+
                SpinnerMarcaToxina.getSelectedItem().toString()+"\n"+
                Spinner_Distribuidora.getSelectedItem().toString()+"\n"+
                Spinner_Conteudo_Frasco.getSelectedItem().toString()+"\n"+

                SpinnerDiamAgulha.getSelectedItem().toString()+"\n"+
                SpinnerCompAgulha.getSelectedItem().toString()+"\n"+
                Edit_DT_Validade.getText().toString()+"\n"+
                Edit_Lote_Toxina.getText().toString()+"\n",
                Toast.LENGTH_SHORT).show();

              /*LayoutInflater inflater2 = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
              View view1 = inflater2.inflate(R.layout.activity_marcar_pontos, null);
              LinearLayout linAux = view1.findViewById(R.id.LinCustom);*/

              //LinearLayout linAux =  findViewById(R.id.LinCustom);
              //linAux.addView(TextoDinamico("qweqwe","#000000",LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL));
               //linAux.addView(TextoDinamico("qweqwe","#000000",LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL));

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
        //return CoodX[0];

    }

    public RelativeLayout InsertLinearTitulo(String titulo){
        RelativeLayout TituloPonto = new RelativeLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10, 20, 10, 20);
        layoutParams.weight = 1f;
        TituloPonto.setLayoutParams(layoutParams);
        TituloPonto.setBackgroundColor(Color.parseColor("#23A4CA"));

        //TODO : Layout com margin customizada para o Relative layout1
        LinearLayout.LayoutParams RelativeParamsMargins = new LinearLayout.LayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        RelativeParamsMargins.setMargins(10, 40, 10, 40);

        TituloPonto.addView(TextoDinamico(titulo,"#FFFFFF",LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER | Gravity.CENTER_HORIZONTAL));
        return  TituloPonto;
    }
    public TextView TextoDinamico(String Texto , String ColorText, int LarguraLinha, int AlturaLinha, int TextoAlinhamento){
        LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(LarguraLinha,AlturaLinha);
        // TODO: Criação de layouts
        TextView TituloText = new TextView(mContext);
        TituloText.setText(Texto);
        TituloText.setGravity(TextoAlinhamento);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        TituloText.setTypeface(boldTypeface);
        TituloText.setTextSize(18);
        TituloText.setTextColor(Color.parseColor(ColorText));
        TituloText.setLayoutParams(layoutParamsTextView);
        return TituloText;
    }
    public LinearLayout CreateRegistroDinamico(String Texto ,String TextoValor){

        /*LinearLayout LinearPontoBody = new LinearLayout(MarcarPontos.this);
        LinearLayout.LayoutParams layoutParamsPontoBody = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsPontoBody.setMargins(20, 40, 20, 10);
        LinearPontoBody.setLayoutParams(layoutParamsPontoBody);
        //LinearPontoBody.setBackgroundColor(Color.YELLOW);
        LinearPontoBody.setOrientation(LinearLayout.VERTICAL);*/

        ////
        LinearLayout LinearPontoBodyPart1 = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParamsBodyPart1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBodyPart1.setMargins(40, 10, 40, 10);
        //LinearPontoBody.setBackgroundColor(Color.RED);
        //LinearPontoBody.setBackground(ContextCompat.getDrawable(MarcarPontos.this, R.drawable.bottom_border));
        layoutParamsBodyPart1.weight = 1f;
        LinearPontoBodyPart1.setLayoutParams(layoutParamsBodyPart1);
        LinearPontoBodyPart1.setOrientation(LinearLayout.VERTICAL);


        RelativeLayout RelativeNomeToxina = new RelativeLayout(mContext);
        LinearLayout.LayoutParams layoutParamsNomeToxina = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsNomeToxina.weight = 1f;
        layoutParamsNomeToxina.setMargins(5, 15, 5, 10);
        //RelativeNomeToxina.setBackgroundColor(Color.GRAY);
        RelativeNomeToxina.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        RelativeNomeToxina.setLayoutParams(layoutParamsNomeToxina);


        RelativeLayout RelativeValorToxina = new RelativeLayout(mContext);
        LinearLayout.LayoutParams layoutParamsValorToxina = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsValorToxina.weight = 1f;
        RelativeValorToxina.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        layoutParamsValorToxina.setMargins(5, 15, 5, 10);
        // RelativeValorToxina.setBackgroundColor(Color.GRAY);
        RelativeValorToxina.setLayoutParams(layoutParamsValorToxina);


        RelativeLayout RelBotaoToxinaAlterar = new RelativeLayout(mContext);
        LinearLayout.LayoutParams layoutParamsRelBotaoToxinaAlterar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsRelBotaoToxinaAlterar.weight = 1.45f;
        RelBotaoToxinaAlterar.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        //layoutParamsRelBotaoToxinaAlterar.setMargins(5, 15, 5, 10);
        // RelativeValorToxina.setBackgroundColor(Color.GRAY);
        RelBotaoToxinaAlterar.setLayoutParams(layoutParamsRelBotaoToxinaAlterar);

        Button BotaoToxinaAlterar = new Button(mContext);
        LinearLayout.LayoutParams layoutParamsBotaoToxinaAlterar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBotaoToxinaAlterar.setMargins(0, 15, 0, 0);
        BotaoToxinaAlterar.setLayoutParams(layoutParamsBotaoToxinaAlterar);
        layoutParamsBodyPart1.setMargins(40, 10, 40, 10);
        BotaoToxinaAlterar.setText("Alterar");
        BotaoToxinaAlterar.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
        BotaoToxinaAlterar.setTypeface(boldTypeface);
        BotaoToxinaAlterar.setTextSize(16);
        BotaoToxinaAlterar.setTextColor(Color.parseColor("#FFFFFF"));
        BotaoToxinaAlterar.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_rounded_bluefacial));
        RelBotaoToxinaAlterar.addView(BotaoToxinaAlterar);


        RelativeNomeToxina.addView(TextoDinamico(Texto,"#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0));
        RelativeValorToxina.addView(TextoDinamico(TextoValor,"#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER | Gravity.CENTER_VERTICAL));
        LinearPontoBodyPart1.addView(RelativeNomeToxina);
        LinearPontoBodyPart1.addView(RelativeValorToxina);

        if(Texto != "Soro Fisiológico:"){
            LinearPontoBodyPart1.addView(RelBotaoToxinaAlterar);
        }

        return LinearPontoBodyPart1;
    }
    public RelativeLayout CreateRegistroDePonto(String[] Texto ,String[] TextoValor){

        RelativeLayout RelativePontoBody = new RelativeLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 30, 20, 5);
        RelativePontoBody.setGravity(Gravity.CENTER);
        layoutParams.weight = 1f;
        RelativePontoBody.setLayoutParams(layoutParams);
        RelativePontoBody.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_border_blue_rounded));

        //TODO: Linear Layout para Relativelayout
        LinearLayout LinearPontoBody = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParamsPontoBody = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsPontoBody.setMargins(20, 40, 20, 60);
        LinearPontoBody.setLayoutParams(layoutParamsPontoBody);
        //LinearPontoBody.setBackgroundColor(Color.YELLOW);
        LinearPontoBody.setOrientation(LinearLayout.VERTICAL);

        //String[] ArrayTextoToxina = new String[]{"Nome da Toxina :","Fabricante da Toxina :","Distribuidora da Toxina :","Conteúdo do Frasco :","Soro Fisiológico :","Data de Validade :","Lote :","Tipo da Agulha :","Tamanho da Agulha :"};
        //String[] ArrayToxinaValores = new String[]{"Botulinum Toxin Type A","Botox","Allegran","100U","Soro Fisiológio 0,9%","06/09/2020","C3214D1","30G 4mm","4mm"};

        for(int i = 0; i <= 8; i++){
            LinearPontoBody.addView(CreateRegistroDinamico(Texto[i],TextoValor[i]));
        }

        RelativePontoBody.addView(LinearPontoBody);
        //CreateRegistroDinamico("Nome da Toxina :","Botulinum Toxin Type A","#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0));


        return RelativePontoBody;
    }
    public void DrawnPoint(int CoodX,int CoodY){

        Point p = new Point();
        p.x = CoodX;
        p.y = CoodY;
        pointX.add(CoodX);
        pointY.add(CoodY);
        points.add(p);

    }
    public List<String> UpdateListToxinas(){
        return ListToxinaValores;
    }

}
