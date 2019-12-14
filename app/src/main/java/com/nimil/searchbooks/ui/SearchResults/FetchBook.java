package com.nimil.searchbooks.ui.SearchResults;

import android.os.AsyncTask;

import com.nimil.searchbooks.BackendClasses.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchBook extends AsyncTask<Void,Void,List<Book>> {
    String mQueryString;
    List<Book> result;
    SearchResultsViewModel mSearchResultsViewModel;
    FetchBook(SearchResultsViewModel ref,String query){
        mSearchResultsViewModel=ref;
        mQueryString=query;
    }
    @Override
    protected List<Book> doInBackground(Void... voids) {
        String jsonString=NetworkUtils.getBookInfo(mQueryString);
        return getBooks(jsonString);
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        mSearchResultsViewModel.mBookList.postValue(books);
    }

    private List<Book> getBooks(String jsonString) {
        try {
            // Convert the response into a JSON object.
            List<Book> bookList=new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            // Get the JSONArray of book items.
            JSONObject itemJsonObj;
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                try {
                    itemJsonObj = itemsArray.getJSONObject(i);
                    Book book = new Book();

                    JSONObject volumeInfo = itemJsonObj.getJSONObject("volumeInfo");
                    book.setTitle(volumeInfo.getString("title"));
                    book.setAuthors(volumeInfo.getString("authors"));

                    JSONArray industryIDArray=volumeInfo.getJSONArray("industryIdentifiers");
                    JSONObject IDObj=industryIDArray.getJSONObject(0);
                    book.setISBN(IDObj.getString("identifier"));



                    JSONObject thumbnailObj=volumeInfo.getJSONObject("imageLinks");
                    book.setThumbnail(thumbnailObj.getString("smallThumbnail"));
                    bookList.add(book);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //progressDialog.dismiss();
                }
            }
            return bookList;

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            e.printStackTrace();
            return null;
        }
    }


}
