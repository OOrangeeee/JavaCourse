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
 * @author 晋晨曦
 */
public class IndexPhraseSearcher extends AbstractIndexSearcher {

    private final AbstractIndexSearcher searcherInput;

    public IndexPhraseSearcher(AbstractIndexSearcher searcherInput) {
        this.searcherInput = searcherInput;
    }

    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
        searcherInput.open(indexFile);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        return searcherInput.search(queryTerm, sorter);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        if (combine == LogicalCombination.OR) {
            return null;
        }
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);
        if (postingList1 == null || postingList2 == null) {
            return new AbstractHit[0];
        }
        Map<Integer, Map<AbstractTerm, AbstractPosting>> result = new HashMap<>();
        int i = 0, j = 0;
        while (i < postingList1.size() && j < postingList2.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            AbstractPosting posting2 = postingList2.get(j);
            if (posting1.getDocId() == posting2.getDocId()) {
                List<Integer> positions1 = posting1.getPositions();
                List<Integer> positions2 = posting2.getPositions();
                for (int pos1 : positions1) {
                    if (positions2.contains(pos1 + 1)) {
                        Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                        termPostingMapping.put(queryTerm1, posting1);
                        termPostingMapping.put(queryTerm2, posting2);
                        result.put(posting1.getDocId(), termPostingMapping);
                        break;
                    }
                }
                i++;
                j++;
            } else if (posting1.getDocId() < posting2.getDocId()) {
                i++;
            } else {
                j++;
            }
        }
        return getHits(sorter, result);
    }

    private AbstractHit[] getHits(Sort sorter, Map<Integer, Map<AbstractTerm, AbstractPosting>> result) {
        sorter = new PhraseSort(sorter);
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

}
