package com.fy.example.spring.beans.factory.parsing;

import com.fy.example.spring.beans.io.Resource;

/**
 * 获取BeanDefinition进程的上下文
 *
 * @author ya.fang
 * @create 2018/03/06
 **/
public class ReaderContext {

    private final Resource resource;

    private final SourceExtractor sourceExtractor;

    public ReaderContext(Resource resource, SourceExtractor sourceExtractor){
        this.resource = resource;
        this.sourceExtractor = sourceExtractor;
    }

    public final Resource getResource() {
        return this.resource;
    }

    public SourceExtractor getSourceExtractor() {
        return this.sourceExtractor;
    }

    public Object extractSource(Object sourceCandidate) {
        return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
    }

}
