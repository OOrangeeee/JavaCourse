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
 * IndexBuilder类是AbstractIndexBuilder的具体实现，用于构建索引。
 * 它使用指定的文档构建器从给定的根目录中的所有文件构建索引。
 * 构建的索引将保存在配置文件中指定的索引目录中。
 *
 * @author 晋晨曦
 */
public class IndexBuilder extends AbstractIndexBuilder {
    /**
     * 使用指定的文档构建器创建一个新的IndexBuilder。
     *
     * @param docBuilder 用于构建文档的文档构建器。
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    /**
     * 从指定的根目录中的所有文件构建索引。
     *
     * @param rootDirectory 包含要索引的文件的根目录。
     * @return 构建的索引。
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        List<String> filePaths = FileUtil.list(rootDirectory);
        AbstractIndex index = new Index();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            AbstractDocument document = docBuilder.build(docId++, filePath, file);
            index.addDocument(document);
        }
        File indexFile = new File(Config.INDEX_DIR + "index.res");
        index.save(indexFile);
        return index;
    }
}
