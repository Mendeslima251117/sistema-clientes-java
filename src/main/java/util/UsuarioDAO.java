package util;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class UsuarioDAO {

    // 🔐 VALIDAR LOGIN
    public boolean validarLogin(String usuario, String senha) {

        MongoCollection<Document> col =
                MongoConnection.getDatabase().getCollection("usuarios");

        String senhaHash = SenhaUtil.hash(senha);

        Document user = col.find(new Document("usuario", usuario)
                .append("senha", senhaHash)).first();

        return user != null;
    }

    // 🔥 PEGAR TIPO (ADMIN / USER)
    public String getTipo(String usuario) {

        MongoCollection<Document> col =
                MongoConnection.getDatabase().getCollection("usuarios");

        Document user = col.find(new Document("usuario", usuario)).first();

        if (user != null) {
            return user.getString("tipo");
        }

        return "USER"; // padrão
    }

    // 🔥 CADASTRAR USUÁRIO (PADRÃO USER)
    public boolean cadastrarUsuario(String usuario, String senha) {

        MongoCollection<Document> col =
                MongoConnection.getDatabase().getCollection("usuarios");

        Document existente = col.find(new Document("usuario", usuario)).first();

        if (existente != null) return false;

        String hash = SenhaUtil.hash(senha);

        Document doc = new Document()
                .append("usuario", usuario)
                .append("senha", hash)
                .append("tipo", "USER"); // 🔥 IMPORTANTE

        col.insertOne(doc);

        return true;
    }
}