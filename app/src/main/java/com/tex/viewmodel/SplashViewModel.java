package com.tex.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SplashViewModel extends ViewModel {

    /**
     * Live data used to notify Activity when it is time to load next screen.
     */
    private final MutableLiveData<Void> mTimerLiveData = new MutableLiveData<>();

    /**
     * Method used to start count down for Splash screen.
     *
     * @return Live data that can be observed to know when time has come to end splash screen.
     */
    public MutableLiveData<Void> startCountDown() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {
            mTimerLiveData.postValue(null);
        }, 1000, TimeUnit.MILLISECONDS);
        return mTimerLiveData;
    }

}
