package com.sunzequn.geo.data.alignment;

import com.sunzequn.geo.data.alignment.dao.ClassLinkDao;
import com.sunzequn.geo.data.alignment.dao.ClassRelDao;
import org.junit.Test;

/**
 * Created by Sloriac on 16/2/28.
 */
public class DaoTest {

    @Test
    public void classRelDaoTest() {
        ClassRelDao dao = new ClassRelDao();
//        System.out.println(dao.getSuperClasses("dbo:EducationalInstitution"));
//        System.out.println(dao.getSubClasses("dbo:EducationalInstitution"));

        System.out.println(dao.getAllSubClasses("dbo:Place"));
        System.out.println(dao.getAllSubClasses("dbo:PopulatedPlace"));
    }

    @Test
    public void classLinkTest() {
        ClassLinkDao dao = new ClassLinkDao();
//        ListUtils.print(dao.getAllDbpediaClasses());
        System.out.println(dao.getAllGeonamesClasses().size());
    }

}
