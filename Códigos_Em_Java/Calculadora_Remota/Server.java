import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 9999;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server escutando na porta " + port + "\n");
        System.out.println("Para funcionar, coloque o cálculo nos argumentos do cliente, exemplo: 5 + 5 \n");
        System.out.println("OPERAÇÕES: +, -, *, / \n");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message = in.readLine();

            String[] parts = message.split(",");
            String operation = parts[0];
            double operand1 = Double.parseDouble(parts[1]);
            double operand2 = Double.parseDouble(parts[2]);
            double result = 0;

            switch (operation) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        clientSocket.close();
                        continue;
                    } else {
                        result = operand1 / operand2;
                    }
                    break;
                default:
                    out.println("Erro: operação inválida");
                    clientSocket.close();
                    continue;
            }

            out.println("Resultado: " + result);
            System.out.println("Resultado enviado para o cliente: " + result);

            clientSocket.close();
        }
    }
}
