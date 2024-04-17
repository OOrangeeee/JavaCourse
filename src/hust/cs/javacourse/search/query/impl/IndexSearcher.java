package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

/**
 * IndexSearcher类是AbstractIndexSearcher的具体实现。
 * 它提供了根据一个或两个查询词来搜索索引，并返回排序后的命中结果的功能。
 *
 * @author 晋晨曦
 */
public class IndexSearcher extends AbstractIndexSearcher {
    /**
     * 打开索引文件并加载索引。
     *
     * @param indexFile 索引文件的路径
     */
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
    }

    /**
     * 根据一个查询词搜索索引，并返回排序后的命中结果。
     *
     * @param queryTerm 查询词
     * @param sorter    排序器
     * @return 排序后的命中结果
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = index.search(queryTerm);
        if (postingList == null) {
            return new AbstractHit[0];
        }
        AbstractHit[] hits = new AbstractHit[postingList.size()];
        for (int i = 0; i < postingList.size(); i++) {
            AbstractPosting posting = postingList.get(i);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm, posting);
            hits[i] = new Hit(posting.getDocId(), index.getDocName(posting.getDocId()), termPostingMapping);
            hits[i].setScore(sorter.score(hits[i]));
        }
        List<AbstractHit> hitList = Arrays.asList(hits);
        sorter.sort(hitList);
        hits = hitList.toArray(hits);
        return hits;
    }

    /**
     * 根据两个查询词和一个逻辑组合方式搜索索引，并返回排序后的命中结果。
     *
     * @param queryTerm1 第一个查询词
     * @param queryTerm2 第二个查询词
     * @param sorter     排序器
     * @param combine    逻辑组合方式
     * @return 排序后的命中结果
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);
        if (combine == LogicalCombination.AND) {
            Map<Integer, Map<AbstractTerm, AbstractPosting>> result = intersect(queryTerm1, queryTerm2, postingList1, postingList2);
            return getHits(sorter, result);
        } else {
            Map<Integer, Map<AbstractTerm, AbstractPosting>> result = union(queryTerm1, queryTerm2, postingList1, postingList2);
            return getHits(sorter, result);
        }
    }

    private AbstractHit[] getHits(Sort sorter, Map<Integer, Map<AbstractTerm, AbstractPosting>> result) {
        if (result.isEmpty()) {
            return new AbstractHit[0];
        }
        AbstractHit[] hits = new AbstractHit[result.size()];
        List<Map.Entry<Integer, Map<AbstractTerm, AbstractPosting>>> entryList = new ArrayList<>(result.entrySet());
        for (int i = 0; i < hits.length; i++) {
            Map.Entry<Integer, Map<AbstractTerm, AbstractPosting>> entry = entryList.get(i);
            hits[i] = new Hit(entry.getKey(), index.getDocName(entry.getKey()), entry.getValue());
            hits[i].setScore(sorter.score(hits[i]));
        }
        List<AbstractHit> hitList = Arrays.asList(hits);
        sorter.sort(hitList);
        hits = hitList.toArray(hits);
        return hits;
    }

    private Map<Integer, Map<AbstractTerm, AbstractPosting>> intersect(AbstractTerm queryTerm1, AbstractTerm queryTerm2, AbstractPostingList postingList1, AbstractPostingList postingList2) {
        Map<Integer, Map<AbstractTerm, AbstractPosting>> result = new HashMap<>();
        int i = 0, j = 0;
        while (i < postingList1.size() && j < postingList2.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            AbstractPosting posting2 = postingList2.get(j);
            if (posting1.getDocId() == posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
                j++;
            } else if (posting1.getDocId() < posting2.getDocId()) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    private Map<Integer, Map<AbstractTerm, AbstractPosting>> union(AbstractTerm queryTerm1, AbstractTerm queryTerm2, AbstractPostingList postingList1, AbstractPostingList postingList2) {
        Map<Integer, Map<AbstractTerm, AbstractPosting>> result = new HashMap<>();
        int i = 0, j = 0;
        while (i < postingList1.size() && j < postingList2.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            AbstractPosting posting2 = postingList2.get(j);
            if (posting1.getDocId() == posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
                j++;
            } else if (posting1.getDocId() < posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
            } else {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting2.getDocId(), termPostingMapping);
                j++;
            }
        }
        while (i < postingList1.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm1, posting1);
            result.put(posting1.getDocId(), termPostingMapping);
            i++;
        }
        while (j < postingList2.size()) {
            AbstractPosting posting2 = postingList2.get(j);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm2, posting2);
            result.put(posting2.getDocId(), termPostingMapping);
            j++;
        }
        return result;
    }
}
