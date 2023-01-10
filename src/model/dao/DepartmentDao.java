package model.dao;

import java.util.List;

import model.entities.Department;

//Interface das operações dos departamentos.
public interface DepartmentDao {
	
	//Insere no banco de dados um departamento.
	void insert(Department obj);
	
	//Atualiza um departamento.
	void update(Department obj);
	
	//Deleta um departamento através do Id dele.
	void deleteById(Integer id);
	
	//Encontra um departamento através do Id dele.
	Department findById(Integer id);
	
	//Lista todos os departamentos.
	List<Department> findAll();
	
	

}
