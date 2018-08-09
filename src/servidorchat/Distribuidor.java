/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import modelos.Mensagem;
import modelos.Usuario;

// Classe responsavel por distribuir as mensagens para os usuarios
public class Distribuidor {
    
    //              email    emissor
    private HashMap<String, Emissor> mapaEmissores;
    
    // MApa de usuarios cadastrados no DB - relacionado com o mapaEmissores; 
    private HashMap<String, Usuario> mapaUsuarios;
    
    private Collection<Emissor> emissores;

    public Distribuidor(HashMap<String, Usuario> mapa) {
        this.mapaEmissores = new HashMap<>();
        this.mapaUsuarios = mapa;
        this.emissores = new ArrayList<>();
    }

    public HashMap<String, Usuario> getMapaUsuarios() {
        return mapaUsuarios;
    }

    public void setMapaUsuarios(HashMap<String, Usuario> mapaUsuarios) {
        this.mapaUsuarios = mapaUsuarios;
    }
   
    
    public void adicionaEmissor(Emissor emissor) { 
        this.emissores.add(emissor);
    }
    
    public Collection pegaEmissor() { 
        return emissores;
    }
    
    public void adicionaUsuarioEmissor(Emissor e, String email) { 
        this.mapaEmissores.put(email, e);
    }
    
    public HashMap pegaMapaEmissores() {
        return this.mapaEmissores;
    }
    
    public void distribuiMensagem(Mensagem mensagem) throws IOException {
        for (Emissor e: this.emissores) {
            e.envia(mensagem);
        }
    }
    
    public void enviaParaUm(Mensagem mensagem, String email) throws IOException { 
        System.out.println("Enviando "+mensagem.getConteudo()+" de "+mensagem.getUsrEnvia().getNome()+" para usuario " +email);
        mapaEmissores.get(email).envia(mensagem);
    }
}
