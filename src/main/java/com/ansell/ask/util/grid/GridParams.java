package com.ansell.ask.util.grid;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class GridParams
{

    String sortIndex;
    String sortOrder;
    int pageIndex;
    int rowsPerPage;
    int startIndex;
    FilterParams filterParams;

    public GridParams()
    {
        super();

    }

    public GridParams(HttpServletRequest request, Integer matchingRowCount) throws JsonParseException, IOException
    {
        this.sortIndex = request.getParameter("sidx");
        this.sortOrder = request.getParameter("sord");
        if(request.getParameter("rows")!=null)
        {
            this.rowsPerPage = Integer.parseInt(request.getParameter("rows"));
        }

        if (matchingRowCount < rowsPerPage)
        {
            this.pageIndex = 1;
            this.startIndex = 0;
        }
        else
        {
            if(request.getParameter("page")!=null)
            {
                this.pageIndex = Integer.parseInt(request.getParameter("page"));
                this.startIndex = rowsPerPage * (pageIndex - 1);
            }
            
        }

        String filters = request.getParameter("filters");
        if (filters != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filters);
            this.filterParams = jp.readValueAs(FilterParams.class);

        }

    }

    public GridParams(String sortIndex, String sortOrder, int pageIndex, int rowsPerPage, int startIndex, FilterParams filterParams)
    {
        super();
        this.sortIndex = sortIndex;
        this.sortOrder = sortOrder;
        this.pageIndex = pageIndex;
        this.rowsPerPage = rowsPerPage;
        this.startIndex = startIndex;
        this.filterParams = filterParams;
    }

    public GridParams(String sortIndex, String sortOrder, int pageIndex, int rowsPerPage, int startIndex, String filterParamsJson)
            throws JsonProcessingException, IOException
    {
        super();
        this.sortIndex = sortIndex;
        this.sortOrder = sortOrder;
        this.pageIndex = pageIndex;
        this.rowsPerPage = rowsPerPage;
        this.startIndex = startIndex;

        if (filterParamsJson != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filterParamsJson);
            this.filterParams = jp.readValueAs(FilterParams.class);

        }
    }
    
    public GridParams(HttpServletRequest request) throws JsonParseException, IOException
    {
        this.sortIndex = request.getParameter("sidx");
        this.sortOrder = request.getParameter("sord");
        this.rowsPerPage = Integer.parseInt(request.getParameter("rows"));

        this.pageIndex = Integer.parseInt(request.getParameter("page"));
        this.startIndex = rowsPerPage * (pageIndex - 1);

        String filters = request.getParameter("filters");
        if (filters != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(filters);
            this.filterParams = jp.readValueAs(FilterParams.class);

        }

    }

    public String getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public int getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    public FilterParams getFilterParams()
    {
        return filterParams;
    }

    public void setFilterParams(FilterParams filterParams)
    {
        this.filterParams = filterParams;
    }

}
