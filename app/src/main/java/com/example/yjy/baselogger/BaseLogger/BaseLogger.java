package com.example.yjy.baselogger.BaseLogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yjy on 2018/2/3.
 */

public class BaseLogger {

    private static BaseLogger logger;
    private static BaseLog baseLog;
    private static boolean isOpen = false;
    private LoggerWriteLock writeLock;
    private static ThreadPoolExecutor executor;
    private int CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private int MAX_COUNT = 20;
    private Context context;
    private String url="";


    private BaseLogger(){
        writeLock = new LoggerWriteLock();
        executor = new ThreadPoolExecutor(5, MAX_COUNT,
                5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    public static BaseLogger getInstance(){
        if(logger == null){
            synchronized (BaseLogger.class){
                if(logger == null){
                    logger = new BaseLogger();
                }
            }
        }
        return logger;
    }

    public void init(Context context,BaseLog logger){
        this.baseLog = logger;
        this.context = context;
    }

    public static void openDebug(){
        isOpen = true;
    }

    public void closeDebug(){
        isOpen = false;
    }

    public static void e(final String tag,final String msg){
        if(isOpen&& baseLog !=null){
            baseLog.e(tag,msg);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.saveFileAndUpload(tag+" :"+msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

        }
    }



    public static void w(final String tag,final String msg){
        if(isOpen&& baseLog !=null){
            baseLog.w(tag,msg);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.saveFileAndUpload(tag+" :"+msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

        }
    }


    public static void i(final String tag,final String msg){
        if(isOpen&& baseLog !=null){
            baseLog.i(tag,msg);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.saveFileAndUpload(tag+" :"+msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });
        }
    }

    public static void d(final String tag,final String msg){
        if(isOpen&& baseLog !=null){
            baseLog.d(tag,msg);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.saveFileAndUpload(tag+" :"+msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });
        }
    }



    public void saveFileAndUpload(String msg) throws IOException,InterruptedException{
        writeLock.writeLock();
        if(context != null){
            File dir = context.getDir("upload",Context.MODE_APPEND);
            if(!dir.exists()){
                dir.mkdirs();
            }
            Log.e("path",dir.getAbsolutePath());

            File file = new File(dir.getAbsolutePath(),"log1.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter out = new FileWriter(file,true);
            try {
                out.write("\r\n");
                out.write(msg);
                out.flush();
            }finally {
                out.close();
            }
            uploadFile(file);

        }
        //upload 使用okhttp upload


        writeLock.writeUnLock();
    }

    public void uploadFile(File file){
        if(!TextUtils.isEmpty(url)){
            OkHttpClient httpClient = new OkHttpClient();
            // 构建请求 Body , 这个我们之前自己动手写过
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            builder.addFormDataPart("platform", "android");
            builder.addFormDataPart("file", file.getName(),
                    RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
            UploadBody downloadbody = new UploadBody(builder.build());
            final Request request = new Request.Builder()
                    .url(url)
                    .post(downloadbody).build();
            // new RealCall 发起请求
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("TAG", response.body().string());
                }
            });

        }

    }

    private String guessMimeType(String absolutePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(absolutePath);
        if(TextUtils.isEmpty(mimeType)){
            mimeType = "application/octet-stream";
        }
        return mimeType;

    }





}
