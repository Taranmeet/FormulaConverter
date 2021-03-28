package com.tex.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tex.network.ApiErrorResponse;

public class WikiResponse extends ApiErrorResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("checked")
    @Expose
    private String checked;
    @SerializedName("requiredPackages")
    @Expose
    private List<Object> requiredPackages = null;
    @SerializedName("identifiers")
    @Expose
    private List<String> identifiers = null;
    @SerializedName("endsWithDot")
    @Expose
    private Boolean endsWithDot;

    public WikiResponse(int iErrorCode, String iErrorMsg) {
        super(iErrorCode, iErrorMsg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<Object> getRequiredPackages() {
        return requiredPackages;
    }

    public void setRequiredPackages(List<Object> requiredPackages) {
        this.requiredPackages = requiredPackages;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public Boolean getEndsWithDot() {
        return endsWithDot;
    }

    public void setEndsWithDot(Boolean endsWithDot) {
        this.endsWithDot = endsWithDot;
    }

}