import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfExtractor {
    public static String extractTextfromPdf(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Arquivo PDF não encontrado: " + filePath);
        }

        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public static void main(String[] args) {
        String pdfPath = "C:\\Users\\euamo\\OneDrive\\Desktop\\Trabalhos - Java\\nivelamento-estagio\\nivelamento-estagio\\downloads\\Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";

        try {
            String extractedText = extractTextfromPdf(pdfPath);
            System.out.println("Texto extraído do PDF:");
            System.out.println(extractedText);
        } catch (IOException e) {
            System.err.println("Erro ao extrair texto do PDF: " + e.getMessage());
        }
    }
}