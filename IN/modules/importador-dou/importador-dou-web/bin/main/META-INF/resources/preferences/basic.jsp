<%@ include file="/init.jsp" %>

<%
String twitterAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, "");
String twitterAccessTokenSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, "");
Boolean isTwitterAuthorized = (twitterAccessToken.length() > 0 && twitterAccessTokenSecret.length() > 0);
String url = portletPreferences.getValue(ImportadorDouPortletKeys.URL_SERVER, "");
Boolean enableNotification =  Boolean.parseBoolean(portletPreferences.getValue(ImportadorDouPortletKeys.ENABLE_NOTIFICATION, "false"));
String facebookPageAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, "");
Boolean isFacebookAuthorized = facebookPageAccessToken.length() > 0;
%>

<portlet:renderURL var="backURL" portletMode="view" />

<portlet:actionURL name="/save_preferences" var="salvarPreferenciasURL" />
<aui:form action="<%= salvarPreferenciasURL %>" method="post" name="formPreferencias" useNamespace="true" cssClass="container-fluid container-fluid-max-xl container-view">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset id="xmlOriginFileSystemOptions">
			<h3><liferay-ui:message key="label-configuracoes-xmls" /></h3>
			<h4><liferay-ui:message key="label-xml-origin-file-system" /></h4>
			<aui:row>
				<aui:col>
					<aui:input name="basePathXMLs" value="${basePathXMLs}" label="label-base-path-xml" required="${xmlOriginIsFileSystem}" />
				</aui:col>
			</aui:row>
			<aui:row cssClass="hide">
				<aui:col span="6">
					<aui:input name="taskTime" value="${taskTime}" type="text" label="task-timer" />
				</aui:col>
				<aui:col span="6">
					<aui:input name="enableAutomation" type="toggle-switch" label="enable-automation" checked="${enableAutomation}" />
				</aui:col>
			</aui:row>
		</aui:fieldset>

		<aui:fieldset label="label-configuracoes-importacao" collapsed="<%= true %>" collapsible="<%= true %>">
			<aui:row>
				<aui:col>
					<aui:select name="idGrupo" label="label-grupo-padrao" showEmptyOption="true">

									<%
									List<Group> groups = (List<Group>) request.getAttribute("grupos");
									Long currentGroupId = GetterUtil.getLong(request.getAttribute("idGrupo"));
									for (Group group : groups) {
										String labelGrupos = group.getName("pt_BR", true)  + " (" + group.getGroupId() + ")";
									%>

									<aui:option value="<%= group.getGroupId() %>" label="<%= labelGrupos %>" selected="<%= group.getGroupId() == currentGroupId %>" />

									<%
									}
									%>

					</aui:select>
				</aui:col>

				<aui:col>
					<aui:select name="idUsuario" label="label-dono" showEmptyOption="true">
						<c:forEach var="usuario" items="${usuarios}">
							<aui:option value="${usuario.userId}" label="${usuario.fullName} (${usuario.userId})" selected="${usuario.userId eq idUsuario}" />
						</c:forEach>
					</aui:select>
				</aui:col>
			</aui:row>

			<aui:row>
				<aui:col span="6">
					<aui:select name="idEstrutura" label="label-estrutura" showEmptyOption="true" onChange="">

									<%
									List<DDMStructure> estruturas = (List<DDMStructure>) request.getAttribute("estruturas");
									Long currentStructureId = GetterUtil.getLong(request.getAttribute("idEstrutura"));
									for (DDMStructure ddmStructure : estruturas) {
										String structureLabel = ddmStructure.getName("pt_BR", true) + " (" + ddmStructure.getStructureId() + ")";
									%>
									<aui:option value="<%= ddmStructure.getStructureId() %>" label="<%= structureLabel %>" selected="<%= ddmStructure.getStructureId() == currentStructureId %>" />
									<%
									}
									%>

					</aui:select>
				</aui:col>
				<aui:col span="6">
					<aui:select name="idTemplate" label="label-template" disabled="${empty idEstrutura}" showEmptyOption="true">

									<%
									List<DDMTemplate> templates = (List<DDMTemplate>) request.getAttribute("templates");
									if (templates != null) {
										Long currentTemplateId = GetterUtil.getLong(request.getAttribute("idTemplate"));
										for (DDMTemplate ddmTemplate : templates) {
											String templateLabel = ddmTemplate.getName("pt_BR", true) + " (" + ddmTemplate.getTemplateId() + ")";
									%>

									<aui:option value="<%= ddmTemplate.getTemplateId() %>" label="<%= templateLabel %>" selected="<%= ddmTemplate.getTemplateId() == currentTemplateId %>" />

									<%
										}
									}
									%>

					</aui:select>
				</aui:col>
			</aui:row>

			<aui:field-wrapper label="label-config-vocab">
				<aui:row>
					<aui:col span="4">
						<aui:select name="idSecaoVocab" label="label-secao-vocab" showEmptyOption="true">
							<c:forEach var="vocab" items="${vocabs}">
								<aui:option value="${vocab.vocabularyId}" label="${vocab.name} (${vocab.vocabularyId})" selected="${vocab.vocabularyId eq idSecaoVocab}" />
							</c:forEach>
						</aui:select>
					</aui:col>

					<aui:col span="4">
						<aui:select name="idArranjoSecaoVocab" label="label-arranjo-secao-vocab" showEmptyOption="true">
							<c:forEach var="vocab" items="${vocabs}">
								<aui:option value="${vocab.vocabularyId}" label="${vocab.name} (${vocab.vocabularyId})" selected="${vocab.vocabularyId eq idArranjoSecaoVocab}" />
							</c:forEach>
						</aui:select>
					</aui:col>

					<aui:col span="4">
						<aui:select name="idTipoMateriaVocab" label="label-tipo-materia-vocab" showEmptyOption="true">
							<c:forEach var="vocab" items="${vocabs}">
								<aui:option value="${vocab.vocabularyId}" label="${vocab.name} (${vocab.vocabularyId})" selected="${vocab.vocabularyId eq idTipoMateriaVocab}" />
							</c:forEach>
						</aui:select>
					</aui:col>
				</aui:row>
			</aui:field-wrapper>
		</aui:fieldset>

		<%
			int assetDisplayPageType = Integer.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, "0"));

			Boolean isAssetDisplayPageTypeDefault = assetDisplayPageType == AssetDisplayPageConstants.TYPE_DEFAULT;
			Boolean isAssetDisplayPageTypeSpecific = assetDisplayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC;
			Boolean isAssetDisplayPageTypeNone = assetDisplayPageType == AssetDisplayPageConstants.TYPE_NONE;

			String assetDisplayPageName = GetterUtil.getString(request.getAttribute("assetDisplayPageName"));
			String defaultAssetDisplayPageName = GetterUtil.getString(request.getAttribute("defaultAssetDisplayPageName"));
			String assetTypeName = GetterUtil.getString(request.getAttribute("assetTypeName"));

			String taglibLabelTypeDefault = LanguageUtil.format(resourceBundle, "use-default-display-page-for-x-x", new Object[] {assetTypeName, Validator.isNotNull(defaultAssetDisplayPageName) ? defaultAssetDisplayPageName : LanguageUtil.get(resourceBundle, "none")}, false);
			if (Validator.isNull(defaultAssetDisplayPageName)) {
				taglibLabelTypeDefault += " <span class=\"text-muted\">" + LanguageUtil.get(resourceBundle, "the-entity-will-not-be-referenceable-with-an-url") + "</span>";
			}

			String taglibLabelTypeNone = LanguageUtil.get(resourceBundle, "none") + " <span class=\"text-muted\">" + LanguageUtil.get(resourceBundle, "the-entity-will-not-be-referenceable-with-an-url") + "</span>";
		%>

		<aui:fieldset label="label-display-page"  collapsed="<%= true %>" collapsible="<%= true %>">
			<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="${ layoutUuid }" />
			<aui:input id="assetDisplayPageIdInput" ignoreRequestValue="<%= true %>" name="assetDisplayPageId" type="hidden" value="${ assetDisplayPageId }" />
			<span><liferay-ui:message key="please-select-one-option" /></span>
			<liferay-frontend:fieldset
				id='<%= renderResponse.getNamespace() + "eventsContainer" %>'
			>
				<aui:input checked="<%= isAssetDisplayPageTypeDefault %>" label="<%= taglibLabelTypeDefault %>" name="assetDisplayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_DEFAULT %>" />
				<aui:input checked="<%= isAssetDisplayPageTypeSpecific %>" label="use-a-specific-display-page-for-the-entity" name="assetDisplayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>" />
				<div class="<%= isAssetDisplayPageTypeSpecific ? "" : "hide" %>" id="<portlet:namespace />displayPageContainer">
					<p class="text-default">
						<span class="<%= Validator.isNull(assetDisplayPageName) ? "hide" : "" %>" id="<portlet:namespace />displayPageItemRemove" role="button">
							<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
						</span>
						<span id="<portlet:namespace />displayPageNameInput">
							<c:choose>
								<c:when test="<%= Validator.isNull(assetDisplayPageName) %>">
									<span class="text-muted"><liferay-ui:message key="none" /></span>
								</c:when>
								<c:otherwise>
									<%= assetDisplayPageName %>
								</c:otherwise>
							</c:choose>
						</span>
					</p>
					<aui:button name="chooseDisplayPage" value="choose" />
				</div>
				<aui:input checked="<%= isAssetDisplayPageTypeNone %>" label="<%= taglibLabelTypeNone %>" name="assetDisplayPageType" type="radio" value="<%= AssetDisplayPageConstants.TYPE_NONE %>" />
			</liferay-frontend:fieldset>
		</aui:fieldset>

		<aui:fieldset label="label-paralelismo"  collapsed="<%= true %>" collapsible="<%= true %>">
			<aui:input name="threadSize" value="${threadSize}" type="text" label="task-threads">
				<aui:validator name="required" />
				<aui:validator name="digits"/>
				<aui:validator name="range">[21, 150]</aui:validator>
			</aui:input>

			<aui:input name="poolSize" value="${poolSize}" type="text" label="task-pool">
				<aui:validator name="required" />
				<aui:validator name="digits"/>
				<aui:validator name="range">[500, 1500]</aui:validator>
			</aui:input>

			<aui:input name="importXmlFileTimeOut" value="${importXmlFileTimeOut}" label="import-xml-file-timeout-label">
				<aui:validator name="digits"/>
			</aui:input>

			<aui:input name="migrationTimeOut" value="${migrationTimeOut}" label="migration-timeout-label">
				<aui:validator name="digits"/>
			</aui:input>
		</aui:fieldset>
		
		<aui:fieldset label="URL SERVER" collapsed="<%= true %>" collapasible="<%= true %>" >
			<aui:input name="url" value="${url}" type="text" label="URL Notification Server">
			</aui:input>
			<aui:input name="enableNotification" type="toggle-switch" label="Habilitar Notificacao?" checked="${enableNotification}" disabled="<%= !enableNotification %>"/>
			
		</aui:fieldset>

		<aui:fieldset label="label-highlights-configuration" collapsed="<%= true %>" collapsible="<%= true %>">
			<aui:row>
				<aui:col>
					<aui:input name="highlightLinkPortalURL" value="${highlightLinkPortalURL}" label="label-highlight-link-portal-url" type="url" placeholder="http://www.example.com">
						<aui:validator name="url" />
					</aui:input>
				</aui:col>
			</aui:row>
			<%
			String twitterScreenName = GetterUtil.getString(request.getAttribute(ImportadorDouPortletKeys.P_TWITTER_SCREEN_NAME));
			String enableTwitterTaglabel = LanguageUtil.get(resourceBundle, "label-enable-twitter") +
					": <span class=\"text-muted\">" + (Validator.isNotNull(twitterScreenName) ? "@" + twitterScreenName : LanguageUtil.get(resourceBundle, "none")) + "</span>";
			String facebookPageName = GetterUtil.getString(request.getAttribute(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_NAME));
			String enableFacebookTagLabel = LanguageUtil.get(resourceBundle, "label-enable-facebook") +
					": <span class=\"text-muted\">" + (Validator.isNotNull(facebookPageName) ? facebookPageName : LanguageUtil.get(resourceBundle, "none")) + "</span>";
			%>
			<aui:field-wrapper label="label-enable-social-publish">
				<aui:row>
					<aui:col>
						<liferay-ui:icon cssClass="icon-monospaced" iconCssClass="icon-twitter" message="Twitter" markupView="lexicon" />
						<aui:input name="enableTwitter" type="toggle-switch" label="<%= enableTwitterTaglabel %>" checked="${enableTwitter}" disabled="<%= !isTwitterAuthorized %>"/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col>
						<liferay-ui:icon cssClass="icon-monospaced" iconCssClass="icon-facebook" message="Facebook" markupView="lexicon" />
						<aui:input name="enableFacebook" type="toggle-switch" label="<%= enableFacebookTagLabel %>" checked="${enableFacebook}" disabled="<%= !isFacebookAuthorized %>"/>
					</aui:col>
				</aui:row>
			</aui:field-wrapper>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="<%= backURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
	String itemSelectorURL = GetterUtil.getString(request.getAttribute("itemSelectorURL"));
