package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;

		try {
			// Faz a consulta e retorna as chaves geradas;
			st = conn.prepareStatement("INSERT INTO department " + "(Name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			// Atribui uma String � interroga��o.
			st.setString(1, obj.getName());

			//Linhas afetadas
			int rowsAffected = st.executeUpdate();
			

			if(rowsAffected > 0) {
				
				//Pega as chaves geradas.
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next() ) {
					//Pega o id e atribui ao novo objeto.
					int id = rs.getInt(1);
					obj.setId(id);
				}
				else {
					throw new DbException("Erro Inesperado! Nenhuma linha afetada.");
				}
				DB.closeResultSet(rs);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		
		try {
			//Consulta de atualiza��o
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
		
			// Atribui as vari�veis do objeto �s interroga��es.
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			//Consulta sql.
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			//Atribui vari�veis �s interroga��es das consultas.
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			//Consulta sql.
			st = conn.prepareStatement("SELECT department.* "
					                   + "FROM department "
					                   + "WHERE department.Id = ?");
			st.setInt(1, id);
			
			//Retorna a consulta pro ResultSet.
			rs = st.executeQuery();
			
			// Testa se tem algo no ResultSet.
			if (rs.next()) {
				
				//Cria o objeto Departament.
				Department dep = instantiateDepartment(rs);
				
				return dep;
			}
			
			//Se n�o tiver nada no ResultSet, retorna nulo.
			return null;
		} 
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//Consulta sql.
			st = conn.prepareStatement("SELECT department.* "
					                   + "FROM department "
					                   + "ORDER BY Name");
			
			//Retorna a consulta sql para um ResultSet.
			rs = st.executeQuery();
			
			//Lista que vai armazenar os departamentos.
			List<Department> departments = new ArrayList<>();
			
			
			
			//Enquanto houver departamentos, os adiciona na lista.
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);			
				departments.add(dep);
				
				
			}
			return departments;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
