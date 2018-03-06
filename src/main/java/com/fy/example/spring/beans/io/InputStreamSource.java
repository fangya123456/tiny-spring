package com.fy.example.spring.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流接口
 *
 * @author ya.fang
 * @create 2018/01/15
 **/
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
