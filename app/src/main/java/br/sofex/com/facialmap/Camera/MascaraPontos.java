package br.sofex.com.facialmap.Camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import br.sofex.com.facialmap.MainActivity;

public class MascaraPontos extends View {

    RelativeLayout layout;
    float x = 0;
    float y = 0;
    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private Paint mPaint2;
    private Paint textPaint;
    private List<Point> points = new ArrayList<Point>();
    List<Integer> pointX = new ArrayList<Integer>();
    List<Integer> pointY = new ArrayList<Integer>();
    Button btnred;
    MainActivity main;


    public MascaraPontos(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint2.setColor(Color.BLACK);
        mPaint2.setStrokeWidth(5);
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(91,canvas.getHeight() / 4 ,canvas.getWidth() - 70,canvas.getHeight() / 4,mPaint2);
        canvas.drawCircle((canvas.getWidth() / 4),canvas.getHeight() / 4, 25,mPaint);
        canvas.drawCircle((canvas.getWidth() / (float)1.3) ,(canvas.getHeight() / 4) , 25,mPaint);
        canvas.drawLine(canvas.getWidth() / 2 ,150,(canvas.getWidth() / 2),canvas.getHeight() / (float)1.2 ,mPaint2);
        //canvas.drawLine(canvas.getWidth()  / (float)1.2,(canvas.getHeight()) / (float)1.3 , canvas.getWidth()  / 3, canvas.getHeight() / (float)2.3,mPaint2);
        /*for (Point point : points) {
            canvas.drawCircle(point.x, point.y, 25, mPaint);
            invalidate();
        }*/
    }

    public void LimparPontos(){

        Point p = new Point();
        for(int i : pointX){p.x = i;}
        for(int i : pointY){p.y = i;}
        points.clear();
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            x = m.getX();
            y = m.getY();

            Point p = new Point();
            p.x = (int) m.getX();
            p.y = (int) m.getY();
            pointX.add((int) m.getX());
            pointY.add((int) m.getY());
            Log.e("App1",""+"Point x :"+p.x+" Point y :"+p.y);

            points.add(p);
            invalidate();

        }
        return false;
    }

}
