package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class HandlerTest extends AppCompatActivity {
    // 출처 : https://huewu.blog.me/110115454542
    // 1. Looper.java
    /* sThreadLocal(쓰레드의 TLS (Thread Local Storage) : Thread 당 하나의 looper 생성되도록 관리
     자바는 쓰레드별로 개별적인 인스턴스를 생성하고 접근할 수 있는 ThreadLocal 클래스를
     제공한다. 따라서 생성된 Looper 객체는 쓰레드당 개별적인 인스턴스를 생성하고 접근되어야
     하므로 ThreadLocal 객체에 저장된다*/

    // 2. Handler.java
    /* 핸들러(Handler)는 안드로이드에서 비동기적인 메시지를 처리하기 위해 사용되며 쓰레드 간 통신을
     할 수 있는 방법을 제공한다. 핸들러가 처리하는 대상은 메시지(Message)와 런어블(Runnable) 객체이며
     단일 쓰레드에 단 하나의 루퍼와 메세지 큐가 존재하지만, 핸들러는 다수 존재 가능하다
     * post() 메소드는 Message 대신 Runnable 객체를 메시지 큐에 전달할 수 있다.
     * Runnable 객체를 메시지 큐에 전달하는 방법
      Runnable 객체를 메시지의 callback 멤버 변수로 저장 후 그 메시지를 sendMessageDelayed()
      메서드를 통해 루퍼의 메시지 큐에 전송하는 것이다. 메시지 큐에 도착한 메시지는 루퍼에 의해서
      스케줄링 된 후 연관된 핸들러에 의해 메시지에 포함된 Runnable 객체가 실행된다. getPostMessage()
      메서드는 Runnable 객체를 메시지화 하는 역할을 담당한다
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeLooper1();
        makeLooper2();
    }

    private void makeLooper1() {
        class LooperThread extends Thread {
            Handler mHandler;
            public void run() {
                Looper.prepare(); // Looper 생성 MessageQueue 객체도 함께 생성됨.
                mHandler = new Handler() { // Thread 핸들러 생성
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                Looper.loop(); // 메시지 큐로부터 메시지를 디스패치하는 동작을 시작
            }
        }
    }

    private void makeLooper2() {
        /*HandlerThread 는 기본적으로 Looper 를 갖고 있으며,
        getLooper() 메서드를 통해 포함된 Looper 를 얻어오거나,
        quit() 메서드를 통해 Looper 의 무한 루프를 정지시킬 수
        있는 메서드를 제공해 줍니다.
        (단, 이 quit() 메서드는 MessageQueue 에서 새로운 메세지를
        꺼내 오는 루프를 중단할 뿐이며, 연결되어 있는 Handler 단에서
        진행중인 작업이 중간에 종료되는 것은 아닙니다.*/
        HandlerThread handlerThread = new HandlerThread("My handler thread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
    }
}

