package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private Object title;
    @JsonProperty("price")
    private Object price;
    @JsonProperty("categoryTitle")
    private Object categoryTitle;
}
