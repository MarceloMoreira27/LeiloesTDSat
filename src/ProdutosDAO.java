/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

        } catch (SQLException erro) {

            System.out.println("Erro na classe ProdutosDAO (Método cadastrar): "
                    + erro.getMessage());
        } finally {

            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao encerrar conexões: "
                        + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem.clear();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar produtos: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException errro) {
                System.out.println("Erro ao fechar conexões: " + errro.getMessage());

            }
        }

        return listagem;
    }

    public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();

        String sqlCheck = "SELECT status FROM produtos WHERE id = ?";
        String sqlUpdate = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        String statusAtual = "";

        try {

            prep = conn.prepareStatement(sqlCheck);
            prep.setInt(1, id);
            resultset = prep.executeQuery();

            if (resultset.next()) {
                statusAtual = resultset.getString("status");

                if ("Vendido".equalsIgnoreCase(statusAtual)) {
                    JOptionPane.showMessageDialog(null, "Este produto já consta como VENDIDO no sistema!");

                } else {

                    prep = conn.prepareStatement(sqlUpdate);
                    prep.setInt(1, id);
                    prep.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
                }
            } else {

                JOptionPane.showMessageDialog(null, "ID não encontrado. Verifique o número digitado.");
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao vender: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException errro) {
                System.out.println("Erro ao fechar conexões: " + errro.getMessage());

            }
        }

    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar vendas no DAO: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException errro) {
                System.out.println("Erro ao fechar conexões: " + errro.getMessage());

            }
        }

        return listagem;

    }

}
