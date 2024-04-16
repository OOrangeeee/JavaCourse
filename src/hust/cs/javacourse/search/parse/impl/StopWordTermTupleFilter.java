package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.Arrays;
import java.util.List;

/**
 * StopWordTermTupleFilter类是AbstractTermTupleFilter的具体实现，用于过滤输入流中的停用词。
 * 它使用StopWords类中定义的停用词列表来检查每个词元组，如果词元组是停用词，那么继续读取下一个词元组，否则返回当前词元组。
 * 当输入流读取完毕时，next方法将返回null。
 *
 * @author 晋晨曦
 */
public class StopWordTermTupleFilter extends AbstractTermTupleFilter {
    private final List<String> stopWords = Arrays.asList(StopWords.STOP_WORDS);

    /**
     * 构造函数，接收一个AbstractTermTupleStream对象作为输入流
     *
     * @param input AbstractTermTupleStream对象，应该关联到一个文本文件
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 从输入流中读取下一个词元组，如果词元组是停用词，那么继续读取下一个词元组，否则返回当前词元组。
     * 如果输入流已经读取完毕，那么返回null。
     *
     * @return 下一个非停用词的词元组，如果输入流已经读取完毕，那么返回null
     */
    @Override
    public AbstractTermTuple next() {
        while (true) {
            AbstractTermTuple termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
            if (!stopWords.contains(termTuple.term.getContent())) {
                return termTuple;
            }
        }
    }
}
