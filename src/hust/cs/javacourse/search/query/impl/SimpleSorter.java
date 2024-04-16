package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SimpleSorter类实现了Sort接口，提供了对搜索结果进行排序的功能。
 * 它使用Java的内置排序函数，根据搜索结果的得分进行降序排序。
 * 得分的计算方式是将搜索结果中所有单词的频率加起来。
 *
 * @author 晋晨曦
 */
public class SimpleSorter implements hust.cs.javacourse.search.query.Sort {
    /**
     * 对搜索结果进行排序的方法。使用Java的内置排序函数，根据搜索结果的得分进行降序排序。
     *
     * @param hits 搜索结果列表
     */
    @Override
    public void sort(List<AbstractHit> hits) {
        hits.sort(Collections.reverseOrder());
    }

    /**
     * 计算搜索结果的得分的方法。得分的计算方式是将搜索结果中所有单词的频率加起来。
     *
     * @param hit 搜索结果
     * @return 搜索结果的得分
     */
    @Override
    public double score(AbstractHit hit) {
        Map<AbstractTerm, AbstractPosting> termPostingMapping = hit.getTermPostingMapping();
        return termPostingMapping.values().stream().mapToDouble(AbstractPosting::getFreq).sum();
    }
}
