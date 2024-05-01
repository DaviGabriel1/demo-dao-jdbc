package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOJDBC implements DepartmentDAO {
    private Connection conn;

    public DepartmentDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO department(Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();

            if(rowsAffected>0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()) {
                    System.out.println("new id of department: " + rs.getInt(1));
                    obj.setId(rs.getInt(1));
                    System.out.println("sucess");
                }
                DB.closeResultSet(rs);
            }
            else{
                System.out.println("unexpected error");
            }
        }catch(SQLException e){
         throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setInt(2,obj.getId());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("sucess");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1,id);
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Id "+id+" deleted sucess");
            }
            else{
                System.out.println("unexpected error");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Department dep = null;
        try{
            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            while(rs.next()){
                dep = new Department();
                dep.setName(rs.getString("Name"));
                dep.setId(rs.getInt("Id"));
            }
            return dep;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        int id;
        String name;
        try{
            st = conn.prepareStatement("SELECT * FROM department");
            rs = st.executeQuery();
            while(rs.next()){
                id = rs.getInt("Id");
                name = rs.getString("Name");
                Department dep = new Department(id,name);
                list.add(dep);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
