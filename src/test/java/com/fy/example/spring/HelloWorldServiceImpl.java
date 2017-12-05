package com.fy.example.spring;

/**
 * Created by ya.fang on 2017/11/27.
 */
public class HelloWorldServiceImpl  implements HelloWorldService{

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
