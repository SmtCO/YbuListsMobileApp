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


    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        private String textContent="";
        @Override
        protected Void doInBackground(Void... params) {


            try {
                doc = Jsoup.connect(url).get();
                Element table = doc.select("table").get(1);
                Iterator<Element> ite = table.select("font").iterator();
                int counter =0;
                while(ite.hasNext()){
                    textContent+=ite.next().text()+"\n";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (doc != null){
                textView.setText(textContent);
            }else{
                textView.setText("FAILURE");
            }
        }
    }
}