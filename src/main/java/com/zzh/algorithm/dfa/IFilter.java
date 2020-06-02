package com.zzh.algorithm.dfa;

import java.util.List;

/**
 * @author zhazhahui
 * Created on 2020/6/2
 */
public interface IFilter {

    boolean match(String input);


    List<String> getMatchResult(String input);

    /**
     * 对于input的子串[lengthFloor, lengthCeiling]， 进行match()
     *
     * @param input
     * @param lengthFloor   包括。不限制传时null。
     * @param lengthCeiling 包括。不限制传时null。
     * @return
     */
    List<String> getMatchResult(String input, Integer lengthFloor, Integer lengthCeiling);

    String replaceAll(String input, String replacement);

    /**
     * 对于input的子串[lengthFloor, lengthCeiling]， 进行replaceAll()
     *
     * @param input
     * @param replacement
     * @param lengthFloor   包括。不限制传时null。
     * @param lengthCeiling 包括。不限制传时null。
     * @return
     */
    String replaceAll(String input, String replacement, Integer lengthFloor, Integer lengthCeiling);
}
