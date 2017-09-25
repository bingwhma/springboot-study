package test.linyi.saml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlSignature {

	public static PrivateKey getPrivateKey() throws Exception {

		KeyStore KeyStore = java.security.KeyStore.getInstance("JKS");

		InputStream kis = KeyStoreTest.class.getResourceAsStream("jbosskeys.jks");

		KeyStore.load(kis, "123456".toCharArray());
		PrivateKey p = (PrivateKey) KeyStore.getKey("jbosskeys", "123456".toCharArray());
		kis.close();

		return p;
	}

	public static PublicKey getPublicKey() throws Exception {

		KeyStore KeyStore = java.security.KeyStore.getInstance("JKS");
		InputStream kis = KeyStoreTest.class.getResourceAsStream("jbosskeys.jks");
		KeyStore.load(kis, "123456".toCharArray());
		PrivateKey p = (PrivateKey) KeyStore.getKey("jbosskeys", "123456".toCharArray());
		kis.close();

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate c = KeyStore.getCertificate("jbosskeys");
		return c.getPublicKey();
	}

	/**
	 * This is a simple example of generating a Detached XML Signature using the
	 * JSR 105 API. The resulting signature will look like (key and signature
	 * values will be different):
	 *
	 * <pre>
	 * <code>
	 * <Signature xmlns="http://www.w3.org/2000/09/xmldsig#">
	 *   <SignedInfo>
	 *     <CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315"/>
	 *     <SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#dsa-sha1"/>
	 *     <Reference URI="http://www.w3.org/TR/xml-stylesheet">
	 *       <DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
	 *       <DigestValue>60NvZvtdTB+7UnlLp/H24p7h4bs=</DigestValue>
	 *     </Reference>
	 *   </SignedInfo>
	 *   <SignatureValue>
	 *     DpEylhQoiUKBoKWmYfajXO7LZxiDYgVtUtCNyTgwZgoChzorA2nhkQ==
	 *   </SignatureValue>
	 *   <KeyInfo>
	 *     <KeyValue>
	 *       <DSAKeyValue>
	 *	   <P>
	 *           rFto8uPQM6y34FLPmDh40BLJ1rVrC8VeRquuhPZ6jYNFkQuwxnu/wCvIAMhukPBL
	 *           FET8bJf/b2ef+oqxZajEb+88zlZoyG8g/wMfDBHTxz+CnowLahnCCTYBp5kt7G8q
	 *           UobJuvjylwj1st7V9Lsu03iXMXtbiriUjFa5gURasN8=
	 *         </P>
	 *         <Q>
	 *           kEjAFpCe4lcUOdwphpzf+tBaUds=
	 *         </Q>
	 *         <G>
	 *           oe14R2OtyKx+s+60O5BRNMOYpIg2TU/f15N3bsDErKOWtKXeNK9FS7dWStreDxo2
	 *           SSgOonqAd4FuJ/4uva7GgNL4ULIqY7E+mW5iwJ7n/WTELh98mEocsLXkNh24HcH4
	 *           BZfSCTruuzmCyjdV1KSqX/Eux04HfCWYmdxN3SQ/qqw=
	 *         </G>
	 *         <Y>
	 *           pA5NnZvcd574WRXuOA7ZfC/7Lqt4cB0MRLWtHubtJoVOao9ib5ry4rTk0r6ddnOv
	 *           AIGKktutzK3ymvKleS3DOrwZQgJ+/BDWDW8kO9R66o6rdjiSobBi/0c2V1+dkqOg
	 *           jFmKz395mvCOZGhC7fqAVhHat2EjGPMfgSZyABa7+1k=
	 *         </Y>
	 *       </DSAKeyValue>
	 *     </KeyValue>
	 *   </KeyInfo>
	 * </Signature>
	 * </code>
	 * </pre>
	 */
	public static void GenDetached() throws Exception {
		// First, create a DOM XMLSignatureFactory that will be used to
		// generate the XMLSignature and marshal it to DOM.
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Create a Reference to an external URI that will be digested
		// using the SHA1 digest algorithm
		Reference ref = fac.newReference("http://www.w3.org/TR/xml-stylesheet",
				fac.newDigestMethod(DigestMethod.SHA1, null));

		// Create the SignedInfo
		SignedInfo si = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
						(C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.DSA_SHA1, null), Collections.singletonList(ref));

		// Create a DSA KeyPair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(512);
		KeyPair kp = kpg.generateKeyPair();

		// Create a KeyValue containing the DSA PublicKey that was generated
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		KeyValue kv = kif.newKeyValue(kp.getPublic());

		// Create a KeyInfo and add the KeyValue to it
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

		// Create the XMLSignature (but don't sign it yet)
		XMLSignature signature = fac.newXMLSignature(si, ki);

		// Create the Document that will hold the resulting XMLSignature
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); // must be set
		Document doc = dbf.newDocumentBuilder().newDocument();

		// Create a DOMSignContext and set the signing Key to the DSA
		// PrivateKey and specify where the XMLSignature should be inserted
		// in the target document (in this case, the document root)
		DOMSignContext signContext = new DOMSignContext(kp.getPrivate(), doc);

		// Marshal, generate (and sign) the detached XMLSignature. The DOM
		// Document will contain the XML Signature if this method returns
		// successfully.
		signature.sign(signContext);

		// output the resulting document
		OutputStream os = System.out;

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
	}

	public static void GenEnveloping() throws Exception {
		// First, create the DOM XMLSignatureFactory that will be used to
		// generate the XMLSignature
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Next, create a Reference to a same-document URI that is an Object
		// element and specify the SHA1 digest algorithm
		Reference ref = fac.newReference("#object", fac.newDigestMethod(DigestMethod.SHA1, null));

		// Next, create the referenced Object
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().newDocument();
		Node text = doc.createTextNode("some text");
		XMLStructure content = new DOMStructure(text);
		XMLObject obj = fac.newXMLObject(Collections.singletonList(content), "object", null, null);

		// Create the SignedInfo
		SignedInfo si = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
						(C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.DSA_SHA1, null), Collections.singletonList(ref));

		// Create a DSA KeyPair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(512);
		KeyPair kp = kpg.generateKeyPair();

		// Create a KeyValue containing the DSA PublicKey that was generated
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		KeyValue kv = kif.newKeyValue(kp.getPublic());

		// Create a KeyInfo and add the KeyValue to it
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

		// Create the XMLSignature (but don't sign it yet)
		XMLSignature signature = fac.newXMLSignature(si, ki, Collections.singletonList(obj), null, null);

		// Create a DOMSignContext and specify the DSA PrivateKey for signing
		// and the document location of the XMLSignature
		DOMSignContext dsc = new DOMSignContext(kp.getPrivate(), doc);

		// Lastly, generate the enveloping signature using the PrivateKey
		signature.sign(dsc);

		// output the resulting document
		OutputStream os = System.out;

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
	}

	public static void main(String[] args) throws Throwable {

		GenDetached();

		System.out.println("\n==================");
		GenEnveloping();
		
		System.out.println("\n==================");
		
		Validate();
		
	}

	public static void Validate() throws Throwable  {

		// Instantiate the document to be validated
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments\"/><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#dsa-sha1\"/><Reference URI=\"#object\"><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><DigestValue>7/XTsHaBSOnJ/jXD5v0zL6VKYsk=</DigestValue></Reference></SignedInfo><SignatureValue>fZM6+yyCjvQ4ctbA7ZUp9FLx9Bo2istoEK8bKkklmIEUlceX+8QJ1w==</SignatureValue><KeyInfo><KeyValue><DSAKeyValue><P>/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9" +
"xD7nN1kuFw==</P><Q>li7dzDacuo67Jg7mtqEm2TRuOMU=</Q><G>Z4Rxsnqc9E7pGknFFH2xqaryRPBaQ01khpMdLRQnG541Awtx/XPaF5Bpsy4pNWMOHCBiNU0Nogps" +
"QW5QvnlMpA==</G><Y>zQrds4un2ArR9VLrwcEpGfPaDcn8pp+KQcEIRr9mg6ktKmecXqdeha/OV2PapaV1XlvNwTFwRspd" +
"wFzOqn8xXg==</Y></DSAKeyValue></KeyValue></KeyInfo><Object Id=\"object\">some text</Object></Signature>";
		
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		Document doc = dbf.newDocumentBuilder().parse(is);

		// Document doc = dbf.newDocumentBuilder()
		// .parse(new FileInputStream(args[0]));

		// Find Signature element
		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0) {
			throw new Exception("Cannot find Signature element");
		}

		// Create a DOM XMLSignatureFactory that will be used to unmarshal the
		// document containing the XMLSignature
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Create a DOMValidateContext and specify a KeyValue KeySelector
		// and document context
		DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nl.item(0));

		// unmarshal the XMLSignature
		XMLSignature signature = fac.unmarshalXMLSignature(valContext);

		// Validate the XMLSignature (generated above)
		boolean coreValidity = signature.validate(valContext);

		// Check core validation status
		if (coreValidity == false) {
			System.err.println("Signature failed core validation");
			boolean sv = signature.getSignatureValue().validate(valContext);
			System.out.println("signature validation status: " + sv);
			// check the validation status of each Reference
			Iterator i = signature.getSignedInfo().getReferences().iterator();
			for (int j = 0; i.hasNext(); j++) {
				boolean refValid = ((Reference) i.next()).validate(valContext);
				System.out.println("ref[" + j + "] validity status: " + refValid);
			}
		} else {
			System.out.println("Signature passed core validation");
		}
	}

	/**
	 * KeySelector which retrieves the public key out of the KeyValue element
	 * and returns it. NOTE: If the key algorithm doesn't match signature
	 * algorithm, then the public key will be ignored.
	 */
	private static class KeyValueKeySelector extends KeySelector {
		public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, AlgorithmMethod method,
				XMLCryptoContext context) throws KeySelectorException {
			if (keyInfo == null) {
				throw new KeySelectorException("Null KeyInfo object!");
			}
			SignatureMethod sm = (SignatureMethod) method;
			List list = keyInfo.getContent();

			for (int i = 0; i < list.size(); i++) {
				XMLStructure xmlStructure = (XMLStructure) list.get(i);
				if (xmlStructure instanceof KeyValue) {
					PublicKey pk = null;
					try {
						pk = ((KeyValue) xmlStructure).getPublicKey();
					} catch (KeyException ke) {
						throw new KeySelectorException(ke);
					}
					// make sure algorithm is compatible with method
					if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
						return new SimpleKeySelectorResult(pk);
					}
				}
			}
			throw new KeySelectorException("No KeyValue element found!");
		}

		// @@@FIXME: this should also work for key types other than DSA/RSA
		static boolean algEquals(String algURI, String algName) {
			if (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
				return true;
			} else if (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) {
				return true;
			} else {
				return false;
			}
		}
	}

	private static class SimpleKeySelectorResult implements KeySelectorResult {
		private PublicKey pk;

		SimpleKeySelectorResult(PublicKey pk) {
			this.pk = pk;
		}

		public Key getKey() {
			return pk;
		}
	}
}
