package lk.waplak.invoice.auth;

public class AddItem {
    private String item;
    private String unit;
    private String sPrice;
    private String cPrice;
    private String qty;
    private String discount;
    private String bonus;

    public String getCat_base() {
        return Cat_base;
    }

    public String getCat_code() {
        return Cat_code;
    }

    public String getItem_code() {
        return Item_code;
    }

    private String Cat_base;
    private String Cat_code;

    public String getStock_code() {
        return Stock_code;
    }

    public void setStock_code(String stock_code) {
        Stock_code = stock_code;
    }

    private String Stock_code;
    public void setCat_base(String cat_base) {
        Cat_base = cat_base;
    }

    public void setCat_code(String cat_code) {
        Cat_code = cat_code;
    }

    public void setItem_code(String item_code) {
        Item_code = item_code;
    }

    private String Item_code;

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    private String itemKey;
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getcPrice() {
        return cPrice;
    }

    public void setcPrice(String cPrice) {
        this.cPrice = cPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getTotValue() {
        return totValue;
    }

    public void setTotValue(String totValue) {
        this.totValue = totValue;
    }

    private String totValue;


}
