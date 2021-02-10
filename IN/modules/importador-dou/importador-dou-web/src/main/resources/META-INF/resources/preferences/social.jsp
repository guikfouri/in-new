<%@ include file="/init.jsp" %>

<%
String twitterConsumerKey = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, "");
String twitterConsumerSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, "");

String twitterAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, "");
String twitterAccessTokenSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, "");
Boolean isTwitterAuthorized = (twitterAccessToken.length() > 0 && twitterAccessTokenSecret.length() > 0);

String facebookAppId = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, "");
String facebookAppSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, "");
String facebookPageId = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, "");
String facebookClientToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, "");

String facebookPageAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, "");
Boolean isFacebookAuthorized = facebookPageAccessToken.length() > 0;
%>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="panel panel-secondary">
		<div class="panel-body">
			<aui:fieldset label="twitter">
				<liferay-ui:icon-list>
					<liferay-ui:icon
						iconCssClass="icon-twitter"
						message="authentication"
						url="#authTwitter"
						id="authTwitter"
						target="top"
						/>
					<liferay-ui:icon
						iconCssClass="icon-remove-sign"
						message="label-de-authorize-access"
						url="#removeAuthTwitter"
						id="removeAuthTwitter"
						cssClass="<%= isTwitterAuthorized ? "" : "hide" %>"
						/>
				</liferay-ui:icon-list>
				<aui:row>
					<aui:col span="6">
						<aui:input name="twitterConsumerKey" value="${twitterConsumerKey}" label="label-twitter-consumer-key" disabled="<%= true %>"/>
					</aui:col>

					<aui:col span="6">
						<aui:input name="twitterConsumerSecret" value="${twitterConsumerSecret}" label="label-twitter-consumer-secret" disabled="<%= true %>"/>
					</aui:col>
				</aui:row>

				<aui:row id="twitterAccessTokenParams">
					<aui:col span="6">
						<aui:input name="twitterAccessToken" value="${twitterAccessToken}" label="label-twitter-access-token" disabled="<%= true %>"/>
					</aui:col>

					<aui:col span="6">
						<aui:input name="twitterAccessTokenSecret" value="${twitterAccessTokenSecret}" label="label-twitter-access-token-secret" disabled="<%= true %>"/>
					</aui:col>
				</aui:row>
			</aui:fieldset>
		</div>
	</div>

	<div class="panel panel-secondary">
		<div class="panel-body">
			<aui:fieldset label="facebook">
				<liferay-ui:icon-list>
					<liferay-ui:icon
						iconCssClass="icon-facebook"
						message="authentication"
						url="#authFacebook"
						id="authFacebook"
						target="top"
						/>
					<liferay-ui:icon
						iconCssClass="icon-remove-sign"
						message="label-de-authorize-access"
						url="#removeAuthFacebook"
						id="removeAuthFacebook"
						cssClass="<%= isFacebookAuthorized ? "" : "hide" %>"
						/>
				</liferay-ui:icon-list>
				<aui:row name="facebookAppParameters">
					<aui:col span="3">
						<aui:input name="facebookAppId" value="${facebookAppId}" label="label-facebook-appid" disabled="<%= true %>" />
					</aui:col>
					<aui:col span="9">
						<aui:input name="facebookAppSecret" value="${facebookAppSecret}" label="label-facebook-app-secret" disabled="<%= true %>" />
						<aui:input name="facebookClientToken" value="${facebookClientToken}" label="label-facebook-client-token" disabled="<%= true %>" />
					</aui:col>
				</aui:row>

				<aui:row name="facebookPageParameters">
					<aui:col span="3">
						<aui:input name="facebookPageId" value="${facebookPageId}" label="label-facebook-pageid" disabled="<%= true %>" />
					</aui:col>
					<aui:col span="9">
						<aui:input name="facebookPageAccessToken" value="${facebookPageAccessToken}" label="label-facebook-page-access-token" disabled="<%= true %>" />
					</aui:col>
				</aui:row>

				<aui:row name="facebookUserParameters">
					<aui:col span="3">
						<aui:input name="facebookUserId" value="${facebookUserId}" label="label-facebook-userid" disabled="<%= true %>" />
					</aui:col>
					<aui:col span="9">
						<aui:input name="facebookUserAccessToken" value="${facebookUserAccessToken}" label="label-facebook-user-access-token" disabled="<%= true %>" />
					</aui:col>
				</aui:row>
			</aui:fieldset>
		</div>
	</div>
