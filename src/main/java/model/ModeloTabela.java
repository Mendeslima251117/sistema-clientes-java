package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModeloTabela extends AbstractTableModel {

    private ArrayList<Cliente> lista;

    private String[] colunas = {
            "ID",
            "CPF/CNPJ",
            "Nome",
            "Email",
            "Telefone",
            "Endereço"
    };

    public ModeloTabela(ArrayList<Cliente> lista) {

        this.lista = lista;
    }

    @Override
    public int getRowCount() {

        return lista.size();
    }

    @Override
    public int getColumnCount() {

        return colunas.length;
    }

    @Override
    public String getColumnName(int col) {

        return colunas[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Cliente c = lista.get(row);

        switch (col) {

            case 0:
                return c.getId();

            case 1:
                return c.getCpfCnpj();

            case 2:
                return c.getNome();

            case 3:
                return c.getEmail();

            case 4:
                return c.getTelefone();

            case 5:
                return c.getEndereco();
        }

        return null;
    }

    // ===== RETORNA CLIENTE =====
    public Cliente getCliente(int linha) {

        return lista.get(linha);
    }

    // ===== ADICIONAR =====
    public void addCliente(Cliente cliente) {

        lista.add(cliente);

        fireTableRowsInserted(
                lista.size() - 1,
                lista.size() - 1
        );
    }

    // ===== ATUALIZAR =====
    public void atualizarCliente(
            int linha,
            Cliente cliente) {

        lista.set(linha, cliente);

        fireTableRowsUpdated(linha, linha);
    }
}