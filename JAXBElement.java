@Service
public class SoapClient {

    @Autowired
    private Jaxb2Marshaller marshaller;
    private WebServiceTemplate template;

    public AddDocumentCommunicationResponseType getLoanStatus(AddDocumentCommunicationType request) {
        template = new WebServiceTemplate(marshaller);

        // Marshal the request using JAXBElement
        JAXBElement<AddDocumentCommunicationType> requestElement = new JAXBElement<>(
            new QName("namespace", "YourRequestElement"), // Specify the XML namespace and element name
            AddDocumentCommunicationType.class,
            request
        );

        // Send the request and receive the response
        JAXBElement<AddDocumentCommunicationResponseType> responseElement = (JAXBElement<AddDocumentCommunicationResponseType>) template.marshalSendAndReceive(
            "http://localhost:8080/ws",
            requestElement
        );

        // Extract the response from the JAXBElement
        AddDocumentCommunicationResponseType response = responseElement.getValue();
        return response;
    }
}
