package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO(); //não expõe a implementação da interface SellerDAO
        System.out.println("=== TEST 1: findByID ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findByDepartment");
        Department department = new Department(2,null);
        List<Seller> list = sellerDAO.findByDepartment(department);
        for(Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n=== TEST 3: seller findAll");
        list = sellerDAO.findAll();
        for(Seller obj : list){
            System.out.println(obj);
        }
    }
}
