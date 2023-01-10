package model.dao;

import java.util.List;

import model.entities.Department;

//Interface das opera��es dos departamentos.
public interface DepartmentDao {
	
	//Insere no banco de dados um departamento.
	void insert(Department obj);
	
	//Atualiza um departamento.
	void update(Department obj);
	
	//Deleta um departamento atrav�s do Id dele.
	void deleteById(Integer id);
	
	//Encontra um departamento atrav�s do Id dele.
	Department findById(Integer id);
	
	//Lista todos os departamentos.
	List<Department> findAll();
	
	

}
