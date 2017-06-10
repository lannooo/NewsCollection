package com.lannooo.newscollection;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

import static android.content.ContentValues.TAG;
public class MainActivity extends Activity {

    private StompClient mStompClient;
    private WebView webView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStompClient.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();

        //创建client 实例
        createStompClient();
        //订阅消息
        registerStompTopic();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init(){
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("http://127.0.0.1:8080/index");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void showMessage(final StompMessage stompMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showNotification(stompMessage.getPayload());
            }
        });
    }

    //创建client 实例
    private void createStompClient() {
        mStompClient = Stomp.over(WebSocket.class, "ws://127.0.0.1:8080/hello/websocket");
        mStompClient.connect();
        Toast.makeText(MainActivity.this,"开始连接 127.0.0.1:8080",Toast.LENGTH_SHORT).show();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.d(TAG, "Stomp connection opened");
                        toast("WebSocket连接已开启");
                        break;

                    case ERROR:
                        Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                        toast("WebSocket连接出错");
                        break;
                    case CLOSED:
                        Log.d(TAG, "Stomp connection closed");
                        toast("WebSocket连接关闭");
                        break;
                }
            }
        });
    }

    //订阅消息
    private void registerStompTopic() {
        mStompClient.topic("/topic/getResponse").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
            }
        });

    }

    private void showNotification(String responseMsg){
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        JSONObject object = null;
        String source="";
        String title="";
        try {
            object = new JSONObject(responseMsg);
            title = object.getString("title");
            source = object.getString("source");
        } catch (JSONException e) {
            e.printStackTrace();
            return ;
        }
        //获取PendingIntent
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        //创建 Notification.Builder 对象
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("新闻聚合系统-"+source+" 报道")
                .setContentText(title)
                .setContentIntent(mainPendingIntent)
                .setVibrate(vibrate);
        //发送通知
        mNotifyMgr.notify(3, builder.build());
    }

    private void toast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
