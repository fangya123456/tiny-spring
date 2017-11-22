package com.fy.example.spring.beans.xml;

import com.fy.example.spring.beans.BeanDefinition;
import com.fy.example.spring.beans.io.ResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("tiny-spring.xml");
		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
		Assert.assertTrue(registry.size() > 0);
	}
}
