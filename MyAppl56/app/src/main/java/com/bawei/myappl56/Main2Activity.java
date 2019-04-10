package com.bawei.myappl56;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.common.io.CharStreams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main2Activity extends AppCompatActivity {
    private ImageView ivHead;

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Uri imageUri; // 拍照时的图片Uri
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0x00);
        }

        ivHead = findViewById(R.id.iv_head);
    }
    //diyige 第一个点击事件
    public void selectHead(View view) {
        takePhoneOrSelectPicture();
    }

    public void takePhoneOrSelectPicture() {
        CharSequence[] item = {"拍照", "图库"};
        new AlertDialog.Builder(this)
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 选择了拍照
                                // 创建保存照片的文件
                                File takePhoneImage = new File(Environment.getExternalStorageDirectory(), "take_photo_image.jpg");
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
                            ivHead.setImageBitmap(bitmap);
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
                            ivHead.setImageBitmap(bitmap);
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
}