package com.bancoalimentos.seguranca;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Guarda senhas somente em forma de hash (PBKDF2 com sal aleatório). Formato: pbkdf2$iteracoes$sal$hash.
 */
public final class HashDeSenha {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACOES = 120_000;
    private static final int BYTES_DO_SAL = 16;
    private static final int BITS_DO_HASH = 256;
    private static final String SEPARADOR = "$";

    private HashDeSenha() {
    }

    public static String gerar(String senha) {
        byte[] sal = new byte[BYTES_DO_SAL];
        new SecureRandom().nextBytes(sal);
        byte[] hash = calcular(senha, sal, ITERACOES);
        return String.join(SEPARADOR, "pbkdf2", String.valueOf(ITERACOES), codificar(sal), codificar(hash));
    }

    public static boolean confere(String senha, String hashGuardado) {
        String[] partes = hashGuardado.split("\\" + SEPARADOR);
        int iteracoes = Integer.parseInt(partes[1]);
        byte[] sal = Base64.getDecoder().decode(partes[2]);
        byte[] esperado = Base64.getDecoder().decode(partes[3]);
        return MessageDigest.isEqual(esperado, calcular(senha, sal, iteracoes));
    }

    private static byte[] calcular(String senha, byte[] sal, int iteracoes) {
        try {
            PBEKeySpec especificacao = new PBEKeySpec(senha.toCharArray(), sal, iteracoes, BITS_DO_HASH);
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(especificacao).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível calcular o hash da senha", e);
        }
    }

    private static String codificar(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
