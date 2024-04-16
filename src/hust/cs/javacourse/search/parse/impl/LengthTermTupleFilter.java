package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

/**
 * LengthTermTupleFilter类是AbstractTermTupleFilter的具体实现，用于过滤输入流中长度不在指定范围内的词元组。
 * 它使用Config类中定义的最小长度和最大长度来检查每个词元组，如果词元组的长度不在这个范围内，那么继续读取下一个词元组，否则返回当前词元组。
 * 当输入流读取完毕时，next方法将返回null。
 *
 * @author 晋晨曦
 */
public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数，接收一个AbstractTermTupleStream对象作为输入流
     *
     * @param input AbstractTermTupleStream对象，应该关联到一个文本文件
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 从输入流中读取下一个词元组，如果词元组的长度不在指定范围内，那么继续读取下一个词元组，否则返回当前词元组。
     * 如果输入流已经读取完毕，那么返回null。
     *
     * @return 下一个长度在指定范围内的词元组，如果输入流已经读取完毕，那么返回null
     */
    @Override
    public AbstractTermTuple next() {
        while (true) {
            AbstractTermTuple termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
            if (termTuple.term.getContent().length() >= Config.TERM_FILTER_MINLENGTH && termTuple.term.getContent().length() <= Config.TERM_FILTER_MAXLENGTH) {
                return termTuple;
            }
        }
    }
}
