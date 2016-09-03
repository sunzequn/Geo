package com.sunzequn.geo.data.shiti;

import com.sunzequn.geo.data.regex.RegexUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-9-3.
 */
public class ParseXml {

    private static final String SHITI = "/home/sloriac/code/Geo/Data/src/main/resources/shiti";
    private static final String CHULI = "/home/sloriac/code/Geo/Data/src/main/resources/chulihou/";

    private static int id = 1;

    public static void main(String[] args) throws Exception {
        File shitiku = new File(SHITI);
        File[] shitis = shitiku.listFiles();
        for (File shiti : shitis) {
            String shuoming = StringUtils.removeEnd(shiti.getName(), ".xml");
            String name = CHULI + "2016-讯飞-" + shuoming;
            //归置id
            id = 1;
            print(name);
            WriteUtils writeUtils = new WriteUtils(name, false);
            //文件头
            writeUtils.write("2016");
            writeUtils.write("讯飞");
            writeUtils.write(shuoming);
            writeUtils.write("答案");
            writeUtils.write("###");
            Document document = Jsoup.parse(shiti, "utf-8");
            Elements content = document.select("section");
            parseXuanzeti(content.get(0), writeUtils);
            parseDati(content.get(1), writeUtils);
            writeUtils.close();
        }
    }

    private static void parseXuanzeti(Element xuanzeti, WriteUtils writeUtils) {
        Elements questionses = xuanzeti.select("questions");
        print("一共有背景：" + questionses.size());
        for (int i = 0; i < questionses.size(); i++){
            parseQuestions(questionses.get(i), writeUtils);
            //最后材料结束
            if (i < questionses.size() - 1)
                writeUtils.write("##");
        }
        //全部的选择题结束
        writeUtils.write("###");
    }

    private static void parseQuestions(Element questions, WriteUtils writeUtils) {
        //背景图片和文字
        Elements texts = questions.select("text");
        Elements oneQuestions = questions.select("question");

        //每个小题必有文字的，文字数目不相同，说明有背景材料
        if (texts.size() > oneQuestions.size()) {
            String backgroundText = texts.get(0).text();
            String pureBackgroundText = getPureBackgroundText(backgroundText);
            print("背景材料：" + pureBackgroundText);
            Set<String> pics = getPic(backgroundText);
            if (pics != null)
                print("图片数目：" + pics.size());
            //写入文件
            int picNum = 0;
            if (pics != null)
                picNum = pics.size();
            //图片数目
            writeUtils.write(String.valueOf(picNum));
            //背景材料
            writeUtils.write(pureBackgroundText);
        }

        //没有背景材料的
        else {
            writeUtils.write("0");
            writeUtils.write("无");
        }

        //处理材料里面的每个小题
        for (Element oneQuestion : oneQuestions) {
            parseOneQuestion(oneQuestion, writeUtils);
        }

    }

    private static void parseOneQuestion(Element oneQuestion, WriteUtils writeUtils) {

        String text = oneQuestion.select("text").get(0).text();

        //判断类型
        int type = 0;
        //组合题目
        if (text.contains("**(label")) {
            type = 1;
        }

        String pureTigan;
        List<String> zuheXiang = new ArrayList<>();
        int picNum = 0;

        //组合问题
        if (text.contains("**(label")) {
            String[] params = text.split("__");
            if (params.length == 2) {
                text = params[0];
                String zuhes = params[1].trim();
                String[] zuhess = zuhes.split(" ");
                for (String s : zuhess) {
                    zuheXiang.add(getPureBackgroundText(s));
                }
            } else {
                print("组合项解析出问题");
            }
            print("有组合项：" + zuheXiang);
        }

        //题干中有图片，特殊输出*加图片数目
        if (text.contains("**(pic")) {
            Set<String> pics = getPic(text);
            if (pics != null)
                picNum = pics.size();
        }

        pureTigan = getPureBackgroundText(text);
        pureTigan = StringUtils.removeEnd(pureTigan, "__");
        print("题干：" + pureTigan);
        print("图片数目" + picNum);

        //写入文件
        //特殊情况，组合问题与题干图片问题
        if (type == 1 || picNum > 0)
            writeUtils.write("*" + picNum + " " + type);
        //写入题号和题干
        writeUtils.write(id + "." + pureTigan);
        id++;
        //写入组合项
        if (zuheXiang.size() > 0){
            for (int i = 0; i < zuheXiang.size(); i++){
                writeUtils.write(getZuheBianHao(i+1) + zuheXiang.get(i));
            }
            writeUtils.write("#");
        }
        //写入四个选项
        Elements ops = oneQuestion.select("option");
        for (Element op : ops) {
            String opText = op.text();
            if (opText == null || opText.trim().equals(""))
                opText = "选项是图片";
            writeUtils.write(opText);
        }

    }

    private static String getPureBackgroundText(String backgroundText) {
        List<String> picstring = RegexUtils.extract("\\*\\*\\(", "\\)\\*\\*", backgroundText);
        if (picstring != null) {
            for (String s : picstring) {
                backgroundText = StringUtils.remove(backgroundText, s);
            }
        }
        backgroundText = StringUtils.remove(backgroundText, "**(");
        backgroundText = StringUtils.remove(backgroundText, ")**");
        return backgroundText.trim();
    }

    private static Set<String> getPic(String backgroundText) {
        List<String> picstring = RegexUtils.extract("\\*\\*\\(", "\\)\\*\\*", backgroundText);
        if (picstring == null) {
            return null;
        }
        Set<String> pics = new HashSet<>();
        for (String s : picstring) {
            String[] params = s.split(",");
            pics.add(params[2]);
        }
        return pics;
    }

    private static String getZuheBianHao(int id){
        switch (id){
            case 1: return "①";
            case 2: return "②";
            case 3: return "③";
            case 4: return "④";
            case 5: return "⑤";
            case 6: return "⑥";
        }
        return "编号出错";
    }

    private static void print(String str){
//        System.out.println(str);
    }

    private static void parseDati(Element dati, WriteUtils writeUtils) {
        Elements questionses = dati.select("questions");
        for (int i = 0; i < questionses.size(); i++){
            parseDatiQuestions(questionses.get(i), writeUtils);
            //最后一道大题结束
            if (i < questionses.size() - 1)
                writeUtils.write("##");
        }
    }

    private static void parseDatiQuestions(Element element, WriteUtils writeUtils) {

    }

}
