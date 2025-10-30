package dao;

import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        try {
            Connection con = Conexao.getConnection();
            System.out.println("Conexão bem-sucedida com o SQL Server!");
            con.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
