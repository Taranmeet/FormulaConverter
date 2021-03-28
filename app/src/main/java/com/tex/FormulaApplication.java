package com.tex;

import android.app.Application;

public class FormulaApplication extends Application {

    public static Application mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
