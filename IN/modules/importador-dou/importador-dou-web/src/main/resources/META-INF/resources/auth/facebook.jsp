<%@ include file="/init.jsp" %>

<portlet:actionURL name="/auth_facebook" var="setupFacebookStartCodeURL">
	<portlet:param name="auth" value="facebook" />
	<portlet:param name="state" value="start" />
</portlet:actionURL>

<portlet:actionURL name="/auth_facebook" var="setupFacebookConfirmCodeURL">
	<portlet:param name="auth" value="facebook" />
	<portlet:param name="state" value="wait" />
</portlet:actionURL>

<liferay-ui:success key="msg-auth-success" message="msg-auth-success" />
<liferay-ui:error key="erro-inesperado" message="erro-inesperado" />

<%
String facebookDeviceUserCode = (String) renderRequest.getAttribute("fb_user_code");
String facebookVerificationUri = (String) renderRequest.getAttribute("fb_verification_uri");
Boolean hasFacebookDeviceCode = (facebookDeviceUserCode != null && facebookDeviceUserCode.length() > 0)
		&& (facebookVerificationUri != null && facebookVerificationUri.length() > 0);

String facebookAppId = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, "");
String facebookAppSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, "");
String facebookPageId = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, "");
String facebookClientToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, "");
String facebookUserId = "";
String facebookUserAccessToken = "";

String facebookPageAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, "");
Boolean isFacebookAuthorized = facebookPageAccessToken.length() > 0;
%>

<c:choose>
	<c:when test="<%=hasFacebookDeviceCode%>">
		<aui:container cssClass="jumbotron" align="center">
			Vá até 
			<aui:a href="<%= facebookVerificationUri %>" target="_blank">
				<strong><%= facebookVerificationUri %></strong>
			</aui:a>
	
			em qualquer computador, tablet ou celular e insira o código a seguir para autorizar acesso a conta do Facebook
	
			<h1><kbd><%= facebookDeviceUserCode %></kbd></h1>
	
			<aui:form action="<%= setupFacebookConfirmCodeURL %>" method="POST" name="facebookActionForm" useNamespace="true" id="facebookActionForm" />
			<aui:button-row>
				<aui:button name="cancel" type="cancel"/>
			</aui:button-row>
		</aui:container>
	</c:when>
	<c:otherwise>
		<aui:form action="<%= setupFacebookStartCodeURL %>" method="POST" name="facebookStartForm" useNamespace="true" cssClass="">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:row name="facebookAppParameters">
						<aui:col span="3">
							<aui:input name="facebookAppId" value="<%= facebookAppId %>" label="label-facebook-appid" disabled="<%= isFacebookAuthorized %>" />
						</aui:col>
						<aui:col span="9">
							<aui:input name="facebookAppSecret" value="<%= facebookAppSecret %>" label="label-facebook-app-secret" disabled="<%= isFacebookAuthorized %>"/>
							<aui:input name="facebookClientToken" value="<%= facebookClientToken %>" label="label-facebook-client-token" disabled="<%= isFacebookAuthorized %>"/>
						</aui:col>
					</aui:row>
			
					<aui:row name="facebookPageParameters">
						<aui:col span="3">
							<aui:input name="facebookPageId" value="<%= facebookPageId %>" label="label-facebook-pageid" disabled="<%= isFacebookAuthorized %>"/>
						</aui:col>
						<aui:col span="9">
							<aui:input name="facebookPageAccessToken" value="<%= facebookPageAccessToken %>" label="label-facebook-page-access-token" disabled="<%= true %>" type="hidden" />
						</aui:col>
					</aui:row>
			
					<aui:row name="facebookUserParameters" hidden="true">
						<aui:col span="3">
							<aui:input name="facebookUserId" value="<%= facebookUserId %>" label="label-facebook-userid"/>
						</aui:col>
						<aui:col span="9">
							<aui:input name="facebookUserAccessToken" value="<%= facebookUserAccessToken %>" label="label-facebook-user-access-token" disabled="<%= true %>"/>
						</aui:col>
					</aui:row>
				</aui:fieldset>
			</aui:fieldset-group>
			<aui:button-row>
				<aui:button type="submit" id="authFacebook"
					cssClass="<%= \"btn btn-social btn-facebook \" + (!isFacebookAuthorized ? \"\" : \"disabled\") %>"
					style="background : #3B5998; color: white"
					value="label-authorize-access" 
					icon="icon-facebook" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.Portlet.ready(function(portletId, node) {
		console.log('hello' + '\n' + 'portletId=' + portletId + '\n' +  'node=' + node);

		if (document.<portlet:namespace />facebookActionForm != null) {
			document.<portlet:namespace />facebookActionForm.submit();
		}

		if (<%=hasFacebookDeviceCode%>) {
			console.log('HAS CODE');
		} else {
			console.log('NO CODE');
		}
	});
</aui:script>

<!-- For Closing -->
<aui:script use="aui-base">
	var cancelButton = A.one('#<portlet:namespace/>cancel');

	if (cancelButton != null) {
		cancelButton.on(
			'click',
			function(event) {
				var data = '';
				Liferay.Util.getOpener().<portlet:namespace/>closeYourPopUp(
						data, '<portlet:namespace/>dialog_fb');
			});
	}
</aui:script>