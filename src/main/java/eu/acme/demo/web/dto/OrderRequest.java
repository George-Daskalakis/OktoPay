package eu.acme.demo.web.dto;

public class OrderRequest {

    String clientReferenceCode;

    public String getClientReferenceCode() {
        return clientReferenceCode;
    }

    public void setClientReferenceCode(String clientReferenceCode) {
        this.clientReferenceCode = clientReferenceCode;
    }

    //TODO: place required fields in order to create an order submitted by client
}
