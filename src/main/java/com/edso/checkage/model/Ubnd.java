package com.edso.checkage.model;

import com.edso.checkage.service.CheckService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Ubnd extends Thread {

    private Data data;
    private String name;
    private Integer age;

    public Ubnd(Data data, String name, Integer age) {
        this.data = data;
        this.name = name;
        this.age = age;
    }

    @Override
    public void run() {
        log.info("u start" + name);
        try {
            Thread.sleep(2000);
            if (checkAge(readFile())) {
                data.setCount(data.getCount() + 1);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        data.setCountT(data.getCountT() + 1);
        log.info("u stop" + name);
    }

    private Date readFile() throws IOException {
        Date birthday = new Date();
        FileReader frd;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/" + name + "ubnd.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null) {
                birthday = new SimpleDateFormat("dd/MM/yyyy").parse(line);
            }
        } catch (IOException | ParseException e) {
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

        return birthday;

    }

    private boolean checkAge(Date birthday) {
        Date date = new Date();
        long diff = date.getTime() - birthday.getTime();
        long age = diff / (1000 * 60 * 60 * 24) / 365;
        return age == this.age;
    }
}

