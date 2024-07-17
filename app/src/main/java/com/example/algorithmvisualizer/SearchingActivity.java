package com.example.algorithmvisualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SearchingActivity extends AppCompatActivity {

    BarChart barchart;
    ArrayList<Integer> list, colors;
    List<BarEntry> barEntryList;
    TextView arrSizeTxt, speedTxt;
    EditText element;
    SeekBar arrSizePicker, algoSpeed;
    Button sortAlgoAct,genArray, linearSearch, binarySearch, interpolationSearch;
    int arraySize = 10, speed = 250;
    MediaPlayer completionMusicPlayer;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        barchart = findViewById(R.id.barchart);
        sortAlgoAct = findViewById(R.id.sortAlgoAct);
        genArray = findViewById(R.id.genArray);
        arrSizeTxt = findViewById(R.id.arrSizeTxt);
        arrSizePicker = findViewById(R.id.arrSizePicker);
        linearSearch = findViewById(R.id.linearSearch);
        binarySearch = findViewById(R.id.binarySearch);
        interpolationSearch = findViewById(R.id.interpolationSearch);
        element = findViewById(R.id.element);

        speedTxt = findViewById(R.id.speedTxt);
        algoSpeed = findViewById(R.id.algoSpeed);

        colors = new ArrayList<>();
        list = new ArrayList<>();
        barEntryList = new ArrayList<>();
        completionMusicPlayer = MediaPlayer.create(this, R.raw.completion_music);

        arrSizePicker.setProgress(arraySize);
        arrSizePicker.setMax(30);
        arrSizePicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arraySize = progress;
                arrSizeTxt.setText("Array Size: " + arraySize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        algoSpeed.setProgress(250);
        algoSpeed.setMax(500);
        algoSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
                speedTxt.setText("Speed: " + speed + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        createArray(arraySize);
        setValues();
        setUpChart();
        sortAlgoAct = (Button) findViewById(R.id.sortAlgoAct);
        sortAlgoAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchingActivity.this, MainActivity.class));
                finish();
            }
        });
        genArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons(0);
                createArray(arraySize);
                setValues();
                setUpChart();
                enableButtons();
            }
        });
        linearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = element.getText().toString();
                if(x.isEmpty()){
                    Toast.makeText(SearchingActivity.this, "Enter number to search!", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disableButtons(1);
                            linearSearch(list, Integer.parseInt(x));
                            enableButtons();
                        }
                    }).start();
                }
            }
        });

        binarySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = element.getText().toString();
                if(x.isEmpty()){
                    Toast.makeText(SearchingActivity.this, "Enter number to search!", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disableButtons(2);
                            binarySearch(list, Integer.parseInt(element.getText().toString()));
                            enableButtons();
                        }
                    }).start();
                }
            }
        });

        interpolationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = element.getText().toString();
                if(x.isEmpty()){
                    Toast.makeText(SearchingActivity.this, "Enter number to search!", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disableButtons(3);
                            interpolationSearch(list, Integer.parseInt(element.getText().toString()));
                            enableButtons();
                        }
                    }).start();
                }
            }
        });
    }
    void disableButtons(int i){
        handler.post(new Runnable() {
            @Override
            public void run() {
                genArray.setEnabled(false);
                linearSearch.setEnabled(false);
                binarySearch.setEnabled(false);
                interpolationSearch.setEnabled(false);
                element.setEnabled(false);
                sortAlgoAct.setEnabled(false);
                switch (i){
                    case 0:
                        genArray.setEnabled(true);
                        genArray.setClickable(false);
                        break;
                    case 1:
                        linearSearch.setEnabled(true);
                        linearSearch.setClickable(false);
                        break;
                    case 2:
                        binarySearch.setEnabled(true);
                        binarySearch.setClickable(false);
                        break;
                    case 3:
                        interpolationSearch.setEnabled(true);
                        interpolationSearch.setClickable(false);
                        break;
                }
            }
        });
    }
    void enableButtons(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                genArray.setEnabled(true);
                linearSearch.setEnabled(true);
                binarySearch.setEnabled(true);
                interpolationSearch.setEnabled(true);
                element.setEnabled(true);
                sortAlgoAct.setEnabled(true);

                genArray.setClickable(true);
                linearSearch.setClickable(true);
                binarySearch.setClickable(true);
                interpolationSearch.setClickable(true);
                element.setClickable(true);
                sortAlgoAct.setClickable(true);
            }
        });
    }
    void startCompletionMusic() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!completionMusicPlayer.isPlaying()) {
                    completionMusicPlayer.start();
                }
            }
        });
    }
    void createArray(int n) {
        list.clear();
        colors.clear();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(50)+1);
            colors.add(Color.GREEN);
        }

    }

    void setValues() {
        barEntryList.clear();
        for (int i = 0; i < list.size(); i++) {
            barEntryList.add(new BarEntry(i, list.get(i)));
        }
    }

    private void setUpChart() {
        barchart.clear();
        barchart.getDescription().setEnabled(false);
        barchart.getAxisRight().setEnabled(false);
        barchart.getXAxis().setEnabled(false);

        BarDataSet barDataSet = new BarDataSet(barEntryList, "Array Elements");
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(10);
        BarData barData = new BarData(barDataSet);
        barchart.setData(barData);
        barchart.invalidate();
    }

    void updateColors(){
        colors.clear();
        for (int i = 0; i < list.size(); i++) {
            colors.add(Color.GREEN);
        }
    }

    int linearSearch(ArrayList<Integer> list, int e) {
        updateColors();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            colors.set(i, Color.RED);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setUpChart();
                }
            });

            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (list.get(i) == e) {
                colors.set(i, Color.BLUE);
                int finalI = i;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setUpChart();
                        startCompletionMusic();
                        Toast.makeText(SearchingActivity.this, "Element Found at Index: " + finalI, Toast.LENGTH_SHORT).show();
                    }
                });
                return i;
            }
            setUpChart();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchingActivity.this, "Element Not Found!", Toast.LENGTH_SHORT).show();
            }
        });
        return -1;
    }

    int binarySearch(ArrayList<Integer> list, int x) {
        updateColors();
        Collections.sort(list);
        setValues();
        setUpChart();
        int low = 0, high = list.size() - 1;

        while (low <= high) {
            colors.set(low, Color.RED);
            colors.set(high, Color.RED);
            int temp_low = low;
            int temp_high = high;

            int mid = low - (low - high) / 2;
            colors.set(mid, Color.YELLOW);
            int temp_mid = mid;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    setUpChart();
                }//Updating UI(graph) on main thread
            });

            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (list.get(mid) == x) {
                colors.set(mid, Color.BLUE);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setUpChart();
                        startCompletionMusic();
                        Toast.makeText(SearchingActivity.this, "Element Found at Index: " + mid, Toast.LENGTH_SHORT).show();
                    }
                });
                return mid;
            }
            if (list.get(mid) < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            colors.set(temp_low, Color.GREEN);
            colors.set(temp_high, Color.GREEN);
            colors.set(temp_mid, Color.GREEN);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchingActivity.this, "Element Not Found!", Toast.LENGTH_SHORT).show();
            }
        });
        return -1;
    }
    public int interpolationSearch(ArrayList<Integer> list, int x) {
        updateColors();
        Collections.sort(list);
        setValues();
        setUpChart();
        int low = 0, high = list.size() - 1;

        while (low <= high && x >= list.get(low) && x <= list.get(high)) {
            colors.set(low, Color.RED);
            colors.set(high, Color.RED);
            int temp_low = low;
            int temp_high = high;

            // Probing the position with keeping uniform distribution in mind.
            int pos = low + ((x - list.get(low)) * (high - low) / (list.get(high) - list.get(low)));
            colors.set(pos, Color.YELLOW);
            int temp_pos = pos;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setUpChart();
                }//Updating UI(graph) on main thread
            });

            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }


            if (list.get(pos) == x){
                colors.set(pos, Color.BLUE);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setUpChart();
                        startCompletionMusic();
                        Toast.makeText(SearchingActivity.this, "Element Found at Index: " + pos, Toast.LENGTH_SHORT).show();
                    }
                });
                return pos;
            }

            if (list.get(pos) < x){
                low = pos + 1;
            }else{
                high = pos - 1;
            }
            colors.set(temp_low, Color.GREEN);
            colors.set(temp_high, Color.GREEN);
            colors.set(temp_pos, Color.GREEN);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchingActivity.this, "Element Not Found!", Toast.LENGTH_SHORT).show();
            }
        });
        return -1;
    }

}
