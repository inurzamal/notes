import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustSelfSignedStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class SoapConfig {

    @Value("${soap.client.keystore.path}") // Replace with the actual keystore path
    private String keystorePath;

    @Value("${soap.client.keystore.password}") // Replace with the keystore password
    private String keystorePassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.javatechie.spring.soap.api.loaneligibility");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(marshaller());

        // Create an HttpComponentsMessageSender with SSL configuration
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();

        // Set SSL properties
        messageSender.setHttpClient(createHttpClientWithSSL());

        // Assign the message sender to the WebServiceTemplate
        webServiceTemplate.setMessageSender(messageSender);

        return webServiceTemplate;
    }

    // Define a method to create and configure the HttpClient with SSL
    private HttpClient createHttpClientWithSSL() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream inputStream = new FileInputStream(keystorePath)) {
                keyStore.load(inputStream, keystorePassword.toCharArray());
            }

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, keystorePassword.toCharArray())
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();

            return HttpClients.custom()
                    .setSSLContext(sslContext)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating HttpClient with SSL", e);
        }
    }
}
