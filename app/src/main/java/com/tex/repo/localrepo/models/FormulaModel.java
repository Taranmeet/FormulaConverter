package com.tex.repo.localrepo.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.tex.network.ApiResponse;
import com.tex.response.WikiResponseModel;

@Entity(tableName = "formula")
public class FormulaModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "formula")
    public String mFormula;

    @ColumnInfo(name = "formula_hash")
    public String mFormulaHash;

    @ColumnInfo(name = "file_downloaded")
    public boolean isFileDownloaded;

    @ColumnInfo(name = "success")
    public boolean mSuccess;

    @Ignore
    public boolean isInternetIssue;

    @Ignore
    public boolean isBadRequest;

    public static FormulaModel convertFromNetworkModel(ApiResponse<WikiResponseModel> iResponse) {
        if (iResponse.isSuccess()) {
            FormulaModel model = new FormulaModel();
            model.isFileDownloaded = false;
            model.mFormulaHash = iResponse.getHeaders().get("x-resource-location");
            model.mFormula = iResponse.getResponse().getChecked();
            model.mSuccess = iResponse.getResponse().getSuccess();
            return model;
        } else {
            return null;
        }
    }
}
