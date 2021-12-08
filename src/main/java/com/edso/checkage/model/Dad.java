package com.edso.checkage.model;

import com.edso.checkage.service.CheckService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class Dad extends Thread {

    private Data data;
    private String name;
    private Integer age;

    public Dad(Data data, String name, Integer age) {
        this.data = data;
        this.name = name;
        this.age = age;
    }

    @Override
    public void run() {
        log.info("d start" + name);
        try {
            Thread.sleep(1000);
            if (checkAge(readFile())) {
                data.setCount(data.getCount() + 1);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        data.setCountT(data.getCountT() + 1);
        log.info("d stop" + name);
    }

    private int readFile() throws IOException {
        int age = 0;
        FileReader frd = null;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/" + name + "dad.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null) {
                age = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufR != null) {
                    bufR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return age;
    }

    private boolean checkAge(int age) {
        return age == this.age;
    }
}

