package com.example.admin.geocodingproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NativeClient extends Thread
{
    final static String TAG = "TEST_TAG";

    String BASE_URL;
    Handler handler;

    public NativeClient(String BASE_URL, Handler handler)
    {
        this.BASE_URL = BASE_URL;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            Scanner scanner = new Scanner(inputStream);

            StringBuilder builder = new StringBuilder();

            while (scanner.hasNext())
            {
                builder.append(scanner.nextLine());
                //Log.d(TAG, "run" + scanner.nextLine());
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("content", builder.toString());
            message.setData(bundle);
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            //handler.onError();
            e.printStackTrace();
        } catch (IOException e) {
            //handler.onError();
            e.printStackTrace();
        }
    }
}
