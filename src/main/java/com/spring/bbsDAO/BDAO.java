package com.spring.bbsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.spring.bbsVO.BVO;

public class BDAO {
	DataSource dataSource;

	public BDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<BVO> list() {
		ArrayList<BVO> bVOs = new ArrayList<BVO>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_bbs"
					+ "order by bGroup desc, bStep asc";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int bNo = resultSet.getInt("bNo");
				String bName = resultSet.getString("bName");
				String bSubject = resultSet.getString("bSubject");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");

				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				BVO bVO = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);
				bVOs.add(bVO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return bVOs;

	}

	public void write(String bName, String bSubject, String bContent) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			System.out.println("----connection 확보----");

			String sql = "insert into mbv_bbs(bNo, bName, bSubject, bContent, bHit, bGroup, bStep, bIndent, "
					+ "values(seq_bbs.nextval,?,?,?,0,seq_bbs.currval,0,0)";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bSubject);
			preparedStatement.setString(3, bContent);

			int n = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public BVO contentView(String bbsNo) {

		addHit(bbsNo);

		BVO bVo = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String sql = "select * from mvc_bbs where bNo = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bbsNo));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int bNo = resultSet.getInt("bNo");
				String bName = resultSet.getString("bName");
				String bSubject = resultSet.getString("bSubject");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");

				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				bVo = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return bVo;
	}// contentView()

	public void modify(String bNo, String bName, String bSubject, String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			System.out.println("커넥션 확보");

			String sql = "update mvc_bbs set bName=?, bSubject=?, bContent=? where bNo = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bSubject);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bNo));

			int n = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}
	}

	public void delete(String bNo) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();

			String sql = "delete from mvc_bbs where bNo= ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bNo));

			int n = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public BVO replyForm(String bbsNo) {
		BVO bVO = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String sql = "select * from mvc_bbs where bNo = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bbsNo));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int bNo = resultSet.getInt("bNo");
				String bName = resultSet.getString("bName");
				String bSubject = resultSet.getString("bSubject");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				bVO = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return bVO;
	}

	public void replyOk(String bNo, String bName, String bSubject, String bContent, String bGroup, String bStep,
			String bIndent) {

		replySet(bGroup, bStep);

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = dataSource.getConnection();
			String sql = "insert into mvc_bbs(bNo, bName, bSubject, bContent, bGroup, bStep, bIndent)"
					+ " values(seq_bbs.nextVal,?,?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bSubject);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep)+1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);

			int n = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void replySet(String group, String step) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			String sql = "update mvc_bbs set bStep = bStep+1 where bGroup=? and bStep>?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(group));
			preparedStatement.setInt(2, Integer.parseInt(step));
			
			int n = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void addHit(String bNo) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			String sql = "update mvc_bbs set bHit = bHit + 1 where bNo = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bNo);

			int n = preparedStatement.executeUpdate();
			System.out.println("hit수 업데이트!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// addHit()
}
