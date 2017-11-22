package com.fy.example.spring.beans.io;

import java.io.InputStream;

/**
 * Created by ya.fang on 2017/11/20.
 */
public interface Resource {
    InputStream getInputStream() throws Exception;
}
