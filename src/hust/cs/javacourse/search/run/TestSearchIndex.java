package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.query.impl.IndexPhraseSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;

import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**
 * 测试搜索
 *
 * @author 晋晨曦
 */
public class TestSearchIndex {
    /**
     * 搜索程序入口
     *
     * @param args ：命令行参数
     */
    public static void main(String[] args) {
        Sort simpleSorter = new SimpleSorter();
        String indexFile = Config.INDEX_DIR + "index.dat";
        AbstractIndexSearcher searcher = new IndexSearcher();
        searcher.open(indexFile);
        AbstractHit[] hits = searcher.search(new Term("ggg"), new Term("aaa"), simpleSorter, AbstractIndexSearcher.LogicalCombination.OR);
        for (AbstractHit hit : hits) {
            System.out.println(hit);
        }

        System.out.println("====================================");

        AbstractIndexSearcher phraseSearcher = new IndexPhraseSearcher(new IndexSearcher());
        phraseSearcher.open(indexFile);
        hits = phraseSearcher.search(new Term("aaa"), new Term("bbb"), simpleSorter, AbstractIndexSearcher.LogicalCombination.AND);
        for (AbstractHit hit : hits) {
            System.out.println(hit);
        }
    }
}
