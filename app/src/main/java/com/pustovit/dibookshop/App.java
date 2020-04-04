package com.pustovit.dibookshop;

import android.app.Application;

import com.pustovit.dibookshop.di.ApplicationModule;
import com.pustovit.dibookshop.di.BookComponent;
import com.pustovit.dibookshop.di.DaggerBookComponent;

public class App extends Application {
    private static App app;
    private BookComponent bookComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        bookComponent= DaggerBookComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static App getApp() {
        return app;
    }

    public BookComponent getBookComponent() {
        return bookComponent;
    }
}
