package eu.acme.demo.web.dto;
import eu.acme.demo.domain.enums.OrderStatus;
import java.math.BigDecimal;

public class OrderRequest {

    private String clientReferenceCode;
    private String description;
    private BigDecimal itemTotalAmount;
    private int itemCount;
    private OrderStatus status;

    public String getClientReferenceCode() {
        return clientReferenceCode;
    }

    public void setClientReferenceCode(String clientReferenceCode) {
        this.clientReferenceCode = clientReferenceCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setItemTotalAmount(BigDecimal itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public BigDecimal getItemTotalAmount() {
        return itemTotalAmount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemCount() {
        return itemCount;
    }




    //TODO: place required fields in order to create an order submitted by client
}
