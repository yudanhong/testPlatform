package net.keepsoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class BaseDao {
	
	protected JdbcTemplate jdbcTemplate ;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 查询 ，通过rowMapper进行映射
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
		return this.jdbcTemplate.query(sql, rowMapper, args);
	}

	/**
	 * 查询单个对象，没有则返回null
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType,
			Object... args) throws DataAccessException {
		try {
			return this.jdbcTemplate.queryForObject(sql, requiredType, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 查询单个对象的列表，注意查出的每行只能有一个值。
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, Class<T> requiredType,
			Object... args) throws DataAccessException {
		try {
			return this.jdbcTemplate.queryForList(sql, requiredType, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 查询Bean的列表
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForBeanList(String sql, Class<T> requiredType,
			Object... args) throws DataAccessException {
		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(
				requiredType), args);
	}

	/**
	 * 查询单个bean，没有则返回null,映射关系为名字映射 Column values are mapped based on matching
	 * the column name as obtained from result set metadata to public setters
	 * for the corresponding properties. The names are matched either directly
	 * or by transforming a name separating the parts with underscores to the
	 * same name using "camel" case.
	 * 
	 * 
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForBean(String sql, Class<T> requiredType, Object... args)
			throws DataAccessException {
		try {
			return this.jdbcTemplate.queryForObject(sql,
					new BeanPropertyRowMapper<T>(requiredType), args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * 查询单条记录，查询结果通过mapper映射
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper,
			Object... args) throws DataAccessException {
		try {
			return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Query given SQL to create a prepared statement from SQL and a list of
	 * arguments to bind to the query, expecting a result Map. The queryForMap()
	 * methods defined by this interface are appropriate when you don't have a
	 * domain model. Otherwise, consider using one of the queryForObject()
	 * methods.
	 * 
	 * The query is expected to be a single row query; the result row will be
	 * mapped to a Map (one entry for each column, using the column name as the
	 * key).
	 * 
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryForMap(String sql, Object... args)
			throws DataAccessException {
		try {
			return this.jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param sql
	 *            字段第一个是KEY,第二个是值
	 * @param args
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map queryForMaps(String sql, Object... args) {
		Map map = this.jdbcTemplate.query(sql, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException {
				Map map = new LinkedHashMap();
				while (rs.next()) {
					Object col1 = rs.getObject(1);
					Object col2 = rs.getObject(2);
					map.put(col1, col2);
				}
				return map;
			};
		});
		return map;
	}

	/**
	 * Query given SQL to create a prepared statement from SQL and a list of
	 * arguments to bind to the query, expecting a result list.
	 * 
	 * The results will be mapped to a List (one entry for each row) of Maps
	 * (one entry for each column, using the column name as the key). Each
	 * element in the list will be of the form returned by this interface's
	 * queryForMap() methods.
	 * 
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForListMap(String sql, Object... args)
			throws DataAccessException {

		return this.jdbcTemplate.queryForList(sql, args);
	}

	/**
	 * Issue a single SQL update operation (such as an insert, update or delete
	 * statement) via a prepared statement, binding the given arguments.
	 * 
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql, Object... args) {
		return this.jdbcTemplate.update(sql, args);
	}


	
	/**
	 * 更新，并且返回更新后的主键值<br />
	 * 
	 * 
	 * @param sql
	 * @param keyColunmName
	 *            主键字段名称
	 * @param args
	 * @return
	 */
	public Object updateAndReturnPrimaryKeyWithKeyColunmName(final String sql,
			final String keyColunmName, final Object... args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql,
						new String[] { keyColunmName });
				for (int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
				// ps.setString(1, name);
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	/**
	 * 更新，并且返回更新后的主键值(主键的名字为id)
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Object updateAndReturnPrimaryKey(final String sql, final Object... args) {
		return this.updateAndReturnPrimaryKeyWithKeyColunmName(sql, "id", args);
	}

	/**
	 * Issue multiple update statements on a single PreparedStatement, using
	 * batch updates and a BatchPreparedStatementSetter to set values.
	 * 
	 * Will fall back to separate updates on a single PreparedStatement if the
	 * JDBC driver does not support batch updates.
	 * 
	 * @param sql
	 * @param bpss
	 * @return
	 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
		return this.jdbcTemplate.batchUpdate(sql, bpss);
		
	}
	
	public int getAllCount(String sql,Object... args){
		return this.jdbcTemplate.queryForInt(sql, args);
	}

	/**
	 * 运行存储过程信息
	 * @param csct
	 * @param cscb
	 * @return
	 */
	public Object runProc(CallableStatementCreator csct,CallableStatementCallback<?> cscb){
		return this.jdbcTemplate.execute(csct,cscb);
	}
	
	public <T> List<T> queryForObjectList(String sql, Class<T> T, Object...  args) {
//		this.jdbcTemplate.query
		return null;
	}
	
	
	
}
