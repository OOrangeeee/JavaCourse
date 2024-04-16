package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentBuilder类是AbstractDocumentBuilder的具体实现，用于构建文档。
 * 它可以从给定的文件或TermTupleStream构建文档。
 * 在构建文档时，它会使用一系列的过滤器来过滤TermTupleStream，包括长度过滤器、模式过滤器和停用词过滤器。
 *
 * @author 晋晨曦
 */
public class DocumentBuilder extends AbstractDocumentBuilder {
    /**
     * 从给定的TermTupleStream构建文档。
     *
     * @param docId           文档的ID。
     * @param docPath         文档的路径。
     * @param termTupleStream 包含文档的TermTuple的TermTupleStream。
     * @return 构建的文档。
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        List<AbstractTermTuple> newList = new ArrayList<>();
        // 读取termTupleStream中的所有termTuple
        while (true) {
            AbstractTermTuple termTuple = termTupleStream.next();
            if (termTuple == null) {
                break;
            }
            newList.add(termTuple);
        }
        termTupleStream.close();
        return new Document(docId, docPath, newList);
    }

    /**
     * 从给定的文件构建文档。
     *
     * @param docId   文档的ID。
     * @param docPath 文档的路径。
     * @param file    包含文档内容的文件。
     * @return 构建的文档。
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        try {
            // 装饰者模式实例化termTupleStream
            AbstractTermTupleStream termTupleStream = new StopWordTermTupleFilter(new PatternTermTupleFilter(new LengthTermTupleFilter(new TermTupleScanner(new BufferedReader(new FileReader(file))))));
            return build(docId, docPath, termTupleStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
