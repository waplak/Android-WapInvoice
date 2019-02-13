package lk.waplak.invoice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import lk.waplak.invoice.auth.Assigner;
import lk.waplak.invoice.auth.Customer;
import lk.waplak.invoice.auth.Division;
import lk.waplak.invoice.auth.Item;
import lk.waplak.invoice.auth.ItemDetails;
import lk.waplak.invoice.auth.Login;
import lk.waplak.invoice.auth.InvResponse;
import lk.waplak.invoice.auth.JsonPlaceHolderApi;
import lk.waplak.invoice.auth.SalesRep;
import lk.waplak.invoice.utility.AndroidUtill;
import lk.waplak.invoice.utility.DownloadedDataCenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;
    private boolean isClickLogin=false;
    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutEmail.requestFocus();
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btn_login);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        TextView txtt=(TextView)findViewById(R.id.footerMark);
        final SpannableStringBuilder sb = new SpannableStringBuilder("Copyright © 2019 Somasiri Stores Private Limited. All Rights Reserved. | Powered by innosoft");
        // Span to set text color to some RGB value
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255,255,255));
        // Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 84, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        //sb.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        txtt.setText(sb);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isClickLogin) {
                    isClickLogin=true;
                    submitForm();
                }
            }
        });
    }
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        final String email = inputEmail.getText().toString().trim();
        final String pwrd = inputPassword.getText().toString().trim();
        if(AndroidUtill.isNetworkConnected(LoginActivity.this)) {
            DownloadedDataCenter.getInstance(LoginActivity.this).getLoadDivition().clear();
            DownloadedDataCenter.getInstance(LoginActivity.this).getLoadCustomer().clear();
            DownloadedDataCenter.getInstance(LoginActivity.this).getLoadSalesRep().clear();
            DownloadedDataCenter.getInstance(LoginActivity.this).getLoadAssignTo().clear();
            DownloadedDataCenter.getInstance(LoginActivity.this).getLoadItem().clear();
            DownloadedDataCenter.getInstance(LoginActivity.this).setSelectedDivition(null);
            dialog = new ProgressDialog(
                    LoginActivity.this,ProgressDialog.THEME_HOLO_DARK);
            this.dialog.setMessage("Authenticating....");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AndroidUtill.COMMON_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            Call<Login> call = jsonPlaceHolderApi.getLoginAuth(email,pwrd);
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if(!response.isSuccessful()){
                        isClickLogin = false;
                        inputLayoutPassword.setError(getString(R.string.err_msg_useraname_password));
                        if(dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                    }else{
                        Login lgData = response.body();
                        if(lgData.isStatus()) {
                            dialog.setMessage("Loading Data....");
                            DownloadedDataCenter.getInstance(LoginActivity.this).setUserName(lgData.getName());
                            loadDivition();
                        }else{
                            isClickLogin = false;
                            inputLayoutPassword.setError(getString(R.string.err_msg_useraname_password));
                            if(dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    isClickLogin = false;
                    inputLayoutPassword.setError(getString(R.string.err_msg_useraname_password));
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

        }else{
            Toast.makeText(
                    LoginActivity.this,
                    "No internet connection", Toast.LENGTH_LONG).show();
            isClickLogin=false;
            return;
        }

    }
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            isClickLogin=false;
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            isClickLogin=false;
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return true;
        //!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }

    private void loadDivition(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Division>> call = jsonPlaceHolderApi.getLoadActiveDivisions();
        call.enqueue(new Callback<List<Division>>() {
            @Override
            public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                if(!response.isSuccessful()){
                    isClickLogin = false;
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Division> divList = response.body();
                    int x = 0;
                    for(Division div:divList){
                        if(div.getMod_code()!=null) {
                            if(x==0){
                                DownloadedDataCenter.getInstance(LoginActivity.this).setSelectedDivition(div.getMod_code().trim());
                            }
                            DownloadedDataCenter.getInstance(LoginActivity.this).setLoadDivition(div);
                        }
                        ++x;
                    }
                    loadSalesRep();
                }
            }

            @Override
            public void onFailure(Call<List<Division>> call, Throwable t) {
                isClickLogin = false;
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void loadSalesRep(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<SalesRep>> call = jsonPlaceHolderApi.getLoadSalesRep();
        call.enqueue(new Callback<List<SalesRep>>() {
            @Override
            public void onResponse(Call<List<SalesRep>> call, Response<List<SalesRep>> response) {
                if(!response.isSuccessful()){
                    isClickLogin = false;
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<SalesRep> rpList = response.body();
                    for(SalesRep slsRp:rpList){
                        if(slsRp.getRep_Code()!=null) {
                            DownloadedDataCenter.getInstance(LoginActivity.this).setLoadSalesRep(slsRp);
                        }
                    }
                    loadCustomer();
                }
            }

            @Override
            public void onFailure(Call<List<SalesRep>> call, Throwable t) {
                isClickLogin = false;
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void loadCustomer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Customer>> call = jsonPlaceHolderApi.getLoadCustomer();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(!response.isSuccessful()){
                    isClickLogin = false;
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Customer> custList = response.body();
                    for(Customer cust:custList){
                        if(cust.getPat_code()!=null) {
                            DownloadedDataCenter.getInstance(LoginActivity.this).setLoadCustomer(cust);
                        }
                    }
                    successResult();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                isClickLogin = false;
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void loadItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Item>> call = jsonPlaceHolderApi.getLoadItemsByDivision(DownloadedDataCenter.getInstance(LoginActivity.this).getSelectedDivition());
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(!response.isSuccessful()){
                    isClickLogin = false;
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Item> itemList = response.body();
                    for(Item item:itemList){
                        if(item.getItem_code()!=null) {
                            DownloadedDataCenter.getInstance(LoginActivity.this).setLoadItem(item);
                        }
                    }
                    loadAssigners();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                isClickLogin = false;
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void loadAssigners(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AndroidUtill.COMMON_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Assigner>> call = jsonPlaceHolderApi.getLoadAssignUserByDivision(DownloadedDataCenter.getInstance(LoginActivity.this).getSelectedDivition());
        call.enqueue(new Callback<List<Assigner>>() {
            @Override
            public void onResponse(Call<List<Assigner>> call, Response<List<Assigner>> response) {
                if(!response.isSuccessful()){
                    isClickLogin = false;
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"Login Failed, Error Code:"+response.code(),Toast.LENGTH_LONG);
                }else{
                    List<Assigner> assignerList = response.body();
                    for(Assigner assign:assignerList){
                        if(assign.getUserID()!=null) {
                            DownloadedDataCenter.getInstance(LoginActivity.this).setLoadAssignTo(assign);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Assigner>> call, Throwable t) {
                isClickLogin = false;
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void successResult(){
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView textV = (TextView) toastLayout.findViewById(R.id.custom_toast_message);
        textV.setText("You are Welcome "+DownloadedDataCenter.getInstance(LoginActivity.this).getUserName());

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
        DownloadedDataCenter.getInstance(LoginActivity.this).setSelectedDivition(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setSalesRep(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setCustomer(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setDate(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setRemark(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setpMode(null);
        DownloadedDataCenter.getInstance(LoginActivity.this).setActive(false);
        DownloadedDataCenter.getInstance(LoginActivity.this).getAddItemList().clear();
        Intent intent = new Intent(LoginActivity.this, NevigationActivity.class);
        startActivity(intent);
    }


}

