package com.reny.http;

import com.reny.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.testng.Reporter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Description: httpbase
 * @Author: reny
 * @CreateTime: 2020/10/20 16:13
 */
public class RetrofitFactory {
    private static Retrofit retrofit;

    private RetrofitFactory() {
        InputStream inputStream = RetrofitFactory.class.getClassLoader().getResourceAsStream("env.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String host = properties.getProperty("test.host");

        OkHttpClient okHttpClient = getClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

    }

    private <T> T getApiService(Class<T> clazz) {
        return retrofit.create(clazz);

    }

    private OkHttpClient getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(Constants.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(Constants.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
        return okHttpClient;
    }


    /**
     * 日志拦截器
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            public void log(String message) {
                Reporter.log("RetrofitLog--> " + message, true);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//Level中还有其他等级. 设置打印内容级别到Body。
        return logging;
    }

    // singleton
    public static RetrofitFactory getInstance() {
        return RetrofitInstance.retrofitFactory;
    }

    private static class RetrofitInstance {
        static RetrofitFactory retrofitFactory = new RetrofitFactory();
    }

}
