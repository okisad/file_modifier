package excel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by oktaysadoglu on 10/01/2017.
 */
public class TextFile {

    Map<String,String> urls;

    public TextFile(Map<String,String > urls){

        this.urls = urls;

    }

    public void createFiles(){

        Iterator it = urls.entrySet().iterator();

        while (it.hasNext()){

            Map.Entry pair = (Map.Entry) it.next();

            String s = "#seeds;\n" +
                    pair.getValue()+"\n" +
                    "\n" +
                    "#include in index;\n" +
                    "\n" +
                    "\n" +
                    "#exclude from crawl;\n" +
                    "\n" +
                    "# generic exclusions\n" +
                    ".*\\.pdf$\n" +
                    ".*\\.mp4$\n" +
                    ".*\\.xml$\n" +
                    "\n" +
                    "#video patterns;\n" +
                    "\n" +
                    "#column patterns;\n" +
                    "\n" +
                    "#category patterns;\n" +
                    "\n" +
                    "#date elements;";

            File file = new File("gazeteler/"+pair.getKey()+".txt");

            try {
                FileUtils.writeStringToFile(file,s);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
