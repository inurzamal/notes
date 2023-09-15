import com.javatechie.spring.soap.api.loaneligibility.CustomerRequest;
import com.javatechie.spring.soap.api.loaneligibility.Acknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SoapClient {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public Acknowledgement getLoanStatus(CustomerRequest request) {
        // Set the request and get the response
        Acknowledgement acknowledgement = (Acknowledgement) webServiceTemplate.marshalSendAndReceive(request);
        return acknowledgement;
    }
}
