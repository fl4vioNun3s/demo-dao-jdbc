package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
	SellerDao sellerDao = DaoFactory.createSellerDao();
	
	System.out.println("=== TESTE 1 - findById ===");
    Seller seller = sellerDao.findById(3);
    System.out.println(seller);
    
    System.out.println("-----------");
    
    System.out.println("=== TESTE 2 - findByDepartment ===");
    Department department = new Department(2, null);
    List<Seller> sellers = sellerDao.findByDepartment(department);
    for(Seller s : sellers) {
    	System.out.println(s);
    }
    
    System.out.println("-----------");
    
    System.out.println("=== TESTE 3 - findAll ===");
 
    sellers = sellerDao.findAll();
    for(Seller s : sellers) {
    	System.out.println(s);
    }
    
    System.out.println("-----------");
    
    System.out.println("=== TESTE 4 - insert ===");
    Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
    sellerDao.insert(newSeller);
    System.out.println("Inserido! Novo id: " + newSeller.getId());
    
    System.out.println("-----------");
    System.out.println("=== TESTE 5 - update ===");
    seller = sellerDao.findById(1);
    seller.setName("Martha Fushia");
    sellerDao.update(seller);
    System.out.println("Atualiza��o completa!");
	}
	

}
