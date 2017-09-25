package test.linyi.saml;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;

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

		InputStream kis = KeyStoreTest.class.getResourceAsStream("jbosskeys.jks");

		
	      // Load the KeyStore and get the signing key and certificate.
		keyKeyStore.load(kis, "123456".toCharArray());
        
        KeyStore.PrivateKeyEntry keyEntry =
            (KeyStore.PrivateKeyEntry) keyKeyStore.getEntry
                ("jbosskeys", new KeyStore.PasswordProtection("123456".toCharArray()));
        X509Certificate cert = (X509Certificate) keyEntry.getCertificate();
        
        System.out.println("=============X509Certificate:" + cert);

        String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM",(Provider) Class.forName(providerName).newInstance());
        // Create the KeyInfo containing the X509Data.
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        List x509Content = new ArrayList();
        x509Content.add(cert.getSubjectX500Principal().getName());
        x509Content.add(cert);
        X509Data xd = kif.newX509Data(x509Content);
        System.out.println("xd:" + xd);

		//
		System.out.println(keyKeyStore.getDefaultType());

		System.out.println("===================Certificate:" + keyKeyStore.getCertificate("jbosskeys"));

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

		kis.close();

	}

}
