package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import org.opencv.core.Mat;
//import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import static org.opencv.imgcodecs.Imgcodecs.imread;


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
    String filename1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button button2 = (Button) findViewById(R.id.button2);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        // Example of a call to a native method

        //tv.setText(stringFromJNI());
        iv = findViewById(R.id.imageView7);
        re = findViewById(R.id.redeye);
        lu = findViewById(R.id.lightup);
        //oof = findViewById(R.id.oof);
        rp = findViewById(R.id.rp);
        //matrixFromJNI(bitmap);
        PermissionUtils.isGrantExternalRW(this,1);

    }



    public native void matrixFromJNI(Object bitmap);//白平衡实现函数
    public native void repair(Object bitmap);
    public native void lightup(Object bitmap);//亮度提高实现函数
    public native void redeye(Object bitmap,String CascadeFileName);


    public void OnClick(View v){
        switch (v.getId()){
            case R.id.rp_btn:
            {
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rp);//drawable.rp为需要的图片
                String strdecode="sdcard/rp.png";
                Bitmap bitmap = BitmapFactory.decodeFile(strdecode); //pathname
                System.out.println("图像修复");

                System.out.println(bitmap);
                bitmap.setHasAlpha(false);
                repair(bitmap);
                bitmap.setHasAlpha(false);
                //bitmap.setConfig(Bitmap.Config.RGB_565);

                strdecode="sdcard/rpoutput.jpg";
                Bitmap bitmap1 = BitmapFactory.decodeFile(strdecode);
                rp.setImageBitmap(bitmap1);
            }
            case R.id.bph_btn :
            {
                TextView tv = findViewById(R.id.sample_text);
                tv.setText("changed");//查看图片是否改变，用于初期实验用，可删除
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pic1);//drawable.pic1为白平衡需要的图片
                System.out.println("白平衡");
                System.out.println(bitmap);
                matrixFromJNI(bitmap);
                iv.setImageBitmap(bitmap);
                break;
            }
            case R.id.lu_btn:
            {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.lu);//drawable.lu为需要的图片

                System.out.println("亮度提升");

                System.out.println(bitmap);
                lightup(bitmap);
                lu.setImageBitmap(bitmap);

            }
            case R.id.re_btn:
            {
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.redeye);//drawable.lu为需要的图片
                Bitmap bitmap = BitmapFactory.decodeFile("sdcard/redeyepic.jpg");
                System.out.println("去红眼");

                System.out.println(bitmap);
                try{
                    InputStream is = getResources().openRawResource(R.raw.haarcascade_eye);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    File mCascadeFile = new File(cascadeDir, "raw/haarcascade_eye.xml");
                    FileOutputStream os = new FileOutputStream(mCascadeFile);


                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                        System.out.println("check");

                    }
                    is.close();
                    os.close();

                    redeye(bitmap,mCascadeFile.getAbsolutePath());
                    Bitmap bitmap1 = BitmapFactory.decodeFile("sdcard/output.jpg");
                    re.setImageBitmap(bitmap1);

                    cascadeDir.delete();
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println( "Failed to load cascade. Exception thrown: " + e);
                }
            }

        }
    }

    /*public native void outoffocus(Object bitmap);
    public void  onclickoof(View view)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.oof);//drawable.oof为需要的图片
        System.out.println("失焦恢复");
        System.out.println(bitmap);
        outoffocus(bitmap);
        //oof.setImageMatrix(bitmap);

        oof.setImageBitmap(bitmap);
    }*/


    //public native void repair(Object bitmap);
    public void  onclickrp(View view)
    {

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rp);//drawable.rp为需要的图片
        String strdecode="sdcard/rp.png";
        Bitmap bitmap = BitmapFactory.decodeFile(strdecode); //pathname
        System.out.println("图像修复");

        System.out.println(bitmap);
        bitmap.setHasAlpha(false);
        repair(bitmap);
        bitmap.setHasAlpha(false);
        //bitmap.setConfig(Bitmap.Config.RGB_565);

        strdecode="sdcard/rpoutput.jpg";
        Bitmap bitmap1 = BitmapFactory.decodeFile(strdecode);
        rp.setImageBitmap(bitmap1);
    }

    //public native void lightup(Object bitmap);//亮度提高实现函数
    public void onclicklightup(View view){//触发器
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.lu);//drawable.lu为需要的图片

        System.out.println("亮度提升");

        System.out.println(bitmap);
        lightup(bitmap);
        lu.setImageBitmap(bitmap);
    }

    //public native void redeye(Object bitmap,String CascadeFileName);
    public void onclickredeye(View view){//触发器
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.redeye);//drawable.lu为需要的图片
        Bitmap bitmap = BitmapFactory.decodeFile("sdcard/redeyepic.jpg");
        System.out.println("去红眼");

        System.out.println(bitmap);
        try{
            InputStream is = getResources().openRawResource(R.raw.haarcascade_eye);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "raw/haarcascade_eye.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);


            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("check");

            }
            is.close();
            os.close();

            redeye(bitmap,mCascadeFile.getAbsolutePath());
            Bitmap bitmap1 = BitmapFactory.decodeFile("sdcard/output.jpg");
            re.setImageBitmap(bitmap1);

            cascadeDir.delete();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println( "Failed to load cascade. Exception thrown: " + e);
        }

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
