package com.parsons.test;

import com.parsons.mapper.TopicMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class TopicTest {
    @Test
    public void testSelectAllTopics() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
        topicMapper.selectAllTopics().forEach(System.out::println);
        sqlSession.close();
    }
}
