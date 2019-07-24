package game.logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {
    public static void Read() throws IOException {
        FileReader fr = new FileReader("log.txt");
        BufferedReader br = new BufferedReader(fr);

        String str ;
        str = br.readLine();
        while(str != null){
            System.out.println(str + "\n");
        }
    }
}
