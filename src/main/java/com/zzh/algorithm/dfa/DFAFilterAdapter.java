package com.zzh.algorithm.dfa;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhazhahui
 * Created on 2020/6/2
 */
public class DFAFilterAdapter implements IFilter {
    DfaFilter filter;
    String defaultReplacement;

    public DFAFilterAdapter(Collection<String> collection, String defaultReplacement) {
        this(DfaFilter.fromWordSet(new HashSet<>(collection)), defaultReplacement);
    }

    public DFAFilterAdapter(DfaFilter filter) {
        this(filter, null);
    }

    public DFAFilterAdapter(DfaFilter filter, String defaultReplacement) {
        this.filter = filter;
        this.defaultReplacement = defaultReplacement;
    }

    @Override
    public boolean match(String input) {
        return filter.contains(input);
    }

    @Override
    public List<String> getMatchResult(String input) {
        return filter.getSensitiveWordList(input);
    }

    @Override
    public String replaceAll(String input, String replacement) {
        if (replacement == null) {
            replacement = defaultReplacement;
        }
        return filter.replaceSensitiveWord(input, replacement);
    }

    @Override
    public List<String> getMatchResult(String input, Integer lengthFloor, Integer lengthCeiling) {
        if (lengthFloor == null) {
            lengthFloor = -1;
        }
        if (lengthCeiling == null) {
            lengthCeiling = -1;
        }
        // DFAFilterAdapter lengthFloor(included) <==> DFAFilter lengthFloor - 1(excluded)
        return filter.getSensitiveWordList(input, DfaFilter.MaxMatchType, lengthFloor - 1, lengthCeiling);
    }

    @Override
    public String replaceAll(String input, String replacement, Integer lengthFloor, Integer lengthCeiling) {
        if (replacement == null) {
            replacement = defaultReplacement;
        }

        List<String> matchResults = getMatchResult(input, lengthFloor, lengthCeiling);
        for (String matchResult : matchResults) {
            input = input.replaceAll(matchResult, replacement);
        }
        return input;
    }

}
