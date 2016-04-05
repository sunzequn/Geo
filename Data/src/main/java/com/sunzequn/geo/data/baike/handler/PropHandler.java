package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bean.BasicInfo;
import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.baike.bean.PropStatistics;
import com.sunzequn.geo.data.baike.dao.BasicInfoDao;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplatePropDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class PropHandler {

    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();
    private static final double threshold = 0.8;

    public static void main(String[] args) {
        List<InfoBoxTemplateProp> props = propDao.getAll();
        for (InfoBoxTemplateProp prop : props) {
            List<BasicInfo> basicInfos = basicInfoDao.getValueByKey(prop.getName());
            PropStatistics propStatistic = new PropStatistics(prop);
            if (!ListUtils.isEmpty(basicInfos)) {
                propStatistic.num = basicInfos.size();
                for (BasicInfo basicInfo : basicInfos) {
                    String value = basicInfo.getValue();
                    if (value.contains("}}") && value.contains("{{") && value.contains("::")) {
                        propStatistic.objectNum++;
                    } else {
                        propStatistic.datatypeNum++;
                        if (StringUtils.isInteger(value)) {
                            propStatistic.intNum++;
                        } else if (StringUtils.isDouble(value)) {
                            propStatistic.datatypeNum++;
                        } else {
                            propStatistic.stringNum++;
                        }
                    }
                }
                updateProp(propStatistic);
                System.out.println(propStatistic);
            }
        }
    }

    private static void updateProp(PropStatistics propStatistics) {
        //属性类型，0代表datatype, 1代表object
        if (propStatistics.objectNum > propStatistics.num * threshold) {
            propDao.updateType(propStatistics.getProp().getId(), 1);
        } else {
            double n = propStatistics.datatypeNum * threshold;
            int id = propStatistics.getProp().getId();
            if (propStatistics.intNum > n) {
                propDao.updateComment(id, "int");
            } else if (propStatistics.doubleNum > n) {
                propDao.updateComment(id, "double");
            } else {
                propDao.updateComment(id, "string");
            }
        }
    }
}
