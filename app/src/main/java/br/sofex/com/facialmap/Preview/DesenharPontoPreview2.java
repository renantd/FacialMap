package br.sofex.com.facialmap.Preview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DesenharPontoPreview2  extends View {


    Activity activity;
    Context mContext;
    private Paint paint;
    public List<String> ListCoord = new ArrayList<>();


    public DesenharPontoPreview2(Context context,Activity activity) {
        super(context);
        this.mContext = context;
        this.activity = activity;
        initView();
    }

    public DesenharPontoPreview2(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public DesenharPontoPreview2(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.mContext = context;
        initView();
    }

    public DesenharPontoPreview2(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
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
                    Log.e("DesenharPontoPreview2 ","X "+(int) m.getX(i)+" Y "+(int) m.getY(i));
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

        /*for(String x : listListaPontoPreview){
            Point p = new Point();
            p.x = RefactorCoordX(x);
            p.y = RefactorCoordY(x);
            points.add(p);
        }

        for (Point point : points) {
            canvas.drawCircle(point.x, point.y, 20, paint);
            paint.setTextSize(60);
            canvas.drawText("P"+count2,point.x-25, point.y-25,paint);
            invalidate();
        }*/

        for (String x : ListCoord) {
            Log.e("DesenharPontoPreviewX ",x);
            if(!x.equalsIgnoreCase("Selecione um ponto")){
              canvas.drawCircle((RefactorCoordX(x)/2) + 17,(RefactorCoordY(x)/2) + 41, 10, paint);
              canvas.drawText(GetPointNumber(x),(RefactorCoordX(x)/2) + 35, (RefactorCoordY(x)/2) + 30,paint);
              paint.setTextSize(50); invalidate();
            }
        }

    }

    public String GetPointNumber(String Coordenada){
        String Pont_Cont = Coordenada.substring(0, Coordenada.indexOf("P"));
        String requiredString = Coordenada.replaceAll(Pont_Cont, "");
        String str4 = requiredString.substring(0,  requiredString.indexOf("X"));
        return str4;
    }
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


}
