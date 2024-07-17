package com.example.algorithmvisualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BarChart barchart;
    ArrayList<Integer> list,colors;
    List<BarEntry> barEntryList;
    TextView arrSizeTxt,speedTxt;
    SeekBar arrSizePicker,algoSpeed;
    Button genArray, bubbleSortBtn,insertionSortBtn,selectionSortBtn,quickSortBtn,mergeSortBtn,searchAlgoAct;
    MediaPlayer completionMusicPlayer;
    Handler handler = new Handler();
    int arraySize=10, speed=250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barchart = findViewById(R.id.barchart);
        genArray = findViewById(R.id.genArray);

        bubbleSortBtn = findViewById(R.id.bubbleSortBtn);
        insertionSortBtn = findViewById(R.id.insertionSortBtn);
        selectionSortBtn = findViewById(R.id.selectionSortBtn);
        quickSortBtn = findViewById(R.id.quickSortBtn);
        mergeSortBtn = findViewById(R.id.mergeSortBtn);

        searchAlgoAct = (Button) findViewById(R.id.searchAlgoAct);
        searchAlgoAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchingActivity.class));
            }
        });

        arrSizeTxt = (TextView) findViewById(R.id.arrSizeTxt);
        arrSizePicker = (SeekBar) findViewById(R.id.arrSizePicker);

        speedTxt = (TextView) findViewById(R.id.speedTxt);
        algoSpeed = (SeekBar) findViewById(R.id.algoSpeed);

        list = new ArrayList<>();
        colors = new ArrayList<>();
        barEntryList = new ArrayList<>();
        completionMusicPlayer = MediaPlayer.create(this, R.raw.completion_music);

        arrSizePicker.setProgress(arraySize);
        arrSizePicker.setMax(30);
        arrSizePicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arraySize = progress;
                arrSizeTxt.setText("Array Size: "+arraySize);
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
                speedTxt.setText("Speed: "+speed+"ms");
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

        bubbleSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons(1);
                        bubbleSort(list);
                        startCompletionMusic();
                        enableButtons();
                    }
                }).start();

            }
        });

        insertionSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons(2);
                        insertionSort(list);
                        startCompletionMusic();
                        enableButtons();
                    }
                }).start();
            }
        });

        selectionSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons(3);
                        selectionSort(list);
                        startCompletionMusic();
                        enableButtons();
                    }
                }).start();
            }
        });

        quickSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons(4);
                        quickSort(list,0,list.size()-1);
                        startCompletionMusic();
                        enableButtons();
                    }
                }).start();
            }
        });

        mergeSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons(5);
                        mergeSort(list,0,list.size()-1);
                        startCompletionMusic();
                        enableButtons();
                    }
                }).start();

            }
        });
    }

    void disableButtons(int i){
        handler.post(new Runnable() {
            @Override
            public void run() {
                genArray.setEnabled(false);
                bubbleSortBtn.setEnabled(false);
                insertionSortBtn.setEnabled(false);
                selectionSortBtn.setEnabled(false);
                quickSortBtn.setEnabled(false);
                mergeSortBtn.setEnabled(false);
                searchAlgoAct.setEnabled(false);
                switch (i){
                    case 0:
                        genArray.setEnabled(true);
                        genArray.setClickable(false);
                        break;
                    case 1:
                        bubbleSortBtn.setEnabled(true);
                        bubbleSortBtn.setClickable(false);
                        break;
                    case 2:
                        insertionSortBtn.setEnabled(true);
                        insertionSortBtn.setClickable(false);
                        break;
                    case 3:
                        selectionSortBtn.setEnabled(true);
                        selectionSortBtn.setClickable(false);
                        break;
                    case 4:
                        quickSortBtn.setEnabled(true);
                        quickSortBtn.setClickable(false);
                        break;
                    case 5:
                        mergeSortBtn.setEnabled(true);
                        mergeSortBtn.setClickable(false);
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
                bubbleSortBtn.setEnabled(true);
                insertionSortBtn.setEnabled(true);
                selectionSortBtn.setEnabled(true);
                quickSortBtn.setEnabled(true);
                mergeSortBtn.setEnabled(true);
                searchAlgoAct.setEnabled(true);

                genArray.setClickable(true);
                bubbleSortBtn.setClickable(true);
                insertionSortBtn.setClickable(true);
                selectionSortBtn.setClickable(true);
                quickSortBtn.setClickable(true);
                mergeSortBtn.setClickable(true);
                searchAlgoAct.setClickable(true);
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
        //barchart.getAxisLeft().setEnabled(false);
        barchart.getAxisRight().setEnabled(false);
        barchart.getXAxis().setEnabled(false);

        BarDataSet barDataSet = new BarDataSet(barEntryList, "Array Elements");
        barDataSet.setColor(Color.GREEN);
        //barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextSize(10);
        BarData barData = new BarData(barDataSet);
        barchart.setData(barData);
        barchart.invalidate();
    }

    void updateChart() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                setValues();
//                setUpChart();
//            }
//        });
        setValues();
        setUpChart();
    }

    void swap(int x, int y){
        int temp = list.get(x);
        list.set(x, list.get(y));
        list.set(y, temp);

        // Update the chart with a delay
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateChart();
            }
        });

        try {
            // Introduce a delay to visualize the sorting process
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Sorting Algorithms
    void bubbleSort(ArrayList<Integer> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 1; i <= n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i; j++) {
                if (list.get(j) > list.get(j + 1)) {

                    swap(j,j+1);

                    swapped = true;


                }
            }
            if (!swapped) break;
        }
    }

    void insertionSort(ArrayList<Integer>list){
        int n = list.size();
        for(int i=1;i<n;i++){
            int j=i;
            while (j>0 && list.get(j-1)>list.get(j)){
                swap(j-1,j);
                j--;
            }
        }
    }

    void selectionSort(ArrayList<Integer>list){
        int n=list.size();
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(list.get(i)>list.get(j)){
                    swap(i,j);
                }
            }
        }
    }

    //    public int partition(ArrayList<Integer>list,int start,int end){
