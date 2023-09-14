@Configuration
public class SoapConfig {

    @Value("${soap.client.keystore-location}") // Replace with the actual location of your keystore
    private Resource keystoreResource;

    @Value("${soap.client.keystore-password}") // Replace with the keystore password
    private String keystorePassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.javatechie.spring.soap.api.loaneligibility");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() throws Exception {
        WebServiceTemplate template = new WebServiceTemplate(marshaller());

        // Configure the keystore for secure communication
        template.setMessageSender(httpsMessageSender());
        return template;
    }

    @Bean
    public HttpsUrlConnectionMessageSender httpsMessageSender() throws Exception {
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();

        // Configure the keystore for secure communication
        sender.setKeyStore(keystoreResource.getFile());
        sender.setKeyStorePassword(keystorePassword);
        sender.setKeyStoreType("JKS"); // Change to "PKCS12" if you used a PKCS12 keystore

        return sender;
    }
}
