package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplate;
import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.baike.bean.Prop;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplateDao;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplatePropDao;

import java.util.List;

/**
 * Created by sloriac on 16-3-29.
 * <p>
 * 处理infobox模板属性
 */
public class InfoBoxTemplateProphandler {

    private static InfoBoxTemplateDao templateDao = new InfoBoxTemplateDao();
    private static InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();

    public static void main(String[] args) {
        generateProps();
    }

    /**
     * 把原来template数据表的内容重新整理一下，把property抽出来写入新的表
     */
    private static void generateProps() {
        List<InfoBoxTemplate> templates = templateDao.getAll();
        for (InfoBoxTemplate template : templates) {
            template.initProps();
            int templateid = template.getId();
            for (Prop prop : template.getProps()) {
                InfoBoxTemplateProp templateProp = new InfoBoxTemplateProp();
                templateProp.setName(prop.getName());
                templateProp.setComment(prop.getComment());
                templateProp.setTemplateid(templateid);
                propDao.save(templateProp);
            }
        }
    }
}

