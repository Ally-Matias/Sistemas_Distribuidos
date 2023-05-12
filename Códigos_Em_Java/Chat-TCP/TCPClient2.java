import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient2 {
    public static void main (String args[]) {
        try {
            // estabelece a conexão com o servidor
            Socket socket = new Socket("localhost", 7896);

            // cria um stream de entrada para receber dados do servidor
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // cria um stream de saída para enviar dados para o servidor
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // cria um scanner para ler a entrada do usuário
            Scanner scanner = new Scanner(System.in);

            // loop para enviar mensagens para o servidor
            while (true) {
                // lê a entrada do usuário
                String message = scanner.nextLine();

                // envia a mensagem para o servidor
                out.writeUTF(message);

                // espera pela resposta do servidor
                String response = in.readUTF();

                // exibe a resposta do outro cliente
                System.out.println("Mensagem do cliente: " + response);
            }
        } catch(IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
