package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department obj = new Department(1,"Books");

        Seller seller = new Seller(21,"Davi","davig@gmail.com",new Date(),3000.00,obj);
        SellerDAO sellerDAO = DAOFactory.createSellerDAO(); //não expõe a implementação da interface SellerDAO
        System.out.println(seller);
    }
}
