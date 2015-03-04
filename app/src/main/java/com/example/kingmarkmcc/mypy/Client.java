package com.example.kingmarkmcc.mypy;


import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Client extends ActionBarActivity {
    private Socket client;
    private PrintWriter printwriter;
    private EditText textField;
    private Button button;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonEnter;

    private Button keyRight;
    private Button keyLeft;
    private Button keyUp;
    private Button keyDown;


    private String message;


    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;

    public boolean onTouchEvent(MotionEvent event) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int trackPadWidth = size.x;
        int trackPadHeight = (size.y / 2)+((int)(size.y*.05));

        int touchedX = (int) event.getX();
        int touchedY = (int) event.getY();

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        if(touchedY<trackPadHeight && touchedX < trackPadWidth ){

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (mVelocityTracker == null) {

                        mVelocityTracker = VelocityTracker.obtain();
                    } else {

                        mVelocityTracker.clear();

                    }

                    mVelocityTracker.addMovement(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mVelocityTracker.addMovement(event);

                    mVelocityTracker.computeCurrentVelocity(1000);

                    message = ("0" + (int) (mVelocityTracker.getXVelocity() * .0125) + " " + (int) (mVelocityTracker.getYVelocity() * .0125)).toString();

                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        textField = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.button1);
        buttonLeft= (Button) findViewById(R.id.buttonLeftClick);
        buttonRight= (Button) findViewById(R.id.buttonRightClick);
        buttonEnter= (Button) findViewById(R.id.buttonEnterClick);

        keyRight= (Button) findViewById(R.id.buttonRightk);
        keyLeft= (Button) findViewById(R.id.buttonLeftk);
        keyUp= (Button) findViewById(R.id.buttonUpk);
        keyDown= (Button) findViewById(R.id.buttonDownk);


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message = "2"+textField.getText().toString();
                textField.setText("");
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="11";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="12";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });

        buttonEnter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="3";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });
        keyRight.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="6";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });
        keyLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="7";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });
        keyUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="4";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });
        keyDown.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="5";
                SendMessage sendMessageTask = new SendMessage();
                sendMessageTask.execute();
            }
        });

    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                client = new Socket("136.206.213.38", 4444);

                printwriter = new PrintWriter(client.getOutputStream(), true);

                printwriter.write(message);

                printwriter.flush();
                printwriter.close();
                client.close(); // closing the connection

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }
}