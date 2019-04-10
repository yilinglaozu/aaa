package com.bawei.myappl56;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import net.AsyncTaskk;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import bean.HomeBean;
import fragment.Afragment;
import fragment.Bfragment;
import fragment.Cfragment;
import fragment.Dfragment;
import httpUrl.NetUrl;

public class MainActivity extends BaseActivity {
    //拍照 ResponseCode
    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库


    //拍照时图片的url
    private Uri imageUri;
    private String filePath;

    private DrawerLayout drawerLayout;
    private ImageView imageView,ceimage;
    private ViewPager viewPager;
    private List<Fragment> list=new ArrayList<>();
    private RadioGroup radioGroup;


    @Override
    protected int initLayput() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //点击拍照
        ceimage=findViewById(R.id.ceimage);



        //
        viewPager=findViewById(R.id.main_pager);
        radioGroup=findViewById(R.id.radiogroup);

        list.add(new Afragment());
        list.add(new Bfragment());
        list.add(new Cfragment());
        list.add(new Dfragment());
        //点击图片弹窗 拍照
        ceimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] item = {"拍照", "图库"};
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // 选择了拍照
                                        // 创建保存照片的文件
                                        File takePhoneImage = new File(Environment.getExternalStorageDirectory(),
                                                "take_photo_image.jpg");
                                        try {
                                            // 如果文件存在删除
                                            if (takePhoneImage.exists()) {
                                                takePhoneImage.delete();
                                            }
                                            takePhoneImage.createNewFile();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        imageUri = Uri.fromFile(takePhoneImage);
                                        filePath = imageUri.getPath();
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                        startActivityForResult(intent, TAKE_PHOTO);
                                        break;
                                    case 1: // 调用系统图库
                                        Intent intent1 = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent1, LOCAL_CROP);
                                        break;
                                }
                            }
                        })
                        .show();

               /* Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);*/

            }


        });

        //第一个viewpager+fragment的适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int i, float v, int i1) {

           }

           @Override
           public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radioGroup.check(R.id.btn1);
                        break;
                    case 1:
                        radioGroup.check(R.id.btn2);
                        break;
                    case 2:
                        radioGroup.check(R.id.btn3);
                        break;
                    case 3:
                        radioGroup.check(R.id.btn4);
                        break;
                }
           }

           @Override
           public void onPageScrollStateChanged(int i) {


           }
       });
       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.btn1:
                       viewPager.setCurrentItem(0);
                       break;
                   case R.id.btn2:
                       viewPager.setCurrentItem(1);
                       break;
                   case R.id.btn3:
                       viewPager.setCurrentItem(2);
                       break;
                   case R.id.btn4:
                       viewPager.setCurrentItem(2);
                       break;
               }
           }
       });
        drawerLayout=findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });




        //图片
        imageView=findViewById(R.id.main_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.openDrawer(Gravity.START);
                }else{
                    drawerLayout.closeDrawer(Gravity.START);
                }
            }
        });
        //圆形图
        Glide.with(this).load(R.mipmap.dog)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
        //填充布局
        getSupportFragmentManager().beginTransaction().replace(R.id.main_pager,new Bfragment()).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:  // 拍照
                if (resultCode == RESULT_OK) {
                    // 创建intent用于裁剪图片
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // 设置数据为文件uri，类型为图片格式
                    intent.setDataAndType(imageUri, "image/*");
                    // 允许裁剪
                    intent.putExtra("scale", true);
                    // 指定输出到文件uri中
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent, CROP_PHOTO);
                }
                //让系统重新扫描SDCard某个文件，相册里也会马上显示
             /*   Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
                intent.setData(uri);
                Context.sendBroadcast(intent);*/
                break;
            case LOCAL_CROP:  // 系统图库
                if (resultCode == RESULT_OK) {
                    // 创建intent用于裁剪图片
                    Intent intent1 = new Intent("com.android.camera.action.CROP");
                    // 获取图库所选图片的uri
                    Uri uri = data.getData();
                    intent1.setDataAndType(uri, "image/*");
                    //  设置裁剪图片的宽高
                    intent1.putExtra("outputX", 300);
                    intent1.putExtra("outputY", 300);
                    // 裁剪后返回数据
                    intent1.putExtra("return-data", true);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent1, CROP_PHOTO);
                }
            case CROP_PHOTO:   // 裁剪后
                if (resultCode == RESULT_OK) {
                    try {
                        // 展示拍照后裁剪的图片
                        if (imageUri != null) {
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            ceimage.setImageBitmap(bitmap);
                            imageUri = null;
                            //让系统重新扫描SDCard某个文件，相册里也会马上显示
                           /* Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
                            intent.setData(uri);
                            Context.sendBroadcast(intent);*/

                            return ;
                        }

                        // 展示图库中选择裁剪后的图片
                        if (data != null) {
                            // 根据返回的data，获取Bitmap对象
                            Bitmap bitmap = data.getExtras().getParcelable("data");
                            // 展示图片
                            ceimage.setImageBitmap(bitmap);
                            data = null;
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void upload(View view) {
        upload();
    }

    private static final String nextLine = "\r\n";
    private static final String twoHyphens = "--";
    //分割线  随便写一个
    private static final String boundary = "wk_file_2519775";

    /**
     * 上传
     */
    //diyige 第二个点击事件
    public void upload() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                File file = new File(filePath);
                HttpURLConnection connection = null;
                try {
                    URL url1 = new URL("http://169.254.5.80:8080/upload");
                    connection = (HttpURLConnection) url1.openConnection();

                    connection.setDoOutput(true);
                    // 设置字符集
                    connection.setRequestProperty("Charsert", "UTF-8");
                    //设置接收编码
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    //开启长连接可以持续传输
                    connection.setRequestProperty("Connection", "keep-alive");
                    //设置请求参数格式以及boundary分割线
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    //设置接收返回值的格式
                    connection.setRequestProperty("Accept", "application/json");

                    OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    //分隔符头部
                    String header = twoHyphens + boundary + nextLine;
                    //分隔符参数设置
                    header += "Content-Disposition: form-data;name=\"file\";" + "filename=\"" + file.getName() + "\"" + nextLine + nextLine;
                    //写入输出流
                    outputStream.write(header.getBytes());


                    //读取文件并写入
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, length);
                    }
                    //文件写入完成后加回车
                    outputStream.write(nextLine.getBytes());

                    //写入结束分隔符
                    String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
                    outputStream.write(footer.getBytes());
                    outputStream.flush();

                    if (connection.getResponseCode() == 200) {
                        return CharStreams.toString(new InputStreamReader(connection.getInputStream()));
                    }

                }  catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i("TAGGG",s);
            }
        }.execute();
    }
    @Override
    protected void initData() {

        //网络获取数据
/*
        AsyncTaskk.getInstance().getData(NetUrl.IP + NetUrl.APP + NetUrl.NEWSAPI, new AsyncTaskk.AsyncCallback() {
            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                HomeBean homeBean = gson.fromJson(result, HomeBean.class);

            }
        });
*/

    }
}
