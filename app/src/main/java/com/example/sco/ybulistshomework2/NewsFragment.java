package com.example.sco.ybulistshomework2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class NewsFragment extends Fragment {

    private View view;

    String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar";
    Document doc = null;
    TextView textView = null;
    ListView listView;
    ArrayList arrayListTexts;
    ArrayList arrayListLinks;

    public NewsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_news, container, false);
        textView = (TextView) view.findViewById(R.id.news);
        listView = (ListView) view.findViewById(R.id.news_listview);

        arrayListTexts = new ArrayList();
        arrayListLinks = new ArrayList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url + arrayListLinks.get(position).toString()));
                startActivity(i);
            }
        });

        textView.setText("Bringing it from the source"); //just to show food list fragment
        new DataGrabber().execute();

        return view;
    }

    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                doc = Jsoup.connect(url).get();
                Elements text = doc.select("div.contentNews");
                Iterator<Element> ites = text.select("a").iterator();
                Iterator<Element> links = text.select("a").iterator();

                while(ites.hasNext()){
                    arrayListTexts.add(ites.next().text());
                }
                while(links.hasNext()){
                    arrayListLinks.add(links.next().attr("href"));
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

                textView.setText("");
                ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayListTexts);
                listView.setAdapter(adapter);
            }else{
                textView.setText("FAILURE");
            }
        }
    }

}