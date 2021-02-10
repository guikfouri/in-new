<%@ include file="/init.jsp" %>

<liferay-ui:success key="msg-preferencias-definidas-sucesso" message="msg-preferencias-definidas-sucesso" />
<liferay-ui:success key="msg-selecione-template" message="msg-selecione-template" />

<liferay-ui:error key="erro-base-path-xmls-invalido" message="erro-base-path-xmls-invalido" />
<liferay-ui:error key="erro-task-time-obrigatorio" message="erro-task-time-obrigatorio" />
<liferay-ui:error key="erro-grupo-obrigatorio" message="erro-grupo-obrigatorio" />
<liferay-ui:error key="erro-usuario-obrigatorio" message="erro-usuario-obrigatorio" />
<liferay-ui:error key="erro-estrutura-obrigatorio" message="erro-estrutura-obrigatorio" />
<liferay-ui:error key="erro-modelo-obrigatorio" message="erro-modelo-obrigatorio" />
<liferay-ui:error key="erro-configuracao-redes-sociais" message="erro-configuracao-redes-sociais" />
<liferay-ui:error key="erro-configuracao-twitter" message="erro-configuracao-twitter" />
<liferay-ui:error key="erro-configuracao-facebook" message="erro-configuracao-facebook" />
<liferay-ui:error key="erro-sem-url-para-link-destaque" message="erro-sem-url-para-link-destaque" />
<liferay-ui:error key="error-xml-origin-kafka-properties-file-missing" message="error-xml-origin-kafka-properties-file-missing" />
<liferay-ui:error key="error-xml-origin-kafka-brokers-missing" message="error-xml-origin-kafka-brokers-missing" />
<liferay-ui:error key="error-xml-origin-kafka-topic-missing" message="error-xml-origin-kafka-topic-missing" />
<liferay-ui:error key="warning-xml-origin-kafka-group-missing" message="warning-xml-origin-kafka-group-missing" />
<liferay-ui:error key="warning-xml-origin-kafka-img-path-missing" message="warning-xml-origin-kafka-img-path-missing" />
<liferay-ui:error key="error-xml-origin-kafka-consumer-not-connected" message="error-xml-origin-kafka-consumer-not-connected" />

<liferay-ui:error key="erro-inesperado" message="erro-inesperado" />

<%
final String tabs1 = ParamUtil.getString(request, "tabs1", "basic");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/edit");
portletURL.setParameter("tabs1", tabs1);

portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "label-preferencias"));
%>

<c:if test="<%=themeDisplay.isSignedIn()%>">
	<clay:navigation-bar inverted="<%=true%>"
		navigationItems="<%=new JSPNavigationItemList(pageContext) {
					{
						add(navigationItem -> {
							navigationItem.setActive(tabs1.equals("basic"));
							navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "basic");
							navigationItem.setLabel(LanguageUtil.get(request, "general"));
						});

						add(navigationItem -> {
							navigationItem.setActive(tabs1.equals("kafka"));
							navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "kafka");
							navigationItem.setLabel(LanguageUtil.get(request, "label-xml-origin-kafka-message"));
						});

						add(navigationItem -> {
							navigationItem.setActive(tabs1.equals("social"));
							navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "social");
							navigationItem.setLabel(LanguageUtil.get(request, "social"));
						});
					}
				}%>" />

	<c:choose>
		<c:when test='<%=tabs1.equals("basic")%>'>
			<liferay-util:include page="/preferences/basic.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%=tabs1.equals("kafka")%>'>
			<liferay-util:include page="/preferences/kafka.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%=tabs1.equals("social")%>'>
			<liferay-util:include page="/preferences/social.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</c:if>