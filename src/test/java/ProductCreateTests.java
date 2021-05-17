import com.github.javafaker.Faker;
import dto.Product;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Converter;
import enums.CategoryType;
import dto.ErrorBody;

import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.Products;
import service.ProductService;
import util.DbUtils;
import util.RetrofitUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductCreateTests {
    Integer productId;
    Object productPrice;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        ProductsMapper productsMapper = DbUtils.getProductsMapper();
    }

    @SneakyThrows
    @Test
    void createNewProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    //****************
    @SneakyThrows
    @Test
    void createNewProductPriceMaxINT() {
        int maxINT = 2147483647;
        product.setPrice(maxINT);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(productDb.getPrice().equals(maxINT));


    }

    @SneakyThrows
    @Test
    void createNewProductPriceZero() {
        int zeroPrice = 0;
        product.setPrice(zeroPrice);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(productDb.getPrice().equals(zeroPrice));

    }

    @SneakyThrows
    @Test
    void createNewProductPriceNull() {
        product.setPrice(null);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();

    }

    @SneakyThrows
    @Test
    void createNewProductNoPrice() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withTitle(faker.food().ingredient());

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();

    }

    @SneakyThrows
    @Test
    void createNewProductPriceDouble() {
        double PriceWithDot = 888.63453;
        product.setPrice(PriceWithDot);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        productPrice = response.body().getPrice();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(productPrice.equals(PriceWithDot));
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(productDb.getPrice().equals(PriceWithDot));

    }


    @SneakyThrows
    @Test
    void createNewProductPriceNegative() {
        product.setPrice(-1000);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();


    }

    @SneakyThrows
    @Test
    void createNewProductPriceString() {
        product.setPrice("price");

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        assertThat(response.isSuccessful()).isFalse();

    }

    @SneakyThrows
    @Test
    void createNewProductPriceSymb() {
        product.setPrice("@!@#");

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        assertThat(response.isSuccessful()).isFalse();
    }

    @SneakyThrows
    @Test
    void createNewProductTitleString() {
        String title = "HelB";
        product.setTitle(title);


        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(productDb.getTitle().equals(title));
    }

    @SneakyThrows
    @Test
    void createNewProductTitleLongString() {
        String title = faker.chuckNorris().fact();
        product.setTitle(title);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(product.getTitle().equals(productDb.getTitle()));

    }

    @SneakyThrows
    @Test
    void createNewProductTitleSymbols() {
        String title = "!@#!@#";
        product.setTitle(title);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(product.getPrice().equals(productDb.getTitle()));

    }

    @SneakyThrows
    @Test
    void createNewProductTitleNumbers() {
        String title = faker.phoneNumber().cellPhone();
        product.setTitle(title);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        ProductsMapper productsMapper = DbUtils.getProductsMapper();
        Products productDb = productsMapper.selectByPrimaryKey(Long.valueOf(productId));
        assertThat(product.getPrice().equals(productDb.getTitle()));


    }


    @SneakyThrows
    @Test
    void createNewProductTitleNull() {
        product.setTitle(null);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();
    }

    @SneakyThrows
    @Test
    void createNewProductTitleNo() {
        product = new Product()
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();

        assertThat(response.isSuccessful()).isFalse();
    }

 @SneakyThrows
    @Test
    void createNewProductCategoryFood() {

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();

        assertThat(response.isSuccessful()).isTrue();

    }

 @SneakyThrows
    @Test
    void createNewProductCategoryElectronic() {
     product = new Product()
             .withCategoryTitle(CategoryType.ELECTRONIC.getTitle())
             .withPrice((int) (Math.random() * 1000 + 1))
             .withTitle(faker.food().ingredient());

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();

        assertThat(response.isSuccessful()).isTrue();
    }


    //*********
    @SneakyThrows
    @Test
    void createNewProductNegativeTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product.withId(555))
                        .execute();
//        productId = Objects.requireNonNull(response.body()).getId();
        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Id must be null for new entity");
        }
    }

    @AfterEach
    void tearDown() {
        if (productId != null)
            try {

                retrofit2.Response<ResponseBody> response =
                        productService.deleteProduct(productId)
                                .execute();
                assertThat(response.isSuccessful()).isTrue();
            } catch (IOException e) {

            }
    }
}
