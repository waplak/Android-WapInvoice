package lk.waplak.invoice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.waplak.invoice.auth.AddItem;
import lk.waplak.invoice.auth.Invoice;
import lk.waplak.invoice.auth.JsonPlaceHolderApi;
import lk.waplak.invoice.auth.tbl_T_Transaction_Details;
import lk.waplak.invoice.auth.tbl_T_Transaction_Header;
import lk.waplak.invoice.utility.AndroidUtill;
import lk.waplak.invoice.utility.DownloadedDataCenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InvoiceActivity extends AppCompatActivity {
    private TableLayout inqTradeSTable;
    private Spinner spnAssign;
    private String selectedAssign;
    private EditText discForTot,subTot,taxTot,discTot,netTot;
    private double subTotal,netTotal,totalDiscount;
    private Button reset,save;
    private ProgressDialog dialog ;
    DecimalFormat df2 = new DecimalFormat( "#####0.00" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        inqTradeSTable =findViewById(R.id.inqTradeSTable);
        discForTot = findViewById(R.id.discForTot);
        subTot = findViewById(R.id.subTot);
        taxTot = findViewById(R.id.taxTot);
        discTot = findViewById(R.id.discTot);
        netTot = findViewById(R.id.netTot);
        spnAssign=  findViewById(R.id.spnAssign);
        reset=  findViewById(R.id.rest);
        save=  findViewById(R.id.save);
        final ArrayList<String> assignName =new ArrayList<>();
        final ArrayList<String> assignCode =new ArrayList<>();
        for(int x=0;x<DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadAssignTo().size();++x){
            assignName.add(x,DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadAssignTo().get(x).getUserName());
            assignCode.add(x,DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadAssignTo().get(x).getUserID().trim());
        }
        //selectedLect = lectCode.get(0);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(InvoiceActivity.this, android.R.layout.simple_spinner_item,
                assignName);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnAssign.setAdapter(dataAdapter1);
        spnAssign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAssign = assignCode.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAssign = assignCode.get(0);
            }
        });
        subTotal =0;
        netTotal =0;
        totalDiscount =0;
        loadTableLayouts();



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().clear();
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setSelectedDivition(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setSalesRep(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setCustomer(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setDate(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setRemark(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setpMode(null);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).setActive(false);
                DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadItem().clear();
                DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadAssignTo().clear();
                Intent intent = new Intent(InvoiceActivity.this, NevigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().size()>0) {
                setData();
            }else{
                Toast.makeText(InvoiceActivity.this, "No Data to Save",
                        Toast.LENGTH_SHORT).show();
            }

            }
        });
        discForTot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!"".equals(discForTot.getText().toString()) && Double.parseDouble(discForTot.getText().toString())<=100) {
                    double sTot =0;
                    double Dtot =0;
                    try {
                        sTot = netTotal - (netTotal * Double.parseDouble(discForTot.getText().toString()) / 100);
                        Dtot = totalDiscount + (netTotal * Double.parseDouble(discForTot.getText().toString()) / 100);
                        discTot.setText(df2.format(Dtot));
                        netTot.setText(df2.format(sTot));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }else if(!"".equals(discForTot.getText().toString()) && Double.parseDouble(discForTot.getText().toString())>100){
                    discForTot.setError("Invalid Number");
                    discForTot.invalidate();
                }else{
                    discTot.setText(df2.format(totalDiscount));
                    netTot.setText(df2.format(netTotal));
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(!"".equals(discForTot.getText().toString())){
            discForTot.setError("Please Remove the Total Discount First");
            discForTot.invalidate();
            return;
        }
        InvoiceActivity.super.onBackPressed();

    }
    private void loadTableLayouts(){
        inqTradeSTable.removeAllViews();
        subTotal = 0;
        totalDiscount = 0;
        netTotal = 0;

        int texthead = (int) (getResources().getDimension(R.dimen.boldtext) / getResources().getDisplayMetrics().density);
        int textRow = (int) (getResources().getDimension(R.dimen.normalText) / getResources().getDisplayMetrics().density);

        LayoutParams rowLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f);
        LayoutParams cellLp = new LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f);
        TableRow th = new TableRow(this);

        TextView thCode = new TextView(this);
        thCode.setTextSize(texthead);
        thCode.setTextColor(Color.WHITE);
        thCode.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        thCode.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        thCode.setText("Item Code");
        thCode.setBackgroundResource(R.drawable.raw_border);
        thCode.setPadding(8, 15, 8, 15);
        th.addView(thCode,cellLp);

        TextView thDmgStk = new TextView(this);
        thDmgStk.setTextSize(texthead);
        thDmgStk.setTextColor(Color.WHITE);
        thDmgStk.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        thDmgStk.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        thDmgStk.setText("Unit");
        thDmgStk.setBackgroundResource(R.drawable.raw_border);
        thDmgStk.setPadding(8, 15, 8, 15);
        th.addView(thDmgStk,cellLp);

        TextView thInvVal = new TextView(this);
        thInvVal.setTextSize(texthead);
        thInvVal.setTextColor(Color.WHITE);
        thInvVal.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        thInvVal.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        thInvVal.setText("Quantity");
        thInvVal.setBackgroundResource(R.drawable.raw_border);
        thInvVal.setPadding(8, 15, 8, 15);
        th.addView(thInvVal,cellLp);

        TextView thQuaType = new TextView(this);
        thQuaType.setTextSize(texthead);
        thQuaType.setTextColor(Color.WHITE);
        thQuaType.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        thQuaType.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        thQuaType.setText("Selling Price");
        thQuaType.setBackgroundResource(R.drawable.raw_border);
        thQuaType.setPadding(8, 15, 8, 15);
        th.addView(thQuaType,cellLp);

        TextView thQualifier = new TextView(this);
        thQualifier.setTextSize(texthead);
        thQualifier.setTextColor(Color.WHITE);
        thQualifier.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        thQualifier.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        thQualifier.setText("Item Value");
        thQualifier.setBackgroundResource(R.drawable.raw_border);
        thQualifier.setPadding(8, 15, 8, 15);
        th.addView(thQualifier,cellLp);


        th.setBackgroundResource(R.color.candidate_other);
        inqTradeSTable.addView(th,rowLp);

        if(DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().size()>0) {
            for (int i=0;i<DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().size();++i) {
                final AddItem adItem = DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().get(i);
                if(adItem!=null) {
                    final TableRow tr = new TableRow(this);
                    tr.setTag(i);
                    subTotal=subTotal+(Double.parseDouble(adItem.getsPrice())*Double.parseDouble(adItem.getQty()));
                    totalDiscount =totalDiscount+Double.parseDouble(adItem.getDiscount());
                    netTotal = subTotal-totalDiscount;
                    final TextView txtInvNo = new TextView(this);
                    txtInvNo.setTextSize(textRow);
                    txtInvNo.setTextColor(Color.BLACK);
                    txtInvNo.setTypeface(Typeface.SANS_SERIF);
                    txtInvNo.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    txtInvNo.setText(adItem.getItem());
                    txtInvNo.setBackgroundResource(R.drawable.data_border);
                    txtInvNo.setPadding(8, 15, 8, 15);
                    tr.addView(txtInvNo, cellLp);


                    TextView txtReName = new TextView(this);
                    txtReName.setTextSize(textRow);
                    txtReName.setTextColor(Color.BLACK);
                    txtReName.setTypeface(Typeface.SANS_SERIF);
                    txtReName.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    txtReName.setText(adItem.getUnit());
                    txtReName.setBackgroundResource(R.drawable.data_border);
                    txtReName.setPadding(8, 15, 8, 15);
                    tr.addView(txtReName, cellLp);

                    TextView txtInValue = new TextView(this);
                    txtInValue.setTextSize(textRow);
                    txtInValue.setTextColor(Color.BLACK);
                    txtInValue.setTypeface(Typeface.SANS_SERIF);
                    txtInValue.setGravity(Gravity.RIGHT);
                    txtInValue.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    txtInValue.setText(adItem.getQty());
                    txtInValue.setBackgroundResource(R.drawable.data_border);
                    txtInValue.setPadding(8, 15, 8, 15);
                    tr.addView(txtInValue, cellLp);

                    TextView txtQualiType = new TextView(this);
                    txtQualiType.setTextSize(textRow);
                    txtQualiType.setTextColor(Color.BLACK);
                    txtQualiType.setTypeface(Typeface.SANS_SERIF);
                    txtQualiType.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    txtQualiType.setText(df2.format(Double.parseDouble(adItem.getsPrice())));
                    txtQualiType.setGravity(Gravity.RIGHT);
                    txtQualiType.setBackgroundResource(R.drawable.data_border);
                    txtQualiType.setPadding(8, 15, 8, 15);
                    tr.addView(txtQualiType, cellLp);

                    TextView txtQualifer = new TextView(this);
                    txtQualifer.setTextSize(textRow);
                    txtQualifer.setTextColor(Color.BLACK);
                    txtQualifer.setTypeface(Typeface.SANS_SERIF);
                    txtQualifer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    txtQualifer.setText(df2.format(Double.parseDouble(adItem.getTotValue())));
                    txtQualifer.setGravity(Gravity.RIGHT);
                    txtQualifer.setBackgroundResource(R.drawable.data_border);
                    txtQualifer.setPadding(8, 15, 8, 15);
                    tr.addView(txtQualifer, cellLp);


                    tr.setBackgroundDrawable(getResources().getDrawable(R.drawable.table_shape));
                    tr.setVisibility(View.VISIBLE);
                    inqTradeSTable.addView(tr, rowLp);
                    tr.setOnClickListener(new DoubleClickListener() {
                        @Override
                        public void onDoubleClick() {
                            DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().remove(Integer.parseInt(tr.getTag().toString()));
                            inqTradeSTable.removeView(tr);
                            loadTableLayouts();
                        }
                    });
                }
            }

        }
        subTot.setText(df2.format(subTotal));
        discTot.setText(df2.format(totalDiscount));
        netTot.setText(df2.format(netTotal));
        discForTot.setText("");
    }

    private void changeFeeType(Invoice invoice){
        dialog = new ProgressDialog(
                InvoiceActivity.this,ProgressDialog.THEME_HOLO_DARK);
        this.dialog.setMessage("Save Invoice....");
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Boolean> call = jsonPlaceHolderApi.SaveInvoice(invoice);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    boolean x = response.body();
                    if(x) {
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().clear();
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setSelectedDivition(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setSalesRep(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setCustomer(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setDate(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setRemark(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setpMode(null);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).setActive(false);
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadItem().clear();
                        DownloadedDataCenter.getInstance(InvoiceActivity.this).getLoadAssignTo().clear();
                        Intent intent = new Intent(InvoiceActivity.this, NevigationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        dialog.dismiss();
                    }
                }else{
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void setData(){
        tbl_T_Transaction_Header header = new tbl_T_Transaction_Header();
        header.setDiv_code("PTS");
        header.setMod_code(DownloadedDataCenter.getInstance(InvoiceActivity.this).getSelectedDivition());
        header.setSt_code(DownloadedDataCenter.getInstance(InvoiceActivity.this).getSelectedDivition());
        header.setTr_code("INV");
        header.setDoc_No("");
        header.setDoc_Date(DownloadedDataCenter.getInstance(InvoiceActivity.this).getDate());
        header.setUser_ID(DownloadedDataCenter.getInstance(InvoiceActivity.this).getUserName());
        header.setTr_Total(Double.parseDouble(netTot.getText().toString()));
        header.setTr_Discount(Double.parseDouble(discTot.getText().toString()));
        header.setTr_Refund(0.00);
        header.setTr_Cancel(null);
        header.setRef_Doc_No(DownloadedDataCenter.getInstance(InvoiceActivity.this).getSalesRep());//rep id
        header.setRef_St_Code("");
        header.setPay_Ref_No(DownloadedDataCenter.getInstance(InvoiceActivity.this).getRemark());
        header.setNarration("");
        header.setTr_Tax(0.00);
        header.setTr_PrintIndex(0);
        header.setRef_Tr_Code("");
        header.setTransfer_Location("");
        header.setCust_code(DownloadedDataCenter.getInstance(InvoiceActivity.this).getCustomer());
        header.setSupp_code("");
        header.setDoc_Payment_Type("CRE");
        header.setBHT_no("");

        List<tbl_T_Transaction_Details> list = new ArrayList<>();
        for (int i=0;i<DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().size();++i) {
            AddItem adItem = DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().get(i);
            tbl_T_Transaction_Details detail = new tbl_T_Transaction_Details();
            detail.setDiv_code("PTS");
            detail.setMod_code(DownloadedDataCenter.getInstance(InvoiceActivity.this).getSelectedDivition());
            detail.setSt_code(DownloadedDataCenter.getInstance(InvoiceActivity.this).getSelectedDivition());
            detail.setTr_code("INV");
            detail.setDoc_No("");
            detail.setCat_base(adItem.getCat_base());//when item load
            detail.setCat_code(adItem.getCat_code());//when item load
            detail.setItem_code(adItem.getItem());
            detail.setStock_code(adItem.getStock_code());//when item load
            detail.setQTY(Double.parseDouble(adItem.getQty()));
            detail.setBalance(0.00);
            detail.setItem_Unit(adItem.getUnit());
            detail.setUnit_Price_Selling(Double.parseDouble(adItem.getsPrice()));
            detail.setUnit_Price_Cost(Double.parseDouble(adItem.getcPrice()));
            detail.setUnit_Price_Whole(0.0);
            detail.setDiscount_Value(Double.parseDouble(adItem.getDiscount()));
            detail.setItem_tax_value(0.00);
            detail.setExpiry_Item(false);
            detail.setGeneral_Item(false);
            detail.setBar_code("");
            detail.setFree_Qty(0.00);
            detail.setBonus_Qty(0.00);
            list.add(detail);
        }
        Invoice invoice = new Invoice();
        invoice.setInvoiceHeader(header);
        invoice.setItemDetails(list);
        invoice.setAssignTo(selectedAssign);
        invoice.setUser(DownloadedDataCenter.getInstance(InvoiceActivity.this).getUserName());
        changeFeeType(invoice);
    }
    public abstract class DoubleClickListener implements View.OnClickListener {

        // The time in which the second tap should be done in order to qualify as
        // a double click
        private static final long DEFAULT_QUALIFICATION_SPAN = 200;
        private long doubleClickQualificationSpanInMillis;
        private long timestampLastClick;

        public DoubleClickListener() {
            doubleClickQualificationSpanInMillis = DEFAULT_QUALIFICATION_SPAN;
            timestampLastClick = 0;
        }

        public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
            this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
            timestampLastClick = 0;
        }

        @Override
        public void onClick(View v) {
            if((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
                onDoubleClick();
            }
            timestampLastClick = SystemClock.elapsedRealtime();
        }

        public abstract void onDoubleClick();

    }
}
