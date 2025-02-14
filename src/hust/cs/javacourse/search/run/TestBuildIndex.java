package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.util.Config;

import java.io.File;

/**
 * 测试索引构建
 *
 * @author 晋晨曦
 */
public class TestBuildIndex {
    /**
     * 索引构建程序入口
     *
     * @param args : 命令行参数
     */
    public static void main(String[] args) {
        AbstractDocumentBuilder documentBuilder = new DocumentBuilder();
        AbstractIndexBuilder indexBuilder = new IndexBuilder(documentBuilder);
        String rootDir = Config.DOC_DIR;
        System.out.println("Start build index ...");
        AbstractIndex index = indexBuilder.buildIndex(rootDir);
        index.optimize();
        //控制台打印 index 的内容
        System.out.println(index);
        //测试保存到文件
        String indexFile = Config.INDEX_DIR + "index.dat";
        //索引保存到文件
        index.save(new File(indexFile));
        //测试从文件读取
        //创建一个空的 index
        AbstractIndex index2 = new Index();
        //从文件加载对象的内容
        index2.load(new File(indexFile));
        System.out.println("\n-------------------\n");
        //控制台打印 index2 的内容
        System.out.println(index2);
    }
}
