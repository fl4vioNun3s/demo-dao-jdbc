package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

//Operações dos vendedores usando JDBC.
public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		try {

			// Faz a consulta e retorna as chaves geradas;
			st = conn.prepareStatement("INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// Atribui as variáveis do objeto às interrogações.
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {

				// Pega as chaves geradas.
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {

					// Pega o id e atribui ao novo objeto.
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Error inesperado! Nenhuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			// Consulta sql.
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);

			// Retorna a consulta no ResultSet.
			rs = st.executeQuery();

			// Testa se tem algo no ResultSet.
			if (rs.next()) {
				
				//Cria o objeto Departament.
				Department dep = instantiateDepartment(rs);

				//Cria o objeto Seller.
				Seller obj = instantiateSeller(rs,dep);

				return obj;
			}
			// Se não tiver nada no ResultSet, retorna nulo.
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	} 
	
	//Método auxiliar que instancia um departamento.
	public Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	
	public Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}
	
	
	//Método auxiliar que instancia um vendedor.

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			// Consulta sql.
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");

			// Retorna a consulta no ResultSet.
			rs = st.executeQuery();

			//Cria a lista que vai armazenar os vendedores.
			List<Seller> list = new ArrayList<>();
			
			//Cria um map para armazenar departamentos.
			Map<Integer, Department> map = new HashMap<>();
			
			// Testa se tem algo no ResultSet.
			while (rs.next()) {
				
				//Pega um departamento que esteja no Map.
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se o departamento não estiver no Map, entra no if.
				if(dep == null) {
				
				 //Cria um novo departamento e adiciona no Map.
				 dep = instantiateDepartment(rs);
				 map.put(rs.getInt("DepartmentId"), dep);
				}

				//Cria o objeto Seller.
				Seller obj = instantiateSeller(rs,dep);

				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			// Consulta sql.
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");

			st.setInt(1, department.getId());

			// Retorna a consulta no ResultSet.
			rs = st.executeQuery();

			//Cria a lista que vai armazenar os vendedores.
			List<Seller> list = new ArrayList<>();
			
			//Cria um map para armazenar departamentos.
			Map<Integer, Department> map = new HashMap<>();
			
			// Testa se tem algo no ResultSet.
			while (rs.next()) {
				
				//Pega um departamento que esteja no Map.
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se o departamento não estiver no Map, entra no if.
				if(dep == null) {
				
				 //Cria um novo departamento e adiciona no Map.
				 dep = instantiateDepartment(rs);
				 map.put(rs.getInt("DepartmentId"), dep);
				}

				//Cria o objeto Seller.
				Seller obj = instantiateSeller(rs,dep);

				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
