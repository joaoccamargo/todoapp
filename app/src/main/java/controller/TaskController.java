package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {

    public void save(Task task) throws SQLException {

        String sql = "INSERT INTO tasks (idProject, name, description, completed, notes, deadline,"
                + "createdAt, updatedAt) VALUES (?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Connection database
            conn = ConnectionFactory.getConnection();
            
            // Prepare Query
            statement = conn.prepareStatement(sql);
            
            //Values
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            
            //Execute query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET ("
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "completed = ?, "
                + "notes = ?, "
                + "deadline = ?,"
                + "createdAt = ?, "
                + "updatedAt = ?"
                + "WHERE id = ?)";

        Connection conn = null;
        PreparedStatement statement = null;
        
         try {
            // Connection database
            conn = ConnectionFactory.getConnection();
            
            // Prepare query
            statement = conn.prepareStatement(sql);
            
            // Values
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            // Execute query
            statement.execute();   
         } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int taskId) throws SQLException {
        // Remove data by ID

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
             // Connection database
            conn = ConnectionFactory.getConnection();
            
             // Prepare query
            statement = conn.prepareStatement(sql);
            
             // Values
            statement.setInt(1, taskId);
            
             // Execute query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao remover a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        // Task List
        List<Task> tasks = new ArrayList<Task>();
              
        try {
            //Connection database
           conn = ConnectionFactory.getConnection();
           
           //Prepare query
           statement = conn.prepareStatement(sql);
           
           //Values
           statement.setInt(1, idProject);
           
           //Execute query
           resultSet = statement.executeQuery();
           
           //Loop
           while (resultSet.next()){
               //Instance List
               Task task = new Task();
               
               //Values
               task.setId(resultSet.getInt("id"));
               task.setIdProject(resultSet.getInt("idProject"));
               task.setName(resultSet.getString("name"));
               task.setDescription(resultSet.getString("description"));
               task.setNotes(resultSet.getString("notes"));
               task.setIsCompleted(resultSet.getBoolean("completed"));
               task.setDeadline(resultSet.getDate("deadline"));
               task.setCreatedAt(resultSet.getDate("createdAt"));
               task.setUpdatedAt(resultSet.getDate("updatedAt"));
               
               //Add to List
               tasks.add(task);
           }
            
        } catch (Exception ex) {
             throw new RuntimeException("Erro na lista de tarefas " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        // Load Task List
        return tasks;
    }

}
