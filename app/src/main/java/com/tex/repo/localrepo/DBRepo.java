package com.tex.repo.localrepo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tex.repo.localrepo.dao.FormulaDao;
import com.tex.repo.localrepo.models.FormulaModel;

@Database(entities = {FormulaModel.class}, version = 1)
public abstract class DBRepo extends RoomDatabase {
    public abstract FormulaDao formulaDao();
}
