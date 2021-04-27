package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        String resource = "mybatisConfig.xml";
        SqlSessionFactory sqlSessionFactory;
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true);
        CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
        Categories categories = categoriesMapper.selectByPrimaryKey(1);
        System.out.println(categories.getTitle());
    }
}
