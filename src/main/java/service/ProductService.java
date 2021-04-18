package service;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import dto.Product;

public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @PUT("products")
    Call<Product> putProduct(@Body Product createProductRequest);

    @GET("products/{id}")
    Call<ResponseBody> getProduct(@Path("id") int id);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);
}
