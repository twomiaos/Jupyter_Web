import com.qyk.Jupyter.dao.ServerDao;
import com.qyk.Jupyter.model.Server;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 测试前需要把mybatis-conifg中的注释取消
 */
public class DaoTest {
    @Test
    public void testServerDao() throws IOException {
        SqlSession sqlSession = this.getSqlSession();
        ServerDao serverDao = sqlSession.getMapper(ServerDao.class);

        // 查询所有
        List<Server> servers = serverDao.getAll();
        System.out.println(servers);

        // 通过port查询
        System.out.println(serverDao.getByPort(8889));

        // 通过port删除
        serverDao.deleteByPort(8888);
        sqlSession.commit();
        servers = serverDao.getAll();
        System.out.println(servers);

        // 通过token删除
        serverDao.deleteByToken("dasfa13j3v4jvj1gj1djg4");
        sqlSession.commit();
        servers = serverDao.getAll();
        System.out.println(servers);

        // 插入一条
        Server newServer = new Server();
        newServer.setPort(9999);
        newServer.setToken("xxxxx");
        newServer.setCreateTime(new Date());
        serverDao.insert(newServer);
        sqlSession.commit();
        servers = serverDao.getAll();
        System.out.println(servers);

        sqlSession.close();
    }

    private SqlSession getSqlSession() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session=sqlSessionFactory.openSession();

        return session;
    }
}
