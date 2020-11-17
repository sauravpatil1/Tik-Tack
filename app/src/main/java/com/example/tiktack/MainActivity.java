package com.example.tiktack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean isPlayerOneTurn = true;
    private boolean isGameActive = true;
    int [][] mark = new int[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void dropIn(View view){
        if(!isGameActive)return;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.turnColor);
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView counter = (ImageView) view;
        String [] arr = counter.getTag().toString().split(" ");

        int row = Integer.parseInt(arr[0]);
        int column = Integer.parseInt(arr[1]);

        if(mark[row][column] == 0) {
            counter.setTranslationY(-1000f);
            if (isPlayerOneTurn) {
                counter.setImageResource(R.drawable.yellow);
                isPlayerOneTurn = false;
                mark[row][column] = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                isPlayerOneTurn = true;
                mark[row][column] = 2;
            }
            counter.animate().translationYBy(1000f).setDuration(300);
            if(isWinner(mark[row][column]) || checkFull()){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                isGameActive = false;
                String winner = isPlayerOneTurn? "Red" : "Yellow";
                builder.setMessage("Winner is "+ winner).setTitle(R.string.dialog_title);
                builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

                        for (int i = 0; i< gridLayout.getChildCount(); i++) {

                            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

                        }
                        //Arrays.fill(mark, 0);
                        for(int i=0;i<3;i++){
                            for(int j=0;j<3;j++){
                                mark[i][j] = 0;
                            }
                        }
                        isGameActive = true;
                        isPlayerOneTurn = true;
                        linearLayout.setBackgroundColor(Color.parseColor("#F9F905"));
                        textView.setText("Yellow's Turn");
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        String color = isPlayerOneTurn? "#F9F905":"#F90523";
        String turn = isPlayerOneTurn? "Yellow's " : "Red's ";
        linearLayout.setBackgroundColor(Color.parseColor(color));
        textView.setText(turn + "turn");
    }

    private boolean checkFull() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(mark[i][j] == 0)return false;
            }
        }
        return true;
    }

    public boolean isWinner(int player){
        int count = 0;
        for(int i=0;i<3;i++){
            if(player == mark[i][i]){
                count++;
            }
            if(count == 3)return true;
        }
        count = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(player == mark[i][j]){
                    count++;
                }
                if(count == 3)return true;
            }
            count = 0;
        }
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(player == mark[j][i]){
                    count++;
                }
                if(count == 3)return true;
            }
            count = 0;
        }
        return player == mark[0][2] && player == mark[1][1] && player == mark[2][0];
    }
}