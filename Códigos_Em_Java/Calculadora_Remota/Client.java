import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        String operator = args[1];
        double operand1 = Double.parseDouble(args[0]);
        double operand2 = Double.parseDouble(args[2]);

        Socket clientSocket = new Socket("localhost", 9999);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(operator + "," + operand1 + "," + operand2);

        String result;
        while ((result = in.readLine()) != null) {
            System.out.println(result);
        }

        clientSocket.close();
    }
}
