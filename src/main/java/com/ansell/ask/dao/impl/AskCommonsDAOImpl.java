package com.ansell.ask.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ansell.ask.common.IConstant;
import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.dao.interfaces.IAskCommonsDAO;
import com.ansell.ask.dao.interfaces.IPropertyReader;

@Repository
public class AskCommonsDAOImpl implements IAskCommonsDAO {

	@Autowired
	private IPropertyReader propertyReader;

	private static Logger gtLogger = LoggerFactory.getLogger(AskCommonsDAOImpl.class);

	private static final String QUERY_FOR_ALL_REGIONS = "select r.region_id,i18n.region_name from int_region r INNER JOIN int_region_i18n i18n "
			+ " ON r.region_id=i18n.region_id and r.is_deleted= :isDeleted";

	private static final String QUERY_FOR_ALL_PRODUCT_CATEGORIES = "select i18n.product_category_id,i18n.product_category_name from int_product_category p INNER JOIN "
			+ " int_product_category_i18n i18n on i18n.product_category_id = p.product_category_id "
			+ " where i18n.language_id= :language_id and p.is_deleted= :isDeleted order by i18n.product_category_name asc";

	private static final String QUERY_FOR_ALL_MANUFACTURERS = "select p.product_manufacturer_id, COALESCE((SELECT p1.manufacturer_name FROM  "
			+ " gn_product_manufacturer_i18n p1 WHERE p1.language_id = :language_id AND "
			+ " p1.product_manufacturer_id = p.product_manufacturer_id), "
			+ " (SELECT p2.manufacturer_name FROM  gn_product_manufacturer_i18n p2 WHERE"
			+ " p2.language_id = :defaultLanguageId AND p2.product_manufacturer_id = p.product_manufacturer_id),"
			+ " ('TRANSLATION MISSING')) as manufacturer, p.is_deleted from  gn_product_manufacturer p "
			+ " left join gn_product_manufacturer_i18n pi on pi.product_manufacturer_id = p.product_manufacturer_id"
			+ " and pi.language_id = :language_id where p.is_deleted = :isDeleted order by manufacturer";

	private static final String QUERY_FOR_ALL_BRANDS = "SELECT pb.product_brand_id,COALESCE (pi.product_brand_name, "
			+ " (select pbi18n.product_brand_name from int_product_brand_i18n pbi18n "
			+ " where pbi18n.product_brand_id = pb.product_brand_id and pbi18n.language_id = :defaultLanguageId ),'Translation Missing') as product_brand_name"
			+ " FROM int_product_brand pb LEFT OUTER JOIN int_product_brand_i18n pi "
			+ " ON pb.product_brand_id = pi.product_brand_id AND pi.language_id = :language_id "
			+ " WHERE pb.is_deleted = :isDeleted order by pi.product_brand_name";

	private static final String QUERY_FOR_URGENCY_LAVELS = "select i18n.record_id,i18n.record_description from lookup_table l LEFT OUTER JOIN lookup_table_i18n i18n "
			+ " ON l.category = i18n.category and l.record_id = i18n.record_id "
			+ " where i18n.language_id = :language_id and i18n.category = :urgency_category and l.is_deleted = :isDeleted";

	private static final String QUERY_TO_CREATE_OTHER_PRODUCT = " INSERT INTO int_requested_other_product (request_id,region_id,other_product_name,other_product_style,product_category_id,product_brand_id,product_brand_name,"
			+ "product_manufacturer_id,product_manufacturer_name,benchmark_testing_required,customer_name,supports_sample_procurements,urgency_level,"
			+ "results_needby_date,tests_tobe_performed,additional_formation,send_copy_of_response,updated_by) VALUES"
			+ "(:request_id, :region_id, :other_product_name, :other_product_style, :product_category_id, :product_brand_id, :product_brand_name,"
			+ ":product_manufacturer_id, :product_manufacturer_name, :benchmark_testing_required,:customer_name,:supports_sample_procurements,"
			+ ":urgency_level,:results_needby_date,:tests_tobe_performed,:additional_formation,:send_copy_of_response,:updated_by)";

