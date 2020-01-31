package br.sofex.com.facialmap.Login;

import java.util.Random;

public class CodigoRecuperacao {

    private static final String DEVELOPERRAMDOM = "FACIALMAPSOFEXSTERILINKODONTOWEBIWG2MIRA";
    public static String randomGenFacialMap(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*DEVELOPERRAMDOM.length());
            builder.append(DEVELOPERRAMDOM.charAt(character));
        }
        return builder.toString();
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public String GenCodRecover(){
        String Devx2 = randomGenFacialMap(1)+getRandomNumberInRange(1,10)+randomGenFacialMap(1)+getRandomNumberInRange(1,10);
        return Devx2;
    }
    public static Boolean CheckCode(String code){
        String StringValidacao = "FACIALMAPSOFEX";
        Boolean Result = null;
        char charA = 0;
        char charB = 0;
        char charC = 0;
        char charD = 0;

        for(int i=0; i < code.length();i++){
            char c = code.charAt(i);
            if(i == 0){
                charA = c;
            }
            else{Result = false;}

            if(i == 1){
                charB = c;
            }
            else{Result = false;}

            if(i == 2){
                charC = c;
            }
            else{Result = false;}

            if(i == 3){
                charD = c;
            }
            else{Result = false;}
        }

        Result = StringValidacao.contains(charA+"") && StringValidacao.contains(charC+"") &&
                Character.isDigit(charB) && Character.isDigit(charD);

        System.out.println("code :"+code+" Result "+Result);
        return Result;
    }

}
