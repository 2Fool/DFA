package java.com.zzh.algorithm.dhke;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DiffieHellmanExample {

    public static void main(String[] args) throws Exception {
        // 1. 创建 Diffie-Hellman 密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(2048); // 可根据需要选择合适的密钥大小
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 2. 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 3. 将公钥传递给对方
        // 在实际应用中，通信双方会通过网络或其他方式交换各自的公钥

        // 4. 对方收到公钥后，使用以下步骤生成共享密钥
        //    a. 获取对方的公钥
        //    b. 使用自己的私钥与对方的公钥执行密钥协商
        //    c. 获取共享密钥
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        DHParameterSpec dhParameterSpec = ((javax.crypto.interfaces.DHPublicKey) publicKey).getParams();
        PublicKey receivedPublicKey = getReceivedPublicKey(); // 替换为对方的公钥获取方式

        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(receivedPublicKey, true);

        SecretKey sharedSecretKey = keyAgreement.generateSecret("AES");

        // 现在，sharedSecretKey 就是双方共享的密钥，可以用于加密通信等操作
    }

    // 在实际应用中，这个方法应该从网络或其他途径获取对方的公钥
    private static PublicKey getReceivedPublicKey() {
        // 在实际应用中，你需要实现获取对方公钥的逻辑，这里简单返回 null
        return null;
    }

}