package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * TermTupleScanner类是AbstractTermTupleScanner的具体实现，用于从输入流中读取词元组。
 * 它使用StringSplitter对象来分割输入流中的每一行，每次调用next方法时，都会返回当前行的下一个词元组。
 * 如果当前行的词元组已经读取完毕，那么会读取下一行。当输入流读取完毕时，next方法将返回null。
 *
 * @author 晋晨曦
 */
public class TermTupleScanner extends AbstractTermTupleScanner {

    private StringSplitter splitter;
    private List<String> words;
    private int pos = 0;

    /**
     * 默认构造函数
     */
    public TermTupleScanner() {
    }

    /**
     * 构造函数，接收一个BufferedReader对象作为输入流
     *
     * @param input BufferedReader对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
        splitter = new StringSplitter();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
        words = new ArrayList<>();
    }

    /**
     * 从输入流中读取下一个词元组，如果输入流已经读取完毕，那么返回null。
     * 在读取词元组时，首先检查words列表是否为空，如果为空，那么读取输入流的下一行并将其分割为词元组。
     * 然后，从words列表中移除并返回第一个词元组。
     *
     * @return 下一个词元组，如果输入流已经读取完毕，那么返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (words.isEmpty()) {
            String line = null;
            try {
                line = input.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (line == null) {
                return null;
            }
            words = splitter.splitByRegex(line);
        }
        String word = words.remove(0);
        AbstractTermTuple ans = new TermTuple();
        ans.term = new Term(word);
        ans.curPos = pos;
        pos++;
        return ans;
    }

    /**
     * 关闭输入流
     */
    @Override
    public void close() {
        super.close();
    }
}
