package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // 2 arguments
        // 1 argument for file
        // 1 argument for the port the server will start on
        String fileName = args[0];
        String port = args[1];

        File cookieFile = new File(fileName);
        if (!cookieFile.exists()) {
            System.out.println("Cookie file not found!");
            System.exit(0);
        }

        // testing the Cookie class
        Cookie cookie = new Cookie();
        cookie.readCookieFile(fileName);
        String myCookie = cookie.getRandomCookie();
        System.out.println(myCookie);
        String myCookie2 = cookie.getRandomCookie();
        System.out.println(myCookie2);


        // slide 8 - establish server connection
        ServerSocket ss = new ServerSocket(Integer.parseInt(port));
        Socket socket = ss.accept();

        // store the data sent over from client , e.g. get-cookie
        String msgReceived = "";

        // slide 9 - allow server to read and write over the communication channel
        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                // write our logic to receive and send
                while (!msgReceived.equals("close")) {
                    // slide 9 - receive message
                    msgReceived = dis.readUTF();
                    System.out.println(msgReceived);

                    if (msgReceived.equalsIgnoreCase("get-cookie")) {
                        // get a random cookie
                        String randomCookie = cookie.getRandomCookie();

                        // send the random cookie out using DataOutputStream (dos.writeUTF(XXXXXXX)))
                        dos.writeUTF(randomCookie);
                        dos.flush();
                    } else {
                        dos.writeUTF("");
                        dos.flush();
                    }
                }

                // closes all output streams in reverse order
                dos.close();
                bos.close();
                os.close();
            } catch (EOFException ex) {
                ex.printStackTrace();
            }

            // closes all input stream in reverse order
            dis.close();
            bis.close();
            is.close();
        } catch (EOFException ex) {
            socket.close();
            ss.close();
        }

    }
}
