package test.linyi.saml;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.BasicX509Credential;

public class KeyStoreTest {

	public static Credential getCredential() throws Exception {

		KeyStore keyKeyStore = KeyStore.getInstance("JKS");
		InputStream kIs = KeyStoreTest.class.getResourceAsStream("jbosskeys.jks");
		keyKeyStore.load(kIs, "123456".toCharArray());
		kIs.close();

		// create credential and initialize
		BasicX509Credential credential = new BasicX509Credential();
		credential.setEntityCertificate((X509Certificate) keyKeyStore.getCertificate("jbosskeys"));
		credential.setPrivateKey((PrivateKey) keyKeyStore.getKey("jbosskeys", "123456".toCharArray()));

		return credential;
	}

	public static void main(String[] args) throws Exception {

		// KeyStore keyKeyStore = KeyStore.getInstance("PKCS12");
		KeyStore keyKeyStore = KeyStore.getInstance("JKS");

		InputStream kIs = KeyStoreTest.class.getResourceAsStream("jbosskeys.jks");

		keyKeyStore.load(kIs, "123456".toCharArray());

		//
		System.out.println(keyKeyStore.getDefaultType());

		System.out.println("===================" + keyKeyStore.getCertificate("jbosskeys"));

		System.out.println("==========" + keyKeyStore.getCertificate("jbosskeys").getPublicKey());
		// create public key (cert) portion of credential
		// CertificateFactory cf = CertificateFactory.getInstance("X.509");
		// X509Certificate publicKey = (X509Certificate)
		// cf.generateCertificate(kIs);
		// kIs.close();

		// create credential and initialize
//		BasicX509Credential credential = new BasicX509Credential();
//		credential.setEntityCertificate((X509Certificate) keyKeyStore.getCertificate("jbosskeys"));
//		credential.setPrivateKey((PrivateKey) keyKeyStore.getKey("jbosskeys", "123456".toCharArray()));
//
//		System.out.println(credential.toString());

//		CertificateFactory cf = CertificateFactory.getInstance("X.509");
//		X509Certificate cert = (X509Certificate) cf.generateCertificate(kIs);

		kIs.close();

	}

}
