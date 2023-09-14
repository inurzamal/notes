import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.javatechie.spring.soap.api.loaneligibility");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(marshaller());

        // Create an HttpsUrlConnectionMessageSender with SSL configuration
        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();

        // Set SSL properties
        messageSender.setSslContext(createSSLContext()); // Define createSSLContext() method

        // Assign the message sender to the WebServiceTemplate
        webServiceTemplate.setMessageSender(messageSender);

        return webServiceTemplate;
    }

    // Define a method to create and configure the SSLContext
    private SSLContext createSSLContext() {
        try {
            // Load your keystore and truststore
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(getClass().getResourceAsStream("/path/to/your/keystore.p12"), "keystorePassword".toCharArray());

            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(getClass().getResourceAsStream("/path/to/your/truststore.jks"), "truststorePassword".toCharArray());

            // Set up the SSL context with your keystore and truststore
            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "keystorePassword".toCharArray())
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .build();

            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Error creating SSLContext", e);
        }
    }
}
