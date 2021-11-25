import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    public Log(){

    }//Constructor

    public void write(String message) {//This methods get an string as an argument then prints console and log.txt
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");//Formatting Date for log
        Date date = new Date(System.currentTimeMillis());
        String s =  formatter.format(date);

        File f = new File("log.txt");//Path for log file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));//Buffered writer for printing into a file
            bw.append(message + " " + s + "\n");//
            System.out.println(message + " " + s);//Printing into console
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void editLog() {
        File f= new File("log.txt");
        f.delete();
    }
}