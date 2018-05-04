package com.example.sco.ybulistshomework2;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class NewsFragment extends Fragment {

    String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar";
    Document doc = null;
    TextView textView = null;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        textView = (TextView) view.findViewById(R.id.news);
        textView.setText("Bringing it from the source"); //just to show food list fragment
        new DataGrabber().execute();

        return view;
    }

    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        private String textContent="";
        private String textLinks="";
        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(url).get();
                Elements text = doc.select("div.contentNews");
                Iterator<Element> ites = text.select("a").iterator();
                Iterator<Element> links = text.select("a").iterator();

                while(ites.hasNext()){
                    textContent+=ites.next().text()+"\n";
                }
                while(links.hasNext()){
                    textLinks+=links.next().attr("href")+"\n";
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
                textView.setText(textContent+"\n"+textLinks);
            }else{
                textView.setText("FAILURE");
            }
        }
    }

}