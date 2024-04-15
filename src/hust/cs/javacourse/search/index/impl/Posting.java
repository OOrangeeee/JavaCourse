package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Posting类是AbstractPosting的具体实现，表示倒排索引中的一个项。
 * Posting对象包含一个文档ID、一个频率和一个位置列表。
 * Posting类提供了获取和设置这些属性的方法，以及比较两个Posting对象的方法。
 * 此外，Posting类还实现了序列化接口的两个方法，用于对象的序列化和反序列化。
 *
 * @author 晋晨曦
 */
public class Posting extends AbstractPosting {
    /**
     * 判断两个Posting对象是否相等。
     * 如果两个Posting对象的文档ID、频率和位置列表都相同，则它们相等。
     *
     * @param obj 要比较的对象
     * @return 如果两个Posting对象相等，则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Posting posting = (Posting) obj;
        return docId == posting.docId && freq == posting.freq && positions.equals(posting.positions);
    }

    /**
     * 返回Posting对象的字符串表示形式。
     *
     * @return Posting对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "Posting{" +
                "docId=" + docId +
                ", freq=" + freq +
                ", positions=" + positions +
                '}';
    }

    /**
     * 获取Posting对象的文档ID。
     *
     * @return Posting对象的文档ID
     */
    @Override
    public int getDocId() {
        return docId;
    }

    /**
     * 设置Posting对象的文档ID。
     *
     * @param docId Posting对象的新文档ID
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 获取Posting对象的频率。
     *
     * @return Posting对象的频率
     */
    @Override
    public int getFreq() {
        return freq;
    }

    /**
     * 设置Posting对象的频率。
     *
     * @param freq Posting对象的新频率
     */
    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    /**
     * 获取Posting对象的位置列表。
     *
     * @return Posting对象的位置列表
     */
    @Override
    public List<Integer> getPositions() {
        return positions;
    }

    /**
     * 设置Posting对象的位置列表。
     *
     * @param positions Posting对象的新位置列表
     */
    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    /**
     * 比较两个Posting对象的大小。
     * 比较的依据是Posting对象的文档ID。
     *
     * @param o 要比较的Posting对象
     * @return 如果当前Posting对象小于、等于或大于指定Posting对象，则分别返回负整数、零或正整数
     */
    @Override
    public int compareTo(AbstractPosting o) {
        return docId - o.getDocId();
    }

    /**
     * 对Posting对象的位置列表进行排序。
     */
    @Override
    public void sort() {
        Collections.sort(positions);
    }

    /**
     * 将Posting对象序列化到指定的ObjectOutputStream。
     *
     * @param out 要写入的ObjectOutputStream
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(docId);
            out.writeObject(freq);
            out.writeObject(positions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定的ObjectInputStream反序列化Posting对象。
     *
     * @param in 要读取的ObjectInputStream
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            Object obj = in.readObject();
            if (obj instanceof Integer) {
                docId = (Integer) obj;
            } else {
                throw new IllegalArgumentException("Expected Integer for docId, got " + obj.getClass().getName());
            }

            obj = in.readObject();
            if (obj instanceof Integer) {
                freq = (Integer) obj;
            } else {
                throw new IllegalArgumentException("Expected Integer for freq, got " + obj.getClass().getName());
            }

            obj = in.readObject();
            if (obj instanceof List) {
                positions = (List<Integer>) obj;
            } else {
                throw new IllegalArgumentException("Expected List<Integer> for positions, got " + obj.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
