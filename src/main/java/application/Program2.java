package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.List;

public class Program2 {

    public static void main(String[] args) {
        DepartmentDAO dep = DAOFactory.createDepartmentDAO();
        Department d = dep.findById(10);
        System.out.println(d);

        Department insert = new Department(null,"Games");
        dep.insert(insert);

        List<Department> list = dep.findAll();

        for(Department department : list){
            System.out.println(department);
        }

        dep.deleteById(11);
        d.setName("Marketing");
        dep.update(d);

    }
}
