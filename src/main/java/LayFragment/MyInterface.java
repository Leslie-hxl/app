package LayFragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyInterface {
    //http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1
    //?stage_id=1&limit=20&page=1
    @GET("dish_list.php")
    Call<javaBean> getintent(@Query("stage_id")int stage_id,@Query("limit")int limit,@Query("page")int page);
}
