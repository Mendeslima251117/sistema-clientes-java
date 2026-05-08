package util;

import java.security.MessageDigest;

public class SenhaUtil {

    public static String criptografar(String senha) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("MD5");

            byte[] array = md.digest(
                    senha.getBytes()
            );

            StringBuilder sb =
                    new StringBuilder();

            for(byte b : array){

                sb.append(
                        String.format(
                                "%02X",
                                b
                        )
                );
            }

            return sb.toString();

        } catch (Exception e){

            throw new RuntimeException(e);
        }
    }
}
