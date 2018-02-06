package com.example.yjy.baselogger.BaseLogger;

/**
 * Created by yjy on 2018/2/4.
 */

public class LoggerWriteLock {

    public int writingCount = 0;
    public int waitingCount = 0;



    public synchronized void writeLock() throws InterruptedException{
        waitingCount++;
        try{
            while (writingCount>0){
                wait();
            }
        }finally {
            waitingCount--;
        }
        writingCount++;
    }


    public synchronized void writeUnLock(){
        writingCount--;
        notifyAll();
    }

}
