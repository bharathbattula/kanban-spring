<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ansell" tagdir="/WEB-INF/tags"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div>
	<div><spring:message code="${messageKey}"/>
	<c:if test="${specificData ne null and specificData ne 'undefined'}">
		${specificData}
	</c:if></div>
	<div class="grey-separator"></div>
	<div class="pull-right">
			<div class="col-md-12 col-sm-12 text-r">
		<c:if test="${cancel ne null}">
				<button
					class="btn btn-md btn-default btnBlueB" onclick="${cancel}">
					<i class="fa fa-times-circle-o namecls"></i><spring:message code="label.cancel" />
				</button></c:if><c:if test="${no ne null}"><button
					class="btn btn-md btn-default btnBlueB" onclick="${no}">
					<i class="fa fa-times namecls"></i><spring:message code="label.no" />
				</button></c:if><c:if test="${yes ne null}"><button onclick="${yes}"
					class="btn btnmargin btn-md btn-default btnBactiveB">
					<i class="fa fa-check namecls"></i><spring:message code="label.yes" />
				</button></c:if><c:if test="${ok ne null}">
				<button onclick="${ok}"
					class="btn btnmargin btn-md btn-default btnBactiveB">
					<i class="fa fa-check-circle namecls"></i><spring:message code="label.ok" />
				</button>
		</c:if>
			</div>
		</div>
</div>
