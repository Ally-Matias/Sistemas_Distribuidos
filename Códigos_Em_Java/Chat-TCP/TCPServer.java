import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
    static List<Connection> connections = new ArrayList<>();
    public static void main(String args[]) {
        try {
            int serverPort = 7896; // a porta do servidor

            // cria um ServerSocket que fica ouvindo a porta especificada
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Servidor iniciado! Aguardando conexões...");

            while (true) {
                // aguarda por uma conexão
                Socket clientSocket = listenSocket.accept();

                // cria uma nova thread para lidar com a conexão
                Connection c = new Connection(clientSocket);

                // adiciona a nova conexão à lista de conexões
                connections.add(c);

                // inicia a thread
                c.start();
            }
        } catch(IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Conexão estabelecida com o cliente " + clientSocket.getInetAddress() + ".");
        } catch(IOException e) {
            System.out.println("Conexão:" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                // lê a mensagem enviada pelo cliente
                String data = in.readUTF();

                // envia a mensagem para todos os outros clientes conectados
                for (Connection c : TCPServer.connections) {
                    if (c != this) {
                        c.out.writeUTF(data);
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Excessão: " + e.getMessage());
        } finally {
            try {
                // remove a conexão da lista de conexões ao finalizar a thread
                TCPServer.connections.remove(this);
                clientSocket.close();
            } catch(IOException e) {
                // ignora a exceção
            }
        }
    }
}
