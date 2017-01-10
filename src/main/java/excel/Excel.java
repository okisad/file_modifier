package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by oktaysadoglu on 10/01/2017.
 */
public class Excel {

    private String path;

    private XSSFWorkbook workbook;

    public Excel(String excelFilePath){

        this.path = excelFilePath;

        openWorkbook();

    }

    private void openWorkbook(){

        File file = new File(path);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            this.workbook  = new XSSFWorkbook(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("file input stream error");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("create xssfworkbook error");
            e.printStackTrace();
        }

    }

    public Map<String,String> getColumnValues(int whichsheet,int urlColumn,int nameColumn){

        Map<String ,String> values = new HashMap<>();

        XSSFSheet sheet = workbook.getSheetAt(whichsheet);

        Iterator<Row> rowIterator = sheet.iterator();

        int rowCount = 0;

        while (rowIterator.hasNext()){

            Row row = rowIterator.next();

            if (rowCount == 0){

                rowCount++;

                continue;

            }


            Iterator<Cell> cellIterator = row.cellIterator();

            int column = 0;

            String name ="";

            while (cellIterator.hasNext()){

                Cell cell = cellIterator.next();

                if (column == nameColumn){

                    name = adjustName(cell.getStringCellValue().trim());

                    values.put(name,null);

                }

                if (column == urlColumn){

                    if (!cell.getStringCellValue().equals(""))
                        values.put(name,adjustUrl(cell.getStringCellValue()));

                }

                column++;

                if (column > 10)
                    break;

            }

        }

        control(values);

        return values;

    }

    private String adjustUrl(String url){

        if (!url.startsWith("www") && !url.startsWith("http")){

            url = "www." + url;

        }

        return url;

    }

    private String adjustName(String name){

        if (!name.equals("")){

            name = name.replace("İ","I");

            name = name.toLowerCase(new Locale("tr-TR"));

            name = name.replace("'","").replace(" ","").replace("ü","u").replace("ç","c").replace("ğ","g").replace("ş","s").replace("ö","o").replace("ı","i");

        }

        return name;

    }

    private void control(Map<String ,String> values){

        Iterator it = values.entrySet().iterator();
        while (it.hasNext()){

            Map.Entry pair = (Map.Entry) it.next();

            if (pair.getKey() == null || pair.getValue() == null){

                it.remove();

                continue;

            }

            String key = (String) pair.getKey();

            String value = (String) pair.getValue();

            if (key.length() < 2 || key.equals("")){

                it.remove();
                continue;
            }

            if (value.length()< 4 || value.equals("")){

                it.remove();

                continue;

            }

            /*System.out.println(pair.getKey() + " = " + pair.getValue());*/

        }

    }

}
