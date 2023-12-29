package util;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public class Security {

    /**
     * 与えられたURLが安全であるかどうかをチェックする。
     *
     * @param url チェックするURL
     * @return 安全な場合はtrue、そうでない場合はfalse
     */
    public static boolean isUrlSafe(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            return "http".equals(scheme) || "https".equals(scheme);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * SSL証明書を検証する。
     *
     * @param url 検証するURL
     * @return SSL証明書が有効な場合はtrue、そうでない場合はfalse
     */
    public static boolean verifySSLCertificate(String url) {
        try {
            URL urlObj = new URI(url).toURL();
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();

            // デフォルトのホスト名検証を使用する
            connection.setHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());

            // 接続を開始することでSSL証明書の検証が行われる
            connection.connect();
            connection.disconnect();
            return true;
        } catch (Exception e) {
            // 検証中に例外が発生した場合は、SSL証明書に問題があるとみなす
            return false;
        }
    }

    /**
     * ドメインがX.509証明書に含まれるCN（コモンネーム）またはサブジェクト別名に一致するかどうかを検証する。
     *
     * @param url 検証するURL
     * @return ドメインが証明書に一致する場合はtrue、そうでない場合はfalse
     */
    public static boolean isDomainMatchCertificate(String url) {
        try {
            URL urlObj = new URI(url).toURL();
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();

            connection.connect();

            Certificate[] certs = connection.getServerCertificates();
            for (Certificate cert : certs) {
                if (cert instanceof X509Certificate) {
                    X509Certificate x509cert = (X509Certificate) cert;

                    // 証明書からサブジェクト名を取得
                    String subjectDN = x509cert.getSubjectX500Principal().getName();
                    String commonName = getCommonName(subjectDN);

                    // URLのホスト名を取得
                    String host = new URI(url).getHost();

                    // ホスト名がコモンネームに一致するかチェック
                    if (host.equals(commonName)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * X.500主体名からコモンネーム(CN)を取得する。
     *
     * @param subjectDN X.500主体名
     * @return コモンネーム(CN)
     */
    private static String getCommonName(String subjectDN) {
        for (String part : subjectDN.split(",")) {
            if (part.startsWith("CN=")) {
                return part.substring(3);
            }
        }
        return "";
    }

    /**
     * 指定されたURLが404 Not Foundであるかどうかを検証する。
     *
     * @param urlString 検証するURLの文字列
     * @return URLが404 Not Foundであればtrue、そうでなければfalse
     */
    public static boolean isNotFound(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URI(urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_NOT_FOUND;
        } catch (Exception e) {
            // 例外が発生した場合は、接続に問題があったとみなしてfalseを返す
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
