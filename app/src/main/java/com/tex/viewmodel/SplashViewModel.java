package com.tex.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SplashViewModel extends ViewModel {

    private final MutableLiveData<Void> timerLiveData = new MutableLiveData<>();

    public MutableLiveData<Void> startCountDown() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {
            timerLiveData.postValue(null);
        }, 1000, TimeUnit.MILLISECONDS);
        return timerLiveData;
    }

}
