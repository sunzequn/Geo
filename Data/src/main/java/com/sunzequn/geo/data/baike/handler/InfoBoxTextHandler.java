package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sloriac on 16/3/15.
 */
public class InfoBoxTextHandler {

    private static final String INFOBOX_FILE = "Data/src/main/resources/data/baike/basic_infobox.txt";
    private static final String NEW_INFOBOX_FILE = "Data/src/main/resources/data/baike/new_basic_infobox.txt";

    public static void main(String[] args) throws IOException {
        errorHandle();
    }

    private static void errorHandle() throws IOException {
        File file = new File(INFOBOX_FILE);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        WriteUtils writeUtils = new WriteUtils(NEW_INFOBOX_FILE, true);
        while (it.hasNext()) {
            String line = it.nextLine();
            if (line.length() > 4 && line.contains(" ")) {
                writeUtils.write(line);
            }

        }
        it.close();
        writeUtils.close();
    }
}
