package global;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Fast {

    public static final String ACCOUNT_PATH = "config/account.json";
    public static final String CONFIG_PATH = "config/config.json";


    private static final JSONObject ACCOUNT_JSON;
    private static final JSONObject PATH_JSON;
    private static final JSONObject WEB_JSON;

    static {
        try {
            PATH_JSON = new JSONObject(readResource(CONFIG_PATH));
            ACCOUNT_JSON = new JSONObject(readResource(ACCOUNT_PATH));
            WEB_JSON = new JSONObject(readResource("web/web.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String PASSWORD = ACCOUNT_JSON.getString("password");
    public static final String USERNAME = ACCOUNT_JSON.getString("username");
    public static final String DRIVER_PATH = PATH_JSON.getString("driver_path");
    public static final String FIRST_VISIT_URL = WEB_JSON.getString("first_visit_url");
    public static final String BASE_URL = WEB_JSON.getString("base_url");

    public static String readResource(String path) throws Exception {
        try (InputStream is = Fast.class.getClassLoader().getResourceAsStream(path);
             Scanner scanner = new Scanner(Objects.requireNonNull(is), StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
