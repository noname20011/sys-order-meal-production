package sys_order_meal_healthy.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleSheetConfig {
    @Bean
    public Sheets getSheetsService() throws Exception {
        String secretPath = "/etc/secrets/credentials.json";
        File file = new File(secretPath);
        InputStream inputStream;

        if (file.exists()) {
            // Nếu chạy trên Render, đọc từ đường dẫn tuyệt đối
            inputStream = new FileInputStream(file);
        } else {
            // Nếu chạy ở Local, đọc từ resources (classpath)
            inputStream = new ClassPathResource("credentials.json").getInputStream();
        }

        GoogleCredential credential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Order-Meal-System")
                .build();
    }
}
