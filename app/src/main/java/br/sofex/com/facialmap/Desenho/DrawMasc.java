package br.sofex.com.facialmap.Desenho;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.sofex.com.facialmap.Mensagem.Mensagem;


public class DrawMasc extends View {

    private Paint paint,paint2,paint3;
    RelativeLayout layout;
    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private Paint textPaint;
    private List<Point> points = new ArrayList<Point>();
    List<Integer> pointX = new ArrayList<Integer>();
    List<Integer> pointY = new ArrayList<Integer>();
    Mensagem mensagem = new Mensagem(getContext());
    Context mContext;


    public DrawMasc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5f);
        paint.setTextSize(80);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStrokeWidth(5f);
        paint2.setTextSize(80);
        paint2.setColor(Color.RED);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStrokeWidth(5f);
        paint3.setTextSize(80);
        paint3.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        Log.e("App1",""+width);
        Log.e("App1",""+height);

        try {
            //float height_canvas = (float)(getHeight()/3.65);
            //float height_canvas = (float)(getHeight()/0);
            //canvas.drawLine(0,(float)(getHeight()/2.22),width,(float)(getHeight()/2.22),paint);
            //float height_canvas = (float)(height/3.65);
            //float width_canvas = (float)(width/3.65);

            if(height != 0 && height != 0){
                canvas.drawLine(0,(float)(height/3.45),width,(float)(height/3.45),paint3); // Linha Horizontal -
                canvas.drawLine((float)(width/2),0,(float)(width/2),height,paint3);  // Linha vertical |
                canvas.drawCircle( (float)(width/2.6),(float)(height/3.45),40,paint2);
                canvas.drawCircle( (float)(width/1.63),(float)(height/3.45),40,paint2);

                /*canvas.drawText("Y",(float)(width/2),80,paint3);
                canvas.drawText("X",(float)(width/2),(float)(height/1.65),paint2);
                canvas.drawText("X1",50,(float)(height/3.8),paint2);
                canvas.drawText("y1",(float)(width/1.08),(float)(height/3.8),paint3);*/
            }else {
                mensagem.ErrorMsg("Erro Critico(DrawMasc)\nValor de y é inválido .\nValor inicial e final de y de desenho é zero");
            }

        } catch ( ArithmeticException e){
            mensagem.ErrorMsg(e.toString()+"\n"+" Erro no calculo a altura da tela do dispositivo ");
        }

        // sintaxe = canvas.drawLine(starX,starY,endX,endY,paint);
    }

}
