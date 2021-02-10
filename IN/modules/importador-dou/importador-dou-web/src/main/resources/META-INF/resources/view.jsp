<%@ include file="/init.jsp" %>

<liferay-ui:success key="msg-falha-parcial" message="msg-falha-parcial" />
<liferay-ui:success key="msg-configuracao-sucesso" message="msg-configuracao-sucesso" />
<liferay-ui:success key="msg-importacao-concluida-sucesso" message="msg-importacao-concluida-sucesso" />
<liferay-ui:success key="msg-migracao-concluida-sucesso" message="msg-migracao-concluida-sucesso" />

<liferay-ui:error key="erro-base-path-xmls-invalido" message="erro-base-path-xmls-invalido" />
<liferay-ui:error key="erro-task-time-obrigatorio" message="erro-task-time-obrigatorio" />
<liferay-ui:error key="erro-grupo-obrigatorio" message="erro-grupo-obrigatorio" />
<liferay-ui:error key="erro-usuario-obrigatorio" message="erro-usuario-obrigatorio" />
<liferay-ui:error key="erro-estrutura-obrigatorio" message="erro-estrutura-obrigatorio" />
<liferay-ui:error key="erro-modelo-obrigatorio" message="erro-modelo-obrigatorio" />
<liferay-ui:error key="erro-diretorio-xml" message="erro-diretorio-xml"/>
<liferay-ui:error key="erro-configuracao-twitter" message="erro-configuracao-twitter" />
<liferay-ui:error key="erro-configuracao-facebook" message="erro-configuracao-facebook" />
<liferay-ui:error key="erro-sem-categoria-destaque" message="erro-sem-categoria-destaque" />
<liferay-ui:error key="error-migration-partial-failure" message="error-migration-partial-failure" />

<liferay-ui:error key="erro-inesperado" message="erro-inesperado" />

<portlet:renderURL var="configKafkaURL" portletMode="edit">
	<portlet:param name="tabs1" value="kafka" />
</portlet:renderURL>

<c:if test="<%= themeDisplay.isSignedIn() %>">

	<div class="panel">
		<div class="panel-body">
			<div class="container-fluid container-fluid-max-xl container-view">
				<c:choose>
					<c:when test="${ isKafkaConsumerEnabled }">
						<p class="text-right ">
							<a href="<%= configKafkaURL.toString() %>"><clay:icon symbol="cog" /> Configurar Kafka</a>
						</p>
						<clay:alert
							message='<%= LanguageUtil.get(resourceBundle, "msg-kafka-is-enabled") %>'
							title="Kafka Status"
							style="success"
						/>
					</c:when>
					<c:otherwise>
						<clay:alert
							message='<%= LanguageUtil.get(resourceBundle, "msg-kafka-is-disabled") %>'
							title="Kafka Status"
							style="warning"
						/>

						<c:if test="${enableAutomation}">
							<div class="portlet-msg-alert">
								A pr&oacute;xima importa&ccedil;&atilde;o est&aacute; agendada para &agrave;s ${proximoAgendamento} horas.
							</div>
						</c:if>

						<hr>

						<div class="portlet-msg-info">
							<c:choose>
								<c:when test="${countItemImportacao gt 0}">
									Existem ${countItemImportacao} XMLs dispon&iacute;veis para importa&ccedil;&atilde;o no total.
								</c:when>
								<c:otherwise>
									Não existem XMLs pendentes para importa&ccedil;&atilde;o.
								</c:otherwise>
							</c:choose>
						</div>

						<portlet:actionURL name="/importador/xml/files" var="importarXMLsURL" />
						<aui:form action="${importarXMLsURL}" name="fmCarregar" method="post" inlineLabels="false">
							<c:if test="${countItemImportacao gt 0}">
								<aui:button-row>
									<aui:button type="submit" value="Carregar XMLs" />
								</aui:button-row>
							</c:if>
							<div class="container border border-warning mb-3 easter_egg " style="display: none">
								<aui:input name="alternateXmlPath" label="Diretorio Alternativo" />
								<aui:button-row>
									<aui:button type="submit" cssClass="btn btn-warning" value="Carregar XMLs" />
								</aui:button-row>
							</div>
						</aui:form>

						<hr>

						<div class="portlet-msg-info">
							<c:choose>
								<c:when test="${countItemMigracao gt 0}">
									Existem ${countItemMigracao} mat&eacute;rias pendentes para publica&ccedil;&atilde;o no total.
								</c:when>
								<c:otherwise>
									Não existem mat&eacute;rias pendentes para publica&ccedil;&atilde;o.
								</c:otherwise>
							</c:choose>
						</div>

						<portlet:actionURL name="/migrate_articles" var="migrarXMLsURL" />
						<aui:form action="${migrarXMLsURL}" name="fmImportar" method="post" inlineLabels="false">
							<c:if test="${countItemMigracao gt 0}">
								<aui:button-row>
									<aui:button type="submit" value="Publicar Matérias" />
								</aui:button-row>
							</c:if>
							<div class="container border border-warning mb-3 easter_egg " style="display: none">
								<aui:input name="alternateXmlPath" label="Diretorio Alternativo" />
								<aui:button-row>
									<aui:button type="submit" cssClass="btn btn-warning" value="Publicar Matérias" />
								</aui:button-row>
							</div>
						</aui:form>

						<portlet:actionURL name="limparXMLs" var="limparXMLsURL" />
						<aui:form action="${limparXMLsURL}" name="fmLimpar" method="post" inlineLabels="false">
							<aui:button-row>
								<aui:button type="submit" cssClass="btn btn-danger" value="Limpar conteúdo temporário" icon="icon-remove"/>
							</aui:button-row>
						</aui:form>
					</c:otherwise>
				</c:choose>

				<hr>

				<div id="last_migration_info" class="text-center">
					<ul class="list-unstyled">
						<li>
							<strong>Data da &uacute;ltima publica&ccedil;&atilde;o:</strong> 
							<c:choose>
								<c:when test="<%= Validator.isNull(request.getAttribute("latestMigrationDate")) %>">
									<span class="text-muted"><liferay-ui:message key="never" /></span>
								</c:when>
								<c:otherwise>
									<jsp:useBean id="date" class="java.util.Date"/>
									<fmt:formatDate value="${latestMigrationDate}" type="date" pattern="dd/MM/yyyy"/>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<strong>Quantidade de mat&eacute;rias publicadas:</strong> 
							<c:choose>
								<c:when test="<%= Validator.isNull(request.getAttribute("latestMigrationCount")) %>">
									<span class="text-muted"><liferay-ui:message key="none" /></span>
								</c:when>
								<c:otherwise>
									${latestMigrationCount}
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>

				<div class="float-right">
					<i class="icon-ban-circle" onclick="showEasterEggs()" style="color: silver"></i>
				</div>
			</div>
		</div>
	</div>
</c:if>

<aui:script>
function showEasterEggs() {
	$('.easter_egg').show();
	$('i.icon-ban-circle').hide();
}
</aui:script>
