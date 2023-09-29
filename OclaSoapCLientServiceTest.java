import com.example.yourpackage.AddDocumentCommunicationType;
import com.example.yourpackage.AddDocumentCommunicationResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OclaSoapClientServiceTest {

    private OclaSoapClientService oclaSoapClientService;

    @Mock
    private Jaxb2Marshaller marshaller;

    @Mock
    private OclaCommunicationUtility oclaCommunicationUtility;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        oclaSoapClientService = new OclaSoapClientService();
        ReflectionTestUtils.setField(oclaSoapClientService, "NAMESPACE", "http://example.com/your-namespace");
        ReflectionTestUtils.setField(oclaSoapClientService, "OCLA_URI", "http://localhost:8080/ws");
        ReflectionTestUtils.setField(oclaSoapClientService, "marshaller", marshaller);
        ReflectionTestUtils.setField(oclaSoapClientService, "oclaCommunicationUtility", oclaCommunicationUtility);
    }

    @Test
    public void sendDataToOCLA_SuccessfulRequest_ReturnsDocumentId() {
        // Arrange
        AddDocumentCommunicationType request = new AddDocumentCommunicationType();
        // Set up your request object here

        JAXBElement<AddDocumentCommunicationResponseType> responseElement = mock(JAXBElement.class);
        when(marshaller.marshalSendAndReceive(anyString(), any(JAXBElement.class)))
            .thenReturn(responseElement);

        AddDocumentCommunicationResponseType acknowledgement = new AddDocumentCommunicationResponseType();
        // Set up your acknowledgement object here

        when(responseElement.getValue()).thenReturn(acknowledgement);

        when(oclaCommunicationUtility.saveOclaResponse(acknowledgement))
            .thenReturn("documentId123");

        // Act
        String documentId = oclaSoapClientService.sendDataToOCLA(request);

        // Assert
        assertEquals("documentId123", documentId);
    }

    @Test
    public void sendDataToOCLA_ErrorInRequest_ThrowsRuntimeException() {
        // Arrange
        AddDocumentCommunicationType request = new AddDocumentCommunicationType();
        // Set up your request object here

        when(marshaller.marshalSendAndReceive(anyString(), any(JAXBElement.class)))
            .thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            oclaSoapClientService.sendDataToOCLA(request);
        });

        assertEquals("Error occurred during SOAP request.", exception.getMessage());
    }
}
