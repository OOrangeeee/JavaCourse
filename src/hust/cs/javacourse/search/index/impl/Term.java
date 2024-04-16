package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Term类是AbstractTerm的具体实现，表示一个词项。
 * 词项是搜索引擎处理的基本单位，每个词项都有一个字符串内容。
 * Term类提供了获取和设置内容的方法，以及比较两个词项大小的方法。
 * 此外，Term类还实现了序列化接口的两个方法，用于对象的序列化和反序列化。
 *
 * @author 晋晨曦
 */
public class Term extends AbstractTerm {
    /**
     * Term类的默认构造函数。
     * 它初始化一个没有内容的Term的新实例。
     */
    public Term() {
        super();
    }

    /**
     * 构造函数，创建一个新的Term对象。
     *
     * @param content 词项的内容
     */
    public Term(String content) {
        super(content);
    }

    /**
     * 判断两个Term对象是否相等。
     * 如果两个Term对象的内容相同，则它们相等。
     *
     * @param obj 要比较的对象
     * @return 如果两个Term对象相等，则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Term term = (Term) obj;
        return content.equals(term.content);
    }

    /**
     * 返回Term对象的字符串表示形式。
     *
     * @return Term对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "Term{" +
                "content='" + content + '\'' +
                '}';
    }

    /**
     * 获取Term对象的内容。
     *
     * @return Term对象的内容
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * 设置Term对象的内容。
     *
     * @param content Term对象的新内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 比较两个Term对象的大小。
     * 比较的依据是Term对象的内容。
     *
     * @param o 要比较的Term对象
     * @return 如果当前Term对象小于、等于或大于指定Term对象，则分别返回负整数、零或正整数
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return content.compareTo(o.getContent());
    }

    /**
     * 将Term对象序列化到指定的ObjectOutputStream。
     *
     * @param out 要写入的ObjectOutputStream
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定的ObjectInputStream反序列化Term对象。
     *
     * @param in 要读取的ObjectInputStream
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            content = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
