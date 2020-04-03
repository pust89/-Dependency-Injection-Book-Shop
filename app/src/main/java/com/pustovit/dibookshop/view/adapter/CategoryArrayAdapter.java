package com.pustovit.dibookshop.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.pustovit.dibookshop.R;
import com.pustovit.dibookshop.databinding.ItemSpinnerCategoryBinding;
import com.pustovit.dibookshop.model.entity.Category;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {
    private static final String TAG = "tag";
    private List<Category> categories;
    private Context context;


    public CategoryArrayAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, R.layout.item_spinner_category, categories);
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = getCustomView(position,convertView,parent);
        view.setBackgroundColor(context.getResources().getColor(R.color.selectedSpinnerItem));
        return view;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = getCustomView(position,convertView,parent);
        view.setBackgroundColor(context.getResources().getColor(R.color.dropDownSpinnerItem));
        return view;
    }


    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ItemSpinnerCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_spinner_category,parent,false);
        binding.setCategory(categories.get(position));
        return binding.getRoot();
    }

}
