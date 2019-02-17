package lk.waplak.invoice.scanner.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import lk.waplak.invoice.InvoiceActivity;
import lk.waplak.invoice.LoginActivity;
import lk.waplak.invoice.R;
import lk.waplak.invoice.auth.AddItem;
import lk.waplak.invoice.auth.Assigner;
import lk.waplak.invoice.auth.Item;
import lk.waplak.invoice.auth.ItemDetails;
import lk.waplak.invoice.auth.JsonPlaceHolderApi;
import lk.waplak.invoice.utility.AndroidUtill;
import lk.waplak.invoice.utility.DownloadedDataCenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddInvoiceFragment extends Fragment {
    DecimalFormat df2 = new DecimalFormat( "#####0.00" );
    private Spinner spRep,spDiv,spRemark,spnMPrice,spnUnit,autoItem;
    private String selectedRep,selectedDiv,selectedCustomer,selectedRemark,selectedMPrice,selectedunit,selectedItem;
    private AutoCompleteTextView autoCustomer;
    private DatePickerDialog datePickerDialog;
    private ImageView selectDate;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    private EditText date,inStock,sPrice,cPrice,quantity,totValue,discount,bonus;
    private Button addButton,nextButton;
    private ProgressDialog dialog ;
    private LinkedHashMap<String,String> custCode;
    private LinkedHashMap<String,String> itemCode;
    private double ItemByQty,disValue;
    private String Cat_base,Cat_code,Stock_code;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String [] remarkArray = {"CASH","21 days","30 days"};
    private String [] priceMArray ={"NOR-Normal Price","WSL-Whole Sale Price"};
    private String [] unitArray = {"NOS"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_invoice, container, false);
        spDiv =    rootView.findViewById(R.id.spnDiv);
        spRep =  rootView.findViewById(R.id.spnRep);
        spRemark=  rootView.findViewById(R.id.spnRemk);
        spnMPrice=  rootView.findViewById(R.id.spnPriceM);
        spnUnit=  rootView.findViewById(R.id.spnUnit);
        autoCustomer =  rootView.findViewById(R.id.autoCust);
        autoItem =  rootView.findViewById(R.id.autoItem);
        selectDate = rootView.findViewById(R.id.calBt);
        date = rootView.findViewById(R.id.date);
        inStock = rootView.findViewById(R.id.inStock);
        sPrice = rootView.findViewById(R.id.sPrice);
        cPrice = rootView.findViewById(R.id.cPrice);
        quantity = rootView.findViewById(R.id.quantity);
        addButton = rootView.findViewById(R.id.btn_add);
        nextButton = rootView.findViewById(R.id.btn_next);
        totValue = rootView.findViewById(R.id.totValue);
        discount= rootView.findViewById(R.id.discount);
        bonus = rootView.findViewById(R.id.bonus);
        if(!DownloadedDataCenter.getInstance(getActivity()).isActive()) {
            String currentDate = df.format(c);
            date.setText(currentDate);
            ItemByQty = 0;
            DownloadedDataCenter.getInstance(getActivity()).setSelectedDivition(null);
            DownloadedDataCenter.getInstance(getActivity()).setSalesRep(null);
            DownloadedDataCenter.getInstance(getActivity()).setCustomer(null);
            DownloadedDataCenter.getInstance(getActivity()).setDate(null);
            DownloadedDataCenter.getInstance(getActivity()).setRemark(null);
            DownloadedDataCenter.getInstance(getActivity()).setpMode(null);
            DownloadedDataCenter.getInstance(getActivity()).setActive(false);

            DownloadedDataCenter.getInstance(getActivity()).setDate(currentDate);
            loadSpinners();
        }
//        selectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                date.setText(month + 1 + "/" + (day) + "/" + year);
//                            }
//                        }, year, month, dayOfMonth);
//                datePickerDialog.show();
//            }
//        });
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                date.setText(month + 1+ "/" + day + "/" + year);
//                                DownloadedDataCenter.getInstance(getActivity()).setDate(date.getText().toString());
//                            }
//                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                datePickerDialog.show();
//            }
//        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(autoCustomer.getText().toString())){
                    autoCustomer.invalidate();
                    autoCustomer.setError("Please Enter the Customer");
                    return;
                }
                if(!custCode.containsKey(autoCustomer.getText().toString())){
                    autoCustomer.invalidate();
                    autoCustomer.setError("Please Enter the Valid Customer");
                    return;
                }
                if(DownloadedDataCenter.getInstance(getActivity()).getSelectedItemCode()==null){
                    TextView errorText = (TextView)autoItem.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please Select the Item");
                return;
                }
                if("".equals(quantity.getText().toString())){
                    quantity.invalidate();
                    quantity.setError("Please Enter the Quantity");
                    return;
                }
                if(Integer.parseInt(quantity.getText().toString())==0){
                    quantity.invalidate();
                    quantity.setError("Please Enter Valid Quantity");
                    return;
                }
                DownloadedDataCenter.getInstance(getActivity()).setCustomer(custCode.get(autoCustomer.getText().toString()));

                AddItem addItem = new AddItem();
                addItem.setItem(DownloadedDataCenter.getInstance(getActivity()).getSelectedItemCode());
                addItem.setUnit(selectedunit);
                addItem.setsPrice(sPrice.getText().toString());
                addItem.setcPrice(cPrice.getText().toString());
                addItem.setQty(quantity.getText().toString());
                addItem.setDiscount(disValue+"");
                addItem.setCat_code(Cat_code);
                addItem.setCat_base(Cat_base);
                addItem.setStock_code(Stock_code);
                //addItem.setBonus();
                addItem.setTotValue(totValue.getText().toString());

                DownloadedDataCenter.getInstance(getActivity()).setAddItemList(addItem);

                quantity.setText("");
                discount.setText("");
                totValue.setText("");
                bonus.setText("");
                Toast.makeText(getActivity(), "Item Successfully Added",
                        Toast.LENGTH_SHORT).show();
            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ItemByQty =0;
                disValue =0;
            if(!"".equals(quantity.getText().toString())&&!"".equals(sPrice.getText().toString())) {
                if(!"".equals(discount.getText().toString()) && Double.parseDouble(discount.getText().toString())<=100) {
                    ItemByQty = Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(sPrice.getText().toString());
                    disValue = ItemByQty*Double.parseDouble(discount.getText().toString())/100;
                    totValue.setText(ItemByQty-disValue + "");
                }else{
                    ItemByQty = Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(sPrice.getText().toString());
                    totValue.setText(ItemByQty + "");
                }
            }

            }
        });
        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ItemByQty = 0;
                disValue = 0;
                if(!"".equals(quantity.getText().toString())&&!"".equals(sPrice.getText().toString()) && !"".equals(discount.getText().toString())) {
                    if(Double.parseDouble(discount.getText().toString())<=100) {
                        ItemByQty = Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(sPrice.getText().toString());
                        disValue = ItemByQty*Double.parseDouble(discount.getText().toString())/100;
                        totValue.setText(ItemByQty-disValue + "");
                    }else{
                        discount.setError("Please Enter Valid percentage");
                        discount.invalidate();
                    }
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DownloadedDataCenter.getInstance(getActivity()).getAddItemList().size()>0) {
                    DownloadedDataCenter.getInstance(getActivity()).setActive(true);
                    Intent intent = new Intent(getActivity(), InvoiceActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "No Item to Invoice",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        sPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ItemByQty = 0;
                disValue = 0;
                if(!"".equals(quantity.getText().toString())&&!"".equals(sPrice.getText().toString())) {
                    if(!"".equals(discount.getText().toString()) && Double.parseDouble(discount.getText().toString())<=100) {
                        ItemByQty = Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(sPrice.getText().toString());
                        disValue = ItemByQty*Double.parseDouble(discount.getText().toString())/100;
                        totValue.setText(ItemByQty-disValue + "");
                    }else{
                        ItemByQty = Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(sPrice.getText().toString());
                        totValue.setText(ItemByQty + "");
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        // getActivity().setTitle("Fragment 1");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void loadSpinners(){

        final ArrayList<String> remarkList = new ArrayList<String>(Arrays.asList(remarkArray));

        final ArrayList<String> DivName =new ArrayList<>();
        final ArrayList<String> DivCode =new ArrayList<>();
        for(int x=0;x<DownloadedDataCenter.getInstance(getActivity()).getLoadDivition().size();++x){
            DivName.add(x,DownloadedDataCenter.getInstance(getActivity()).getLoadDivition().get(x).getMod_code()+"|"+DownloadedDataCenter.getInstance(getActivity()).getLoadDivition().get(x).getMod_Name());
            DivCode.add(x,DownloadedDataCenter.getInstance(getActivity()).getLoadDivition().get(x).getMod_code().trim());
        }
        //selectedLect = lectCode.get(0);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                DivName);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDiv.setAdapter(dataAdapter1);
        spDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDiv = DivCode.get(position);
                DownloadedDataCenter.getInstance(getActivity()).setSelectedDivition(selectedDiv);
                DownloadedDataCenter.getInstance(getActivity()).getLoadItem().clear();
                DownloadedDataCenter.getInstance(getActivity()).getLoadAssignTo().clear();
                loadItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDiv = DivCode.get(0);
                DownloadedDataCenter.getInstance(getActivity()).setSelectedDivition(selectedDiv);
                DownloadedDataCenter.getInstance(getActivity()).getLoadItem().clear();
                DownloadedDataCenter.getInstance(getActivity()).getLoadAssignTo().clear();
            }
        });
        final ArrayList<String> repName =new ArrayList<>();
        final ArrayList<String> repCode =new ArrayList<>();
        for(int x=0;x<DownloadedDataCenter.getInstance(getActivity()).getLoadSalesRep().size();++x){
            repName.add(x,DownloadedDataCenter.getInstance(getActivity()).getLoadSalesRep().get(x).getRep_Name());
            repCode.add(x,DownloadedDataCenter.getInstance(getActivity()).getLoadSalesRep().get(x).getRep_Code());
        }
        //selectedCource = courseCode.get(0);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                repName);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRep.setAdapter(dataAdapter2);
        spRep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRep = repCode.get(position);
                DownloadedDataCenter.getInstance(getActivity()).setSalesRep(selectedRep);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRep = repCode.get(0);
                DownloadedDataCenter.getInstance(getActivity()).setSalesRep(selectedRep);
            }
        });
        final ArrayList<String> custName =new ArrayList<>();
        custCode =new LinkedHashMap<>();
        custCode.clear();
        for(int x=0;x<DownloadedDataCenter.getInstance(getActivity()).getLoadCustomer().size();++x) {
            custName.add(x, DownloadedDataCenter.getInstance(getActivity()).getLoadCustomer().get(x).getPat_Name().trim());
            custCode.put(DownloadedDataCenter.getInstance(getActivity()).getLoadCustomer().get(x).getPat_Name().trim()
                    , DownloadedDataCenter.getInstance(getActivity()).getLoadCustomer().get(x).getPat_code().trim());
        }
        //selectedCenter = centerCode.get(0);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                custName);
        autoCustomer.setAdapter(dataAdapter3);
        autoCustomer.setThreshold(1);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                remarkList);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRemark.setAdapter(dataAdapter4);
        spRemark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRemark = remarkList.get(position);
                DownloadedDataCenter.getInstance(getActivity()).setRemark(selectedRemark);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRemark = remarkList.get(0);
                DownloadedDataCenter.getInstance(getActivity()).setRemark(selectedRemark);
            }
        });
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                priceMArray);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMPrice.setAdapter(dataAdapter5);
        spnMPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMPrice = priceMArray[position];
                DownloadedDataCenter.getInstance(getActivity()).setpMode(selectedMPrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMPrice = priceMArray[0];
                DownloadedDataCenter.getInstance(getActivity()).setpMode(selectedMPrice);
            }
        });
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                unitArray);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUnit.setAdapter(dataAdapter6);
        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedunit = unitArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedunit = unitArray[0];
            }
        });

    }
    private void loadItems(){
        DownloadedDataCenter.getInstance(getActivity()).setSelectedItemCode(null);
        dialog = new ProgressDialog(
                getActivity(),ProgressDialog.THEME_HOLO_DARK);
        this.dialog.setMessage("Loading....");
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Item>> call = jsonPlaceHolderApi.getLoadItemsByDivision(DownloadedDataCenter.getInstance(getActivity()).getSelectedDivition());
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(!response.isSuccessful()){
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getActivity(),"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Item> itemList = response.body();
                    for(Item item:itemList){
                        if(item.getItem_code()!=null) {
                            DownloadedDataCenter.getInstance(getActivity()).setLoadItem(item);
                        }
                    }
                    final ArrayList<String> itemName =new ArrayList<>();
                    itemCode =new LinkedHashMap<>();
                    itemCode.clear();
                    itemName.add(0,"Select Item");
                    itemCode.put("Select Item",null);
                    int y = 1;
                    for(int x=1;x<=DownloadedDataCenter.getInstance(getActivity()).getLoadItem().size();++x) {
                        if(!"".equals(DownloadedDataCenter.getInstance(getActivity()).getLoadItem().get(x-1).getItem_Inv_Print_name().trim())) {
                            itemName.add(y, DownloadedDataCenter.getInstance(getActivity()).getLoadItem().get(x-1).getItem_Inv_Print_name().trim());
                            itemCode.put(DownloadedDataCenter.getInstance(getActivity()).getLoadItem().get(x-1).getItem_Inv_Print_name().trim()
                                    , DownloadedDataCenter.getInstance(getActivity()).getLoadItem().get(x-1).getItem_code().trim());
                            ++y;
                        }
                    }
                    ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                            itemName);
                    dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    autoItem.setAdapter(dataAdapter7);
                    autoItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedItem = itemName.get(position);
                            quantity.setText("");
                            totValue.setText("");
                            discount.setText("");
                            DownloadedDataCenter.getInstance(getActivity()).setSelectedItemCode(itemCode.get(selectedItem));
                            DownloadedDataCenter.getInstance(getActivity()).getLoadAssignTo().clear();
                            loadAssigners();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            selectedItem = itemName.get(0);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void loadAssigners(){
        if(!dialog.isShowing()){
            dialog = new ProgressDialog(
                    getActivity(),ProgressDialog.THEME_HOLO_DARK);
            this.dialog.setMessage("Loading....");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Assigner>> call = jsonPlaceHolderApi.getLoadAssignUserByDivision(DownloadedDataCenter.getInstance(getActivity()).getSelectedDivition());
        call.enqueue(new Callback<List<Assigner>>() {
            @Override
            public void onResponse(Call<List<Assigner>> call, Response<List<Assigner>> response) {
                if(!response.isSuccessful()){
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getActivity(),"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Assigner> assignerList = response.body();
                    for(Assigner assign:assignerList){
                        if(assign.getUserID()!=null) {
                            DownloadedDataCenter.getInstance(getActivity()).setLoadAssignTo(assign);
                        }
                    }
                    loadItemDetails();


                }
            }

            @Override
            public void onFailure(Call<List<Assigner>> call, Throwable t) {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void loadItemDetails(){
        Cat_base =null;
        Cat_code = null;
        Stock_code = null;
        if(DownloadedDataCenter.getInstance(getActivity()).getSelectedItemCode()==null){
            inStock.setText("0.00");
            sPrice.setText("0.00");
            cPrice.setText("0.00");
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<ItemDetails>> call = jsonPlaceHolderApi.getLoadItemDetailsByItemCode(DownloadedDataCenter.getInstance(getActivity()).getSelectedDivition(),
                DownloadedDataCenter.getInstance(getActivity()).getSelectedItemCode());
        call.enqueue(new Callback<List<ItemDetails>>() {
            @Override
            public void onResponse(Call<List<ItemDetails>> call, Response<List<ItemDetails>> response) {
                if(!response.isSuccessful()){
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getActivity(),"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<ItemDetails> itemDetail = response.body();
                    if(itemDetail!=null){
                        ItemDetails crnt =itemDetail.get(0);
                        if(crnt!=null) {
                            inStock.setText(crnt.getQty_inhand()+"");
                            sPrice.setText(df2.format(crnt.getItem_P_Whole()));
                            cPrice.setText(df2.format(crnt.getItem_P_Cost()));
                            Cat_base =crnt.getCat_base();
                            Cat_code = crnt.getCat_code();
                            Stock_code = crnt.getStock_code();
                        }else{
                            inStock.setText("0.00");
                            sPrice.setText("0.00");
                            cPrice.setText("0.00");
                        }
                    }else{
                        inStock.setText("0.00");
                        sPrice.setText("0.00");
                        cPrice.setText("0.00");
                    }

                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ItemDetails>> call, Throwable t) {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                inStock.setText("0.00");
            }
        });
    }

}
