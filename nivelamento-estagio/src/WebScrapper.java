
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WebScrapper {
    private static final String TARGET_URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String DOWNLOAD_FOLDER = "downloads/";
    private static final String ZIP_FILE = "Anexos.zip";

    public static void main(String[] args) {
        try {
          
            Files.createDirectories(Paths.get(DOWNLOAD_FOLDER));

           
            Document doc = Jsoup.connect(TARGET_URL).get();
            for (Element link : doc.select("a[href$=.pdf]")) {
                String fileUrl = link.absUrl("href");
                String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
                downloadFile(fileUrl, DOWNLOAD_FOLDER + fileName);
            }
            
            
            
            zipFiles(DOWNLOAD_FOLDER, ZIP_FILE);
            System.out.println("Processo concluído! Arquivo ZIP criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro de IO ao baixar o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileURL, String savePath) throws IOException {
        System.out.println("Baixando: " + fileURL);        
        File file = new File(savePath);
        int i = 1;
        String newSavePath = savePath;

        while (file.exists()) {
            System.out.println("Arquivo já existe, tentando novo nome: " + newSavePath); // Log para verificação
            newSavePath = savePath.replaceFirst("(\\.[^.]+)$", "(" + i + ")$1");
            file = new File(newSavePath);
            i++;
        }

        System.out.println("Caminho final para salvar: " + newSavePath);  
        
        try (InputStream in = new URL(fileURL).openStream()) {
            System.out.println("Iniciando o download...");
            Files.copy(in, Paths.get(newSavePath));
            System.out.println("Download concluído com sucesso.");
        }
    }
    

    private static void zipFiles(String folderPath, String zipFileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        zipOut.closeEntry();
                    }
                }
            }
        }
    }
}

