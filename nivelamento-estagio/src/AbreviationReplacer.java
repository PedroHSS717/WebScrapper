import java.util.HashMap;
import java.util.Map;

public class AbreviationReplacer {
    private static final Map<String, String> abbreviationMap = new HashMap<>();

    static {
        abbreviationMap.put("OD", "Olho Direito");
        abbreviationMap.put("AMB", "Ambulatório");
    }

    public static String replaceAbbreviations(String line) {
        for (Map.Entry<String, String> entry : abbreviationMap.entrySet()) {
            line = line.replace(entry.getKey(), entry.getValue());
        }
        return line;
    }

    public static void main(String[] args) {
        String originalText = "OD, AMB, Exame";
        String replacedText = replaceAbbreviations(originalText);
        System.out.println(replacedText);  
    }
}