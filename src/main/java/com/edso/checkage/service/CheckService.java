package com.edso.checkage.service;

import com.edso.checkage.model.Dad;
import com.edso.checkage.model.Data;
import com.edso.checkage.model.Mom;
import com.edso.checkage.model.Ubnd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class CheckService {

    public boolean runCheck(String name, Integer age) {
        Data data = new Data();
        Dad dad = new Dad(data, name, age);
        Mom mom = new Mom(data, name, age);
        Ubnd ubnd = new Ubnd(data, name, age);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(dad);
        executor.execute(mom);
        executor.execute(ubnd);

        executor.shutdown();

        boolean checkAge = false;

        while (data.getCountT() < 3) {
            if (data.getCount() >= 2) {
                checkAge = true;
                break;
            }
        }
        if (data.getCount() >= 2)
            checkAge = true;
        data.setCountT(0);
        data.setCount(0);

        write(name, checkAge);
        log.info("Check success");
        return checkAge;
    }

    public static void write(String name, boolean check) {
        log.info("Write start");
        try {
            FileWriter fw = new FileWriter(name + "result.txt");
            fw.write(String.valueOf(check));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        log.info("Write success");
    }
}
