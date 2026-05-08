package util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

public class Tema {

    // ================= CORES =================
    public static final Color BG_PRINCIPAL = new Color(30,30,30);
    public static final Color BG_SECUNDARIO = new Color(35,35,35);
    public static final Color BG_MENU = new Color(45,45,45);
    public static final Color BG_HEADER = new Color(20,20,20);

    public static final Color TEXTO = Color.WHITE;
    public static final Color SELECAO = new Color(70,130,180);
    public static final Color GRID = new Color(50,50,50);

    // ================= FONTE =================
    public static final Font FONTE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 16);

    // ================= BOTÃO PADRÃO =================
    public static JButton botao(String texto, String icone) {
        JButton btn = new JButton(texto);

        ImageIcon icon = getIcon(icone);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
        }

        btn.setFocusPainted(false);
        btn.setForeground(TEXTO);
        btn.setBackground(BG_MENU);
        btn.setFont(FONTE_BOLD);

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(8);
        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        return btn;
    }

    // ================= HEADER =================
    public static JPanel header(String tituloTexto) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setPreferredSize(new Dimension(0,50));

        JLabel titulo = new JLabel(tituloTexto);
        titulo.setForeground(TEXTO);
        titulo.setFont(TITULO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        header.add(titulo, BorderLayout.WEST);
        return header;
    }

    // ================= MENU LATERAL =================
    public static JPanel menuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(0,1,5,5));
        menu.setBackground(BG_PRINCIPAL);
        menu.setPreferredSize(new Dimension(150,0));
        menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return menu;
    }

    // ================= TABELA PADRÃO =================
    public static void aplicarTemaTabela(JTable tabela) {

        tabela.setRowHeight(30);
        tabela.setBackground(BG_SECUNDARIO);
        tabela.setForeground(TEXTO);

        tabela.setSelectionBackground(SELECAO);
        tabela.setSelectionForeground(TEXTO);

        tabela.setShowGrid(true);
        tabela.setGridColor(GRID);

        tabela.setIntercellSpacing(new Dimension(0,0));
        tabela.setBorder(null);

        // HEADER
        tabela.getTableHeader().setBackground(BG_HEADER);
        tabela.getTableHeader().setForeground(TEXTO);
        tabela.getTableHeader().setFont(FONTE_BOLD);

        // 🔥 LINHAS ZEBRADAS
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(SELECAO);
                } else {
                    c.setBackground(row % 2 == 0 ? BG_SECUNDARIO : new Color(40,40,40));
                }

                c.setForeground(TEXTO);
                return c;
            }
        });
    }

    // ================= SCROLL =================
    public static JScrollPane scroll(JTable tabela) {
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(BG_SECUNDARIO);
        scroll.setBorder(null);
        return scroll;
    }

    // ================= ICON =================
    public static ImageIcon getIcon(String nome) {
        URL url = Tema.class.getClassLoader().getResource("icons/" + nome);

        if (url == null) {
            System.out.println("Ícone não encontrado: " + nome);
            return null;
        }

        return new ImageIcon(url);
    }
}
