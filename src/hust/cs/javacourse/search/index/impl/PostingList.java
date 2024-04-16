package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * PostingList类实现了AbstractPostingList接口，用于存储一个文档的倒排列表。
 * <p>
 * PostingList类的主要方法包括：添加一个posting、添加多个postings、获取指定index的posting、
 * 获取指定docId的posting在list中的index、判断list中是否包含指定的posting、移除指定index的posting、
 * 移除指定的posting、获取list的大小、清空list、判断list是否为空、对list进行排序。
 * <p>
 * 此外，PostingList类还提供了两个序列化和反序列化方法：writeObject和readObject。
 * <p>
 *
 * @author 晋晨曦
 */
public class PostingList extends AbstractPostingList {
    /**
     * 无参构造函数
     */
    public PostingList() {

    }

    /**
     * 添加一个posting到list中
     * <p>
     * 如果list中已经包含该posting，则不添加
     *
     * @param posting 要添加的posting
     */
    @Override
    public void add(AbstractPosting posting) {
        if (this.list.contains(posting)) {
            return;
        }
        this.list.add(posting);
    }

    /**
     * 返回list的字符串表示
     *
     * @return list的字符串表示
     */
    @Override
    public String toString() {
        return "PostingList{" +
                "list=" + list +
                '}';
    }

    /**
     * 添加多个postings到list中
     *
     * @param postings 要添加的postings
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            this.add(posting);
        }
    }

    /**
     * 获取指定index的posting
     *
     * @param index posting的index
     * @return 指定index的posting
     */
    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    /**
     * 获取指定posting在list中的index。
     *
     * @param posting 要查找的posting
     * @return 指定posting在list中的index，如果不存在则返回-1
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    /**
     * 获取指定docId的posting在list中的index
     *
     * @param docId posting的docId
     * @return 指定docId的posting在list中的index，如果不存在则返回-1
     */
    @Override
    public int indexOf(int docId) {
        for (int i = 0; i < list.size(); i++) {
            if (docId == list.get(i).getDocId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断list中是否包含指定的posting
     *
     * @param posting 要查找的posting
     * @return 如果包含该posting则返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    /**
     * 移除指定index的posting
     *
     * @param index 要移除的posting的index
     */
    @Override
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * 移除指定的posting
     *
     * @param posting 要移除的posting
     */
    @Override
    public void remove(AbstractPosting posting) {
        list.remove(posting);
    }

    /**
     * 获取list的大小
     *
     * @return list的大小
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * 清空list
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * 判断list是否为空
     *
     * @return 如果为空则返回true，否则返回false
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 对list进行排序
     */
    @Override
    public void sort() {
        Collections.sort(list);
    }

    /**
     * 将list写入ObjectOutputStream
     *
     * @param out ObjectOutputStream对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从ObjectInputStream读取一个对象，并将其转换为List<AbstractPosting>类型。
     *
     * @param in ObjectInputStream对象，用于读取对象
     * @throws ClassCastException 如果读取的对象不是List<AbstractPosting>类型
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                list = (List<AbstractPosting>) obj;
            } else {
                throw new ClassCastException("读取的对象不是 List<AbstractPosting> 类型");
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}
