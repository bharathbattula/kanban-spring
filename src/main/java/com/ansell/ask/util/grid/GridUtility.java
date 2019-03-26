package com.ansell.ask.util.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GridUtility {
	
	private static Logger gtLogger = LoggerFactory.getLogger(GridUtility.class);

	public static String generateQueryForCount(GridDetails gridDetails, GenericGridParams gridParams){
		boolean criteriaParamsPressent=gridParams.getCriteriaParams()!= null && gridParams.getCriteriaParams().size()>0 ? true : false;
		boolean filterParamsPresent =gridParams.getFilterParams()!=null && (gridParams.getFilterParams().getRules() != null && gridParams.getFilterParams().getRules().size()>0) ? true: false;
		String whereClauseForOr = " ";
		StringBuilder query=new StringBuilder("select count(*) from "+gridDetails.getTableName()+" ");
		if(criteriaParamsPressent){
			query.append("where ");
			for(Map.Entry<String,Object> criteriaParams : gridParams.getCriteriaParams().entrySet()){
				if(!(criteriaParams.getValue() == null || criteriaParams.getValue().equals(""))){
					if(criteriaParams.getKey().startsWith("orand_")){
						String[] s=criteriaParams.getKey().substring(6).split("_");
						///query.append(" "+s[0]+" like ? or ("+s[1] + " like ?  and ");
						whereClauseForOr += "( "+s[0]+" like ? or ("+s[1] + " like ?  and ";
					}else if (criteriaParams.getKey().startsWith("and_")){
						//query.append(criteriaParams.getKey().substring(4).split("_")[0]+" = ?) and ");
						whereClauseForOr += criteriaParams.getKey().substring(4).split("_")[0]+" = ?)) and ";
					}
					else if (criteriaParams.getKey().startsWith("notin_")){						
						whereClauseForOr += criteriaParams.getKey().substring(6).split("_")[0]+" not in  (?) and ";
					}
					else if(criteriaParams.getKey().startsWith("or_")){
						String[] s=criteriaParams.getKey().substring(3).split("_");
						whereClauseForOr += "( "+s[0]+" like ? or ("+s[1] + " like ? )) and ";
					}else{
						query.append(criteriaParams.getKey()+" = ? and ");
					}
				}
				
			}
		}
		if(filterParamsPresent){
			query.append(!criteriaParamsPressent ? "where " : "");
			for(SearchFields sf : gridParams.getFilterParams().getRules()){
				query.append(sf.getField()+" like ? and ");
			}
		}
		
		query.append(whereClauseForOr);
		if(query.lastIndexOf("and ")> -1){
		    query.replace(query.lastIndexOf("and "), query.lastIndexOf("and ")+4, "");
		}
		return query.toString();
	}
	
	public static Object[] generateCriteriaForCount(GenericGridParams gridParams){
		boolean criteriaParamsPressent=gridParams.getCriteriaParams()!= null && gridParams.getCriteriaParams().size()>0 ? true : false;
		boolean filterParamsPresent =gridParams.getFilterParams()!=null && (gridParams.getFilterParams().getRules() != null && gridParams.getFilterParams().getRules().size()>0) ? true: false;
		ArrayList<Object> params = new ArrayList<Object>();
		ArrayList<Object> paramsForOrCondition = new ArrayList<Object>();
		if(criteriaParamsPressent){
			for(Map.Entry<String,Object> criteriaParams : gridParams.getCriteriaParams().entrySet()){
				if(!(criteriaParams.getValue() == null || criteriaParams.getValue().equals(""))){
					if(criteriaParams.getKey().startsWith("orand_")){
						String[] s= criteriaParams.getValue().toString().split(",");
						paramsForOrCondition.add(s[0]);
						paramsForOrCondition.add(s[1]);
					}else if(criteriaParams.getKey().startsWith("and_")){
						paramsForOrCondition.add(criteriaParams.getValue());
					}else if(criteriaParams.getKey().startsWith("or_")){
						String[] s= criteriaParams.getValue().toString().split(",");
						paramsForOrCondition.add(s[0]);
						paramsForOrCondition.add(s[1]);
					}
					else if(criteriaParams.getKey().startsWith("notin_")){
						Object obj = criteriaParams.getValue();
						String s = obj.toString();
						List<String> list = new ArrayList<String>();
						ObjectMapper objectMapper = new ObjectMapper();
						try{
						//List<String> response = (List<String>) objectMapper.readValue(s, List.class);
					/*	String data = criteriaParams.getValue().toString().replace("[", "").replace("]", "");
						String[] s= data.split(",");
						//String data = s[1] ;
						String data = "edbc578e-472d-11e9-9472-54bf6403bad3";
						paramsForOrCondition.add(s);*/
						/*ObjectMapper mapper = new ObjectMapper();
						try {
						JSONArray arr = new JSONArray(s);
						List<String> list = new ArrayList<String>();
						for(int i = 0; i < arr.length(); i++){						    
								list.add(arr.getString(i));
						}*/
						//String data = criteriaParams.getValue().toString();
						//String data = "('edbc578e-472d-11e9-9472-54bf6403bad3','edba1efb-472d-11e9-9472-54bf6403bad3')";
						/*
							JSONArray arr = new JSONArray(s);
							
							for(int i = 0; i < arr.length(); i++){						    
									list.add(arr.getString(i));}
						
						String data = "'','edbc578e-472d-11e9-9472-54bf6403bad3'";
						list = new ArrayList<String>();
						list.add(data);*/
						List<String> response  = new ArrayList<String>();
						response.add("edbc578e-472d-11e9-9472-54bf6403bad3");
						paramsForOrCondition.add(response);
						}catch(Exception ex){
							ex.getMessage();
						}
						/*} 
						catch (JSONException ex) {
							gtLogger.error("Error message in generateCriteriaForCount "+ex);
						}	*/			
						
					}
					else{
						params.add(criteriaParams.getValue());
					}
				}
				//params.add(criteriaParams.getValue());
			}
		}
		if(filterParamsPresent){
			for(SearchFields sf : gridParams.getFilterParams().getRules()){
				params.add("%"+sf.getData()+"%");
			}
		}
		
		if(paramsForOrCondition.isEmpty() == false || paramsForOrCondition.size() != 0){
			params.addAll(paramsForOrCondition);
		}
		return params.toArray();
	}
	
	public static String generateQueryForList(GridDetails gridDetails, GenericGridParams gridParams){
		boolean criteriaParamsPressent=gridParams.getCriteriaParams()!= null && gridParams.getCriteriaParams().size()>0 ? true : false;
		boolean filterParamsPresent =gridParams.getFilterParams()!=null && (gridParams.getFilterParams().getRules() != null && gridParams.getFilterParams().getRules().size()>0) ? true: false;
		String whereClauseForOr = " ";
		StringBuilder query=new StringBuilder("select ");
		query.append(gridDetails.getColumnNames()+" from "+gridDetails.getTableName()+" ");
		if(criteriaParamsPressent){
			query.append("where ");
			for(Map.Entry<String,Object> criteriaParams : gridParams.getCriteriaParams().entrySet()){
				if(!(criteriaParams.getValue() == null || criteriaParams.getValue().equals(""))){
					if(criteriaParams.getKey().startsWith("orand_")){
						String[] s=criteriaParams.getKey().substring(6).split("_");
						//query.append("("+s[0]+" like ? ) or ("+s[1] + " like ?  and ");
						whereClauseForOr += "( "+s[0]+" like ?  or ("+s[1] + " like ?  and ";
					}else if (criteriaParams.getKey().startsWith("and")){
						//query.append(criteriaParams.getKey().substring(4).split("_")[0]+" = ?) and ");
						whereClauseForOr += criteriaParams.getKey().substring(4).split("_")[0]+" = ?)) and ";
					}else if(criteriaParams.getKey().startsWith("or_")){
						String[] s=criteriaParams.getKey().substring(3).split("_");
						whereClauseForOr += "( "+s[0]+" like ?  or ("+s[1] + " like ? )) and ";
					}
					else if (criteriaParams.getKey().startsWith("notin_")){						
						whereClauseForOr += criteriaParams.getKey().substring(6).split("_")[0]+" not in  (?) and ";
					}
					else{
						query.append(criteriaParams.getKey()+" = ? and ");
					}
				}
				//query.append(criteriaParams.getKey()+" = ? and ");
			}
		}
		if(filterParamsPresent){
			query.append(!criteriaParamsPressent ? "where " : "");
			for(SearchFields sf : gridParams.getFilterParams().getRules()){
				query.append(sf.getField()+" like ? and ");
			}
		}
		
		query.append(whereClauseForOr);
		if(query.lastIndexOf("and ")> -1){
		    query.replace(query.lastIndexOf("and "), query.lastIndexOf("and ")+4, "");
		}
		if((gridParams.getSortIndex()!= null && !gridParams.getSortIndex().isEmpty()) && (gridParams.getSortOrder()!=null && !gridParams.getSortOrder().isEmpty())){
			query.append("order by "+gridParams.getSortIndex()+" "+gridParams.getSortOrder());
		}
		query.append(" limit ?,?");
		return query.toString();
	}
	
	public static Object[] generateCriteriaForList(GenericGridParams gridParams){
		boolean criteriaParamsPressent=gridParams.getCriteriaParams()!= null && gridParams.getCriteriaParams().size()>0 ? true : false;
		boolean filterParamsPresent =gridParams.getFilterParams()!=null && (gridParams.getFilterParams().getRules() != null && gridParams.getFilterParams().getRules().size()>0) ? true: false;
		ArrayList<Object> params = new ArrayList<Object>();
		ArrayList<Object> paramsForOrCondition = new ArrayList<Object>();
		if(criteriaParamsPressent){
			for(Map.Entry<String,Object> criteriaParams : gridParams.getCriteriaParams().entrySet()){
				if(!(criteriaParams.getValue() == null || criteriaParams.getValue().equals(""))){
					if(criteriaParams.getKey().startsWith("orand_")){
						String[] s= criteriaParams.getValue().toString().split(",");
						paramsForOrCondition.add(s[0]);
						paramsForOrCondition.add(s[1]);
					}else if(criteriaParams.getKey().startsWith("and")){
						paramsForOrCondition.add(criteriaParams.getValue());
					}else if(criteriaParams.getKey().startsWith("or_")){
						String[] s= criteriaParams.getValue().toString().split(",");
						paramsForOrCondition.add(s[0]);
						paramsForOrCondition.add(s[1]);
					}
					else if(criteriaParams.getKey().startsWith("notin_")){
						Object obj = criteriaParams.getValue();
					/*	String data = criteriaParams.getValue().toString().replace("[", "").replace("]", "");
						String[] s= data.split(",");
						//String data = s[1] ;
						//String data = "edbc578e-472d-11e9-9472-54bf6403bad3";
						paramsForOrCondition.add(s);
						ObjectMapper mapper = new ObjectMapper();
						try {
						JSONArray arr = new JSONArray(obj);
						List<String> list = new ArrayList<String>();
						for(int i = 0; i < arr.length(); i++){						    
								list.add(arr.getJSONObject(i).getString("name"));
						}
						paramsForOrCondition.add(list);
						} 
						catch (JSONException ex) {
							gtLogger.error("Error message in generateCriteriaForCount "+ex);
						}				
						*/
						String data = "edbc578e-472d-11e9-9472-54bf6403bad3";
						//String data = "('edbc578e-472d-11e9-9472-54bf6403bad3','edba1efb-472d-11e9-9472-54bf6403bad3')";
						paramsForOrCondition.add(data);
					}
					else{
						params.add(criteriaParams.getValue());
					}
				}
				//params.add(criteriaParams.getValue());
			}
		}
		if(filterParamsPresent){
			for(SearchFields sf : gridParams.getFilterParams().getRules()){
				params.add("%"+sf.getData()+"%");
			}
		}
		
		if(paramsForOrCondition.isEmpty() == false || paramsForOrCondition.size() != 0){
			params.addAll(paramsForOrCondition);
		}
		
		params.add(gridParams.getStartIndex());
		params.add(gridParams.getRowsPerPage());
		return params.toArray();
	}
	
	public static Map<String, Object> generateParamMap(GridDetails gridDetails, GenericGridParams gridParams, boolean forCnt){
		boolean criteriaParamsPressent=gridParams.getCriteriaParams()!= null && gridParams.getCriteriaParams().size()>0 ? true : false;
		boolean filterParamsPresent =gridParams.getFilterParams()!=null && (gridParams.getFilterParams().getRules() != null && gridParams.getFilterParams().getRules().size()>0) ? true: false;
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		String[] columnNames=gridDetails.getColumnNames().split(",");
		List<String> columns = new ArrayList<String>(Arrays.asList(columnNames));
		if(criteriaParamsPressent){
			for(Map.Entry<String,Object> criteriaParams : gridParams.getCriteriaParams().entrySet()){
				inParamMap.put(criteriaParams.getKey(), criteriaParams.getValue());
			}
		}
		if(filterParamsPresent){
			for(SearchFields sf : gridParams.getFilterParams().getRules()){
				inParamMap.put(sf.getField(), sf.getData());
				columns.remove(sf.getField());
			}
		}
		for(String column : columns){
			if(!column.isEmpty() && column.length()>0){
				inParamMap.put(column, null);
			}
		}
		
		if(forCnt){
			inParamMap.put("sortIndex", null);
			inParamMap.put("sortOrder", null);
			inParamMap.put("limitFrom", null);
			inParamMap.put("limitTo", null);
			inParamMap.put("forCount", 1);
		}else{
			inParamMap.put("sortIndex", gridParams.getSortIndex()!= null && !gridParams.getSortIndex().isEmpty() ?gridParams.getSortIndex():null);
			inParamMap.put("sortOrder", gridParams.getSortOrder()!=null && !gridParams.getSortOrder().isEmpty()?gridParams.getSortOrder():null);
			inParamMap.put("limitFrom", gridParams.getStartIndex());
			inParamMap.put("limitTo", gridParams.getRowsPerPage());
			inParamMap.put("forCount", 0);
		}
		return inParamMap;
	}

}
