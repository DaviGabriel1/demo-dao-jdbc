package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO(); //não expõe a implementação da interface SellerDAO
        System.out.println("=== TEST 1: findByID ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
