package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.Products;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args)  {




        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products product8794 = productsMapper.selectByPrimaryKey(8794L);


        System.out.println(product8794.getTitle());

    }
}
