package org.example;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;
public class Main {
        public static void main( String[] args ) throws NoSuchAlgorithmException {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            List<DatatoSTR> list = new MyList<>(new DatatoSTR(""), pair);

            list.add(new DatatoSTR("Something"));
            list.add(new DatatoSTR("Somewhere"));

            for (var data : list) {
                System.out.println(data);
            }
        }
    }

