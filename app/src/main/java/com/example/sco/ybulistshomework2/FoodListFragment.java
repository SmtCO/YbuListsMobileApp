package com.example.sco.ybulistshomework2;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;

public class FoodListFragment extends Fragment {

    String url = "http://www.ybu.edu.tr/sks/";
    Document doc = null;
    TextView textView = null;

    public FoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        textView = (TextView) view.findViewById(R.id.foodlist);
        textView.setText("Bringing it from the source"); //just to show food list fragment
        new DataGrabber().execute();


        return view;
    }
    //New class for the Asynctask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        private String textContent="";
        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(url).get();
                Element table = doc.select("table").get(1);
                Iterator<Element> ite = table.select("td").iterator();
                int counter =0;
                while(ite.hasNext()){
                    if(counter==0){
                        ite.next().text(); //first one is image, skip it
                        counter++;
                    } else textContent+=ite.next().text()+"\n";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            if (doc != null){
                //textView.setText(doc.title().toString());
                textView.setText(textContent);
            }else{
                textView.setText("FAILURE");
            }
        }
    }
}