package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.alignment.type.Clazz;
import com.sunzequn.geo.data.alignment.type.ClazzGraph;
import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.TimeUtils;
import org.apache.jena.rdf.model.*;

import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class SameModel {

    private static final String GEONAMES_LINK = "Data/src/main/resources/data/sw/geonames_links.nt";
    private static final String GEONAMES_LINK_EN = "Data/src/main/resources/data/sw/geonames_links_en.nt";
    private EntityHandler entityHandler = new EntityHandler();
    public Model getModel(String file) {
        Rdf rdf = new Rdf();
        return rdf.getModel(file, "N-TRIPLE");
    }

    public ClazzGraph graphHandler(String uri) {
        return entityHandler.getGraph(uri);
    }

    public static void main(String[] args) {
        SameModel sameModel = new SameModel();
        Model model = sameModel.getModel(GEONAMES_LINK);
        StmtIterator iter = model.listStatements();
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            String uri = subject.getURI();
            sameModel.graphHandler(uri);
        }
        timeUtils.end();
        timeUtils.print();
    }
}