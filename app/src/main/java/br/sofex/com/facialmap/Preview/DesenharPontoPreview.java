package br.sofex.com.facialmap.Preview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DesenharPontoPreview extends View {

    Activity activity;
    Context mContext;
    private Paint paint;
    private List<Point> points = new ArrayList<>();
    List<Integer> pointX = new ArrayList<>();
    List<Integer> pointY = new ArrayList<>();
    List<Integer> pointX_Result = new ArrayList<>();
    List<Integer> pointY_Result = new ArrayList<>();
    int count = 0;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    Point p2 = new Point();
    public List<String> ListCoord = new ArrayList<>();
    public List<String> ListToxinaValores = new ArrayList<>();


    public DesenharPontoPreview(Context context,Activity activity) {
        super(context);
        this.mContext = context;
        this.activity = activity;
        initView();
    }

    public DesenharPontoPreview(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public DesenharPontoPreview(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.mContext = context;
        initView();
    }

    public DesenharPontoPreview(Context context, AttributeSet attrs, int defStyle) {
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
                    /*count++;
                    Point p = new Point();
                    p.x = (int) m.getX(i);
                    p.y = (int) m.getY(i);
                    pointX.add((int) m.getX(i));
                    pointY.add((int) m.getY(i));
                    points.add(p);
                    Log.e("App1","X "+(int) m.getX(i)+" Y "+(int) m.getY(i));*/
                    Log.e("DesenharPontoPreview1 ","X "+(int) m.getX(i)+" Y "+(int) m.getY(i));
                    break;
                case MotionEvent.ACTION_MOVE: break;
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
                //Log.e("App1"," ListCoord "+ x);
                //canvas.drawCircle(RefactorCoordX(x),RefactorCoordY(x), 20, paint);
                if(RefactorCoordX(x) >= 900 && RefactorCoordX(x) <=1000){
                  canvas.drawCircle(RefactorCoordX(x) - ((float)Math.floor(RefactorCoordX(x)/15)),RefactorCoordY(x) - ((float)Math.floor(RefactorCoordX(x)/16)+6), 20, paint);
                }else{
                  canvas.drawCircle(RefactorCoordX(x) - ((float)Math.floor(RefactorCoordX(x)/15)),RefactorCoordY(x) - ((float)Math.floor(RefactorCoordX(x)/14.5)+6), 20, paint);
                }
                //canvas.drawCircle(RefactorCoordX(x) - ((float)Math.floor(RefactorCoordX(x)/15)),RefactorCoordY(x) - ((float)Math.floor(RefactorCoordY(x)/14.5)+6), 20, paint);
                //canvas.drawCircle(RefactorCoordX(x) - ((float)Math.floor(RefactorCoordX(x)/15))-5,RefactorCoordY(x) - ((float)Math.floor(RefactorCoordY(x)/13.5))-25, 20, paint);
                canvas.drawText(GetPointNumber(x),RefactorCoordX(x) - ((float)Math.floor(RefactorCoordX(x)/12))-20, RefactorCoordY(x) - ((float)Math.floor(RefactorCoordY(x)/17.5))-42,paint);
                paint.setTextSize(60);
                invalidate();
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
