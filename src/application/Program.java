package application;

import java.time.LocalDate;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Tchelvs");
		System.out.println(obj);
		System.out.println();
		
		Seller seller = new Seller(21,"Bob", "tchlvs@gmail.com", LocalDate.now(), 3000.0, obj);
		System.out.println(seller);
		
		

	}

}
