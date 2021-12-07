package com.edso.checkage.model;

import com.edso.checkage.service.CheckService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Dad extends Thread{

    private final ReentrantLock lock;
    public Dad(ReentrantLock lock) {
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
        int age = 0;
        FileReader frd = null;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/dad.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null)
            {
                age = Integer.parseInt(line);
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

        return age;
    }

    private boolean checkAge(int age) {
        return age == 21;
    }
}

