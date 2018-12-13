package com.example.lenovo.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import static android.view.View.*;


public class ContentActivity extends FragmentActivity{

    RadioGroup radioGroup;
    FragmentManager fragManager;
    RadioButton result;
    public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = this.getIntent();
        name = intent.getStringExtra("name");
        setResult(1,intent);

        fragManager = getSupportFragmentManager();
        //获取radioGroup控件
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        FragmentTransaction beginTransaction=fragManager.beginTransaction();
        TijianFragment fragment=new TijianFragment();
        beginTransaction.replace(R.id.fg_main,fragment);
        beginTransaction.commit();
        //监听点击按钮事件,实现不同Fragment之间的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragManager.beginTransaction();
                Fragment fragment = ChangeFragment.getInstanceByIndex(checkedId);
                transaction.replace(R.id.fg_main, fragment);
                transaction.commit();
            }
        });

        requestContact();
//        requestStorage();

    }
    protected void onResume() {
        int id = getIntent().getIntExtra("userloginflag", 0);

        if (id == 2 ) {
            FragmentTransaction beginTransaction=fragManager.beginTransaction();
            MyFragment fragment=new MyFragment();
            beginTransaction.replace(R.id.fg_main,fragment);
            beginTransaction.commit();
        }
        super.onResume();
    }

    //获取联系人权限
    private void requestContact() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
                public void permissionGranted(@NonNull String[] permissions) {
                    Toast.makeText(ContentActivity.this, "允许访问通讯录", Toast.LENGTH_SHORT).show();
                }
                public void permissionDenied(@NonNull String[] permissions) {
                    Toast.makeText(ContentActivity.this, "用户拒绝了访问通讯录", Toast.LENGTH_SHORT).show();
                }
            }, Manifest.permission.READ_CONTACTS);
        }
    }
//    private void requestStorage() {
//        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//        } else {
//            PermissionsUtil.requestPermission(this.getApplication(), new PermissionListener() {
//                public void permissionGranted(@NonNull String[] permissions) {
//                    Toast.makeText(ContentActivity.this, "允许访问访问相册", Toast.LENGTH_SHORT).show();
//                }
//
//                public void permissionDenied(@NonNull String[] permissions) {
//                    Toast.makeText(ContentActivity.this, "用户拒绝了访问相册", Toast.LENGTH_SHORT).show();
//                }
//            }, Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//    }


}
