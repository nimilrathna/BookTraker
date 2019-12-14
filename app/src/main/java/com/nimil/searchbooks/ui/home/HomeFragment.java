package com.nimil.searchbooks.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nimil.searchbooks.R;
import com.nimil.searchbooks.ui.SearchResults.SearchResultsActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SearchView mSearchBook;
    private View mRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_home, container, false);
        mSearchBook = mRoot.findViewById(R.id.search_book);

        initialActions();
        return mRoot;
    }
    private void initialActions() {
        mSearchBook.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
            private void showInputMethod(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(view, 0);
                }
            }

        });
        mSearchBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Hide the keyboard when the button is pushed.
             /*   InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(mRoot.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }*/

                // Check the status of the network connection.
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }

                // If the network is available, connected, and the search field
                // is not empty, start a BookLoader AsyncTask.
                if (networkInfo != null && networkInfo.isConnected()
                        && query.length() != 0) {
                    Intent searchResultIntent=new Intent(getActivity(), SearchResultsActivity.class);
                    searchResultIntent.putExtra(SearchResultsActivity.SEARCH_QUERY,query);
                    startActivity(searchResultIntent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

    }
}