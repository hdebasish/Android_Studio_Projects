package com.example.guesscelebs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String[] names = new String[101];
    String[] images = new String[101];
    String[] httpImages = new String[101];
    ImageView imageView;
    int id;
    String buttonText;
    Button onTap;
    Button button;
    Button button1;
    Button button2;
    Button button3;
    ArrayList<Integer> random;
    int[] wrongAnswer;
    Random rand;
    Bitmap bitmap;
    int nextImage=0;
    int nextName=0;
    DownloadImage downloadImage;
    DownloadURLs task;
    int count=0;
    int score=0;
    TextView textView;


    public class DownloadURLs extends AsyncTask<String,Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            URL url = null;
            StringBuilder result;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                result = new StringBuilder();
                String inputLine;

                while ((inputLine= in.readLine()) != null) {
                    result.append("\n");
                    result.append(inputLine);
                    result.append("\n");
                }return String.valueOf(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void names(String result)
    {
        int i=0;
        int k=0;
        Pattern p = Pattern.compile("alt=\"(.*?)\"></a>");
        Matcher m = p.matcher(result);
        String http = "https://www.babepedia.com";
        do{

            while (m.find()) {
                names[i]=m.group(1);
                i++;
            }
        }while (i<=99);

         p = Pattern.compile("\" src=\"(.*?)\"");
         m = p.matcher(result);
        i=0;
        do {

            while (m.find()) {
                images[i]=m.group(1);
                httpImages[k++]=http.concat(images[i]);
                httpImages[i] = httpImages[i].trim();
                httpImages[i] = httpImages[i].replaceAll("\\s", "%20");
                i++;
            }

        }while (i<=99);

    }
    public class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    public void ButtonTapped(View view)
    {
        wrongAnswer = new int[4];
        rand = new Random();
        id = view.getId();
        onTap = findViewById(id);
        buttonText = (String) onTap.getText();
        downloadImage = new DownloadImage();
        imageView = findViewById(R.id.imageView);
        int correctAnswerPosition = rand.nextInt(4);
        if (buttonText.equals(names[nextName]))
        {
            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
            score++;
        }else {
            Toast.makeText(getApplicationContext(),"She is "+names[nextName]+"! Incorrect!",Toast.LENGTH_SHORT).show();
        }
        ++nextImage;
        ++nextName;

        try {
            bitmap = downloadImage.execute(httpImages[nextImage]).get();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0;i<100;i++)
        {
            random.add(i);
        }
        Collections.shuffle(random);
        for(int i=0;i<100;i++)
        {
            random.add(i);
        }
        Collections.shuffle(random);
        int i=0;
        int j=0;
        while (j<4) {

            if (nextName!=random.get(i))
            {
                wrongAnswer[j]= random.get(i);
                j++;
            }
            i++;

        }

        switch (correctAnswerPosition)
         {

             case 0:
                    button.setText(names[nextName]);
                    button1.setText(names[wrongAnswer[1]]);
                    button2.setText(names[wrongAnswer[2]]);
                    button3.setText(names[wrongAnswer[3]]);
                    break;

             case 1:
                    button.setText(names[wrongAnswer[1]]);
                    button1.setText(names[nextName]);
                    button2.setText(names[wrongAnswer[2]]);
                    button3.setText(names[wrongAnswer[3]]);
                    break;

             case 2:
                    button.setText(names[wrongAnswer[1]]);
                    button1.setText(names[wrongAnswer[2]]);
                    button2.setText(names[nextName]);
                    button3.setText(names[wrongAnswer[3]]);
                    break;

             case 3:
                    button.setText(names[wrongAnswer[1]]);
                    button1.setText(names[wrongAnswer[2]]);
                    button2.setText(names[wrongAnswer[3]]);
                    button3.setText(names[nextName]);
                    break;
            }
            count++;
        if(count==100)
        {
            imageView.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("You are "+ score+"%"+" HAWASI!");

        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = new DownloadURLs();
        downloadImage = new DownloadImage();
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button2);
        button2=findViewById(R.id.button3);
        button3=findViewById(R.id.button4);
        wrongAnswer = new int[4];
        rand = new Random();
        imageView = findViewById(R.id.imageView);
        random= new ArrayList<Integer>();
        nextImage=0;
        nextName=0;
        textView = findViewById(R.id.score);
        textView.setVisibility(View.INVISIBLE);

        String result="";
        try {

            result = task.execute("https://www.babepedia.com/pornstartop100").get();

        } catch (ExecutionException e)
        {
            e.printStackTrace();

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }


        names(result);


        try {
            bitmap = downloadImage.execute(httpImages[nextImage]).get();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int correctAnswerPosition = rand.nextInt(4);
        for(int i=0;i<100;i++)
        {
            random.add(i);
        }
        Collections.shuffle(random);
        int i=0;
        int j=0;
        while (j<4) {

            if (nextName!=random.get(i))
            {
                wrongAnswer[j]= random.get(i);
                j++;
            }
            i++;
        }


        switch (correctAnswerPosition)
        {

            case 0:
                button.setText(names[nextName]);
                button1.setText(names[wrongAnswer[1]]);
                button2.setText(names[wrongAnswer[2]]);
                button3.setText(names[wrongAnswer[3]]);
                break;

            case 1:
                button.setText(names[wrongAnswer[1]]);
                button1.setText(names[nextName]);
                button2.setText(names[wrongAnswer[2]]);
                button3.setText(names[wrongAnswer[3]]);
                break;

            case 2:
                button.setText(names[wrongAnswer[1]]);
                button1.setText(names[wrongAnswer[2]]);
                button2.setText(names[nextName]);
                button3.setText(names[wrongAnswer[3]]);
                break;

            case 3:
                button.setText(names[wrongAnswer[1]]);
                button1.setText(names[wrongAnswer[2]]);
                button2.setText(names[wrongAnswer[3]]);
                button3.setText(names[nextName]);
                break;
        }


    }

}

