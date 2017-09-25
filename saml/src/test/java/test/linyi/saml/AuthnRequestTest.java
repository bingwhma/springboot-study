package test.linyi.saml;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.saml2.core.impl.RequestedAuthnContextBuilder;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;

public class AuthnRequestTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		String actionUrl = "http://127.0.0.1:8080/idp";
		String redirectionUrl = "http://127.0.0.1:8080/sp";
		System.out.println(buildAuthnRequest(actionUrl, redirectionUrl, "1"));
	}

	/**
	 * @param actionUrl
	 * @param redirectionUrl
	 * @param relayState
	 * @return
	 */
	public static String buildAuthnRequest(String actionURL, String redirectionUrl, String relayState) {
		try {
			// 生成ID
			String randId = Math.abs(UUID.randomUUID().getMostSignificantBits()) + "";
			System.out.println("Random ID: " + randId);

			// 创建 issuer Object
			IssuerBuilder issuerBuilder = new IssuerBuilder();
			Issuer issuer = issuerBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "samlp");
			issuer.setValue(actionURL);

			// 创建 NameIDPolicy
			NameIDPolicyBuilder nameIdPolicyBuilder = new NameIDPolicyBuilder();
			NameIDPolicy nameIdPolicy = nameIdPolicyBuilder.buildObject();
			nameIdPolicy.setSchemaLocation("urn:oasis:names:tc:SAML:2.0:protocol");
			nameIdPolicy.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
			nameIdPolicy.setSPNameQualifier(redirectionUrl);
			nameIdPolicy.setAllowCreate(true);

			// Create AuthnContextClassRef
			AuthnContextClassRefBuilder authnContextClassRefBuilder = new AuthnContextClassRefBuilder();
			AuthnContextClassRef authnContextClassRef = authnContextClassRefBuilder
					.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml");
			authnContextClassRef
					.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

			// Create RequestedAuthnContext
			RequestedAuthnContextBuilder requestedAuthnContextBuilder = new RequestedAuthnContextBuilder();
			RequestedAuthnContext requestedAuthnContext = requestedAuthnContextBuilder.buildObject();
			requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
			requestedAuthnContext.getAuthnContextClassRefs().add(authnContextClassRef);

			DateTime issueInstant = new DateTime();
			AuthnRequestBuilder authRequestBuilder = new AuthnRequestBuilder();
			AuthnRequest authRequest = authRequestBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:protocol",
					"AuthnRequest", "samlp");
			authRequest.setForceAuthn(false);
			authRequest.setIsPassive(false);
			authRequest.setIssueInstant(issueInstant);
			authRequest.setProtocolBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
			authRequest.setAssertionConsumerServiceURL(redirectionUrl);
			authRequest.setIssuer(issuer);
			authRequest.setNameIDPolicy(nameIdPolicy);
			authRequest.setRequestedAuthnContext(requestedAuthnContext);
			authRequest.setID(randId);
			authRequest.setVersion(SAMLVersion.VERSION_20);

			String stringRep = authRequest.toString();

			System.out.println("===============New AuthnRequestImpl: " + stringRep);
			System.out.println("===============Assertion Consumer Service URL: " + authRequest.getAssertionConsumerServiceURL());

			Marshaller marshaller = org.opensaml.Configuration.getMarshallerFactory().getMarshaller(authRequest);
			org.w3c.dom.Element authDOM = marshaller.marshall(authRequest);
			StringWriter rspWrt = new StringWriter();
			XMLHelper.writeNode(authDOM, rspWrt);
			String messageXML = rspWrt.toString();

			Deflater deflater = new Deflater(Deflater.DEFLATED, true);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
			deflaterOutputStream.write(messageXML.getBytes());
			deflaterOutputStream.close();
			
			String samlRequest = Base64.encodeBytes(byteArrayOutputStream.toByteArray(), Base64.DONT_BREAK_LINES);
			String outputString = new String(byteArrayOutputStream.toByteArray());
			System.out.println("===============Compressed String: " + outputString);
			samlRequest = URLEncoder.encode(samlRequest, "UTF-8");

			System.out.println("===============Converted AuthRequest: " + messageXML);
			System.out.println("===============samlRequest: " + samlRequest);
			// messageXML = messageXML.replace("<", "&lt;");
			// messageXML = messageXML.replace(">", "&gt;");

			String url = actionURL + "?SAMLRequest=" + samlRequest + "&RelayState=" + relayState;
			System.out.println("===============url:" + url);
			return url;
		} catch (MarshallingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Nothing yet
		}
		return "";
	}
	

}