package com.pustovit.dibookshop.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pustovit.dibookshop.R;
import com.pustovit.dibookshop.databinding.ItemBookBinding;
import com.pustovit.dibookshop.model.entity.Book;

import java.util.ArrayList;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH> {
    private static final String TAG = "tag";
    private List<Book> books;
    private final OnBookClickListener bookClickListener;


    public BookAdapter(OnBookClickListener bookClickListener) {
        this.books = new ArrayList<>();
        this.bookClickListener = bookClickListener;
    }

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public void setNewData(List<Book> newBooks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BookDiffUtil(books, newBooks), false);
        books = newBooks;
        diffResult.dispatchUpdatesTo(BookAdapter.this);
    }

    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding bookBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_book, parent, false);
        return new BookVH(bookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookVH holder, int position) {
        holder.bookBinding.setBook(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    public Book getBook(int adapterPosition) {
        if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < getItemCount()) {
            return books.get(adapterPosition);
        }
        throw new IllegalArgumentException("Wrong adapter position=" + adapterPosition);
    }

    public class BookVH extends RecyclerView.ViewHolder {
        ItemBookBinding bookBinding;

        public BookVH(@NonNull ItemBookBinding bookBinding) {
            super(bookBinding.getRoot());
            this.bookBinding = bookBinding;
            this.bookBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookClickListener.onBookClick(getBook(getAdapterPosition()));
                }
            });
        }
    }

    private static class BookDiffUtil extends DiffUtil.Callback {
        private List<Book> oldList;
        private List<Book> newList;

        public BookDiffUtil(List<Book> oldList, List<Book> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getBook_id() == newList.get(newItemPosition).getBook_id();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getBook_title().equals(newList.get(newItemPosition).getBook_title())
                    && oldList.get(oldItemPosition).getBook_description().equals(newList.get(newItemPosition).getBook_description())
                    && oldList.get(oldItemPosition).getBook_price() == newList.get(newItemPosition).getBook_price();
        }

    }
}
