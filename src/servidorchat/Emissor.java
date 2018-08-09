/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import modelos.Mensagem;
import modelos.Usuario;

// Classe responsavel pelas saidas de mensagens do servidor
public class Emissor {
    
    private ObjectOutputStream saida;
    
    public Emissor(ObjectOutputStream saida) { 
        this.saida = saida;
    }
   
    public void envia(Mensagem mensagem) throws IOException { 
        this.saida.writeObject(mensagem);
        this.saida.flush();
    }
    
    public ObjectOutputStream getSaida() {
        return saida;
    }

    public void setSaida(ObjectOutputStream saida) {
        this.saida = saida;
    }
    
}
