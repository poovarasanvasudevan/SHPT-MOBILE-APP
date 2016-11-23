package `in`.shpt.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by poovarasanv on 14/11/16.
 */

interface API {

    @GET
    fun getUserDetails(@Url url: String): Call<ResponseBody>


    @FormUrlEncoded
    @POST
    fun saveUserDetails(
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("fax") fax: String,
            @Url url: String
    ): Call<ResponseBody>

    @GET
    fun getAllCategories(@Url url: String): Call<ResponseBody>

    @GET
    fun getBanner(@Url url: String): Call<ResponseBody>


    @GET
    fun getCart(@Url url: String): Call<ResponseBody>

    @GET
    fun getMyOrders(@Url url: String, @Query("page") page: Int): Call<ResponseBody>

    @GET
    fun getOrderDetail(@Url url: String, @Query("order_id") order_id: Int): Call<ResponseBody>

    @GET
    fun getAddress(@Url url: String): Call<ResponseBody>


    @GET
    fun addAddress(@Url url: String): Call<ResponseBody>

    @GET
    fun editAddress(@Url url: String, @Query("address_id") address_id: Int): Call<ResponseBody>

    @GET
    fun getState(@Url url: String, @Query("country_id") country_id: Int): Call<ResponseBody>

    @GET
    fun getWishList(@Url url: String): Call<ResponseBody>

    @GET
    fun deleteAddress(@Url url: String, @Query("address_id") address_id: Int): Call<ResponseBody>

    @GET
    fun getProductDetail(@Url url: String, @Query("product_id") product_id: Int): Call<ResponseBody>

    @GET
    fun getCategotyProduct(@Url url: String, @Query("path") category: String, @Query("page") page: String): Call<ResponseBody>


    @FormUrlEncoded
    @POST
    fun saveAddress(
            @Url url: String,
            @Query("address_id") address_id: Int,
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("company") company: String,
            @Field("address_1") address_1: String,
            @Field("address_2") address_2: String,
            @Field("city") city: String,
            @Field("postcode") postcode: String,
            @Field("country_id") country_id: Int,
            @Field("zone_id") zone_id: Int,
            @Field("default") default: Int
    ): Call<ResponseBody>


    @FormUrlEncoded
    @POST
    fun addNewAddress(
            @Url url: String,
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("company") company: String,
            @Field("address_1") address_1: String,
            @Field("address_2") address_2: String,
            @Field("city") city: String,
            @Field("postcode") postcode: String,
            @Field("country_id") country_id: Int,
            @Field("zone_id") zone_id: Int,
            @Field("default") default: Int
    ): Call<ResponseBody>


    @GET
    fun getFullCart(@Url url: String): Call<ResponseBody>


    @GET
    fun getGiftVoucherPage(@Url url: String): Call<ResponseBody>


    @POST
    @FormUrlEncoded
    fun addGiftVoucher(
            @Url url: String,
            @Field("to_name") to_name: String,
            @Field("to_email") to_email: String,
            @Field("from_name") from_name: String,
            @Field("from_email") from_email: String,
            @Field("voucher_theme_id") voucher_theme_id: String,
            @Field("message") message: String,
            @Field("amount") amount: String,
            @Field("agree") agree: String
    ): Call<ResponseBody>
}
