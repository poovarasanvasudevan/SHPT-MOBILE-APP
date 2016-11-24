package `in`.shpt.ext

import `in`.shpt.config.Config
import `in`.shpt.pref.Prefs
import `in`.shpt.rest.API
import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.IOException


/**
 * Created by poovarasanv on 14/11/16.
 */


fun Context.getAdapter(): API {

    if (Prefs.with(this).contains(Config.COOKIE)) {

        Log.i("cookies", Prefs.with(this).read(Config.COOKIE))

        val client = OkHttpClient.Builder()
                .addInterceptor(CookieInterceptor(Prefs.with(this).read(Config.COOKIE)))
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE)
                .client(client)
                .build()

        return retrofit.create(API::class.java!!)

    } else {

        val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE)
                .build()

        return retrofit.create(API::class.java!!)

    }
}

class CookieInterceptor(cookie: String) : Interceptor {

    var cookies: String = cookie
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()


        builder.addHeader("Cookie", "PHPSESSID=" + cookies + "; display=list")
        builder.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 OPR/39.0.2256.71")
        return chain.proceed(builder.build())
    }

}


fun Context.getuserprofile(): JSONObject? {
    return JSONObject(getAdapter().getUserDetails(Config.USERDETAILS).execute().body().string())
}


fun Context.saveUserDetails(
        firstname: String,
        lastname: String,
        email: String,
        telephone: String,
        fax: String
): JSONObject? {
    return JSONObject(getAdapter().saveUserDetails(firstname, lastname, email, telephone, fax, Config.USERDETAILS).execute().body().string())
}

fun Context.getAllCategories(): JSONObject? {
    return JSONObject(getAdapter().getUserDetails(Config.CATEGORIES).execute().body().string())
}

fun Context.getBanner(): JSONObject? {
    return JSONObject(getAdapter().getBanner(Config.BANNER).execute().body().string())
}

fun Context.getCart(): JSONArray? {
    return JSONArray(getAdapter().getBanner(Config.CART).execute().body().string())
}

fun Context.getMyOrders(page: Int = 1): JSONArray? {
    return JSONArray(getAdapter().getMyOrders(Config.MYORDERS, page!!).execute().body().string())
}

fun Context.getOrderDetail(order_id: Int = 0): JSONObject? {
    return JSONObject(getAdapter().getOrderDetail(Config.ORDERDETAIL, order_id).execute().body().string())
}


fun Context.getAddress(): JSONObject? {
    return JSONObject(getAdapter().getAddress(Config.ADDRESSDETAIL).execute().body().string())
}


fun Context.editAddress(address_id: Int): JSONObject? {
    return JSONObject(getAdapter().editAddress(Config.EDITADDRESSDETAIL, address_id!!).execute().body().string())
}

fun Context.getState(state_id: Int): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getState(Config.STATEURL, state_id).execute().body().string())
}


fun Context.deleteAddress(address_id: Int): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().deleteAddress(Config.DELETEADDRESS, address_id).execute().body().string())
}

fun Context.addAddress(): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().addAddress(Config.ADDADDRESS).execute().body().string())
}

fun Context.getWishList(): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getWishList(Config.WISLIST).execute().body().string())
}


fun Context.getProductDetail(product_id: Int): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getProductDetail(Config.PRODUCTDETAIL, product_id).execute().body().string())
}


fun Context.getCategortProducts(category: String, page: String): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getCategotyProduct(Config.CATEGORYPRODUCTDETAIL, category, page).execute().body().string())
}


fun Context.getFullCart(): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getFullCart(Config.CHECKOUTCART).execute().body().string())
}

fun Context.getGiftVoucherPage(): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().getGiftVoucherPage(Config.VOUCHERPURCHASE).execute().body().string())
}



fun Context.removeFromCart(product_id: String): JSONObject? {
    // Log.i("Hello", getAdapter().editAddress(Config.STATEURL, state_id!!).execute().body().string())
    return JSONObject(getAdapter().removeFromCart(Config.CHECKOUTCART,product_id).execute().body().string())
}



fun Context.updateAddress(
        address_id: Int,
        firstname: String,
        lastname: String,
        company: String,
        address_1: String,
        address_2: String,
        city: String,
        postcode: String,
        country_id: Int,
        zone_id: Int,
        default: Int
): JSONObject {
    // Log.i("Hello", getAdapter().saveAddress(Config.UPDATEADDRESS, address_id, firstname, lastname, company, address_1, address_2, city, postcode, country_id, zone_id, default).execute().body().string())
    return JSONObject(getAdapter().saveAddress(Config.UPDATEADDRESS, address_id, firstname, lastname, company, address_1, address_2, city, postcode, country_id, zone_id, default).execute().body().string())

}

fun Context.addNewAddress(
        firstname: String,
        lastname: String,
        company: String,
        address_1: String,
        address_2: String,
        city: String,
        postcode: String,
        country_id: Int,
        zone_id: Int,
        default: Int
): JSONObject {
    //Log.i("Hello", getAdapter().saveAddress(Config.UPDATEADDRESS, address_id, firstname, lastname, company, address_1, address_2, city, postcode, country_id, zone_id, default).execute().body().string())
    return JSONObject(getAdapter().addNewAddress(Config.ADDADDRESS, firstname, lastname, company, address_1, address_2, city, postcode, country_id, zone_id, default).execute().body().string())

}


fun Context.addGiftVoucher(
        to_name: String,
        to_email: String,
        from_name: String,
        from_email: String,
        voucher_theme_id: String,
        message: String,
        amount: String
): JSONObject {


    var response = getAdapter().addGiftVoucher(
            Config.VOUCHERPURCHASE,
            to_name,
            to_email,
            from_name,
            from_email,
            voucher_theme_id,
            message,
            amount,
            "1"
    ).execute().body().string()

    return JSONObject(response)
}