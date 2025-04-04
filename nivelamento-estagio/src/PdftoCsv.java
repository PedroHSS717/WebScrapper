import com.opencsv.CSVWriter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdftoCsv {
    
    public static List<String[]> extractDataFromPdf(String filePath) throws IOException {
        PDDocument document = Loader.loadPDF(new File(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        
        List<String[]> extractedData = new ArrayList<>();
        extractedData.add(new String[]{"oi", "Procedimento", "Valor"}); // Cabeçalho CSV
        
        String[] lines = text.split("\n"); // Separar por linhas
        for (String line : lines) {
            String[] parts = line.split(" "); // Separar por espaços
            if (parts.length >= 3) { // Garantir que há pelo menos código, nome e valor
                extractedData.add(new String[]{parts[0], parts[1] + " " + parts[2], parts[parts.length - 1]});
            }
        }
        
        return extractedData;
    }
    
    public static void saveToCsv(List<String[]> data, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(data);
        }
    }
    
    public static void main(String[] args) throws IOException {
        String pdfPath = "C:\\Users\\euamo\\OneDrive\\Desktop\\Trabalhos - Java\\nivelamento-estagio\\nivelamento-estagio\\downloads\\Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String csvPath = "dados.csv";
        
        List<String[]> extractedData = extractDataFromPdf(pdfPath);
        saveToCsv(extractedData, csvPath);
        
        System.out.println("CSV gerado com sucesso!");
    }
}