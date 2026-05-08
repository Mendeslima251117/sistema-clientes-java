package util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Usuario;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UsuarioDAO {

    private MongoCollection<Document> colecao;

    // =====================================================
    // CONSTRUTOR
    // =====================================================

    public UsuarioDAO() {

        MongoDatabase db =
                MongoConnection.getDatabase();

        colecao =
                db.getCollection("usuarios");
    }

    // =====================================================
    // SALVAR
    // =====================================================

    public void salvar(Usuario u){

        Document doc =
                new Document();

        doc.append(
                "usuario",
                u.getUsuario()
        );

        doc.append(
                "senha",
                u.getSenha()
        );

        doc.append(
                "tipo",
                u.getTipo()
        );

        colecao.insertOne(doc);
    }

    // =====================================================
    // LISTAR
    // =====================================================

    public List<Usuario> listar(){

        List<Usuario> lista =
                new ArrayList<>();

        for(Document d : colecao.find()){

            Usuario u =
                    new Usuario();

            u.setUsuario(
                    d.getString("usuario")
            );

            u.setSenha(
                    d.getString("senha")
            );

            u.setTipo(
                    d.getString("tipo")
            );

            lista.add(u);
        }

        return lista;
    }

    // =====================================================
    // BUSCAR
    // =====================================================

    public Usuario buscar(String usuario){

        Document d =
                colecao.find(
                        eq("usuario", usuario)
                ).first();

        if(d == null){

            return null;
        }

        Usuario u =
                new Usuario();

        u.setUsuario(
                d.getString("usuario")
        );

        u.setSenha(
                d.getString("senha")
        );

        u.setTipo(
                d.getString("tipo")
        );

        return u;
    }

    // =====================================================
    // VALIDAR LOGIN
    // =====================================================

    public boolean validarLogin(
            String usuario,
            String senha){

        Document d =
                colecao.find(
                        eq("usuario", usuario)
                ).first();

        if(d == null){

            return false;
        }

        String senhaBanco =
                d.getString("senha");

        return senhaBanco.equals(senha);
    }

    // =====================================================
    // GET TIPO
    // =====================================================

    public String getTipo(String usuario){

        Document d =
                colecao.find(
                        eq("usuario", usuario)
                ).first();

        if(d == null){

            return "USER";
        }

        return d.getString("tipo");
    }

    // =====================================================
    // ATUALIZAR
    // =====================================================

    public void atualizar(
            String usuarioAtual,
            Usuario novo){

        Document update =
                new Document(
                        "$set",
                        new Document()
                                .append(
                                        "usuario",
                                        novo.getUsuario()
                                )
                                .append(
                                        "senha",
                                        novo.getSenha()
                                )
                                .append(
                                        "tipo",
                                        novo.getTipo()
                                )
                );

        colecao.updateOne(
                eq("usuario", usuarioAtual),
                update
        );
    }

    // =====================================================
    // EXCLUIR
    // =====================================================

    public void excluir(String usuario){

        colecao.deleteOne(
                eq("usuario", usuario)
        );
    }

    // =====================================================
    // CADASTRAR USUÁRIO
    // =====================================================

    public void cadastrarUsuario(
            String usuario,
            String senha){

        Usuario u =
                new Usuario();

        u.setUsuario(usuario);

        u.setSenha(senha);

        u.setTipo("USER");

        salvar(u);
    }
}