package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		DepartmentDao dpDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TESTE 1 - findById ===");
	    Department department = dpDao.findById(2);
	    System.out.println(department);
	    System.out.println("---------------");

		System.out.println("=== TESTE 2 - findAll ===");
	    List<Department> departments = dpDao.findAll();
	    for(Department dp : departments) {
	    	System.out.println(dp);
	    }
	    System.out.println("---------------");
	    
		System.out.println("=== TESTE 3 - insert ===");
		Department dep = new Department(null, "Music");
		dpDao.insert(dep);
		System.out.println("Departamento adicionado com sucesso!");
		department = dpDao.findById(dep.getId());
		System.out.println(department);
	    System.out.println("---------------");
	    
	    
		System.out.println("=== TESTE 4 - update ===");
		department = dpDao.findById(6);
		department.setName("Food");
		dpDao.update(department);
		System.out.println("Atualização feita com sucesso!");
		System.out.println(department);
	    System.out.println("---------------");
	    
		System.out.println("=== TESTE 5 - deleteById ===");
		System.out.print("Escolha o Id do departamento a ser excluído: ");
		int id = sc.nextInt();
		dpDao.deleteById(id);
		System.out.println("Exclusão completa!");
	    System.out.println("---------------");
	    
	    
	    sc.close();
	}
}
