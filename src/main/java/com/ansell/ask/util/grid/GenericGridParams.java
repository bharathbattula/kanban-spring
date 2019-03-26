package com.ansell.ask.util.grid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericGridParams {
	private String sortIndex=null;
    private String sortOrder=null;
    private int pageIndex=Integer.MIN_VALUE;
    private int rowsPerPage=Integer.MIN_VALUE;
    private int startIndex=Integer.MIN_VALUE;
    private FilterParams filterParams=null;
    private Map<String,Object> criteriaParams=null;

    public GenericGridParams(){
        super();
    }

    @SuppressWarnings("unchecked")
	public GenericGridParams(HttpServletRequest request, Integer matchingRowCount) throws JsonParseException, IOException{
        this.sortIndex = request.getParameter("sidx");
        this.sortOrder = request.getParameter("sord");
        if(request.getParameter("rows")!=null){
            this.rowsPerPage = Integer.parseInt(request.getParameter("rows"));
        }
        if (matchingRowCount < rowsPerPage){
            this.pageIndex = 1;
            this.startIndex = 0;
        }
        else{
            if(request.getParameter("page")!=null){
                this.pageIndex = Integer.parseInt(request.getParameter("page"));
                this.startIndex = rowsPerPage * (pageIndex - 1);
            }
        }
        String filters = request.getParameter("filters");
        if (filters != null){
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filters);
            this.filterParams = jp.readValueAs(FilterParams.class);
        }
        Set<String> reqParamKeys = request.getParameterMap().keySet();
        this.criteriaParams = new HashMap<String, Object>();
        for(String reqParamKey : reqParamKeys){
        	if(reqParamKey.contains("cr_")){
        		Object obj=null;
        		if(request.getParameter(reqParamKey).contains("int_")){
        			obj = Integer.parseInt(request.getParameter(reqParamKey).replace("int_", ""));
        		}
        		if(request.getParameter(reqParamKey).contains("str_")){
        			obj = request.getParameter(reqParamKey).replace("str_", "");
        		}
        		this.criteriaParams.put(reqParamKey.replace("cr_", ""),obj);
        	}
        }
    }

    public GenericGridParams(String sortIndex, String sortOrder, int pageIndex, int rowsPerPage, int startIndex, FilterParams filterParams){
        super();
        this.sortIndex = sortIndex;
        this.sortOrder = sortOrder;
        this.pageIndex = pageIndex;
        this.rowsPerPage = rowsPerPage;
        this.startIndex = startIndex;
        this.filterParams = filterParams;
    }

    public GenericGridParams(String sortIndex, String sortOrder, int pageIndex, int rowsPerPage, int startIndex, String filterParamsJson)
            throws JsonProcessingException, IOException {
        super();
        this.sortIndex = sortIndex;
        this.sortOrder = sortOrder;
        this.pageIndex = pageIndex;
        this.rowsPerPage = rowsPerPage;
        this.startIndex = startIndex;

        if (filterParamsJson != null){
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filterParamsJson);
            this.filterParams = jp.readValueAs(FilterParams.class);
        }
        
    }
    
    @SuppressWarnings("unchecked")
	public GenericGridParams(HttpServletRequest request) throws JsonParseException, IOException{
        this.sortIndex = request.getParameter("sidx");
        this.sortOrder = request.getParameter("sord");
        this.rowsPerPage = Integer.parseInt(request.getParameter("rows"));

        this.pageIndex = Integer.parseInt(request.getParameter("page"));
        this.startIndex = rowsPerPage * (pageIndex - 1);

        String filters = request.getParameter("filters");
        if (filters != null){
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filters);
            this.filterParams = jp.readValueAs(FilterParams.class);
        }
        Set<String> reqParamKeys = request.getParameterMap().keySet();
        for(String reqParamKey : reqParamKeys){
        	if(reqParamKey.contains("cr_")){
        		if(this.criteriaParams == null){
        			this.criteriaParams = new HashMap<String, Object>();
        		}
        		Object obj=null;
        		if(request.getParameter(reqParamKey).contains("int_")){
        			obj = Long.parseLong(request.getParameter(reqParamKey).replace("int_", ""));
        		}else if(request.getParameter(reqParamKey).contains("flt_")){
        			obj = Double.parseDouble(request.getParameter(reqParamKey).replace("flt_", ""));
        		}else if(request.getParameter(reqParamKey).contains("str_")){
        			obj = request.getParameter(reqParamKey).replace("str_", "");
        		}/*else {
        			obj = request.getParameter(reqParamKey);
        		}*/
        		this.criteriaParams.put(reqParamKey.replace("cr_", ""),obj);
        	}
        }
    }

    public String getSortIndex(){
        return sortIndex;
    }

    public void setSortIndex(String sortIndex){
        this.sortIndex = sortIndex;
    }

    public String getSortOrder(){
        return sortOrder;
    }

    public void setSortOrder(String sortOrder){
        this.sortOrder = sortOrder;
    }

    public int getPageIndex(){
        return pageIndex;
    }

    public void setPageIndex(int pageIndex){
        this.pageIndex = pageIndex;
    }

    public int getRowsPerPage(){
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage){
        this.rowsPerPage = rowsPerPage;
    }

    public int getStartIndex(){
        return startIndex;
    }

    public void setStartIndex(int startIndex){
        this.startIndex = startIndex;
    }

    public FilterParams getFilterParams(){
        return filterParams;
    }

    public void setFilterParams(FilterParams filterParams){
        this.filterParams = filterParams;
    }

	public Map<String, Object> getCriteriaParams() {
		return criteriaParams;
	}

	public void setCriteriaParams(Map<String, Object> criteriaParams) {
		this.criteriaParams = criteriaParams;
	}
    
}
