import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class HttpTest {
    @Test
    public void testGet() {
        String result = "";

        try {
            URL url = new URL("http://39.100.66.38/v1/file/6");    //把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接

            //connection.setRequestProperty("设置请求头key", "请求头value");
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println(sb.toString());
            result = sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("连接失败");
        }

        JSONObject json = JSONObject.parseObject(result);
    }
}
