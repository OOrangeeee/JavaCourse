package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.Date;
import java.util.List;

/**
 * Document类是AbstractDocument的具体实现。
 * 它包含了文档的ID，文档的路径，以及文档包含的词元三元组列表。
 * 提供了获取和设置这些属性的方法，以及添加和获取三元组的方法。
 *
 * @author 晋晨曦
 */
public class Document extends AbstractDocument {
    /**
     * 默认构造函数，创建一个新的 Document 对象，但不初始化任何字段。
     */
    public Document() {
        super();
    }

    /**
     * 创建一个新的 Document 对象，并初始化其 docId 和 docPath 字段。
     *
     * @param docId   文档的 ID
     * @param docPath 文档的绝对路径
     */
    public Document(int docId, String docPath) {
        super(docId, docPath);
    }

    /**
     * 创建一个新的 Document 对象，并初始化其 docId，docPath 和 tuples 字段。
     *
     * @param docId   文档的 ID
     * @param docPath 文档的绝对路径
     * @param tuples  文档包含的词元三元组列表
     */
    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        super(docId, docPath, tuples);
    }

    /**
     * 获取文档的ID。
     *
     * @return 文档的ID
     */
    @Override
    public int getDocId() {
        return docId;
    }

    /**
     * 设置文档的ID。
     *
     * @param docId 要设置的文档ID
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 获取文档的路径。
     *
     * @return 文档的路径
     */
    @Override
    public String getDocPath() {
        return docPath;
    }

    /**
     * 设置文档的路径。
     *
     * @param docPath 要设置的文档路径
     */
    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    /**
     * 获取文档包含的词元三元组列表。
     *
     * @return 文档包含的词元三元组列表
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return tuples;
    }

    /**
     * 向文档中添加一个词元三元组，如果该三元组已存在，则不添加。
     *
     * @param tuple 要添加的词元三元组
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!tuples.contains(tuple)) {
            tuples.add(tuple);
        }
    }

    /**
     * 判断文档是否包含指定的词元三元组。
     *
     * @param tuple 指定的词元三元组
     * @return 如果文档包含指定的词元三元组，返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return tuples.contains(tuple);
    }

    /**
     * 获取指定下标位置的词元三元组。
     *
     * @param index 指定的下标位置
     * @return 指定下标位置的词元三元组
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return tuples.get(index);
    }

    /**
     * 获取文档包含的词元三元组的数量。
     *
     * @return 文档包含的词元三元组的数量
     */
    @Override
    public int getTupleSize() {
        return tuples.size();
    }

    /**
     * 返回文档的字符串表示形式。
     *
     * @return 文档的字符串表示形式
     */
    @Override
    public String toString() {
        return "Document{" +
                "docId=" + docId +
                ", docPath='" + docPath + '\'' +
                ", tuples=" + tuples +
                '}';
    }
}
