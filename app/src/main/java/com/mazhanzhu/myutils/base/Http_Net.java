package com.mazhanzhu.myutils.base;

import com.mazhanzhu.utils.Log_Ma;

import java.io.File;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.collection.SimpleArrayMap;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/21 14:09
 * Desc   : 网络请求
 */
public class Http_Net {
    public static final String TAG = "";
    //使用volatile关键字保其可见性
    volatile private static Http_Net instance = null;
    private SimpleArrayMap<String, List<Cookie>> map = new SimpleArrayMap<>();

    private Http_Net() {
    }

    public static Http_Net getInstance() {
        try {
            if (instance == null) {//懒汉式
                //创建实例之前可能会有一些准备性的耗时工作
                Thread.sleep(300);
                synchronized (Http_Net.class) {
                    if (instance == null) {//二次检查
                        instance = new Http_Net();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 缓存文件
     */
    private final String RESPONSE_CACHE_FILE = "responseCache";
    /**
     * 缓存大小
     */
    private final long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L;

    public Impl_DayTxt getDayTxt() {
        Impl_DayTxt netInterface = getRetrofit("https://v1.hitokoto.cn/").create(Impl_DayTxt.class);//NetInterface为接口定义类
        return netInterface;
    }

    private Retrofit getRetrofit(String baseUrl) {
        /*BaseUrl 一般网络请求地址里面，总会有一样得前缀，就像是
        http://192.168.20.241:11008****这就是那个基地址，基地址+NetInterface类里面的
        post（）的地址就是一个完整的网络请求地址，这点要注意*/
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)//添加网络请求的基地址
                .addConverterFactory(ScalarsConverterFactory.create())//增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())//添加转换工厂，用于解析json并转化为javaBean
                .client(getHttpClient())//设置参数
                .build();
        return mRetrofit;
    }


    //配置网络请求参数
    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLogging interceptor = new HttpLogging("mzz");
        interceptor.setColorLevel(Level.INFO);
        if (BaseApp.isApkInDebug()) {//是否是Debug模式
            interceptor.setPrintLevel(HttpLogging.Level.BODY);
            Log_Ma.setIsShow(true);
            builder.readTimeout(180, TimeUnit.SECONDS);
            builder.writeTimeout(180, TimeUnit.SECONDS);
            builder.connectTimeout(180, TimeUnit.SECONDS);
        } else {
            interceptor.setPrintLevel(HttpLogging.Level.NONE);
            Log_Ma.setIsShow(false);
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);
            builder.connectTimeout(60, TimeUnit.SECONDS);
        }
        File cacheFile = new File(BaseApp.getContext().getCacheDir(), RESPONSE_CACHE_FILE);//缓存文件
        builder.cache(new Cache(cacheFile, RESPONSE_CACHE_SIZE))//设置用于读取和写入缓存的响应的响应缓存
                .sslSocketFactory(getSSLSocketFactory(), new CustomTrustManager())//信任所有https请求
                .hostnameVerifier(getHostnameVerifier())//设置用于确认响应证书应用于HTTPS连接请求的主机名的验证器。
                .cookieJar(new CookieJar() {

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        map.put(url.toString(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = map.get(url.toString());
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })

                .addInterceptor(interceptor)//使用日志拦截器
                .proxy(Proxy.NO_PROXY);//不使用代理的模式

        OkHttpClient client = builder.build();
        return client;
    }

    private SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    private class CustomTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = (hostname, session) -> true;
        return hostnameVerifier;
    }

    public static Map<String, String> getMap() {
        return new HashMap<>();
    }
}
