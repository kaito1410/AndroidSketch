package ca.steven_zhu.a3paint;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.UUID;
import android.support.v4.content.ContextCompat;
import android.Manifest;

public class DrawCanvas extends AppCompatActivity{

    DrawView dv;
    private ImageButton currPaint, currBrush, currTool;
    private float small, medium, large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_canvas);

        } else {
            setContentView(R.layout.activity_canvas_land);
        }

        small = getResources().getInteger(R.integer.small_size);
        medium = getResources().getInteger(R.integer.medium_size);
        large = getResources().getInteger(R.integer.large_size);

        dv = (DrawView)findViewById(R.id.drawing);
        dv.setBackgroundColor(Color.WHITE);
        dv.setBrushSize(medium);

        dv.setLayoutParams(new LinearLayout.LayoutParams(656, 656));

        if (savedInstanceState != null && savedInstanceState.containsKey("allshape")) {
            ArrayList<MyShape> temp = new ArrayList<MyShape>();
            temp = savedInstanceState.getParcelableArrayList("allshape");
            dv.setallShapes(temp);
        }

        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));

        LinearLayout brushLayout = (LinearLayout)findViewById(R.id.brush_chooser);
        currBrush = (ImageButton)brushLayout.getChildAt(1);

        LinearLayout toolLayout = (LinearLayout)findViewById(R.id.tool_chooser);
        currTool = (ImageButton)toolLayout.getChildAt(1);
        currTool.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("allshape", dv.getallShapes());
        super.onSaveInstanceState(outState);
    }

    public void paintClicked(View view){
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            dv.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.btn_unpressed));
            currPaint=(ImageButton)view;

        }
    }

    public void brushClicked(View view){
        if(view!=currBrush) {
            ImageButton imgView = (ImageButton)view;
            String brushSize = view.getTag().toString();
            currBrush = (ImageButton)view;

            if (brushSize.equals("small")) {
                dv.setBrushSize(small);
            } else if (brushSize.equals("medium")) {
                dv.setBrushSize(medium);
            } else {
                dv.setBrushSize(large);
            }
        }
    }

    public void toolClicked(View view){
        if(view!=currTool) {
            ImageButton imgView = (ImageButton)view;
            String toolselect = view.getTag().toString();
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));
            currTool.setImageDrawable(getResources().getDrawable(R.drawable.btn_unpressed));
            currTool = (ImageButton)view;

            if (toolselect.equals("select")) {
                dv.setToolType(0);
            } else if (toolselect.equals("line")) {
                dv.setToolType(1);
            } else if (toolselect.equals("circle")) {
                dv.setToolType(2);
            } else if (toolselect.equals("rectangle")) {
                dv.setToolType(3);
            } else if (toolselect.equals("erase")) {
                dv.setToolType(4);
            } else {
                dv.setToolType(5);
            }
        }
    }

    public void saveCanvas(View view) {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                int permissionCheck = ContextCompat.checkSelfPermission(DrawCanvas.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DrawCanvas.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    }
                    else {
                        ActivityCompat.requestPermissions(DrawCanvas.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                        );
                    }
                }
                dv.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), dv.getDrawingCache(),
                        UUID.randomUUID().toString() + ".png", "Shapes");
                dv.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

}
