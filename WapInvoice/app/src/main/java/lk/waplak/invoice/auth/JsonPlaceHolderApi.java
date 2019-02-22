package lk.waplak.invoice.auth;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("Login/LoginAuth")
    Call<Login> getLoginAuth(@Query("userName")String username, @Query("password")String password);

    @GET("Invoice/LoadActiveDivisions")
    Call<List<Division>> getLoadActiveDivisions();

    @GET("Invoice/LoadSalesRep")
    Call<List<SalesRep>> getLoadSalesRep();

    @GET("Invoice/LoadCustomer")
    Call<List<Customer>> getLoadCustomer();

    @GET("Invoice/LoadItemsByDivision")
    Call<List<Item>> getLoadItemsByDivision(@Query("divisionCode")String divCode);

    @GET("Invoice/LoadAssignUserByDivision")
    Call<List<Assigner>> getLoadAssignUserByDivision(@Query("divisionCode")String divCode);

    @GET("Invoice/LoadItemDetailsByItemCode")
    Call<List<ItemDetails>> getLoadItemDetailsByItemCode(@Query("divisionCode")String divCode,@Query("itemCode")String itemCode);

    @POST("Invoice/SaveInvoice")
    Call <Boolean> SaveInvoice(@Body Invoice invoice);

    @GET("Invoice/IsQuantityAvailable")
    Call<Boolean> IsQuantityAvailable(@Query("storeCode")String divCode,@Query("itemCode")String itemCode,@Query("itemQty")String itemQty);
}