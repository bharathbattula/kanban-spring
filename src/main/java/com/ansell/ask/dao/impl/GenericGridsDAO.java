package com.ansell.ask.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import com.ansell.ask.dao.impl.PropertyMasterDAO;
import com.ansell.ask.util.grid.GridDetails;
import com.ansell.ask.common.IConstant;
import com.ansell.ask.dao.interfaces.IGenericGridsDAO;
import com.ansell.ask.util.grid.GenericGridParams;
import com.ansell.ask.util.grid.GridUtility;

@Repository
@Transactional
public class GenericGridsDAO implements IGenericGridsDAO{
	
	/*@Autowired
	private PropertyMasterDAO propertyMasterDAO;
*/
	@Autowired
	private JdbcTemplate jdbcTemplate = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer findCount(GridDetails gridDetails, GenericGridParams gridParams) {
		Integer rowCount=null;
		if(gridDetails.getQueryImpl().intValue()== IConstant.queryImplementationType.VIEW.getType()){
			String query = GridUtility.generateQueryForCount(gridDetails, gridParams);
			Object criteriaParams[]=GridUtility.generateCriteriaForCount(gridParams);
			rowCount = jdbcTemplate.queryForObject(query,criteriaParams,Integer.class);
		}else{
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(gridDetails.getTableName());
			Map<String, Object> inParamMap = GridUtility.generateParamMap(gridDetails, gridParams,true);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
			List<Map<String, Long>> list=(List<Map<String, Long>>)(Object) simpleJdbcCallResult.get("#result-set-1");
			rowCount = list.get(0).get("COUNT(*)").intValue();			
		}
		return rowCount;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findAllRecords(GridDetails gridDetails, GenericGridParams gridParams) {
		List<Map<String, String>> list=null;
		if(gridDetails.getQueryImpl().intValue()== IConstant.queryImplementationType.VIEW.getType()){
			String query = GridUtility.generateQueryForList(gridDetails, gridParams);
			Object criteriaParams[]=GridUtility.generateCriteriaForList(gridParams);
			list = (List<Map<String, String>>) (Object) getJdbcTemplate().queryForList(query, criteriaParams) ;
		}else{
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName(gridDetails.getTableName());
			Map<String, Object> inParamMap = GridUtility.generateParamMap(gridDetails, gridParams,false);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
			list=(List<Map<String, String>>)(Object) simpleJdbcCallResult.get("#result-set-1");
		}
		return list;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findAllRecords(GridDetails gridDetails, GenericGridParams gridParams) {
		//String dateFormat = propertyMasterDAO.getSingleProperty("system", "system", "dateFormat").getPropertyValue();
		//String timeFormat = propertyMasterDAO.getSingleProperty("system", "system", "timeFormat").getPropertyValue();
		String dateFormat = "dd-MMM-yyyy";
		String timeFormat = "HH:mm" ;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		if(gridDetails.getQueryImpl().intValue()== IConstant.queryImplementationType.VIEW.getType()){
			String query = GridUtility.generateQueryForList(gridDetails, gridParams);
			Object criteriaParams[] = GridUtility.generateCriteriaForList(gridParams);
			//list = (List<Map<String, String>>) (Object) getJdbcTemplate().queryForList(query, criteriaParams) ;
			Map<String, String> result = null;
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, criteriaParams);
			SqlRowSetMetaData metaData = rowSet.getMetaData();
			while(rowSet.next()) {
				result = new HashMap<String, String>();
				for(int columnCounter = 1; columnCounter <= metaData.getColumnCount(); columnCounter++) {
					//System.out.println(metaData.getColumnName(columnCounter) + " : " + metaData.getColumnType(columnCounter) + " : " + metaData.getColumnClassName(columnCounter) + " : " + metaData.getColumnTypeName(columnCounter));
					
					if(metaData.getColumnType(columnCounter) == Types.DATE) {
						Date dt = rowSet.getDate(columnCounter);
						if(rowSet.wasNull() == false) {
							result.put(metaData.getColumnLabel(columnCounter), new SimpleDateFormat(dateFormat).format(dt));	
						}else {
							result.put(metaData.getColumnLabel(columnCounter), null);
						}
					}else if(metaData.getColumnType(columnCounter) == Types.TIME) {
						Date dt = rowSet.getDate(columnCounter);
						if(rowSet.wasNull() == false) {
							
							result.put(metaData.getColumnLabel(columnCounter), new SimpleDateFormat(timeFormat).format(dt));	
						}else {
							result.put(metaData.getColumnLabel(columnCounter), null);
						}
					}else if(metaData.getColumnType(columnCounter) == Types.TIMESTAMP) {
						Date dt = rowSet.getDate(columnCounter);
						if(rowSet.wasNull() == false) {
							result.put(metaData.getColumnLabel(columnCounter), new SimpleDateFormat(dateFormat + " " + timeFormat).format(dt));	
						}else {
							result.put(metaData.getColumnLabel(columnCounter), null);
						}
					}else {
						result.put(metaData.getColumnLabel(columnCounter), rowSet.getString(columnCounter));
					}
				}
				list.add(result);
			}
			
		}else{
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(gridDetails.getTableName());
			Map<String, Object> inParamMap = GridUtility.generateParamMap(gridDetails, gridParams,false);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
			list=(List<Map<String, String>>)(Object) simpleJdbcCallResult.get("#result-set-1");
		}
		return list;
	}	


	
	@Override
	public GridDetails getGridDetails(String gridId) {
		String query = "select * from grid_details where grid_id=?";
		GridDetails gridDetails = jdbcTemplate.queryForObject(query, new Object[]{gridId}, new RowMapper<GridDetails>(){
 
            public GridDetails mapRow(ResultSet result, int rowNum) throws SQLException {
            	GridDetails gridDetails = new GridDetails();
            	gridDetails.setGridId(result.getString("grid_id"));
            	gridDetails.setGridName(result.getString("grid_name"));
            	gridDetails.setGridDescription(result.getString("grid_description"));
            	gridDetails.setTableName(result.getString("grid_table_name"));
            	gridDetails.setColumnNames(result.getString("grid_column_names"));
            	gridDetails.setQueryImpl(Integer.parseInt(result.getString("query_type")));
                return gridDetails;
            }
        });
		return gridDetails;
	}
	
}