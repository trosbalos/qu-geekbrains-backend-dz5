import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import enums.CategoryType;
import dto.Product;
import service.ProductService;
import util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductGetTest {

    Integer productId;
    Object productTitle;
    Object productCategoryTitle;
    Object productPrice;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    Product productUntil;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }
    @SneakyThrows
    @BeforeEach
    void setUp() {
        productUntil = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
        retrofit2.Response<Product> response =
                productService.createProduct(productUntil)
                        .execute();
        productId = response.body().getId();
        productTitle = response.body().getTitle();
        productPrice = response.body().getPrice();
        productCategoryTitle = response.body().getCategoryTitle();

    }



    @SneakyThrows
    @Test
    void getProduct() {

        retrofit2.Response<ResponseBody> response =
                productService.getProduct(productId)
                        .execute();

        assertThat(response.isSuccessful()).isTrue();
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
