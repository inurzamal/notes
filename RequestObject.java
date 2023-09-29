import com.example.yourpackage.AddDocumentCommunicationType;
import com.example.yourpackage.DocumentElementListType;
import com.example.yourpackage.MetaDataElementListType;
import com.example.yourpackage.UserDefinedElementListType;
import com.example.yourpackage.DocumentElementType;
import com.example.yourpackage.MetaDataElementType;
import com.example.yourpackage.UserDefinedElementType;

public class RequestObjectFactory {

    public static AddDocumentCommunicationType createRequestObject() {
        AddDocumentCommunicationType request = new AddDocumentCommunicationType();

        // Create and set MetaDataElementList
        MetaDataElementListType metaDataElementList = new MetaDataElementListType();
        for (int i = 1; i <= 2; i++) {
            MetaDataElementType metaDataElement = new MetaDataElementType();
            metaDataElement.setName("MetaField" + i);
            metaDataElement.setValue("MetaValue" + i);
            metaDataElementList.getMetaDataElement().add(metaDataElement);
        }
        request.setMetaDataElementList(metaDataElementList);

        // Create and set DocumentElementList
        DocumentElementListType documentElementList = new DocumentElementListType();
        for (int i = 1; i <= 2; i++) {
            DocumentElementType documentElement = new DocumentElementType();
            documentElement.setName("DocumentField" + i);
            documentElement.setValue("DocumentValue" + i);
            documentElementList.getDocumentElement().add(documentElement);
        }
        request.setDocumentElementList(documentElementList);

        // Create and set UserDefinedElementList
        UserDefinedElementListType userDefinedElementList = new UserDefinedElementListType();
        for (int i = 1; i <= 2; i++) {
            UserDefinedElementType userDefinedElement = new UserDefinedElementType();
            userDefinedElement.setName("UserDefinedField" + i);
            userDefinedElement.setValue("UserDefinedValue" + i);
            userDefinedElementList.getUserDefinedElement().add(userDefinedElement);
        }
        request.setUserDefinedElementList(userDefinedElementList);

        // Set the base64-encoded PDF document
        request.setPdfDocument("base64_encoded_pdf_data_here");

        return request;
    }
}
