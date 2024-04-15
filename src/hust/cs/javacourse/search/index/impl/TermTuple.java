package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

/**
 * TermTuple类是AbstractTermTuple的具体实现，表示一个词项元组。
 * 词项元组是一个三元组，包含一个词项、词项的出现频率和词项的当前位置。
 * TermTuple类提供了判断两个词项元组是否相等的方法，以及获取词项元组的字符串表示形式的方法。
 *
 * @author 晋晨曦
 */
public class TermTuple extends AbstractTermTuple {
    /**
     * 判断两个TermTuple对象是否相等。
     * 如果两个TermTuple对象的词项和当前位置相同，则它们相等。
     *
     * @param obj 要比较的对象
     * @return 如果两个TermTuple对象相等，则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TermTuple termTuple = (TermTuple) obj;
        // freq 始终是1 无需比较
        return term.equals(termTuple.term) && curPos == termTuple.curPos;
    }

    /**
     * 返回TermTuple对象的字符串表示形式。
     *
     * @return TermTuple对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "TermTuple{" +
                "term=" + term +
                ", freq=" + freq +
                ", curPos=" + curPos +
                '}';
    }
}
