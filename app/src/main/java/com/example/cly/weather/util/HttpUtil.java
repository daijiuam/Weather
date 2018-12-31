package com.example.cly.weather.util;


import com.example.cly.weather.ChooseAreaFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}*/
public class HttpUtil {
    public static void sendHttpRequest(final String address,final ChooseAreaFragment.HttpCallbackListener listener){
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod( "GET" );
                    connection.setConnectTimeout( 8000 );//8000毫秒
                    connection.setReadTimeout( 8000 );

                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader( new InputStreamReader( in ) );
                    StringBuilder response=new StringBuilder(  );
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append( line );
                    }
                    if(listener!=null){
                        listener.onFinish( response.toString() );
                    }
                }catch(Exception e){
                    listener.onError( e );
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        } ).start();
    }
}
