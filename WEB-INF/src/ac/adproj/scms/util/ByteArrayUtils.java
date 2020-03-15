package ac.adproj.scms.util;

import java.util.ArrayList;

public final class ByteArrayUtils {
    private ByteArrayUtils() { }
    
    public static String convertByteArrayToString(byte[] arr) {
        StringBuilder sBuilder = new StringBuilder();
        for(byte b : arr) {
            sBuilder.append(b);
            sBuilder.append(',');
        }
        sBuilder.deleteCharAt(sBuilder.length() - 1);
        return sBuilder.toString();
    }
    
    public static byte[] convertStringToByteArray(String str) {
        String[] splitArr = str.split("(\\s)*,(\\s)*");
        ArrayList<Byte> listOfByte = new ArrayList<>(splitArr.length);
        
        for(String s : splitArr) {
            listOfByte.add(Byte.parseByte(s));
        }
        
        byte[] bA = new byte[splitArr.length];
        for(int i = 0; i < listOfByte.size(); i++) {
            bA[i] = listOfByte.get(i);
        }
        
        return bA;
    }
}
