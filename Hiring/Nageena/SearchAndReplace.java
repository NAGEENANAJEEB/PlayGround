import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Scanner;

class FileSearch {

    public static void main(String[] args) {
        Map<String,String> variableMap = search();
        System.out.println("Enter full Path to the file");
        Scanner myObj = new Scanner(System.in);
        String loc = myObj.nextLine();
        Path path = Paths.get(loc);
        Stream<String> lines;
        try {
            lines = Files.lines(path,Charset.forName("UTF-8"));
            List<String> replacedLines = lines.map(line -> replaceTag(line,variableMap))
                    .collect(Collectors.toList());
            Files.write(path, replacedLines, Charset.forName("UTF-8"));
            lines.close();
            System.out.println(" Search phrase replaced with #");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> search() {
        Map<String,String> map = new HashMap<String,String>();
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Search Phrase");
        String search = myObj.nextLine();
        int na=search.length();
        String hash="";
        for (int i=0;i<na;i++)
        {
            if (search.charAt(i)!=' ')
            {
                hash=hash+"#";
            }
            else
            {
                hash=hash+" ";
            }
        }
        map.put(search, hash);
        return map;
    }
    private static String replaceTag(String str, Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (str.contains(entry.getKey())) {
                str = str.replace(entry.getKey(), entry.getValue());
            }
        }
        return str;
    }
}
