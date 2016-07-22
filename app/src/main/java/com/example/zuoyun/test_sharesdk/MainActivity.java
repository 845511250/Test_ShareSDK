package com.example.zuoyun.test_sharesdk;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;

public class MainActivity extends Activity {
    ImageView imageView;
    String method= Facebook.NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this, "151d596959fc0");

        imageView =(ImageView) findViewById(R.id.iv_icon);

        findViewById(R.id.bt_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareToQzone();
            }
        });

        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginByqq();
            }
        });

        findViewById(R.id.bt_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        findViewById(R.id.bt_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform platform = ShareSDK.getPlatform(method);
                if(platform.isAuthValid())
                    platform.removeAccount();
            }
        });
    }

    public  void shareToQzone(){
        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.disableSSOWhenAuthorize();
        onekeyShare.setTitle("title");
        onekeyShare.setTitleUrl("https://www.baidu.com");
        onekeyShare.setText("first test share");
        onekeyShare.setImagePath("/storage/sdcard0/duoku/sdk/cache/18911.jpg");
        onekeyShare.setComment("评论示例");
        onekeyShare.setSite("来自360");
        onekeyShare.setSiteUrl("http://www.360.com/");

        onekeyShare.show(this);
    }

    public void loginByqq(){
        final Platform qqlogin = ShareSDK.getPlatform(method);
        qqlogin.SSOSetting(false);
        qqlogin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Iterator iter = hashMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();log(key.toString()+": "+val.toString());
                    }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qqlogin.showUser(null);
    }

    public void check(){
        Platform qqlogin = ShareSDK.getPlatform(method);
        if(qqlogin.isAuthValid()){
            log("已授权");
            log("username "+qqlogin.getDb().getUserName());
            Glide.with(this).load(qqlogin.getDb().getUserIcon()).into(imageView);
            log("ico "+qqlogin.getDb().getUserIcon());
            log("token "+qqlogin.getDb().getToken());
        }

        else
            log("未授权");
    }
    public  void log(String str){
        Log.e("log",str);
    }

}
