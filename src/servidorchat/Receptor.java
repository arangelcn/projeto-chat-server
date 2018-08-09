/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Mensagem;

// Aguarda as mensagens enviadas pelo usuario correspondente
public class Receptor implements Runnable {
    
    // Receptor 
    public static boolean primeiraConexao;
    
    // Atributos da classe Scanner
    private ObjectInputStream entrada; 
    private Distribuidor distribuidor;
    
    public Receptor (ObjectInputStream entrada, Distribuidor dist) { 
        this.entrada = entrada;
        this.distribuidor = dist;
        this.primeiraConexao = true;
    }

    @Override
    public void run() {
        while (true) { 
                try {
                    Mensagem mensagem = (Mensagem) this.entrada.readObject();
                    if (mensagem.getConteudo().equals("conexao")) {
                        // Envia sinal de Online: conteudo = conexao
                        this.distribuidor.distribuiMensagem(mensagem);
                    } else {
                        // Envia mensagem
                        System.out.println("Receptor recebeu de "+mensagem.getUsrEnvia().getEmail()+":"+mensagem.getConteudo());
                        System.out.println("Enviando para: " +mensagem.getUsrRecebe().getEmail());

                        this.distribuidor.enviaParaUm(mensagem, mensagem.getUsrRecebe().getEmail());
                    }                
                } catch (IOException ex) {
                    System.out.println("Esperando algo");
                } catch (ClassNotFoundException ex) {
                   System.out.println("Esperando algo");
                }
        }
    }
}