%>
<aui:script use="liferay-item-selector-dialog">
	var assetDisplayPageIdInput = $('#<portlet:namespace />assetDisplayPageIdInput');
	var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');
	var displayPageContainer = $('#<portlet:namespace />displayPageContainer');
	var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
	var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');

	$('#<portlet:namespace />chooseDisplayPage').on(
	'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(  
				{
					eventName: 'importadorDouWebAssetDisplayPageItemSelectorEvent',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							assetDisplayPageIdInput.val('');
							pagesContainerInput.val('');

							if (selectedItem) {
								if (selectedItem.type === "asset-display-page") {
									assetDisplayPageIdInput.val(selectedItem.id);
								}
								else {
									pagesContainerInput.val(selectedItem.id);
								}

								displayPageNameInput.html(selectedItem.name);
								displayPageItemRemove.removeClass('hide');
							}
						}
					},
					title: '<liferay-ui:message key="select-page" />',
					url: '<%= itemSelectorURL.toString() %>'
				}
			);
			itemSelectorDialog.open();
		}
	);

	displayPageItemRemove.on(
			'click',
			function(event) {
				displayPageNameInput.html('<liferay-ui:message key="none" />');

				assetDisplayPageIdInput.val('');
				pagesContainerInput.val('');

				displayPageItemRemove.addClass('hide');
			}
		);

	$('#<portlet:namespace />eventsContainer').on(
		'change',
		function(event) {
			var target = event.target;

			if (target && target.value === '<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>') {
				displayPageContainer.removeClass('hide');
			}
			else {
				displayPageContainer.addClass('hide');
			}
		}
	);

</aui:script>

<script type="text/javascript">
	YUI().use(
			'aui-timepicker',
			function(Y) {
				new Y.TimePicker({
					trigger : 'input[name*=taskTime]',
					mask : '%H:%M',
					values : [ '00:00', '00:30', '01:00', '01:30', '02:00',
							'02:30', '03:00', '03:30', '04:00', '04:30',
							'05:00', '05:00', '6:00', '6:30' ],
					popover : {
						zIndex : 1
					},
					on : {
						selectionChange : function(event) {
							console.log(event.newSelection)
						}
					}
				});
			});
</script>