//        int c=0;
//        int pivot = list.get(start);
//        for(int i=start+1;i<=end;i++){
//            if(list.get(i)<pivot){
//                c++;
//            }
//        }
//        int pivotIndex = start+c;
//        swap(start,pivotIndex);
//
//        int i = start; int j = end;
//        while(i<pivotIndex && j>pivotIndex){
//            while(list.get(i)<=list.get(pivotIndex)){
//                i++;
//            }
//            while(list.get(j)>list.get(pivotIndex)){
//                j--;
//            }
//            if(i<pivotIndex && j>pivotIndex){
//                swap(i,j);
//            }
//        }
//
//        return pivotIndex;
//    }
//    public void quickSort(ArrayList<Integer>list,int l,int r){
//        if(l>=r){
//            return;
//        }
//        int pi = partition(list,l,r);
//        quickSort(list,l,pi-1);
//        quickSort(list,pi+1,r);
//    }
    public int partition(ArrayList<Integer> list, int start, int end) {
        int pivot = list.get(end);
        int i = start - 1; // Index of the smaller element

        for (int j = start; j < end; j++) {
            // If the current element is smaller than or equal to the pivot
            if (list.get(j) <= pivot) {
                i++;

                // Swap list[i] and list[j]
                swap(i, j);
            }
        }

        // Swap list[i+1] and list[end] (or pivot)
        swap(i + 1, end);

        return i + 1;
    }

    public void quickSort(ArrayList<Integer> list, int l, int r) {
        if (l < r) {
            // pi is the partitioning index, list[pi] is now at the right place
            int pi = partition(list, l, r);

            // Recursively sort elements before and after partition
            quickSort(list, l, pi - 1);
            quickSort(list, pi + 1, r);
        }
    }

    public void merge(ArrayList<Integer> list, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; ++i) {
            left[i] = list.get(l + i);
        }
        for (int j = 0; j < n2; ++j) {
            right[j] = list.get(m + 1 + j);
        }

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                list.set(k, left[i]);
                i++;
            } else {
                list.set(k, right[j]);
                j++;
            }
            final int currentIndex = k;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    colors.set(currentIndex, Color.YELLOW); // Highlight the active element
                    setValues();
                    setUpChart();
                }
            });
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            k++;
        }

        while (i < n1) {
            list.set(k, left[i]);
            final int currentIndex = k;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    colors.set(currentIndex, Color.YELLOW);
                    setValues();
                    setUpChart();
                }
            });
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, right[j]);
            final int currentIndex = k;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    colors.set(currentIndex, Color.RED);
                    setValues();
                    setUpChart();
                }
            });
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            j++;
            k++;
        }
    }

    public void mergeSort(ArrayList<Integer> list, int l, int r) {
        if (l >= r) {
            return;
        }
        int m = l + (r - l) / 2;
        mergeSort(list, l, m);
        mergeSort(list, m + 1, r);
        merge(list, l, m, r);
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = l; i <= r; i++) {
                    colors.set(i, Color.GREEN); // Reset color to green after merging
                }
                setValues();
                setUpChart();
            }
        });
    }
}
