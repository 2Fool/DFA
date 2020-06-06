package com.zzh.algorithm.dfa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhazhahui
 * Created on 2020/6/5
 */
public class DfaDemo {

    @Bean
    public DfaFilter foodStoreDFAFilter() {
        return DfaFilter.fromWordSet(new HashSet<>());
    }

    //接入
    @Autowired
    DfaFilter foodStoreDFAFilter;

    public void demo(){
        //匹配到的敏感词
        Set<String> keywordSet = foodStoreDFAFilter.getSensitiveWord("包含敏感词的句子");
    }


}
