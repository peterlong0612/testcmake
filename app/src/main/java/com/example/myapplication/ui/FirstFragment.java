package com.example.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.PermissionUtils;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class FirstFragment extends MainActivity {
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




}
