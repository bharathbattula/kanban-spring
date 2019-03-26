#TEST

INSERT INTO app_version_info VALUES (1.0000, "ask" ,'admin'  ,now());
INSERT INTO app_codes VALUES (8 ,'ask' ,NULL);
INSERT INTO language_app_codes_association VALUES (NULL,1 ,8 ,0 ,'admin' );

REPLACE INTO int_autocomplete_details (AC_ID,AC_DESCRIPTION,AC_SELECT_QUERY) VALUES('askSelectProduct','For Comparision dashboard products','(select prd.region_id as regionId,IF((select count(*) from gn_product_country_association p_c_asso where p_c_asso.product_id = prd.product_id)>0,\'true\',\'false\')
         AS isExcluded, prd.product_manufacturer_id as manufacturerId,		 
		 p18n.product_id AS productId,		 
       COALESCE(p18n.product_name,
                (SELECT pi18n.product_name
                 FROM   int_product_i18n pi18n
                 WHERE  pi18n.product_id = prd.product_id AND pi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productName,
       COALESCE(p18n.product_style,
                (SELECT pi18n.product_style
                 FROM   int_product_i18n pi18n
                 WHERE  pi18n.product_id = prd.product_id AND pi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productStyle,
         pc18n.product_category_id AS productCategoryId,
		 COALESCE(pc18n.product_category_name,
                (SELECT pci18n.product_category_name
                 FROM   int_product_category_i18n pci18n
                 WHERE  pci18n.product_category_id = prd.product_category_id AND pci18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productCategoryName,
       COALESCE(pm18n.manufacturer_name,
                (SELECT pmi18n.manufacturer_name
                 FROM   gn_product_manufacturer_i18n pmi18n
                 WHERE  pmi18n.product_manufacturer_id = prd.product_manufacturer_id AND pmi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productManufacturer,
       COALESCE(pb18n.product_brand_name,
                (SELECT pbi18n.product_brand_name
                 FROM   int_product_brand_i18n pbi18n
                 WHERE  pbi18n.product_brand_id = prd.product_brand_id AND pbi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         as productBrandName
from int_product  prd
       left outer join int_product_i18n p18n on prd.product_id = p18n.product_id and p18n.language_id = 1 and prd.region_id = :region_id
       left outer join int_product_category_i18n pc18n
         on prd.product_category_id = pc18n.product_category_id and (pc18n.language_id = 1)
       left outer join gn_product_manufacturer_i18n pm18n
         on prd.product_manufacturer_id = pm18n.product_manufacturer_id and (pm18n.language_id = 1)
       left outer join int_product_brand_i18n pb18n ON prd.product_brand_id = pb18n.product_brand_id AND pb18n.language_id = 1
       LEFT JOIN int_region_i18n ri ON ri.region_id = prd.region_id AND ri.language_id = 1 and ri.region_id = :region_id 
where  prd.is_deleted = 0 and prd.product_id is not null and prd.product_manufacturer_id=\'0\' and prd.region_id=:region_id and
	  (p18n.product_name LIKE CONCAT("%", :searchText, "%") OR pm18n.manufacturer_name LIKE CONCAT ("%", :searchText, "%") OR product_brand_name LIKE CONCAT ("%", :searchText, "%")))

UNION ALL

(select prd.region_id as regionId,IF((select count(*) from gn_product_country_association p_c_asso where p_c_asso.product_id = prd.product_id)>0,\'true\',\'false\')
         AS isExcluded, prd.product_manufacturer_id as manufacturerId,
		 p18n.product_id AS productId,
       COALESCE(p18n.product_name,
                (SELECT pi18n.product_name
                 FROM   int_product_i18n pi18n
                 WHERE  pi18n.product_id = prd.product_id AND pi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productName,
       COALESCE(p18n.product_style,
                (SELECT pi18n.product_style
                 FROM   int_product_i18n pi18n
                 WHERE  pi18n.product_id = prd.product_id AND pi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productStyle,
         pc18n.product_category_id AS productCategoryId,
       COALESCE(pc18n.product_category_name,
                (SELECT pci18n.product_category_name
                 FROM   int_product_category_i18n pci18n
                 WHERE  pci18n.product_category_id = prd.product_category_id AND pci18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productCategoryName,
       COALESCE(pm18n.manufacturer_name,
                (SELECT pmi18n.manufacturer_name
                 FROM   gn_product_manufacturer_i18n pmi18n
                 WHERE  pmi18n.product_manufacturer_id = prd.product_manufacturer_id AND pmi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         AS productManufacturer,
       COALESCE(pb18n.product_brand_name,
                (SELECT pbi18n.product_brand_name
                 FROM   int_product_brand_i18n pbi18n
                 WHERE  pbi18n.product_brand_id = prd.product_brand_id AND pbi18n.language_id = 1),
                (select text
                 from   resource_bundle
                 where  resource_key = \'tm\' and language_id = 1))
         as productBrandName
from   int_product  prd
       left outer join int_product_i18n p18n on prd.product_id = p18n.product_id and p18n.language_id = 1 
       left outer join int_product_category_i18n pc18n
         on prd.product_category_id = pc18n.product_category_id and (pc18n.language_id = 1)
       left outer join gn_product_manufacturer_i18n pm18n
         on prd.product_manufacturer_id = pm18n.product_manufacturer_id and (pm18n.language_id = 1)
       left outer join int_product_brand_i18n pb18n ON prd.product_brand_id = pb18n.product_brand_id AND pb18n.language_id = 1
       LEFT JOIN int_region_i18n ri ON ri.region_id = prd.region_id AND ri.language_id = 1 
where  prd.is_deleted = 0 and prd.product_id is not null and prd.product_manufacturer_id<>\'0\' and
	  (p18n.product_name LIKE CONCAT("%", :searchText, "%") OR pm18n.manufacturer_name LIKE CONCAT ("%", :searchText, "%") OR product_brand_name LIKE CONCAT ("%", :searchText, "%")))');


REPLACE INTO app_version_info VALUES (1.0000, "ask" ,'admin'  ,now());
REPLACE INTO app_codes VALUES (8 ,'ask' ,NULL);
REPLACE INTO language_app_codes_association VALUES (NULL,1 ,8 ,0 ,'admin' );

REPLACE into resource_bundle values('label.pleaseEnterOtherProductName',1,'Please enter Other Product Name');
REPLACE into resource_bundle values('label.pleaseEnterOtherBrandName',1,'Please enter Other Brand Name');
REPLACE into resource_bundle values('label.pleaseEnterOtherManufacturerName',1,'Please enter Other Manufacturer Name');
REPLACE into resource_bundle values('label.excludedProduct',1,'This product is exclude for your region, Please select other product.');
REPLACE into resource_bundle values('label.maxFourProductsAllowedToCompare',1,'Maximum four products are allowed for comparision.');
REPLACE into resource_bundle values('label.productAlreadySelectedForComparision',1,'This product is already selected for comparision.');
REPLACE into resource_bundle values('label.clearComparisionDashBoard',1,'Do you want to remove all products from comparision dashboard.');
REPLACE into resource_bundle values('label.otherProductSaved',1,'New product request details saved, Generated request id - ');
REPLACE into resource_bundle values('label.productAlreadyRequested',1,'This product is already requested, Request Id is - ');
REPLACE into resource_bundle values('label.pleaseSelectDateForResults',1,'Please select results needed by date.');

REPLACE into resource_bundle values('ask.label.mainpage.label.salesknowledge',1,'SALES KNOWLEDGE');
REPLACE into resource_bundle values('ask.label.mainpage.label.salesknowledge',2,'&#38144;&#21806;&#30693;&#35782;');
REPLACE into resource_bundle values('ask.label.mainpage.label.searchbar.note',1,'Use the search below to add products to compare lab results. Include some intro text to explain what action should be taken.');
REPLACE into resource_bundle values('ask.label.mainpage.label.searchbar.note',2,'&#20351;&#29992;&#19979;&#38754;&#30340;&#25628;&#32034;&#28155;&#21152;&#20135;&#21697;&#20197;&#27604;&#36739;&#23454;&#39564;&#23460;&#32467;&#26524;&#12290;&#21253;&#25324;&#19968;&#20123;&#20171;&#32461;&#25991;&#26412;&#65292;&#20197;&#35299;&#37322;&#24212;&#37319;&#21462;&#30340;&#25514;&#26045;&#12290;');
REPLACE into resource_bundle values('ask.askcontainer.label.addproducts',1,'ADD PRODUCTS');
REPLACE into resource_bundle values('ask.askcontainer.label.addproducts',2,'&#28155;&#21152;&#20135;&#21697;');
REPLACE into resource_bundle values('ask.askcontainer.label.cleardashboard',1,'CLEAR DASHBOARD');
REPLACE into resource_bundle values('ask.askcontainer.label.cleardashboard',2,'&#28165;&#26224;&#30340;&#20202;&#34920;&#26495;');
REPLACE into resource_bundle values('ask.askContainer.product.specs',1,'PRODUCT SPECIFICATIONS');
REPLACE into resource_bundle values('ask.askContainer.product.specs',2,'&#20135;&#21697;&#35268;&#26684;');
REPLACE into resource_bundle values('ask.askConatiner.test.results',1,'LAB TESTS');
REPLACE into resource_bundle values('ask.askConatiner.test.results',2,'&#23454;&#39564;&#23460;&#27979;&#35797;');
REPLACE into resource_bundle values('ask.askContainer.label.timingandurgency',1,'Timing/Urgency');
REPLACE into resource_bundle values('ask.askContainer.label.timingandurgency',2,'&#23450;&#26102;/&#32039;&#36843;&#24615;');
REPLACE into resource_bundle values('label.enterAdditionalInfo',1,'Enter Additional Info');
REPLACE into resource_bundle values('label.enterAdditionalInfo',2,'&#36755;&#20837;&#38468;&#21152;&#20449;&#24687;');
REPLACE into resource_bundle values('ask.welocme.label.enter',1,'Enter');
REPLACE into resource_bundle values('ask.welocme.label.enter',2,'&#36755;&#20837;');
REPLACE into resource_bundle values('ask.askContainer.js.label.no.productfound',1,'No product found, Do you want to request product?');
REPLACE into resource_bundle values('ask.askContainer.js.label.no.productfound',2,'&#26410;&#25214;&#21040;&#20135;&#21697;&#65292;&#24744;&#24819;&#35201;&#20135;&#21697;&#21527;&#65311;');
REPLACE into resource_bundle values('ask.askContainer.js.product.name',1,'Product Name');
REPLACE into resource_bundle values('ask.askContainer.js.product.name',2,'&#20135;&#21697;&#21517;&#31216;');
REPLACE into resource_bundle values('ask.askContainer.js.product.category',1,'Product Category');
REPLACE into resource_bundle values('ask.askContainer.js.product.category',2,'&#20135;&#21697;&#20998;&#31867;');
REPLACE into resource_bundle values('ask.askContainer.js.product.brand',1,'Product Brand');
REPLACE into resource_bundle values('ask.askContainer.js.product.brand',2,'&#20135;&#21697;&#21697;&#29260;');
REPLACE into resource_bundle values('ask.askContainer.js.product.manufacturer',1,'Product Manufacturer');
REPLACE into resource_bundle values('ask.askContainer.js.product.manufacturer',2,'&#20135;&#21697;&#21046;&#36896;&#21830;');
REPLACE into resource_bundle values('ask.askContainer.js.please.type.text',1,'Please type text to search');
REPLACE into resource_bundle values('ask.askContainer.js.please.type.text',2,'&#35831;&#36755;&#20837;&#35201;&#25628;&#32034;&#30340;&#25991;&#23383;');
REPLACE into resource_bundle values('ask.askContainer.js.please.select.product',1,'Please select product');
REPLACE into resource_bundle values('ask.askContainer.js.please.select.product',2,'&#35831;&#36873;&#25321;&#20135;&#21697;');
REPLACE into resource_bundle values('ask.askContainer.js.search.available.tests',1,'Search Available Tests');
REPLACE into resource_bundle values('ask.askContainer.js.search.available.tests',2,'&#25628;&#32034;&#21487;&#29992;&#30340;&#27979;&#35797;');
REPLACE into resource_bundle values('ask.askContainer.js.product.details',1,'Product Details');
REPLACE into resource_bundle values('ask.askContainer.js.product.details',2,'&#20135;&#21697;&#35814;&#24773;');
REPLACE into resource_bundle values('ask.askContainer.js.request.data',1,'Request Data');
REPLACE into resource_bundle values('ask.askContainer.js.request.data',2,'&#35831;&#27714;&#25968;&#25454;');
REPLACE into resource_bundle values('ask.askContainer.js.request.product.form',1,'Request New Product Form');
REPLACE into resource_bundle values('ask.askContainer.js.request.product.form',2,'&#32034;&#21462;&#26032;&#20135;&#21697;&#34920;&#26684;');
REPLACE into resource_bundle values('ask.askContainer.js.no.match.found',1,'No match found !!');
REPLACE into resource_bundle values('ask.askContainer.js.no.match.found',2,'&#25214;&#19981;&#21040;&#21305;&#37197;!!');
REPLACE into resource_bundle values('ask.askContainer.js.informationsavesucessfully.requestId',1,'Information saved successfully, Request Id ');
REPLACE into resource_bundle values('ask.askContainer.js.informationsavesucessfully.requestId',2,'&#20449;&#24687;&#24050;&#25104;&#21151;&#20445;&#23384;&#65292;&#35831;&#27714;ID');
REPLACE into resource_bundle values('ask.askContainer.js.request.test.data.product',1,'Request Test Data for product');
REPLACE into resource_bundle values('ask.askContainer.js.request.test.data.product',2,'&#35831;&#27714;&#20135;&#21697;&#30340;&#27979;&#35797;&#25968;&#25454;');
REPLACE into resource_bundle values('ask.welcome.label.welocmepage.title',1,'Welcome');
REPLACE into resource_bundle values('ask.askContainer.js.request.test.data.product',2,'&#27426;&#36814;');

REPLACE into property_master values('system','system','ask.newproduct.email.to','deepak.patil@trigyn.com',0,now(),'admin','1.0','email');
REPLACE into property_master values('system','system','ask.newproduct.email.from','deepak.patil@trigyn.com',0,now(),'admin','1.0','email');
REPLACE into property_master values('system','system','ask.newproduct.email.cc','deepak.patil@trigyn.com',0,now(),'admin','1.0','email');
REPLACE into property_master values('system','system','ask.newproduct.email.bcc','deepak.patil@trigyn.com',0,now(),'admin','1.0','email');
REPLACE into property_master values('system','system','ask.newproduct.email.subject','New product request email notification',0,now(),'admin','1.0','email');


REPLACE into resource_bundle values('label.isthisbenchmarktestingrequiredforcompetitivebid',1,'Is this benchmark testing required for a competitive bid');
REPLACE into resource_bundle values('label.isthisbenchmarktestingrequiredforspecificcustomer',1,'Is this benchmark testing required for a specific customer');
REPLACE into resource_bundle values('label.supportsampleprocurement',1,'can you support sample procurement if the lab does not have access to samples?');
REPLACE into resource_bundle values('label.label.linktoguardianoppurtunity',1,'Link to Guardian Opportunity.');
REPLACE into resource_bundle values('label.maxFourProductsAllowedToCompare',1,'Maximum four products are allowed for comparision.');
REPLACE into resource_bundle values('label.productAlreadySelectedForComparision',1,'This product is already selected for comparision.');
REPLACE into resource_bundle values('label.clearComparisionDashBoard',1,'Do you want to remove all products from comparision dashboard.');



INSERT INTO GRID_DETAILS ("grid_id","grid_name","grid_description","grid_table_name","grid_column_names","query_type") VALUES(
"testSearch","Ask Tests Grid","ASK Tests Grid","view_search_askTests","testId,testName,testCategoryName,testCategoryId,testStandardId,regionId",1);
