package com.fy.example.spring.beans.io;

import java.net.URL;

/**
 * Created by ya.fang on 2017/11/20.
 */
public class ResourceLoader {

    public Resource getResource(String location){
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
