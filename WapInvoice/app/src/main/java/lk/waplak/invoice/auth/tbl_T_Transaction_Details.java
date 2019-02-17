package lk.waplak.invoice.auth;

 public class tbl_T_Transaction_Details {
    private String Div_code ;
    private String Mod_code;
    private String St_code ;
    private String Tr_code ;
    private String Doc_No;
    private String Cat_base;
    private String Cat_code;
    private String Item_code;
    private String Stock_code;
    private double QTY;
    private double Balance;
    private String Item_Unit;
    private double Unit_Price_Selling;
    private double Unit_Price_Cost;
    private double Unit_Price_Whole;
    private double Discount_Value;
    private double Item_tax_value;
    private boolean Expiry_Item ;
    private boolean General_Item;
    private String Bar_code;
    private double Free_Qty ;

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

    public String getCat_base() {
        return Cat_base;
    }

    public void setCat_base(String cat_base) {
        Cat_base = cat_base;
    }

    public String getCat_code() {
        return Cat_code;
    }

    public void setCat_code(String cat_code) {
        Cat_code = cat_code;
    }

    public String getItem_code() {
        return Item_code;
    }

    public void setItem_code(String item_code) {
        Item_code = item_code;
    }

    public String getStock_code() {
        return Stock_code;
    }

    public void setStock_code(String stock_code) {
        Stock_code = stock_code;
    }

    public double getQTY() {
        return QTY;
    }

    public void setQTY(double QTY) {
        this.QTY = QTY;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getItem_Unit() {
        return Item_Unit;
    }

    public void setItem_Unit(String item_Unit) {
        Item_Unit = item_Unit;
    }

    public double getUnit_Price_Selling() {
        return Unit_Price_Selling;
    }

    public void setUnit_Price_Selling(double unit_Price_Selling) {
        Unit_Price_Selling = unit_Price_Selling;
    }

    public double getUnit_Price_Cost() {
        return Unit_Price_Cost;
    }

    public void setUnit_Price_Cost(double unit_Price_Cost) {
        Unit_Price_Cost = unit_Price_Cost;
    }

    public double getUnit_Price_Whole() {
        return Unit_Price_Whole;
    }

    public void setUnit_Price_Whole(double unit_Price_Whole) {
        Unit_Price_Whole = unit_Price_Whole;
    }

    public double getDiscount_Value() {
        return Discount_Value;
    }

    public void setDiscount_Value(double discount_Value) {
        Discount_Value = discount_Value;
    }

    public double getItem_tax_value() {
        return Item_tax_value;
    }

    public void setItem_tax_value(double item_tax_value) {
        Item_tax_value = item_tax_value;
    }

    public boolean isExpiry_Item() {
        return Expiry_Item;
    }

    public void setExpiry_Item(boolean expiry_Item) {
        Expiry_Item = expiry_Item;
    }

    public boolean isGeneral_Item() {
        return General_Item;
    }

    public void setGeneral_Item(boolean general_Item) {
        General_Item = general_Item;
    }

    public String getBar_code() {
        return Bar_code;
    }

    public void setBar_code(String bar_code) {
        Bar_code = bar_code;
    }

    public double getFree_Qty() {
        return Free_Qty;
    }

    public void setFree_Qty(double free_Qty) {
        Free_Qty = free_Qty;
    }

    public double getBonus_Qty() {
        return Bonus_Qty;
    }

    public void setBonus_Qty(double bonus_Qty) {
        Bonus_Qty = bonus_Qty;
    }

    private double Bonus_Qty;
}
