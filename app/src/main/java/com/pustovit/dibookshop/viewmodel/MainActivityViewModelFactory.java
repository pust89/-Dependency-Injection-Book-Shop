package com.pustovit.dibookshop.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pustovit.dibookshop.model.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    @Inject
    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
