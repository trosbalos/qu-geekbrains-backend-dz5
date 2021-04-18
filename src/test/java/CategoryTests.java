import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import dto.Category;
import service.CategoryService;
import util.RetrofitUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static enums.CategoryType.ELECTRONIC;
import static enums.CategoryType.FOOD;

public class CategoryTests {
   static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    void getFoodCategoryPositiveTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(FOOD.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 1!").isEqualTo(1);
        assertThat(response.body().getTitle()).isEqualTo(FOOD.getTitle());
    }
    @Test
    void getElectronicsCategoryPositiveTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(ELECTRONIC.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 2!").isEqualTo(2);
        assertThat(response.body().getTitle()).isEqualTo(ELECTRONIC.getTitle());
    }

}
