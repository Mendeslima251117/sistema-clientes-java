package session;

public class Session {

    private static String usuario;
    private static String tipo;

    public static void setUsuario(String u) {
        usuario = u;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setTipo(String t) {
        tipo = t;
    }

    public static String getTipo() {
        return tipo;
    }

    public static void limpar() {
        usuario = null;
        tipo = null;
    }
}