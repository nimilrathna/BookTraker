package com.nimil.searchbooks.ui.SearchResults;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.BackendClasses.BookLocalRepository;
import com.nimil.searchbooks.R;
import com.nimil.searchbooks.ui.SearchResults.BookLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    public static final String SEARCH_QUERY="com.nimil.booktraker.SEARCH_QUERY";
    List<Book> mBookList;
    SearchRecyclerAdapter mSearchAdapter;
    RecyclerView.LayoutManager mSearchLayoutManager;
    private RecyclerView mSearchResultView;
    private SearchResultsViewModel mSearchResultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        mBookList=new ArrayList<>();

        String queryString=getIntent().getExtras().getString(SEARCH_QUERY);
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);

        mSearchAdapter=new SearchRecyclerAdapter(this,mBookList);

        mSearchLayoutManager=new LinearLayoutManager(this);
        mSearchResultView = findViewById(R.id.recycler_search_result);
        mSearchResultView.setLayoutManager(mSearchLayoutManager);
        mSearchResultView.setAdapter(mSearchAdapter);

        BookLocalRepository repository=new BookLocalRepository(getApplication());
        SearchResultsViewFactory searchFactory=new SearchResultsViewFactory(repository,queryString);
        mSearchResultsViewModel = ViewModelProviders
                .of(this,searchFactory).get(SearchResultsViewModel.class);

        mSearchResultsViewModel.getBookList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                mBookList=books;
                mSearchAdapter.setBookList(mBookList);
            }
        });
        mSearchAdapter.setOnItemClickListener(new SearchRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(Book book) {
                Toast.makeText(getBaseContext(), "Item Clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAddButtonClick(Book book) {
                book.setBookStatus(Book.BookStatus.READING);
                mSearchResultsViewModel.addBook(book);
                Toast.makeText(getBaseContext(), "added",Toast.LENGTH_LONG);
            }

            @Override
            public void onAddFavouriteClick(View v,Book book) {
                ImageButton button_favourite=v.findViewById(R.id.button_favourite);
                if(!book.isFavourite()) {
                    book.setFavourite(true);
                    button_favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                else{
                    book.setFavourite(false);
                    button_favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                mSearchResultsViewModel.addFavourite(book);
            }
        });

    }
}
