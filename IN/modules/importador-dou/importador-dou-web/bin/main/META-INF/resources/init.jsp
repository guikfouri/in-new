<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@page import="br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys" %>

<%@page import="com.liferay.asset.display.page.constants.AssetDisplayPageConstants" %>
<%@page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %>
<%@page import="com.liferay.dynamic.data.mapping.model.DDMTemplate" %>
<%@page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@page import="com.liferay.portal.kernel.model.Group" %>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@page import="com.liferay.portal.kernel.util.Validator" %>

<%@page import="java.util.List" %>

<%@page import="javax.portlet.PortletURL" %>


<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />