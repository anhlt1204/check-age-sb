package com.edso.checkage.model;

import com.edso.checkage.service.CheckService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

public class Mom extends Thread {

    private final ReentrantLock lock;

    public Mom(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            if (checkAge(readFile()))
                CheckService.count++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private int readFile() throws IOException {
        int year = 0;
        FileReader frd;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/mom.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null)
            {
                year = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufR != null ) {
                    bufR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return year;
    }

    private boolean checkAge(int year) {
        Calendar instance = Calendar.getInstance();
        int now = instance.get(Calendar.YEAR);
        return now - year == 21;
    }
}