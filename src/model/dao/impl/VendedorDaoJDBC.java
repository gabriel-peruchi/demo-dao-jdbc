package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bd.BD;
import bd.BdException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conexao = null;

	public VendedorDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Vendedor obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Vendedor obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePorId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vendedor consultaPorId(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement(
					"SELECT seller.*,department.Name as DepName\r\n" + "FROM seller INNER JOIN department\r\n"
							+ "ON seller.DepartmentId = department.Id\r\n" + "WHERE seller.Id = ?;");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {

				Departamento departamento = instanciaDepartamento(rs);

				Vendedor vendedor = instanciaVendedor(rs, departamento);

				return vendedor;

			}

			return null;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharResultSet(rs);
			BD.fecharStatement(ps);
		}
	}

	private Vendedor instanciaVendedor(ResultSet rs, Departamento departamento) throws SQLException {

		Vendedor vendedor = new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setNome(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
		vendedor.setDataNascimento(rs.getDate("BirthDate"));
		vendedor.setDepartamento(departamento);

		return vendedor;
	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {

		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("DepartmentId"));
		departamento.setNome(rs.getString("DepName"));

		return departamento;
	}

	@Override
	public List<Vendedor> consultaTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
