package com.example.sco.ybulistshomework2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
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

public class AnnouncementsFragment extends Fragment {

    private Activity activity;
    private View view;

    String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar";
    Document doc = null;
    TextView textView = null;
    ListView listView;
    ArrayList arrayListTexts;
    ArrayList arrayListLinks;

    public AnnouncementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_announcements, container, false);
        textView = (TextView) view.findViewById(R.id.announcements);
        listView = (ListView) view.findViewById(R.id.announcements_listview);

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

    //New class for the Asynctask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        private String textContent="";
        private String textLinks="";
        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(url).get();
                Elements text = doc.select("div.contentAnnouncements");
                Iterator<Element> ites = text.select("a").iterator();
                Iterator<Element> links = text.select("a").iterator();

                while(ites.hasNext()){
                    arrayListTexts.add(ites.next().text());
                    //textContent+=ites.next().text()+"\n";
                }
                while(links.hasNext()){
                    arrayListLinks.add(links.next().attr("href"));
                    //textLinks+=links.next().attr("href")+"\n";
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
                //textView.setText(textContent+"\n"+textLinks);
                textView.setText("");
                ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayListTexts);
                listView.setAdapter(adapter);
            }else{
                textView.setText("FAILURE");
            }
        }
    }


}