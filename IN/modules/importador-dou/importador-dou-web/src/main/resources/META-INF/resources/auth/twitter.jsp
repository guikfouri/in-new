<%@ include file="/init.jsp" %>

<liferay-ui:success key="msg-auth-success" message="msg-auth-success" />
<liferay-ui:error key="erro-inesperado" message="erro-inesperado" />

<%
String twitterStartAuthURL = GetterUtil.getString(renderRequest.getAttribute("auth_url"));

String twitterConsumerKey = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, "");
String twitterConsumerSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, "");

Boolean isTwitterAuthButtonEnabled = false;

if (twitterConsumerKey.length() > 0 && twitterConsumerSecret.length() > 0) {
	isTwitterAuthButtonEnabled = true;
}

String twitterAccessToken = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, "");
String twitterAccessTokenSecret = portletPreferences.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, "");
Boolean isTwitterAuthorized = (twitterAccessToken.length() > 0 && twitterAccessTokenSecret.length() > 0);
%>

<portlet:actionURL var="startTwitterOAuthURL" name="/auth_twitter">
	<portlet:param name="auth" value="twitter" />
	<portlet:param name="state" value="start" />
</portlet:actionURL>

<portlet:actionURL var="setupTwitterOAuthURL" name="/auth_twitter">
	<portlet:param name="auth" value="twitter" />
	<portlet:param name="state" value="enter_pin" />
</portlet:actionURL>

<c:choose>
	<c:when test="<%= Validator.isNotNull(twitterStartAuthURL) %>">
		<aui:container cssClass="jumbotron" align="center">
			Clique 
			<aui:a id="authTwitter" href="<%= twitterStartAuthURL.toString() %>" target="_blank">
				aqui
			</aui:a>
			para autorizar acesso a sua conta do Twitter e depois insira o PIN gerado no campo abaixo
			<aui:form action="<%= setupTwitterOAuthURL %>" name="formTwitterAuth">
				<aui:input name="pin" type="text" label="" autoFocus="<%= true %>" cssClass="form-control form-control-lg" style="width : 120px"/>
			
				<aui:button-row>
					<aui:button type="submit" value="label-confirm-access" />
					<aui:button name="cancel" type="cancel" />
				</aui:button-row>
			</aui:form>
		</aui:container>
	</c:when>
	<c:otherwise>
		<aui:form action="<%= startTwitterOAuthURL %>" name="startTwitterfm">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:row>
						<aui:col>
							<aui:input name="twitterConsumerKey" value="<%= twitterConsumerKey %>" label="label-twitter-consumer-key" disabled="<%= isTwitterAuthorized %>"/>
						</aui:col>
					</aui:row>
					<aui:row>
						<aui:col>
							<aui:input name="twitterConsumerSecret" value="<%= twitterConsumerSecret %>" label="label-twitter-consumer-secret" disabled="<%= isTwitterAuthorized %>"/>
						</aui:col>
					</aui:row>
				</aui:fieldset>
			</aui:fieldset-group>
			<aui:button-row>
				<aui:button type="submit" 
					cssClass="btn btn-social btn-twitter "
					style="background : #55acee; color : white"
					value="label-authorize-access"
					icon="icon-twitter"
					disabled="<%= isTwitterAuthorized %>" />
				<aui:button name="cancel" type="cancel" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>

<!-- For Closing -->
<aui:script use="aui-base">
	var button = A.one('#<portlet:namespace/>cancel');

	if (button != null) {
		button.on(
			'click',
			function(event) {
				var data = '';
				Liferay.Util.getOpener().<portlet:namespace/>closeYourPopUp(
						data, '<portlet:namespace/>dialog_tw');
			});
	}
</aui:script>
