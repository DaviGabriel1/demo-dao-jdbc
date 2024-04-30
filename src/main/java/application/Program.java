package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

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

        System.out.println("\n===TEST 4: seller insert ===");
        Seller newSeller = new Seller(null,"greg","greg@gmail.com",new Date(),4000.00,department);
        sellerDAO.insert(newSeller);
        System.out.println("Inserted! New Id = " + newSeller.getId());

        System.out.println("\n===TEST 5: seller update ===");
        seller = sellerDAO.findById(1);
        seller.setName("Martha Waine");
        sellerDAO.update(seller);
        System.out.println("Update complete!");

        System.out.println("\n===TEST 6: seller delete ===");
        System.out.println("Enter id for delete test: ");
        int id = scan.nextInt();
        sellerDAO.deleteById(id);
        System.out.println("delete completed!");
        scan.close();
    }
}
