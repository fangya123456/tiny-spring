package com.fy.example.spring.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ya.fang
 * @date 2017/11/20
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
