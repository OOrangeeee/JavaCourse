package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.util.List;

/**
 * @author 晋晨曦
 */
public class IndexBuilder extends AbstractIndexBuilder {

    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        List<String> filePaths = FileUtil.list(rootDirectory);
        AbstractIndex index = new Index();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            AbstractDocument document = docBuilder.build(docId++, filePath, file);
            index.addDocument(document);
        }
        File indexFile=new File(Config.INDEX_DIR+"index.res");
        index.save(indexFile);
        return index;
    }
}
