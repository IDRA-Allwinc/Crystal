package com.crystal.infrastructure.security;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

public class CryptoRfc2898 implements PasswordEncoder {
    byte[] salt;

    public CryptoRfc2898(){
        try {
            salt = "..@s@lp@ssw04rD$$@..".getBytes("UTF-8");
        }catch (Exception ex){
            System.out.println("error HashPassword::: " + ex.getMessage());
        }
    }

    @Override
    public String encode(CharSequence charSequence) {
        String result;
        byte[] inArray = new byte[49];
        String password = charSequence.toString();
        try {
            if (password == null)
                throw new NullPointerException("password es nulo");

            Rfc2898DeriveBytes rfc2898DeriveBytes = new Rfc2898DeriveBytes(password, salt, 1000);
            byte[] bytes = rfc2898DeriveBytes.getBytes(32);

            System.arraycopy(salt, 0, inArray, 1, 16);
            System.arraycopy(bytes, 0, inArray, 17, 32);

            result = Base64.getEncoder().encodeToString(inArray);

        } catch (Exception e) {
            System.out.println("error HashPassword::: " + e.getMessage());
            return null;
        }
        return result;
    }

    @Override
    public boolean matches(CharSequence charSequence, String hashedPassword) {
        byte[] a = new byte[32];
        byte[] bytes = null;
        String password = charSequence.toString();
        try {
            if (hashedPassword == null)
                return false;
            if (password == null)
                throw new NullPointerException("password es nulo");

            byte[] numArray = Base64.getDecoder().decode(hashedPassword.getBytes());
            if (numArray.length != 49 || numArray[0] != 0)
                return false;

            System.arraycopy(numArray, 1, salt, 0, 16);
            System.arraycopy(numArray, 17, a, 0, 32);

            Rfc2898DeriveBytes rfc2898DeriveBytes = new Rfc2898DeriveBytes(password, salt, 1000);
            bytes = rfc2898DeriveBytes.getBytes(32);

        } catch (Exception e) {
            System.out.println("error VerifyHashedPassword::: " + e.getMessage());
            return false;
        }
        return ByteArraysEqual(a, bytes);
    }

    public boolean ByteArraysEqual(byte[] a, byte[] b) {
        if (a == b)
            return true;
        if (a == null || b == null || a.length != b.length)
            return false;
        boolean flag = true;
        for (int index = 0; index < a.length; ++index)
            flag &= a[index] == b[index];
        return flag;
    }

}
