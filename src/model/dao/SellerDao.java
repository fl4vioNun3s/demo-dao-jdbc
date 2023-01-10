package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

//Interface das operações dos vendedores.
public interface SellerDao {
	// Insere no banco de dados um vendedor.
	void insert(Seller seller);

	// Atualiza um vendedor.
	void update(Seller seller);

	// Deleta um vendedor através do Id dele.
	void deleteById(Integer id);

	// Encontra um vendedor através do Id dele.
	Seller findById(Integer id);

	// Lista todos os vendedores.
	List<Seller> findAll();
	
	//Lista que encontra os vendedores pelo departamento.
	List<Seller> findByDepartment(Department department);
}
