
package com.android.wellsandwhistles.brewerytest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("labels")
    @Expose
    private Labels labels = new Labels();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Labels getLabels() {
        return labels;
    }
}