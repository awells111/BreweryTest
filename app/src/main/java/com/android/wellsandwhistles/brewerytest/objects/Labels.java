
package com.android.wellsandwhistles.brewerytest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Labels {

    @SerializedName("icon")
    @Expose
    private String icon = "";
    @SerializedName("medium")
    @Expose
    private String medium = "";
    @SerializedName("large")
    @Expose
    private String large = "";

    public String getIcon() {
        return icon;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }
}
