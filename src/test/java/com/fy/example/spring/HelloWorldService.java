package com.fy.example.spring;

/**
 * @author yihua.huang@dianping.com
 */
public class HelloWorldService {
    private OutputService outputService;
    private String text;

    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void helloWorld(){
        outputService.output(text);
    }

}
