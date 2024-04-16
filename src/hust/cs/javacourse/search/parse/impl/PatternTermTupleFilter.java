package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

/**
 * PatternTermTupleFilter类是AbstractTermTupleFilter的具体实现，用于过滤输入流中不匹配正则表达式的词元组。
 * 它使用Config类中定义的正则表达式来检查每个词元组，如果词元组不匹配正则表达式，那么继续读取下一个词元组，否则返回当前词元组。
 * 当输入流读取完毕时，next方法将返回null。
 *
 * @author 晋晨曦
 */
public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数，接收一个AbstractTermTupleStream对象作为输入流
     *
     * @param input AbstractTermTupleStream对象，应该关联到一个文本文件
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 从输入流中读取下一个词元组，如果词元组不匹配正则表达式，那么继续读取下一个词元组，否则返回当前词元组。
     * 如果输入流已经读取完毕，那么返回null。
     *
     * @return 下一个匹配正则表达式的词元组，如果输入流已经读取完毕，那么返回null
     */
    @Override
    public AbstractTermTuple next() {
        while (true) {
            AbstractTermTuple termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
            if (termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
                return termTuple;
            }
        }
    }
}
