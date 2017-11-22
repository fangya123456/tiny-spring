package com.fy.example.spring.beans.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 加载各种传输协议的资源
 * Created by ya.fang on 2017/11/20.
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url){
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
