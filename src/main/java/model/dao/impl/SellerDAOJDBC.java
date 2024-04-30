package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAOJDBC implements SellerDAO {
    private Connection conn;

    public SellerDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO seller "+
                    "(Name,Email,BirthDate,BaseSalary,DepartmentId) VALUES " +
                    "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if(rowsAffected>0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Unexpected error! No rows affected!");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "+
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentID = department.Id " +
                    "WHERE seller.id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){ //verifica se retornou alguma linha
                Department dep = instantiateDepartment(rs); //método retornando cada instancia
                Seller seller = intantiateSeller(rs,dep);
                return seller;
            }
            return null;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs); //não pode fechar a conexão, pois pode ter outra ação depois dessa
        }

    }

    private Seller intantiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name AS DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentID = department.Id " +
                    "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>(); // impedir que haja mais de uma instancia de um mesmo departamento
            while(rs.next()){ //verifica se retornou alguma linha
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){ //verifica se o departamento já foi instanciado
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller seller = intantiateSeller(rs,dep);
                list.add(seller);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs); //não pode fechar a conexão, pois pode ter outra ação depois dessa
        }
    }
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name AS DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentID = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");
            st.setInt(1,department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>(); // impedir que haja mais de uma instancia de um mesmo departamento
            while(rs.next()){ //verifica se retornou alguma linha
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){ //verifica se o departamento já foi instanciado
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller seller = intantiateSeller(rs,dep);
                list.add(seller);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs); //não pode fechar a conexão, pois pode ter outra ação depois dessa
        }
    }
}
