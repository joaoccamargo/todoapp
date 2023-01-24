package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {

    public void save(Project project){

        String sql = "INSERT INTO projects (name, description, createdAt, updatedAt) VALUES (?,?,?,?)";
                
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            //Connection database
            conn = ConnectionFactory.getConnection();
            
            //Prepare Query
            statement = conn.prepareStatement(sql);
            
            //Values
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            //Execute query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }   
    }
    
    public void update(Project project){
    
        String sql = "UPDATE projects SET (name = ?, description = ?, createdAt = ?, updatedAt = ? WHERE id = ?)";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
             //Connection database
            conn = ConnectionFactory.getConnection();
            
            //Prepare Query
            statement = conn.prepareStatement(sql);
            
            //Values
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new  Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Execute query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }   
    }
    
    
    public void removeById(int idProject){
        // Remove data by ID
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement statement= null;
        
        try {
            // Connection database
            conn = ConnectionFactory.getConnection();
            
            //Prepare query
            statement = conn.prepareStatement(sql);
            
            //Values
            statement.setInt(1, idProject);
            
            //Execute query
            statement.execute();
         } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover o projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }     
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
            }
            
         } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }     
        return projects;
    } 
}
