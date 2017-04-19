package ca.steven_zhu.a3paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;


/**
 * Created by steven on 11/07/16.
 */
public class DrawView extends View {

    //current shape
    private ArrayList<MyShape> allShapes;

    private MyShape currShape;

    private int toolType;

    private Paint drawPaint, canvasPaint, shapePaint;

    private int paintColor = 0xFF660000;

    private Canvas drawCanvas;

    private Bitmap canvasBitmap;

    private float brushSize;

    private float starttouchX;
    private float starttouchY;

    private float endtouchX;
    private float endtouchY;

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDraw();
    }

    private void setupDraw(){

        drawPaint = new Paint();
        allShapes = new ArrayList<MyShape>();
        toolType = 1;
        brushSize = getResources().getInteger(R.integer.medium_size);

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeWidth(brushSize);

        shapePaint = new Paint();
        shapePaint.setAntiAlias(true);
        shapePaint.setStyle(Paint.Style.STROKE);
        shapePaint.setStrokeJoin(Paint.Join.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int maxwh = Math.max(w, h);
        canvasBitmap = Bitmap.createBitmap(maxwh, maxwh, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        for (int i = 0; i < allShapes.size(); ++i) {
            MyShape temp = allShapes.get(i);
            float x1 = temp.getX1();
            float x2 = temp.getX2();
            float y1 = temp.getY1();
            float y2 = temp.getY2();
            shapePaint.setStrokeWidth(temp.getthickness());
            shapePaint.setColor(temp.getColor());
            shapePaint.setStyle(Paint.Style.STROKE);
            if (temp.getshapetype() == 1) {
                canvas.drawLine(x1, y1, x2, y2, shapePaint);
            }
            else if (temp.getshapetype() == 2) {
                float radius = (Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2)))/2;
                canvas.drawCircle(Math.min(x1, x2) + Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2))/2, Math.min(y1, y2) + Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2))/2, radius, shapePaint);
                if (temp.getfill()) {
                    shapePaint.setStyle(Paint.Style.FILL);
                    shapePaint.setColor(temp.getfillcolor());
                    canvas.drawCircle(Math.min(x1, x2) + Math.min(Math.max(x1 - x2, x2 - x1), Math.max(y2 - y1, y1 - y2)) / 2, Math.min(y1, y2) + Math.min(Math.max(x1 - x2, x2 - x1), Math.max(y2 - y1, y1 - y2)) / 2, radius - 2, shapePaint);
                }
            }
            else {
                canvas.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.min(x1, x2) + Math.max(x2-x1, x1-x2), Math.min(y1, y2) + Math.max(y2-y1, y1-y2), shapePaint);
                if (temp.getfill()) {
                    shapePaint.setStyle(Paint.Style.FILL);
                    shapePaint.setColor(temp.getfillcolor());
                    canvas.drawRect(Math.min(x1, x2)-2, Math.min(y1, y2)-2, Math.min(x1, x2) + Math.max(x2-x1, x1-x2)-4, Math.min(y1, y2) + Math.max(y2-y1, y1-y2)-4, shapePaint);
                }
            }
        }
        if (currShape != null) {
            float x1 = currShape.getX1();
            float x2 = currShape.getX2();
            float y1 = currShape.getY1();
            float y2 = currShape.getY2();
            shapePaint.setStrokeWidth(currShape.getthickness());
            shapePaint.setColor(currShape.getColor());
            shapePaint.setStyle(Paint.Style.STROKE);
            if (currShape.getshapetype() == 1) {
                canvas.drawLine(x1, y1, x2, y2, shapePaint);
            }
            else if (currShape.getshapetype() == 2) {
                float radius = (Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2)))/2;
                canvas.drawCircle(Math.min(x1, x2) + Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2))/2, Math.min(y1, y2) + Math.min(Math.max(x1-x2, x2-x1), Math.max(y2-y1, y1-y2))/2, radius, shapePaint);
                if (currShape.getfill()) {
                    shapePaint.setStyle(Paint.Style.FILL);
                    shapePaint.setColor(currShape.getfillcolor());
                    canvas.drawCircle(Math.min(x1, x2) + Math.min(Math.max(x1 - x2, x2 - x1), Math.max(y2 - y1, y1 - y2)) / 2, Math.min(y1, y2) + Math.min(Math.max(x1 - x2, x2 - x1), Math.max(y2 - y1, y1 - y2)) / 2, radius - 2, shapePaint);
                }
            }
            else {
                canvas.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.min(x1, x2) + Math.max(x2-x1, x1-x2), Math.min(y1, y2) + Math.max(y2-y1, y1-y2), shapePaint);
                if (currShape.getfill()) {
                    shapePaint.setStyle(Paint.Style.FILL);
                    shapePaint.setColor(currShape.getfillcolor());
                    canvas.drawRect(Math.min(x1, x2)-2, Math.min(y1, y2)-2, Math.min(x1, x2) + Math.max(x2-x1, x1-x2)-4, Math.min(y1, y2) + Math.max(y2-y1, y1-y2)-4, shapePaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (toolType == 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    starttouchX = event.getX();
                    starttouchY = event.getY();;
                    selectShape(starttouchX, starttouchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (currShape != null) {
                        endtouchX = event.getX() - starttouchX;
                        starttouchX = event.getX();
                        endtouchY = event.getY() - starttouchY;
                        starttouchY = event.getY();
                        moveShape(endtouchX, endtouchY);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    endtouchX = event.getX() - starttouchX;
                    starttouchX = event.getX();
                    endtouchY = event.getY() - starttouchY;
                    starttouchY = event.getY();
                    moveShape(endtouchX, endtouchY);
                    addcurrshape();
                    break;
                default:
                    return false;
            }
        }
        else if (toolType == 1 || toolType == 2 || toolType == 3) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    starttouchX = event.getX();
                    starttouchY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    currShape = new MyShape(starttouchX, starttouchY, event.getX(), event.getY(), toolType, drawPaint.getColor(), drawPaint.getStrokeWidth());
                    break;
                case MotionEvent.ACTION_UP:
                    endtouchX = event.getX();
                    endtouchY = event.getY();
                    currShape = new MyShape(starttouchX, starttouchY, endtouchX, endtouchY, toolType, drawPaint.getColor(), drawPaint.getStrokeWidth());
                    addcurrshape();
                    break;
                default:
                    return false;
            }
        }
        else if (toolType == 4) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    starttouchX = event.getX();
                    starttouchY = event.getY();
                    eraseShape(starttouchX, starttouchY);
                    break;
                default:
                    return false;
            }
        }
        else if (toolType == 5) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    starttouchX = event.getX();
                    starttouchY = event.getY();
                    fillShape(starttouchX, starttouchY);
                    break;
                default:
                    return false;
            }
        }

        invalidate();
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
        if (currShape != null) {
            currShape.setcolor(paintColor);
        }
    }

    public void setBrushSize(float newSize){
        brushSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        drawPaint.setStrokeWidth(brushSize);
        if (currShape != null) {
            currShape.setthickness(brushSize);
        }
    }

    public void setToolType(int newTool){
        addcurrshape();
        toolType = newTool;
    }


    public void addcurrshape() {
        if (currShape != null) {
            currShape.setcurr(false);
            allShapes.add(currShape);
        }
        currShape = null;
    }

    public boolean selectShape(float x, float y){
        addcurrshape();
        for (int i = allShapes.size()-1; i >= 0; i--) {
            MyShape temp = allShapes.get(i);
            if ((x >= temp.getX1() && x <= temp.getX2() && y >= temp.getY1() && y <= temp.getY2()) ||
                    (x >= temp.getX1() && x <= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y >= temp.getY1() && y <= temp.getY2())) {
                currShape = temp;
                allShapes.remove(i);
                currShape.setcurr(true);
                drawPaint.setColor(currShape.getColor());
                drawPaint.setStrokeWidth(currShape.getthickness());
                return true;
            }
        }
        return false;
    }

    public void moveShape(float x, float y) {
        if (currShape != null) {
            currShape.setX1(currShape.getX1() + x);
            currShape.setY1(currShape.getY1() + y);
            currShape.setX2(currShape.getX2() + x);
            currShape.setY2(currShape.getY2() + y);
        }
    }

    public void eraseShape(float x, float y) {
        addcurrshape();
        for (int i = allShapes.size()-1; i >= 0; i--) {
            MyShape temp = allShapes.get(i);
            if ((x >= temp.getX1() && x <= temp.getX2() && y >= temp.getY1() && y <= temp.getY2()) ||
                    (x >= temp.getX1() && x <= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y >= temp.getY1() && y <= temp.getY2())) {
                allShapes.remove(i);
                break;
            }
        }
    }

    public void fillShape(float x, float y) {
        addcurrshape();
        for (int i = allShapes.size()-1; i >= 0; i--) {
            MyShape temp = allShapes.get(i);
            if ((x >= temp.getX1() && x <= temp.getX2() && y >= temp.getY1() && y <= temp.getY2()) ||
                    (x >= temp.getX1() && x <= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y <= temp.getY1() && y >= temp.getY2()) ||
                    (x <= temp.getX1() && x >= temp.getX2() && y >= temp.getY1() && y <= temp.getY2())) {
                temp.setfill(true);
                temp.setfillcolor(drawPaint.getColor());
                break;
            }
        }
    }

    public void setallShapes(ArrayList<MyShape> newlist) {
        allShapes = newlist;
    }

    public ArrayList<MyShape> getallShapes() {
        return allShapes;
    }
}
