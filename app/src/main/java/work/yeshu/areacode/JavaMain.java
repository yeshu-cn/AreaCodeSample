package work.yeshu.areacode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yeshu on 2016/11/28.
 * 用来临时整理地区号文件
 */

public class JavaMain {

    public static void main(String args[]) {
        File file = new File("/Users/yeshu/Downloads/test.md");
        File out = new File("/Users/yeshu/Downloads/out.md");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(out));
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int line = -1;
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] code = tempString.split("\\+");
                stringBuilder.append("<item>");
                stringBuilder.append(code[0].trim());
                stringBuilder.append(" +");
                stringBuilder.append(code[1].trim());
                stringBuilder.append("</item>");
                writer.write(stringBuilder.toString());
                writer.newLine();
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
