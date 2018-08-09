
package conexaodb;

import modelos.Usuario;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;

public class ConexaoDbUsuario {
    private final String HOST = "localhost";
    private final int PORT = 27017;
    private MongoClient user;
    private final String db = "aps";
    
    private MongoDatabase dbase;
    
   public void abrirConexao(){
       user = new MongoClient(HOST,PORT);
       dbase = user.getDatabase("aps" );
       System.out.println("Banco de dados aberto");
      
   }
   
   public void fecharConexao(){
       user.close();
       System.out.println("Conexao fechada");
   }
   
   public void inserirUser(Usuario usuario){
       abrirConexao();
        MongoCollection<Document> coll = dbase.getCollection("serverusers");
        Document doc = new Document("email",usuario.getEmail()).append("nome",usuario.getNome() ).append("senha",usuario.getSenha());
       coll.insertOne(doc);
       fecharConexao();
   }
   
   public Usuario pegaUsuario(String email) {
       abrirConexao();
       MongoCollection<Document> coll = dbase.getCollection("serverusers");
       BasicDBObject whereQuery = new BasicDBObject();
       whereQuery.put("email", email);
       MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
       
       // Cria um usr para retornar: 
       Usuario usrDB = new Usuario(); 
       while (cursor.hasNext()) {
           Document doc = cursor.next();
           usrDB.setEmail(doc.getString("email"));
           usrDB.setNome(doc.getString("nome"));
           usrDB.setSenha(doc.getString("senha"));
           usrDB.setImagem("avatar"); // mudar aqui
       } 
       return usrDB;
   }
   
   public HashMap pegaTodosUsuarios() {
       HashMap<String, Usuario> mapaUsuarios = new HashMap<>();
       
       abrirConexao();
       MongoCollection<Document> coll = dbase.getCollection("serverusers");
       BasicDBObject whereQuery = new BasicDBObject();
       MongoCursor<Document> cursor = coll.find().iterator();
       
       // Cria um usr para retornar: 
       Usuario usrDB; 
       while (cursor.hasNext()) {
           Document doc = cursor.next();
           usrDB = new Usuario();
           usrDB.setEmail(doc.getString("email"));
           usrDB.setNome(doc.getString("nome"));
           usrDB.setSenha(doc.getString("senha"));
           usrDB.setImagem("avatar");
           mapaUsuarios.put(usrDB.getEmail(), usrDB);
       } 
       return mapaUsuarios;
   }
   
   public void inserirContato(Usuario usuario, Usuario usuarioAdd){
       abrirConexao();
        MongoCollection<Document> coll = dbase.getCollection("serverusers");
        Document doc = new Document("email", usuario.getEmail()).append("email", new Document("$in",usuario.getEmail()));
       
       coll.insertOne(doc);
       fecharConexao();
   }
   
    public ArrayList pegaTodosContatos(Usuario usuario) { 
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
       
        abrirConexao();
        MongoCollection<Document> coll = dbase.getCollection("user");
        Document whereQuery = new Document();
        whereQuery.put("email", usuario.getEmail());
        MongoCursor<Document> cursor = coll.find(whereQuery).iterator();
        
        // Cria um usr para retornar: 
       Usuario usrDB; 
       while (cursor.hasNext()) {
           List<Document> listaContatos = new ArrayList<>();
           listaContatos = (List<Document>) cursor.next().get("contatos");
           int i=0;
           for (Document contato: listaContatos) { 
               usrDB = new Usuario();
               usrDB.setEmail(listaContatos.get(i).getString("email"));
               usrDB.setNome(listaContatos.get(i).getString("nome"));
               usrDB.setImagem("avatar");
               listaUsuarios.add(usrDB);
               i++;
           }
         
       } 
       
       fecharConexao();
       return listaUsuarios;
   }
   
   public void alterarContato(Usuario usuario, Usuario usuarioAdd){
       abrirConexao();
        MongoCollection<Document> coll = dbase.getCollection("serverusers");
        coll.updateOne(Filters.eq("nome", usuario.getNome()),
                                 new Document("$set", new Document("nome",usuario.getNome())));
       
       fecharConexao();
   }
   
   public void deletarContato(Usuario usuario, Usuario usuarioAdd){
       abrirConexao();
        MongoCollection<Document> coll = dbase.getCollection("serverusers");
        coll.deleteOne(Filters.eq("nome", usuario.getNome()));
       
       fecharConexao();
   }
   
   
    
}
