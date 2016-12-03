package com.sunzequn.geo.data.geonamesplus;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/20.
 *
 * 自己封装的文件读取工具类,包含一些基本的文件读取方法
 */
public class ReadUtils {

    private LineIterator lineIterator = null;

    private ReadUtils() {
    }

    public ReadUtils(String filepath) {
        File file = new File(filepath);
        try {
            lineIterator = FileUtils.lineIterator(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计文件行数
     *
     * @return 文件行数
     */
    public long countLines() {
        if (lineIterator == null) {
            return 0;
        }
        long res = 0;
        while (lineIterator.hasNext()) {
            lineIterator.nextLine();
            res++;
        }
        return res;
    }

    /**
     * 按行读取文件
     *
     * @return 由文件每行字符串组成的List
     */
    public List<String> readByLine() {
        if (lineIterator == null) {
            return null;
        }
        List<String> strings = new ArrayList<>();
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            strings.add(line);
        }
        return strings;
    }

    public void close() {
        lineIterator.close();
    }


}
