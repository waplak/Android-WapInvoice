package lk.waplak.invoice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import lk.waplak.invoice.auth.AddItem;
import lk.waplak.invoice.utility.DownloadedDataCenter;

public class InvoiceActivity extends AppCompatActivity {
    private TableLayout inqTradeSTable;
    private Spinner spnAssign;
    private String selectedAssign;
    private EditText totDisc,subTot,taxTot,discTot,netTot;
    private double subTotal,netTotal,totalDiscount;
    private Button reset,save;
    DecimalFormat df2 = new DecimalFormat( "#####0.00" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        inqTradeSTable =findViewById(R.id.inqTradeSTable);
        //totDisc = findViewById(R.id.totDisc);
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

        subTot.setText(df2.format(subTotal));
        discTot.setText(df2.format(totalDiscount));
        netTot.setText(df2.format(netTotal));

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
    }
    private void loadTableLayouts(){
        //TextSize
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
                AddItem adItem = DownloadedDataCenter.getInstance(InvoiceActivity.this).getAddItemList().get(i);
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
                    tr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }

        }
    }
}
