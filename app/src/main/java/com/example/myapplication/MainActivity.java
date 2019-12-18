package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;//白平衡
    private ImageView lu;//亮度提升
    private ImageView re;//红眼
    private ImageView oof;//失焦
    private ImageView rp;//图像修复
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

public static Bitmap toGrayscale(Bitmap bmpOriginal) {
    int width, height;
    height = bmpOriginal.getHeight();
    width = bmpOriginal.getWidth();

    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    Canvas c = new Canvas(bmpGrayscale);
    Paint paint = new Paint();
    ColorMatrix cm = new ColorMatrix();
    cm.setSaturation(0);
    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
    paint.setColorFilter(f);
    c.drawBitmap(bmpOriginal, 0, 0, paint);
    return bmpGrayscale;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2 = (Button) findViewById(R.id.button2);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        // Example of a call to a native method

        //tv.setText(stringFromJNI());
        iv = findViewById(R.id.imageView7);
        //re = findViewById(R.id.redeye);
        lu = findViewById(R.id.lightup);
        //oof = findViewById(R.id.oof);
        rp = findViewById(R.id.rp);
        //matrixFromJNI(bitmap);
    }

    public native void matrixFromJNI(Object bitmap);//白平衡实现函数

    public void OnClick(View view){
        if(view.getId()==R.id.button2)
        {
            TextView tv = findViewById(R.id.sample_text);
            tv.setText("changed");//查看图片是否改变，用于初期实验用，可删除
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pic1);//drawable.pic1为白平衡需要的图片
            System.out.println("白平衡");
            System.out.println(bitmap);
            matrixFromJNI(bitmap);
            iv.setImageBitmap(bitmap);
        }
    }

    //public native void outoffocus(Object bitmap);
    /*public void  onclickoof(View view)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.oof);//drawable.oof为需要的图片
        toGrayscale(bitmap);
        System.out.println("失焦恢复");
        System.out.println(bitmap);
        outoffocus(bitmap);
        //oof.setImageMatrix(bitmap);

        oof.setImageBitmap(bitmap);
    }*/


    public native void repair(Object bitmap);
    public void  onclickrp(View view)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rp,opts);//drawable.rp为需要的图片
        System.out.println("图像修复");

        System.out.println(bitmap);
        bitmap.setHasAlpha(false);
        repair(bitmap);
        bitmap.setHasAlpha(false);
        //bitmap.setConfig(Bitmap.Config.RGB_565);

        rp.setImageBitmap(bitmap);
    }

    public native void lightup(Object bitmap);//亮度提高实现函数
    public void onclicklightup(View view){//触发器
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.lu);//drawable.lu为需要的图片
        System.out.println("亮度提升");

        System.out.println(bitmap);
        lightup(bitmap);
        lu.setImageBitmap(bitmap);
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
