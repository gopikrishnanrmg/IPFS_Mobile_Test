package com.example.test_ipfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import cz.msebera.android.httpclient.Header;

import static com.example.test_ipfs.R.id.button;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        File newdir = new File(getApplication().getFilesDir()+"FILES/"); //Creating an internal dir;
        if (!newdir.exists())
            newdir.mkdirs();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    writeToFile("FILES/sample.txt","This is a sample file for testing ipfs on phones",false);
                    ipfsadd("FILES/sample.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void writeToFile(String filePath, String data, Boolean appendStatus) throws IOException {
        File inputFile = new File(getApplicationContext().getFilesDir()+filePath);
        FileWriter fw;
        fw = new FileWriter(inputFile, appendStatus);
        fw.write(data);
        fw.close();
    }

    public void ipfsadd(final String filePath){
        final File inputFile = new File(getApplicationContext().getFilesDir()+filePath);
        RequestParams params = new RequestParams();
        try {
            params.put("file", inputFile);
        } catch(FileNotFoundException e) {}

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://localhost:9090/upload", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                AsyncHttpClient client = new AsyncHttpClient();


                client.get("http://localhost:9090/execrep?com="+"ipfs add "+inputFile.getName(), new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getApplicationContext(),"<ERROR>" +responseString,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
                        textView.setText(responseString);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"<ERROR>" +responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
