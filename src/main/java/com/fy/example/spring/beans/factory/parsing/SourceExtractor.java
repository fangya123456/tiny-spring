package com.fy.example.spring.beans.factory.parsing;

import com.fy.example.spring.beans.io.Resource;

/**
 * @author ya.fang
 * @create 2018/03/06
 **/
public interface SourceExtractor {

    Object extractSource(Object sourceCandidate, Resource definingResource);
}
