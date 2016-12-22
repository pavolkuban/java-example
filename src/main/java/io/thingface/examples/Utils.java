package io.thingface.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Utils {

    public static TrustManager[] GetTrustManagers() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        return trustAllCerts;
    }
    
    public static SocketFactory getSocketFactory() throws KeyManagementException, NoSuchAlgorithmException{
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, Utils.GetTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }
    
    public static ObjectMapper createObjectMapper(){
        ObjectMapper _mapper = new ObjectMapper();
        _mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        _mapper.disable(SerializationFeature.INDENT_OUTPUT);
        _mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        return _mapper;
    }
    
    public static byte[] toPayload(Object value) throws JsonProcessingException{
        ObjectMapper mapper = createObjectMapper();
        return mapper.writeValueAsBytes(value);
    }
    
    public static DeviceCommand toCommand(byte[] payload) throws IOException{
        ObjectMapper _mapper = createObjectMapper();
        return _mapper.readValue(payload, DeviceCommand.class);
    }
}
