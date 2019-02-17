package lk.waplak.invoice.auth;

import com.google.gson.annotations.SerializedName;

public class InvResponse {
    @SerializedName("key")
    private String id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @SerializedName("value")
    private String name;
}
