package lk.waplak.invoice.auth;

import java.util.Date;

public class tbl_T_Transaction_Header {
    private String Div_code;
    private String Mod_code;
    private String St_code ;
    private String Tr_code ;
    private String Doc_No ;
    public String Doc_Date ;
    private String User_ID ;
    public double Tr_Total ;
    public double Tr_Discount ;
    public double Tr_Refund ;
    public String Tr_Cancel ;
    private String Ref_Doc_No ;
    private String Ref_St_Code ;
    private String Ref_Tr_Code ;
    private String Transfer_Location ;
    private String Cust_code ;
    private String Supp_code ;
    private String Doc_Payment_Type ;
    private String Pay_Ref_No ;
    private String Narration ;
    private String cus_1 ;
    private String cus_2 ;
    private String cus_3 ;
    private String cus_4 ;
    private String cus_5 ;
    private String BHT_no ;
    private String Tr_cancel_userid ;
    public double Tr_Tax ;

    public String getDiv_code() {
        return Div_code;
    }

    public void setDiv_code(String div_code) {
        Div_code = div_code;
    }


    public String getMod_code() {
        return Mod_code;
    }

    public void setMod_code(String mod_code) {
        Mod_code = mod_code;
    }

    public String getSt_code() {
        return St_code;
    }

    public void setSt_code(String st_code) {
        St_code = st_code;
    }

    public String getTr_code() {
        return Tr_code;
    }

    public void setTr_code(String tr_code) {
        Tr_code = tr_code;
    }

    public String getDoc_No() {
        return Doc_No;
    }

    public void setDoc_No(String doc_No) {
        Doc_No = doc_No;
    }

    public String getDoc_Date() {
        return Doc_Date;
    }

    public void setDoc_Date(String doc_Date) {
        Doc_Date = doc_Date;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public double getTr_Total() {
        return Tr_Total;
    }

    public void setTr_Total(double tr_Total) {
        Tr_Total = tr_Total;
    }

    public double getTr_Discount() {
        return Tr_Discount;
    }

    public void setTr_Discount(double tr_Discount) {
        Tr_Discount = tr_Discount;
    }

    public double getTr_Refund() {
        return Tr_Refund;
    }

    public void setTr_Refund(double tr_Refund) {
        Tr_Refund = tr_Refund;
    }

    public String getTr_Cancel() {
        return Tr_Cancel;
    }

    public void setTr_Cancel(String tr_Cancel) {
        Tr_Cancel = tr_Cancel;
    }

    public String getRef_Doc_No() {
        return Ref_Doc_No;
    }

    public void setRef_Doc_No(String ref_Doc_No) {
        Ref_Doc_No = ref_Doc_No;
    }

    public String getRef_St_Code() {
        return Ref_St_Code;
    }

    public void setRef_St_Code(String ref_St_Code) {
        Ref_St_Code = ref_St_Code;
    }

    public String getRef_Tr_Code() {
        return Ref_Tr_Code;
    }

    public void setRef_Tr_Code(String ref_Tr_Code) {
        Ref_Tr_Code = ref_Tr_Code;
    }

    public String getTransfer_Location() {
        return Transfer_Location;
    }

    public void setTransfer_Location(String transfer_Location) {
        Transfer_Location = transfer_Location;
    }

    public String getCust_code() {
        return Cust_code;
    }

    public void setCust_code(String cust_code) {
        Cust_code = cust_code;
    }

    public String getSupp_code() {
        return Supp_code;
    }

    public void setSupp_code(String supp_code) {
        Supp_code = supp_code;
    }

    public String getDoc_Payment_Type() {
        return Doc_Payment_Type;
    }

    public void setDoc_Payment_Type(String doc_Payment_Type) {
        Doc_Payment_Type = doc_Payment_Type;
    }

    public String getPay_Ref_No() {
        return Pay_Ref_No;
    }

    public void setPay_Ref_No(String pay_Ref_No) {
        Pay_Ref_No = pay_Ref_No;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
    }

    public String getCus_1() {
        return cus_1;
    }

    public void setCus_1(String cus_1) {
        this.cus_1 = cus_1;
    }

    public String getCus_2() {
        return cus_2;
    }

    public void setCus_2(String cus_2) {
        this.cus_2 = cus_2;
    }

    public String getCus_3() {
        return cus_3;
    }

    public void setCus_3(String cus_3) {
        this.cus_3 = cus_3;
    }

    public String getCus_4() {
        return cus_4;
    }

    public void setCus_4(String cus_4) {
        this.cus_4 = cus_4;
    }

    public String getCus_5() {
        return cus_5;
    }

    public void setCus_5(String cus_5) {
        this.cus_5 = cus_5;
    }

    public String getBHT_no() {
        return BHT_no;
    }

    public void setBHT_no(String BHT_no) {
        this.BHT_no = BHT_no;
    }

    public String getTr_cancel_userid() {
        return Tr_cancel_userid;
    }

    public void setTr_cancel_userid(String tr_cancel_userid) {
        Tr_cancel_userid = tr_cancel_userid;
    }

    public double getTr_Tax() {
        return Tr_Tax;
    }

    public void setTr_Tax(double tr_Tax) {
        Tr_Tax = tr_Tax;
    }

    public int getTr_PrintIndex() {
        return Tr_PrintIndex;
    }

    public void setTr_PrintIndex(int tr_PrintIndex) {
        Tr_PrintIndex = tr_PrintIndex;
    }

    public int Tr_PrintIndex ;
}
