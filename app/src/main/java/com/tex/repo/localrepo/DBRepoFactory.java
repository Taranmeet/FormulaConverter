package com.tex.repo.localrepo;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.tex.FormulaApplication;
import com.tex.repo.localrepo.dao.FormulaDao;
import com.tex.repo.localrepo.models.FormulaModel;
import com.tex.utils.LiveDataUtil;

public class DBRepoFactory {

    private static final DBRepoFactory mInstance = new DBRepoFactory();

    private DBRepo mDB = null;

    /**
     * Private constructor for singleton.
     */
    private DBRepoFactory() {
        mDB = Room
                .databaseBuilder(FormulaApplication.mContext, DBRepo.class, "formula-db")
                .allowMainThreadQueries()
                .build();
    }

    /**
     * Method to get access to DB Repository
     *
     * @return DBRepoFactory which can be used to call db queries.
     */
    public static DBRepoFactory getInstance() {
        return mInstance;
    }

    private FormulaDao getFormulaDao() {
        return mDB.formulaDao();
    }

    public LiveData<FormulaModel> getFormula(String iFormula) {
        return getFormulaDao().getFormula(iFormula);
    }

    public void saveFormulaToDb(FormulaModel iModel) {
        LiveDataUtil.observeOnce(getFormulaDao().getFormula(iModel.mFormula), model -> {
            if (model == null) {
                // new data save as it is
                getFormulaDao().saveFormula(iModel);
            } else {
                // update row
                getFormulaDao().updateFormula(iModel);
            }
        });
    }


}
