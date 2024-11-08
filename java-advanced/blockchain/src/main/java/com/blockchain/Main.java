package com.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Transaction {

    private final double amount;
    private final byte[] lastTransactionHash;
    private int nonce;
    private byte[] hash;

    public Transaction(double amount, byte[] lastTransactionHash) {
        this.amount = amount;
        this.lastTransactionHash = lastTransactionHash;
        this.nonce = 0;
    }

    public void incrementNonce() {
        nonce++;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] createData() {
        return ByteBuffer.allocate(Double.SIZE / Byte.SIZE + lastTransactionHash.length + Integer.SIZE / Byte.SIZE)
                .putDouble(amount)
                .put(lastTransactionHash)
                .putInt(nonce)
                .array();
    }

    public byte[] generateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(this.createData());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isValidHash(byte[] hash) {
        for (int i = 0; i < 3; i++) {
            if (hash[i] != 0) {
                return false;
            }
        }
        return true;
    }

    static Transaction mineTransaction(double amount, byte[] lastTransactionHash) {
        Transaction transaction = new Transaction(amount, lastTransactionHash);
        transaction.hash = transaction.generateHash();
        while (!Transaction.isValidHash(transaction.hash)) {
            transaction.incrementNonce();
            transaction.hash = transaction.generateHash();
        }
        return transaction;
    }

    public static byte[] createGenesisHash() {
        String genesisBlock = "genesis_block";

        return ByteBuffer.allocate(32)
                .put(genesisBlock.getBytes()).array();

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public String toString() {
        return String.format("Transaction:\n"
                + "-------------------------\n"
                + "Hash: %s\n"
                + "Previous Hash: %s\n"
                + "Amount: %f\n"
                + "Nonce: %d\n",
                bytesToHex(this.hash), bytesToHex(this.lastTransactionHash), this.amount, this.nonce
        );
    }

}

public class Main {

    public static void main(String[] args) {
        Transaction previousTransaction = Transaction.mineTransaction(
                Math.random() * 100,
                Transaction.createGenesisHash()
        );

        for (int i = 0; i < 4; i++) {
            Transaction transaction = Transaction.mineTransaction(
                    Math.random() * 100,
                    previousTransaction.getHash()
            );
            System.out.println(transaction);
            previousTransaction = transaction;
        }
    }
}
