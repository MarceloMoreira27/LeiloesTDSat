package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import model.ProdutosDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            
            prep = conn.prepareStatement(sql);
            
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.execute();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
        } catch (SQLException erro) {
            
            JOptionPane.showMessageDialog(null, "Erro ao salvar no banco (DAO): " + erro.getMessage());
        } finally {
            
            try { if (prep != null) prep.close(); } catch (SQLException e) {}
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB();
        
        String sql =  "SELECT * FROM produtos";
        
        try{
            
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()){ 
                
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            
            }
            
            
        }catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro listarProdutos: " + erro.getMessage());
        }
        
        return listagem;
    }
    
    public String buscarStatus(int id) {
        
        conn = new conectaDAO().connectDB();
        
        String sql = "SELECT status FROM produtos WHERE id = ?";
    
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
        
            resultset = prep.executeQuery();
        
            if (resultset.next()) {
                return resultset.getString("status");
            } 
         
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Id: " + id + erro.getMessage());
            
        }finally {
            try { if (prep != null) prep.close(); } catch (SQLException e) {}
        }
    
            return null;
            
        }
    
        public void venderProduto(int id) {
            
            conn = new conectaDAO().connectDB();
    
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
            try {
            
                prep = conn.prepareStatement(sql);
                prep.setInt(1, id); 
        
                prep.executeUpdate(); 
        
                JOptionPane.showMessageDialog(null, "Produto " + id + " vendido com sucesso!");
        
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
                
            }finally {
            
                try { if (prep != null) prep.close(); } catch (SQLException e) {}
        }
            
        }



    
   
    
}

