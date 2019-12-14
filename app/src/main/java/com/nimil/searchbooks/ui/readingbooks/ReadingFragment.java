package com.nimil.searchbooks.ui.readingbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.BackendClasses.BookLocalRepository;
import com.nimil.searchbooks.R;

import java.util.List;

public class ReadingFragment extends Fragment {

    private ReadingViewModel mReadingViewModel;
    List<Book> mBookList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        BookLocalRepository repository=new BookLocalRepository(getActivity().getApplication());
        ReadingViewModelFactory factory=new ReadingViewModelFactory(repository);
        mReadingViewModel =
                ViewModelProviders.of(this,factory).get(ReadingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reading, container, false);
        RecyclerView recyclerReadingBooks=root.findViewById(R.id.recycler_reading);
        final ReadingAdapter readingAdapter=new ReadingAdapter();
        mReadingViewModel.getReadingBooks().observe(this, new Observer<PagedList<Book>>() {
            @Override
            public void onChanged(PagedList<Book> bookList) {
                mBookList=bookList;
                readingAdapter.submitList(bookList);
            }
        });


        recyclerReadingBooks.setAdapter(readingAdapter);
        recyclerReadingBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        readingAdapter.setClickListener(new ReadingViewHolder.ClickListener() {
            @Override
            public void itemClick(Book book) {
                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}