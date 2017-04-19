package ca.steven_zhu.a3paint;
import android.graphics.Color;
import android.os.Parcelable;
import android.os.Parcel;
import java.io.*;

/**
 * Created by steven on 10/07/16.
 */


public class MyShape implements Parcelable{
    private int shapetype;
    private float x1, y1, x2, y2;
    private int color;
    private int fillcolor;
    private float thickness;
    private boolean fill;
    private boolean curr;

    public MyShape(float x1, float y1, float x2, float y2, int shapetype, int currcolor, float currthickness) {
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.shapetype = shapetype;
        this.color = currcolor;
        this.thickness = currthickness;
        this.fill = false;
        this.fillcolor = Color.WHITE;
        this.curr = true;
    }

    public MyShape(Parcel in) {
        this.x1=Float.parseFloat(in.readString());
        this.y1=Float.parseFloat(in.readString());
        this.x2=Float.parseFloat(in.readString());
        this.y2=Float.parseFloat(in.readString());
        this.shapetype = Integer.parseInt(in.readString());
        this.color = Integer.parseInt(in.readString());
        this.fillcolor = Integer.parseInt(in.readString());
        this.thickness = Float.parseFloat(in.readString());
        this.fill = Boolean.parseBoolean(in.readString());
        this.curr = Boolean.parseBoolean(in.readString());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(Float.toString(x1));
        out.writeString(Float.toString(y1));
        out.writeString(Float.toString(x2));
        out.writeString(Float.toString(y2));
        out.writeString(Integer.toString(shapetype));
        out.writeString(Integer.toString(color));
        out.writeString(Integer.toString(fillcolor));
        out.writeString(Float.toString(thickness));
        out.writeString(Boolean.toString(fill));
        out.writeString(Boolean.toString(curr));
    }

    public static final Parcelable.Creator<MyShape> CREATOR = new Parcelable.Creator<MyShape>() {
        public MyShape createFromParcel(Parcel in) {
            return new MyShape(in);
        }

        public MyShape[] newArray(int size) {
            return new MyShape[size];
        }
    };

    public void setX1(float x1){
        this.x1=x1;
    }

    public void setY1(float y1){
        this.y1=y1;
    }

    public void setX2(float x2){
        this.x2=x2;
    }
    public void setY2(float y2){
        this.y2=y2;
    }

    public void setcolor(int newcolor){
        this.color=newcolor;
    }

    public void setthickness(float newthickness){
        this.thickness = newthickness;
    }

    public void setcurr(boolean bool) {
        curr = bool;
    }

    public void setfill(boolean filled) {
        fill = filled;
    }

    public void setfillcolor(int col) {
        fillcolor = col;
    }

    public float getX1(){
        return x1;
    }

    public float getY1(){
        return y1;
    }

    public float getX2(){
        return x2;
    }

    public float getY2(){
        return y2;
    }

    public int getshapetype() {
        return shapetype;
    }

    public int getColor(){
        return color;
    }

    public float getthickness(){
        return thickness;
    }


    public boolean getcurr() {
        return curr;
    }

    public boolean getfill() {
        return fill;
    }

    public int getfillcolor() {
        return fillcolor;
    }
}
