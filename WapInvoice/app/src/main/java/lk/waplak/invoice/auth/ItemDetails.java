package lk.waplak.invoice.auth;

public class ItemDetails {
    private String St_code;
    private String Cat_base;
    private String Cat_code;
    private String Item_code;
    private String Item_Unit;
    private double Item_P_Cost;
    private double Item_P_Selling;
    private String Stock_code;
    private double Item_P_Whole;
    private double Qty_inhand;
    private String Item_Inv_Print_name;

    public String getSt_code() {
        return St_code;
    }

    public String getCat_base() {
        return Cat_base;
    }

    public String getCat_code() {
        return Cat_code;
    }

    public String getItem_code() {
        return Item_code;
    }

    public String getItem_Unit() {
        return Item_Unit;
    }

    public double getItem_P_Cost() {
        return Item_P_Cost;
    }

    public double getItem_P_Selling() {
        return Item_P_Selling;
    }

    public String getStock_code() {
        return Stock_code;
    }

    public double getItem_P_Whole() {
        return Item_P_Whole;
    }

    public double getQty_inhand() {
        return Qty_inhand;
    }

    public String getItem_Inv_Print_name() {
        return Item_Inv_Print_name;
    }

    public String getItem_Barcode() {
        return Item_Barcode;
    }

    private String Item_Barcode;

}
