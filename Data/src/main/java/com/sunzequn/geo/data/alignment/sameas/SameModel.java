package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.jena.Rdf;
import org.apache.jena.rdf.model.*;

/**
 * Created by Sloriac on 16/2/27.
 */
public class SameModel {

    private static final String GEONAMES_LINK = "Data/src/main/resources/data/sw/geonames_links.nt";
    private static final String GEONAMES_LINK_EN = "Data/src/main/resources/data/sw/geonames_links_en.nt";

    public Model getModel(String file) {
        Rdf rdf = new Rdf();
        return rdf.getModel(file, "N-TRIPLE");
    }

    public static void main(String[] args) {
        SameModel sameModel = new SameModel();
        Model model = sameModel.getModel(GEONAMES_LINK);
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            //jena解析namespace不靠谱
        }
    }
}
