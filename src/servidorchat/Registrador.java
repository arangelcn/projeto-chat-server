
package servidorchat;

import com.sun.prism.Texture;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.print.attribute.standard.Severity;
import modelos.Mensagem;
import modelos.Usuario;

/**
 *
 * @author arcn
 */

// Metodo que registra a conexao do usuario
public class Registrador implements Runnable{
    
    private Distribuidor distribuidor;
    private ServerSocket serverSocket;
    public boolean isRuning;
    
    public Registrador(Distribuidor dist, ServerSocket servSock) {
        this.distribuidor = dist;
        this.serverSocket = servSock;
        this.isRuning = true;
    }

    @Override
    public void run() {
        while (isRuning) {
            try {
                Socket socket = this.serverSocket.accept();

                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());

                Emissor emissor = new Emissor(saida);
                
                if (!this.distribuidor.pegaMapaEmissores().containsKey(emissor)) {
                    Mensagem mensagem = (Mensagem) entrada.readObject();
                    System.out.println("Usuario " + mensagem.getUsrEnvia().getNome()+ " conectado!");
                    this.distribuidor.adicionaEmissor(emissor);
                    this.distribuidor.adicionaUsuarioEmissor(emissor, mensagem.getUsrEnvia().getEmail());
                }
                
                Receptor receptor = new Receptor(entrada, this.distribuidor);
                Thread pilha = new Thread(receptor);
                pilha.start();
                
            } catch (Exception e) {
                System.out.println("ERRO");
            }
        }
    }
    
}
