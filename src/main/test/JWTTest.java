import com.qyk.Jupyter.utils.JwtUtils;
import org.junit.Test;

import java.util.Map;

public class JWTTest {
    @Test
    public void testDeco(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTc2NTgwMDQsIm5hbWUiOiJhZG1pbiIsInJpZCI6MSwicm9sZXMiOjIwMCwidWlkIjoyfQ.MjHsCOokADn5koqu64MgBx-h-P7TUaFuz5UpIJsVgjA";

        Map<String, Object> map = null;
        try {
            map = JwtUtils.verifyToken(token);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
