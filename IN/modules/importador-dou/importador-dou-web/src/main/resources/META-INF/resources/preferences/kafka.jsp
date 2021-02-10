<%@ include file="/init.jsp" %>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="br.com.seatecnologia.in.importador.dou.kafka.SourceFormat" %>

<portlet:actionURL name="/kafka_consumer" var="startKafkaURL">
	<portlet:param name="path" value="start_kafka_consumer" />
	<portlet:param name="tabs1" value="kafka" />
</portlet:actionURL>

<portlet:actionURL name="/kafka_consumer" var="stopKafkaURL">
	<portlet:param name="path" value="stop_kafka_consumer" />
	<portlet:param name="tabs1" value="kafka" />
</portlet:actionURL>

<aui:form action="<%= startKafkaURL %>" method="post" name="formKafka" useNamespace="true" cssClass="container-fluid container-fluid-max-xl container-view">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset id="xmlOriginKafkaMessageOptions">
			<aui:input name="xmlOriginKafkaMessageOptionsTopic" type="text" value="${xmlOriginKafkaMessageOptionsTopic}" label="label-xml-origin-kafka-message-topic" required="${xmlOriginIsKafkaMessage && !xmlOriginKafkaMessageOptionsUsePropertiesFile}"/>

			<%
			String sourceFormat = portletPreferences.getValue(ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT, SourceFormat.JSON.toString());

			Boolean isFormatJSON = SourceFormat.JSON.toString().equals(sourceFormat);
			Boolean isFormatXML = SourceFormat.XML.toString().equals(sourceFormat);
			Boolean isFormatLegacyXML = SourceFormat.LEGACY_XML.toString().equals(sourceFormat);
			%>
			<aui:fieldset label="format">
				<span><liferay-ui:message key="please-select-one-option" /></span>
				<aui:input name="<%= ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT %>" type="radio" value="<%= SourceFormat.JSON.toString() %>" label="JSON" checked="<%= isFormatJSON %>" />
				<aui:input name="<%= ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT %>" type="radio" value="<%= SourceFormat.XML.toString() %>" label="XML" checked="<%= isFormatXML %>" />
				<aui:input name="<%= ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT %>" type="radio" value="<%= SourceFormat.LEGACY_XML.toString() %>" label="legacy-xml" checked="<%= isFormatLegacyXML %>" />
			</aui:fieldset>

			<aui:input name="xmlOriginKafkaMessageOptionsUsePropertiesFile" type="toggle-switch" label="label-xml-origin-kafka-message-use-property-file" checked="${xmlOriginKafkaMessageOptionsUsePropertiesFile}"></aui:input>
			<aui:fieldset id="xmlOriginKafkaMessageOptionsMinimal">
				<aui:input name="xmlOriginKafkaMessageOptionsBrokers" type="text" value="${xmlOriginKafkaMessageOptionsBrokers}" label="label-xml-origin-kafka-message-brokers" required="${xmlOriginIsKafkaMessage && !xmlOriginKafkaMessageOptionsUsePropertiesFile}"/>
				<aui:input name="xmlOriginKafkaMessageOptionsGroup" type="text" value="${xmlOriginKafkaMessageOptionsGroup}" label="label-xml-origin-kafka-message-group" required="${xmlOriginIsKafkaMessage && !xmlOriginKafkaMessageOptionsUsePropertiesFile}"/>
				<div id="xmlOriginKafkaMessageOptionsCustom">
					<aui:row>
						<aui:col span="12">
							<h5>Outras configurações</h5>
						</aui:col>
					</aui:row>
					<aui:row>
						<aui:col span="6">
							<h6>Nome</h6>
						</aui:col>
						<aui:col span="6">
							<h6>Valor</h6>
						</aui:col>
					</aui:row>
					<%
					Map<String, String> kafkaCustomConfigurations = (Map<String, String>) request.getAttribute("kafkaCustomConfigurations");
					if (kafkaCustomConfigurations == null) {
						kafkaCustomConfigurations = new HashMap<>(1);
						kafkaCustomConfigurations.put("","");
					}
					
					int i = 0;
					for (Entry<String, String> currentEntry : kafkaCustomConfigurations.entrySet()) {
						String currentKey = currentEntry.getKey();
						String currentValue = currentEntry.getValue();
					%>
					<div class="lfr-form-row lfr-form-row-inline">
						<div class="row-fields">
							<aui:row>
								<aui:col span="6">
									<aui:input name='<%= "customKey" + i %>' value='<%= currentKey %>' label="" />
								</aui:col>
								<aui:col span="6">
									<aui:input name='<%= "customValue" + i %>' value='<%= currentValue %>' label="" />
								</aui:col>
							</aui:row>
						</div>
					</div>
					<%
						++i;
					}
					%>
				</div>
			</aui:fieldset>
			<aui:fieldset id="xmlOriginKafkaMessageOptionsComplete" hidden="hidden">
				<aui:input name="xmlOriginKafkaMessageOptionsPropertiesFileLocation" type="text" value="${xmlOriginKafkaMessageOptionsPropertiesFileLocation}" label="label-xml-origin-kafka-message-config-file" required="${xmlOriginIsKafkaMessage && xmlOriginKafkaMessageOptionsUsePropertiesFile}">
					<aui:validator name="acceptFiles">'properties'</aui:validator>
				</aui:input>
			</aui:fieldset>
			<aui:input name="xmlOriginKafkaMessageOptionsImgsPath" type="text" value="${xmlOriginKafkaMessageOptionsImgsPath}" label="label-xml-origin-kafka-message-img-path" helpMessage="label-xml-origin-kafka-message-img-path-helper" required="${xmlOriginIsKafkaMessage}" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row id="xmlOriginKafkaMessageOptionsBtns">
		<aui:button name="btnKafkaStart" cssClass="btn btn-primary" type="submit" disabled="${ isKafkaConsumerEnabled }" value="label-btn-start-kafka-consumer" icon="icon-play" />
		<aui:button name="btnKafkaStopConsumer" cssClass="btn btn-secondary" type="button" onClick="<%= stopKafkaURL.toString() %>" disabled="${ !isKafkaConsumerEnabled }" value="label-btn-stop-kafka-consumer" icon="icon-stop" />
	</aui:button-row>

	<aui:script use="node">
		var xmlOriginKafkaMessageOption = A.one('#<portlet:namespace/>xmlOriginKafkaMessageOption')
		if (xmlOriginKafkaMessageOption) {
			if (xmlOriginKafkaMessageOption.hasAttribute('checked')) {
				A.one('#xmlOriginKafkaMessageOptions').show();
			}
			xmlOriginKafkaMessageOption.on('click', function(event) {
				A.all('.xml-origin-fieldset').hide()
				A.one('#xmlOriginKafkaMessageOptions').show();
			})
		}

		var xmlOriginKafkaMessageOptionsUsePropertiesFile = A.one('#<portlet:namespace/>xmlOriginKafkaMessageOptionsUsePropertiesFile')
		if (xmlOriginKafkaMessageOptionsUsePropertiesFile) {
			if (xmlOriginKafkaMessageOptionsUsePropertiesFile.attr('checked')) {
				A.one('#xmlOriginKafkaMessageOptionsMinimal').hide();
				A.one('#xmlOriginKafkaMessageOptionsComplete').show();
			} else {
				A.one('#xmlOriginKafkaMessageOptionsComplete').hide();
				A.one('#xmlOriginKafkaMessageOptionsMinimal').show();
			}
			xmlOriginKafkaMessageOptionsUsePropertiesFile.on('click', function(event) {
				if (xmlOriginKafkaMessageOptionsUsePropertiesFile.attr('checked')) {
					A.one('#xmlOriginKafkaMessageOptionsMinimal').hide();
					A.one('#xmlOriginKafkaMessageOptionsComplete').show();
				} else {
					A.one('#xmlOriginKafkaMessageOptionsComplete').hide();
					A.one('#xmlOriginKafkaMessageOptionsMinimal').show();
				}
			})
		}
	</aui:script>
</aui:form>

<aui:script>
AUI().use('liferay-auto-fields',function(A) {
	new Liferay.AutoFields(
		{
			contentBox: '#xmlOriginKafkaMessageOptionsCustom',
			fieldIndexes: '<portlet:namespace />xmlOriginKafkaMessageOptionsCustomIndexes'
		}
	).render();
});
</aui:script>