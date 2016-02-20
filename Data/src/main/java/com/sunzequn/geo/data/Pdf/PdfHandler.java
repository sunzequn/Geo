package com.sunzequn.geo.data.Pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.rdf.model.RDFReader;

import java.io.IOException;

/**
 * Created by Sloriac on 16/2/18.
 */
public class PdfHandler {


    private static final String PDF_FILE = "Data/src/main/resources/data/pdf/chinageo.pdf";
    private static final String FILE = "Data/src/main/resources/data/pdf/chinageo";

    public static void main(String[] args) throws IOException {
        getPdfFileText(PDF_FILE);
    }

    public static void getPdfFileText(String fileName) throws IOException {
        WriteUtils writeUtils = new WriteUtils(FILE, true);
        PdfReader reader = new PdfReader(fileName);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
//        StringBuffer buff = new StringBuffer();
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i,
                    new SimpleTextExtractionStrategy());
            writeUtils.write(strategy.getResultantText());
            writeUtils.flush();
//            buff.append(strategy.getResultantText());
        }
//        return buff.toString();
        writeUtils.close();
    }
}
