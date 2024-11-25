package com.todolist.data.network

import android.content.Context
import com.example.todolist.R
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object NetworkConfig {

    fun createCustomOkHttpClient(context: Context): OkHttpClient {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certInputStream: InputStream = context.resources.openRawResource(R.raw.localhost)
        val certificate = certificateFactory.generateCertificate(certInputStream)
        certInputStream.close()

        // Load the certificate into a KeyStore
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()) // Use the default type (JKS)
        keyStore.load(null, null) // Initialize the KeyStore
        keyStore.setCertificateEntry("myCert", certificate) // Add the certificate

        // Create a TrustManager that trusts the certificate in the KeyStore
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        // Set up SSLContext with the TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        // Return a custom OkHttpClient that uses the SSLContext
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
            .build()
    }
}
