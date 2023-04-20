package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cookie {
    List<String> cookies = null;

    public void readCookieFile(String fileName) throws FileNotFoundException, IOException {
        // instantiate the cookies collection object
        cookies = new ArrayList<String>();

        // use a File object to pass the fileName 
        File cookieFile = new File(fileName);

        // use FileReader and BufferedReader for reading from cookie file
        // instantiate a FileReader follow by a BufferedReader
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);

        // while loop to loop through the file
        // read each line and add into the cookies collection object
        String idiom = "";
        while((idiom = br.readLine()) != null) {
            cookies.add(idiom);
        }

        // close the BufferedReader and FileReader in reverse order
        br.close();
        fr.close();
    }

    public String getRandomCookie() {

        return "";
    }
}
