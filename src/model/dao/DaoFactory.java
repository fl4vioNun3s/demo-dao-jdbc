package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//Classe que instancia objetos DAO.
public class DaoFactory {

	//Cria um DAO JDBC;
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
