package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;
import javafx.geometry.Pos;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Index类是AbstractIndex的具体实现，表示一个倒排索引对象。
 * 一个倒排索引对象包含了一个文档集合的倒排索引。
 * 内存中的倒排索引结构为HashMap，key为Term对象，value为对应的PostingList对象。
 * 另外在Index里还定义了从docId和docPath之间的映射关系。
 * <p>
 * Index类提供了以下功能：
 * 1. 添加文档到索引，更新索引内部的HashMap。
 * 2. 从索引文件里加载已经构建好的索引。
 * 3. 将在内存里构建好的索引写入到文件。
 * 4. 返回指定单词的PostingList。
 * 5. 返回索引的字典，字典为索引里所有单词的并集。
 * 6. 对索引进行优化，包括对索引里每个单词的PostingList按docId从小到大排序，同时对每个Posting里的positions从小到大排序。
 * 7. 根据docId获得对应文档的完全路径名。
 *
 * @author 晋晨曦
 */
public class Index extends AbstractIndex {
    /**
     * Index类的默认构造函数，构建空的索引。
     */
    public Index() {
        super();
    }

    /**
     * 返回索引的字符串表示。
     *
     * @return 索引的字符串表示。
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Index{\n");

        builder.append("docIdToDocPathMapping:\n");
        for (Map.Entry<Integer, String> entry : docIdToDocPathMapping.entrySet()) {
            builder.append("docId: ").append(entry.getKey()).append(", docPath: ").append(entry.getValue()).append("\n");
        }

        builder.append("termToPostingListMapping:\n");
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry : termToPostingListMapping.entrySet()) {
            builder.append("term: ").append(entry.getKey()).append(", postingList: ").append(entry.getValue()).append("\n");
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap。
     *
     * @param document ：文档的AbstractDocument子类型表示。
     */
    @Override
    public void addDocument(AbstractDocument document) {
        // 将文档添加到docIdToDocPathMapping
        docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());

        // 将文档的三元组添加到termToPostingListMapping
        for (AbstractTermTuple termTuple : document.getTuples()) {
            AbstractTerm term = termTuple.term;
            AbstractPostingList postingList = termToPostingListMapping.get(term);
            // 如果termToPostingListMapping中包含term，则根据当前三元组修改postingList
            if (postingList != null) {
                boolean ifHasDoc = false;
                for (int i = 0; i < postingList.size(); i++) {
                    AbstractPosting nowPosting = postingList.get(i);
                    // 如果在这个PostingList中有当前文档的Posting，那么直接修改其Posting即可
                    if (nowPosting.getDocId() == document.getDocId()) {
                        nowPosting.setFreq(nowPosting.getFreq() + termTuple.freq);
                        nowPosting.getPositions().add(termTuple.curPos);
                        ifHasDoc = true;
                        break;
                    }
                }
                // 如果没有当前文档的Posting，那么创建一个新的
                if (!ifHasDoc) {
                    List<Integer> newPositions = new ArrayList<>();
                    newPositions.add(termTuple.curPos);
                    AbstractPosting newPosting = new Posting(document.getDocId(), termTuple.freq, newPositions);
                    postingList.add(newPosting);
                }
            } else {
                AbstractPostingList newPostingList = new PostingList();
                List<Integer> newPositions = new ArrayList<>();
                newPositions.add(termTuple.curPos);
                AbstractPosting newPosting = new Posting(document.getDocId(), termTuple.freq, newPositions);
                newPostingList.add(newPosting);
                termToPostingListMapping.put(term, newPostingList);
            }
        }
    }

    /**
     * 从索引文件里加载已经构建好的索引。
     *
     * @param file ：索引文件。
     */
    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            this.readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将在内存里构建好的索引写入到文件。
     *
     * @param file ：写入的目标索引文件。
     */
    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            this.writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList。
     *
     * @param term : 指定的单词。
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null。
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        if (termToPostingListMapping.containsKey(term)) {
            return termToPostingListMapping.get(term);
        }
        return null;
    }

    /**
     * 返回索引的字典。字典为索引里所有单词的并集。
     *
     * @return ：索引中Term列表。
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * 对索引进行优化，包括对索引里每个单词的PostingList按docId从小到大排序，同时对每个Posting里的positions从小到大排序。
     */
    @Override
    public void optimize() {
        for (AbstractPostingList postingList : termToPostingListMapping.values()) {
            postingList.sort();
            for (int i = 0; i < postingList.size(); i++) {
                AbstractPosting posting = postingList.get(i);
                Collections.sort(posting.getPositions());
            }
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名。
     *
     * @param docId ：文档id。
     * @return : 对应文档的完全路径名。
     */
    @Override
    public String getDocName(int docId) {
        return docIdToDocPathMapping.get(docId);
    }

    /**
     * 将Index对象写入到ObjectOutputStream。
     *
     * @param out ：ObjectOutputStream对象。
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从ObjectInputStream读取一个对象，并将其转换为Index类型。
     *
     * @param in ：ObjectInputStream对象，用于读取对象。
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            docIdToDocPathMapping = (Map<Integer, String>) in.readObject();
            termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
