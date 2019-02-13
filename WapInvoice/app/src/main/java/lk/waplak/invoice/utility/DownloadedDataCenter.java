package lk.waplak.invoice.utility;

import android.content.Context;

import java.util.ArrayList;

import lk.waplak.invoice.auth.AddItem;
import lk.waplak.invoice.auth.Assigner;
import lk.waplak.invoice.auth.Customer;
import lk.waplak.invoice.auth.Division;
import lk.waplak.invoice.auth.InvResponse;
import lk.waplak.invoice.auth.Item;
import lk.waplak.invoice.auth.SalesRep;


public class DownloadedDataCenter {
    private final Context myContext;
    private static DownloadedDataCenter mdownloadedInstence;
    private ArrayList<Division> loadDivition = new ArrayList<Division>();
    private ArrayList<SalesRep> loadSalesRep = new ArrayList<SalesRep>();
    private ArrayList<Customer> loadCustomer = new ArrayList<Customer>();
    private ArrayList<Item> loadItem = new ArrayList<Item>();
    private ArrayList<Assigner> loadAssignTo = new ArrayList<Assigner>();
    private String salesRep;
    private String date;
    private String remark;
    private ArrayList<AddItem> addItemList = new ArrayList<AddItem>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active;
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    private String customer;

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getpMode() {
        return pMode;
    }

    public void setpMode(String pMode) {
        this.pMode = pMode;
    }

    private String pMode;
    public String getSelectedItemCode() {
        return selectedItemCode;
    }

    public void setSelectedItemCode(String selectedItemCode) {
        this.selectedItemCode = selectedItemCode;
    }

    private String selectedItemCode;
    public String getSelectedDivition() {
        return selectedDivition;
    }

    public void setSelectedDivition(String selectedDivition) {
        this.selectedDivition = selectedDivition;
    }

    private String selectedDivition;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;


    private DownloadedDataCenter(Context context) {
        this.myContext = context;
    }

    public static synchronized DownloadedDataCenter getInstance(Context context) {
        try{
            if (mdownloadedInstence == null) {
                mdownloadedInstence = new DownloadedDataCenter(context);
            }
            return mdownloadedInstence;
        } catch (Exception e) {
            throw new Error("Error  ");
        }
    }
    public ArrayList<Division> getLoadDivition() {
        return loadDivition;
    }
    public ArrayList<SalesRep> getLoadSalesRep() {
        return loadSalesRep;
    }
    public ArrayList<Customer> getLoadCustomer() {
        return loadCustomer;
    }
    public ArrayList<Item> getLoadItem() {
        return loadItem;
    }
    public ArrayList<Assigner> getLoadAssignTo() {
        return loadAssignTo;
    }
    public ArrayList<AddItem> getAddItemList() {
        return addItemList;
    }

    public void setLoadDivition(Division test) {
        this.loadDivition.add(test);
    }
    public void setLoadSalesRep(SalesRep test) {
        this.loadSalesRep.add(test);
    }
    public void setLoadCustomer(Customer test) {
        this.loadCustomer.add(test);
    }
    public void setLoadItem(Item test) {
        this.loadItem.add(test);
    }
    public void setLoadAssignTo(Assigner test) {
        this.loadAssignTo.add(test);
    }
    public void setAddItemList(AddItem test) {
        this.addItemList.add(test);
    }

}