	private static final String CREATE_OTHER_PRODUCT_EMAIL_RESPONSE = "INSERT INTO gn_mail_queue (to_user,from_user,cc,bcc,subject,body,creation_time) VALUES ("
			+ " :to_user, :from_user, :cc, :bcc, :subject, :body, :creation_time)";

	private static final String GET_MAX_ID = "SELECT IFNULL(MAX(SUBSTRING(request_id, 17)),0) from int_requested_other_product";

	private static final String GET_USER_DETAILS = "select user_id as userId,first_name as firstName,last_name as lastName,email,region_id as regionId,language_id as languageId from int_user where is_deleted = :is_deleted and user_id = :user_id";

	private static final String CHECK_ASK_USER_QUERY = "select count(*) from int_user_ask where user_id = :user_id and is_deleted = :is_deleted";

	private static final String CHECK_PRODUCT_IN_DB_QUERY = "select request_id from int_requested_other_product where other_product_name = :other_product_name "
			+ " and region_id = :region_id and status = :status and is_deleted = :is_deleted and product_manufacturer_name= :product_manufacturer_name and product_brand_name = :product_brand_name";
	
	private static final String GET_LANGUAGE_NAME_BY_ID = "select language_name from language where language_id= :language_id" ;
			
	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplate = null;

	@Override
	public ArrayList<Map<String, Object>> getAllRegions() {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("isDeleted",
					IConstant.RecordStatus.INSERTED.getStatus());
			return (ArrayList<Map<String, Object>>) namedJdbcTemplate.queryForList(QUERY_FOR_ALL_REGIONS,
					namedParameters);
		} catch (Exception ex) {
			gtLogger.error("getAllRegions() " + ex.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Map<String, Object>> getAllManufacturers() {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("language_id", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("defaultLanguageId", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());

			return (ArrayList<Map<String, Object>>) namedJdbcTemplate.queryForList(QUERY_FOR_ALL_MANUFACTURERS,
					namedParameters);
		} catch (Exception ex) {
			gtLogger.error("getAllManufacturers() " + ex.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Map<String, Object>> getAllBrands() {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("language_id", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("defaultLanguageId", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());

			return (ArrayList<Map<String, Object>>) namedJdbcTemplate.queryForList(QUERY_FOR_ALL_BRANDS,
					namedParameters);
		} catch (Exception ex) {
			gtLogger.error("getAllBrands() " + ex.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Map<String, Object>> getAllProductCategories() {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("language_id", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());

			return (ArrayList<Map<String, Object>>) namedJdbcTemplate.queryForList(QUERY_FOR_ALL_PRODUCT_CATEGORIES,
					namedParameters);
		} catch (Exception ex) {
			gtLogger.error("getAllProductCategories() " + ex.getMessage());
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getUrgencyLevels() {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("language_id", IConstant.DEFAULT_LANGUAGE_ID)
					.addValue("isDeleted", IConstant.RecordStatus.INSERTED.getStatus())
					.addValue("urgency_category", IConstant.URGENCY_CATEGORY);

			return (ArrayList<Map<String, Object>>) namedJdbcTemplate.queryForList(QUERY_FOR_URGENCY_LAVELS,
					namedParameters);
		} catch (Exception ex) {
			gtLogger.error("getUrgencyLevels() " + ex.getMessage());
			return null;
		}
	}

	@Override
	public String saveOtherProduct(RequestProductVO requestProductVO,String updated_by) {
		String request_Id = "PROD_REQUEST_ID_0001";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(propertyReader.getProperty("dateFormat"));
			if (this.getMaxId(GET_MAX_ID) != 0) {
				request_Id = "PROD_REQUEST_ID_" + (this.getMaxId(GET_MAX_ID) + 1);
			}			
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("request_id", request_Id)
					.addValue("region_id", requestProductVO.getRegionScopeSelected())
					.addValue("other_product_name", requestProductVO.getOtherProductName())
					.addValue("other_product_style", requestProductVO.getOtherProductStyleName())
					.addValue("product_category_id", requestProductVO.getProductCategoryId())
					.addValue("product_brand_id", requestProductVO.getProductBrandId())
					.addValue("product_brand_name", requestProductVO.getOthProdBrand())
					.addValue("product_manufacturer_id", requestProductVO.getProductManufacturerId())
					.addValue("product_manufacturer_name", requestProductVO.getOthProdManufacturer())
					.addValue("benchmark_testing_required", requestProductVO.getIsBenchmarktestingRequired())
					.addValue("customer_name", requestProductVO.getSpecificCustomerName())
					.addValue("supports_sample_procurements", requestProductVO.getSupportsProcurement())
					.addValue("urgency_level", requestProductVO.getUrgencyLevel())
					.addValue("results_needby_date",
							(Date) formatter.parse(requestProductVO.getTestResultsNeededByDate()))
					.addValue("tests_tobe_performed", requestProductVO.getTestToPerform())
					.addValue("additional_formation", requestProductVO.getAdditionalInfo())
					.addValue("send_copy_of_response", requestProductVO.getCopyOfResponse())
					.addValue("updated_by", updated_by);

					gtLogger.info("saveOtherProduct() Formatted Date : "+(Date) formatter.parse(requestProductVO.getTestResultsNeededByDate()));

					namedJdbcTemplate.update(QUERY_TO_CREATE_OTHER_PRODUCT, namedParameters);
					gtLogger.info("saveOtherProduct..Generated request id : " + request_Id);
					return request_Id;

		} catch (Exception e) {
			gtLogger.error("saveOtherProduct : " + e.getMessage());
			return null;
		}

	}

	@Override
	public int saveEmailDetails(String toUser, String fromUser, String cc, String bcc, String subject, String body) {
		try {
			KeyHolder holder = new GeneratedKeyHolder();

			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("to_user", toUser)
					.addValue("from_user", fromUser).addValue("cc", cc).addValue("bcc", bcc)
					.addValue("subject", subject).addValue("body", body).addValue("creation_time", new Date());

			int val = namedJdbcTemplate.update(CREATE_OTHER_PRODUCT_EMAIL_RESPONSE, namedParameters, holder);
			gtLogger.info("saveEmailDetails..Generated row id : " + holder.getKey().intValue());

			return val;
		} catch (Exception e) {
			gtLogger.error("saveEmailDetails() : " + e.getMessage());
			return 0;
		}
	}

	@Override
	public int getMaxId(String query) {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource();
			return namedJdbcTemplate.queryForObject(query, namedParameters, Integer.class);
		} catch (Exception e) {
			gtLogger.error("getMaxId() : " + e.getMessage());
			return 0;
		}
	}

	@Override
	public String getRequestedProductId(String productName,String  regionId,String prodManufacturer,String prodBrand) {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("other_product_name", productName)
					.addValue("region_id", regionId)
					.addValue("product_manufacturer_name", prodManufacturer)
					.addValue("product_brand_name", prodBrand)
					.addValue("status", IConstant.RecordStatusForNewProducts.DRAFT.getStatus())
					.addValue("is_deleted", IConstant.RecordStatus.INSERTED.getStatus());
					return namedJdbcTemplate.queryForObject(CHECK_PRODUCT_IN_DB_QUERY, namedParameters, String.class);
					
					
		} catch (Exception ex) {			
			gtLogger.error("getRequestedProductId: "+ex.getMessage());
			return null;			
		}
	}

	@Override
	public Map<String, Object> getUserDetails(String userId) {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_id", userId)
					.addValue("is_deleted", IConstant.RecordStatus.INSERTED.getStatus());

			Map<String, Object> userDetailsMap = namedJdbcTemplate.queryForMap(GET_USER_DETAILS, namedParameters);
			return userDetailsMap;
		} catch (Exception e) {
			gtLogger.error("getUserDetails() : " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean isAskUser(String userId) {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_id", userId)
					.addValue("is_deleted", IConstant.RecordStatus.INSERTED.getStatus());

			Integer count = namedJdbcTemplate.queryForObject(CHECK_ASK_USER_QUERY, namedParameters, Integer.class);
			if (count > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			gtLogger.error("isAskUser() : " + e.getMessage());
			return false;
		}
	}

	@Override
	public String getLanguageName(Integer langId) {		
		try {
				SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("language_id", langId);				
				String languageName = namedJdbcTemplate.queryForObject(GET_LANGUAGE_NAME_BY_ID, namedParameters, String.class);			
				return languageName;
		} catch (Exception e) {
				gtLogger.error("getLanguageName() : " + e.getMessage());
				return null;
		}
	}
}