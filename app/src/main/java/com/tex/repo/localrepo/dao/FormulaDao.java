package com.tex.repo.localrepo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tex.repo.localrepo.models.FormulaModel;

@Dao
public interface FormulaDao {

    @Query("SELECT * FROM formula WHERE formula = :iFormula")
    LiveData<FormulaModel> getFormula(String iFormula);

    @Insert
    void saveFormula(FormulaModel iModel);

    @Update
    void updateFormula(FormulaModel iModel);

    @Delete
    void deleteFormula(FormulaModel iModel);

}
