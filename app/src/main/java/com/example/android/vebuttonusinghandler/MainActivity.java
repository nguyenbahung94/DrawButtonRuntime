package com.example.android.vebuttonusinghandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    Handler handlerMain;
    AtomicBoolean atomic=null;
    LinearLayout layoutdevebutton;
    Button btnOk;
    EditText edtOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lấy LinearLayout chứa Button ra
        layoutdevebutton = (LinearLayout) findViewById(R.id.layout_drawble);
        btnOk = (Button) findViewById(R.id.btndrawButton);
        edtOk = (EditText) findViewById(R.id.edtnumber);
        handlerMain = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Nhận nhãn của Button được gửi về từ tiến trình con
                String nhan_button = msg.obj.toString();
                //khởi tạo 1 Button
                Button b = new Button(MainActivity.this);
                //thiết lập nhãn cho Button
                b.setText(nhan_button);
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                //thiết lập layout cho Button
                b.setLayoutParams(params);
                //đưa Button vào layoutdevebutton
                layoutdevebutton.addView(b);
            }
        };
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dostart();
            }
        });
    }
        private void dostart()
        {
            atomic=new AtomicBoolean(false);
            final int sobutton=Integer.parseInt(edtOk.getText().toString());
            Thread thCon=new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    for(int i=0;i<sobutton && atomic.get();i++)
                    {
                        //nghỉ 200 mili second
                        SystemClock.sleep(200);
                        //lấy message từ Main Thread
                        Message msg=handlerMain.obtainMessage();
                        //gán dữ liệu cho msg Mainthread, lưu vào biến obj
                        //chú ý ta có thể lưu bất kỳ kiểu dữ liệu nào vào obj
                        msg.obj="Button thứ "+i;
                        //gửi trả lại message cho Mainthread
                        handlerMain.sendMessage(msg);
                    }
                }
            });
            atomic.set(true);
            //thực thi tiến trình
            thCon.start();
        }
    }
