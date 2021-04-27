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
        product.setPrice(2147483647);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();

    }

    @SneakyThrows
    @Test
    void createNewProductPriceZero() {
        product.setPrice(0);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isFalse();
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
    void createNewProductPriceNo() {
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
        product.setPrice(888.63453);

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        productPrice = response.body().getPrice();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(productPrice.equals(888.63453));

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
        product.setTitle("HelB");


        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void createNewProductTitleLongString() {
        product.setTitle(faker.chuckNorris().fact());

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void createNewProductTitleSymbols() {
        product.setTitle("!@#!@#");

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void createNewProductTitleNumbers() {
        product.setTitle(faker.phoneNumber().cellPhone());

        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
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
