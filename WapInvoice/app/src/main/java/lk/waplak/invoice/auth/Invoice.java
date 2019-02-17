package lk.waplak.invoice.auth;

import java.util.List;

public class Invoice {
    public tbl_T_Transaction_Header invoiceHeader;
    public List<tbl_T_Transaction_Details> itemDetails;
    public String assignTo;

    public tbl_T_Transaction_Header getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(tbl_T_Transaction_Header invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public List<tbl_T_Transaction_Details> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<tbl_T_Transaction_Details> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String user;
}
