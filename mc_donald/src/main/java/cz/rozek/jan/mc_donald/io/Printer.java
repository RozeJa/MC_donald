package cz.rozek.jan.mc_donald.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import cz.rozek.jan.mc_donald.models.Order;

@Service
public class Printer {
    private String path = "";

    public Printer () {
        path = System.getenv("APPDATA") + File.separator + "pokladna4" + File.separator + "uctenky";

        File f = new File(path);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
    
    public void print(Order order) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + getTicketName(order) + ".txt"))) {
            bw.write(order.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTicketName(Order order) {
        StringBuilder sb = new StringBuilder();

        LocalDateTime ldt = LocalDateTime.now();

        sb.append(File.separator);
        sb.append(ldt.getYear());
        sb.append(ldt.getMonthValue());
        sb.append(ldt.getDayOfMonth());
        sb.append(ldt.getHour());
        sb.append(ldt.getMinute());
        sb.append(ldt.getSecond());
        sb.append(String.format("-%d", order.getNumber()));
        
        return sb.toString();
    }
}
