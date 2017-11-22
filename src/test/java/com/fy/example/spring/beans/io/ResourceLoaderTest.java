package com.fy.example.spring.beans.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author yihua.huang@dianping.com
 */
public class ResourceLoaderTest {

	@Test
	public void test() throws Exception {
		ResourceLoader resourceLoader = new ResourceLoader();
        Resource resource = resourceLoader.getResource("tiny-spring.xml");
        InputStream inputStream = resource.getInputStream();
        Assert.assertNotNull(inputStream);
    }
}