</div>

<!-- Twitter Authorization -->	
<portlet:renderURL var="authTwitterURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="auth" value="twitter" />
	<portlet:param name="state" value="start" />
	<portlet:param name="mvcPath" value="/auth/twitter.jsp" />
	<portlet:param name="tabs1" value="social"/>
</portlet:renderURL>
<portlet:actionURL name="removeAuthorization" var="removeTwitterAuthorizationURL">
	<portlet:param name="auth" value="twitter"/>
	<portlet:param name="tabs1" value="social"/>
</portlet:actionURL>

<aui:script use="liferay-util-window">
	var twitterAuthButton = A.one('#<portlet:namespace/>authTwitter');
	var twitterRemoveAuthButton = A.one('#<portlet:namespace/>removeAuthTwitter');

	if (!twitterAuthButton.hasClass('disabled')) {
		twitterAuthButton.on('click', function(event) {
			Liferay.Util.openWindow({
				dialog : {
					modal : true,
					centered : true,
					draggable: false,
					resizable: false,
					destroyOnHide: true,
					height : 450,
					width : 1000,
					after : {
						destroy : function(event) {
								window.location.reload();
							}
					}
				},
				id : '<portlet:namespace/>dialog_tw',
				title : '<liferay-ui:message key="label-authorize-access" /> Twitter',
				uri : '<%= authTwitterURL %>'
			});
		});
	}
	if (!twitterRemoveAuthButton.hasClass('disabled')) {
		twitterRemoveAuthButton.set('href','<%= removeTwitterAuthorizationURL.toString() %>');
	}
</aui:script>

<!-- Facebook Authorization -->
<portlet:renderURL var="authFacebookURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="auth" value="facebook" />
	<portlet:param name="state" value="start" />
	<portlet:param name="mvcPath" value="/auth/facebook.jsp" />
	<portlet:param name="tabs1" value="social"/>
</portlet:renderURL>
<portlet:actionURL name="removeAuthorization" var="removeFacebookAuthorizationURL">
	<portlet:param name="auth" value="facebook"/>
	<portlet:param name="tabs1" value="social"/>
</portlet:actionURL>

<aui:script use="liferay-util-window">
	var facebookAuthButton = A.one('#<portlet:namespace/>authFacebook');
	var facebookRemoveAuthButton = A.one('#<portlet:namespace/>removeAuthFacebook');

	if (!facebookAuthButton.hasClass('disabled')) {
		facebookAuthButton.on('click', function(event) {
			Liferay.Util.openWindow({
				cache: false,
				dialog : {
					modal : true,
					centered : true,
					draggable: false,
					resizable: false,
					destroyOnHide: true,
					height : 450,
					width : 1000,
					after : {
						destroy : function(event) {
								window.location.reload();
							}
					}
				},
				id : '<portlet:namespace/>dialog_fb',
				title : '<liferay-ui:message key="label-authorize-access" /> Facebook',
				uri : '<%= authFacebookURL %>'
			});
		});
	}
	if (!facebookRemoveAuthButton.hasClass('disabled')) {
		facebookRemoveAuthButton.set('href','<%= removeFacebookAuthorizationURL.toString() %>');
	}
</aui:script>

<!-- For Closing -->
<aui:script>
	Liferay.provide(
			window, 
			'<portlet:namespace/>closeYourPopUp', 
			function(data, dialogId) {
				var A = AUI();
				var dialog = Liferay.Util.Window.getById(dialogId);
				dialog.destroy();
			}, 
			[ 'liferay-util-window' ]
	);
</aui:script>