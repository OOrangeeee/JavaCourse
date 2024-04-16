package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

/**
 * Hit类是AbstractHit的具体实现，表示搜索命中的结果。
 * 它包含了文档的id，路径，内容，得分以及命中的单词和对应的Posting键值对。
 * 该类实现了Comparable接口，可以根据得分进行比较和排序。
 *
 * @author 晋晨曦
 */
public class Hit extends AbstractHit {
    /**
     * 默认构造函数
     */
    public Hit() {

    }

    /**
     * 构造函数
     *
     * @param docId   文档id
     * @param docPath 文档绝对路径
     */
    public Hit(int docId, String docPath) {
        super(docId, docPath);
    }

    /**
     * 构造函数
     *
     * @param docId              文档id
     * @param docPath            文档绝对路径
     * @param termPostingMapping 命中的单词和对应的Posting键值对
     */
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping) {
        super(docId, docPath, termPostingMapping);
    }

    /**
     * 获取文档id
     *
     * @return 文档id
     */
    @Override
    public int getDocId() {
        return docId;
    }

    /**
     * 获取文档绝对路径
     *
     * @return 文档绝对路径
     */
    @Override
    public String getDocPath() {
        return docPath;
    }

    /**
     * 获取文档内容
     *
     * @return 文档内容
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * 设置文档内容
     *
     * @param content 文档内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取文档得分
     *
     * @return 文档得分
     */
    @Override
    public double getScore() {
        return score;
    }

    /**
     * 设置文档得分
     *
     * @param score 文档得分
     */
    @Override
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 获取命中的单词和对应的Posting键值对
     *
     * @return 命中的单词和对应的Posting键值对
     */
    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return termPostingMapping;
    }

    /**
     * 返回Hit对象的字符串表示形式
     *
     * @return Hit对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "Hit{" +
                "docId=" + docId +
                ", docPath='" + docPath + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", termPostingMapping=" + termPostingMapping +
                '}';
    }

    /**
     * 根据得分比较两个Hit对象的大小
     *
     * @param o 要比较的Hit对象
     * @return 两个Hit对象得分的差值
     */
    @Override
    public int compareTo(AbstractHit o) {
        return (int) (this.score - o.getScore());
    }
}
