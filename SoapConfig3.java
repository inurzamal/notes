import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

@Configuration
public class SoapConfig {

    @Value("${soap.service-url}")
    private String serviceUrl;

    @Value("${soap.keystore-location}")
    private Resource keystoreLocation;

    @Value("${soap.keystore-password}")
    private String keystorePassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.javatechie.spring.soap.api.loaneligibility");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate template = new WebServiceTemplate(marshaller());

        // Choose the appropriate message sender based on your keystore type (JKS or PKCS12)
        template.setMessageSender(httpsMessageSender());

        // Set the service URL
        template.setDefaultUri(serviceUrl);

        return template;
    }

    // Define a method to create and configure the HttpClient with SSL
    private HttpComponentsMessageSender httpsMessageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();

        // Configure the keystore for secure communication
        messageSender.setHttpClient(createHttpClientWithSSL());

        return messageSender;
    }

    // Define a method to create and configure the HttpClient with SSL
    private HttpsUrlConnectionMessageSender httpsMessageSender() {
        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();

        // Configure the keystore for secure communication
        messageSender.setKeyStore(keystoreLocation);
        messageSender.setKeyStorePassword(keystorePassword);
        messageSender.setKeyStoreType("JKS"); // Change to "PKCS12" if you used a PKCS12 keystore

        return messageSender;
    }

    // Define a method to create and configure the HttpClient with SSL
    private HttpClient createHttpClientWithSSL() {
        try {
            // Load the keystore from the provided location
            KeyStore keyStore = KeyStore.getInstance("JKS"); // Change to "PKCS12" if you used a PKCS12 keystore
            keyStore.load(keystoreLocation.getInputStream(), keystorePassword.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, keystorePassword.toCharArray())
                    .build();

            return HttpClients.custom()
                    .setSSLContext(sslContext)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating HttpClient with SSL", e);
        }
    }
}
