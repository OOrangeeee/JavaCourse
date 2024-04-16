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
 * @author 晋晨曦
 */
public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        List<AbstractTermTuple> newList = new ArrayList<>();
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

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        try {
            AbstractTermTupleStream termTupleStream = new StopWordTermTupleFilter(new PatternTermTupleFilter(new LengthTermTupleFilter(new TermTupleScanner(new BufferedReader(new FileReader(file))))));
            return build(docId, docPath, termTupleStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
