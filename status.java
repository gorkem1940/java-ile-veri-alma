import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class status {
    public static void main(String[] args){

        String urlStr = "http://88.250.204.138:1940/status.xml";

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable(){
            @SuppressWarnings("deprecation")
            public void run() {
                try{
                    URL url = new URL(urlStr);
                    Scanner scanner = new Scanner(url.openStream());
                    StringBuilder stringBuilder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        stringBuilder.append(scanner.nextLine());
                        stringBuilder.append("\n");
                    }
                    scanner.close();
                    String xmlData = stringBuilder.toString();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("status.xml"))){
                        writer.write(xmlData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}