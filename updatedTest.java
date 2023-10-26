import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OclaSoapClientServiceTest {

    @InjectMocks
    private OclaSoapClientService oclaSoapClientService;

    @Mock
    private HttpsConnector httpsConnector;
    @Mock
    private OclaCommunicationUtility oclaCommunicationUtility;

    @Value("${ocla-connection.oclaUri}")
    private String oclaUri;

    public static AddDocumentCommunicationType createaddDocumentCommunicationType() {
        return Instancio.create(AddDocumentCommunicationType.class);
    }

    @Test
    void senddataToOclaTest() {
        // Create a sample request
        AddDocumentCommunicationType request = createaddDocumentCommunicationType();

        // Create a sample request XML using the utility method
        String sampleRequestXml = TestUtils.createSampleRequestXml();

        // Create a sample response XML
        String sampleResponseXml = "<your sample response XML here>";

        // Mock the behavior of httpsConnector
        doReturn(sampleResponseXml).when(httpsConnector).sendHttpsForStringResponse(eq(oclaUri), eq(sampleRequestXml), anyString());

        // Mock the behavior of oclaCommunicationUtility
        when(oclaCommunicationUtility.saveOCLAResponse(any(), eq(sampleRequestXml), eq(sampleResponseXml), anyString()))
                .thenReturn(true); // Simulate a successful response

        // Call the method under test
        boolean result = oclaSoapClientService.senddataToOcla(request, "sampleStepRateDocumentId");

        // Verify that the saveOCLAResponse method was called
        verify(oclaCommunicationUtility).saveOCLAResponse(any(), eq(sampleRequestXml), eq(sampleResponseXml), anyString());

        // Ensure the result is true since we mocked a successful response
        assertTrue(result);
    }

    @Test
    void senddataToOclaErrorResponseTest() {
        // Create a sample request
        AddDocumentCommunicationType request = createaddDocumentCommunicationType();

        // Create a sample request XML using the utility method
        String sampleRequestXml = TestUtils.createSampleRequestXml();

        // Mock the behavior of httpsConnector to throw an exception
        doThrow(new Exception("Simulated exception")).when(httpsConnector).sendHttpsForStringResponse(eq(oclaUri), eq(sampleRequestXml), anyString());

        // Call the method under test within an assertThrows block to capture the thrown exception
        assertThrows(RuntimeException.class, () -> oclaSoapClientService.senddataToOcla(request, "sampleStepRateDocumentId"));

        // Verify that the saveOclaErrorResponse method was called
        verify(oclaCommunicationUtility).saveOclaErrorResponse(eq(sampleRequestXml), anyString(), eq("sampleStepRateDocumentId"));
    }
}
