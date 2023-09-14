import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SoapClient {

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public SoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Acknowledgement getLoanStatus(CustomerRequest request) {
        Acknowledgement acknowledgement = (Acknowledgement) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/ws", request);
        return acknowledgement;
    }
}
