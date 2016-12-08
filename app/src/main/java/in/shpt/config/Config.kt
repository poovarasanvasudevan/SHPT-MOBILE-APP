package `in`.shpt.config

import `in`.shpt.ext.Theme
import com.mcxiaoke.koi.HASH

/**
 * Created by poovarasanv on 14/11/16.
 */

object Config {

    var BASE = "http://localhost:8080/"
    val WEBAPI = "&webapi=" + HASH.md5("password")
    var LOGIN_PAGE = "$BASE?route=account/login"
    var USERDETAILS = "${BASE}index.php?route=account/edit" + WEBAPI;
    var CATEGORIES = "${BASE}index.php?route=api/mobile/categories" + WEBAPI
    var BANNER = "${BASE}/index.php?route=api/mobile/banner" + WEBAPI
    var CART = "${BASE}/index.php?route=api/mobile/mycart" + WEBAPI
    var MYORDERS = "${BASE}/index.php?route=account/order" + WEBAPI
    var ORDERDETAIL = "${BASE}/index.php?route=account/order/info" + WEBAPI
    var ADDRESSDETAIL = "${BASE}/index.php?route=account/address" + WEBAPI
    var EDITADDRESSDETAIL = "${BASE}/index.php?route=account/address/update" + WEBAPI
    var STATEURL = "${BASE}/index.php?route=account/address/country"
    var UPDATEADDRESS = "${BASE}/index.php?route=account/address/update" + WEBAPI
    var DELETEADDRESS = "${BASE}/index.php?route=account/address/delete" + WEBAPI
    var ADDADDRESS = "${BASE}/index.php?route=account/address/insert" + WEBAPI
    var WISLIST = "${BASE}/index.php?route=account/wishlist" + WEBAPI
    var PRODUCTDETAIL = "${BASE}/index.php?route=product/product" + WEBAPI
    var CATEGORYPRODUCTDETAIL = "${BASE}/index.php?route=product/category" + WEBAPI
    var CHECKOUTCART = "${BASE}/index.php?route=checkout/cart" + WEBAPI
    var VOUCHERPURCHASE = "${BASE}/index.php?route=account/voucher" + WEBAPI
    var ADDTOCART = "${BASE}/index.php?route=checkout/cart/add"
    var PAYMENTADDRESSSTEP = "${BASE}/index.php?route=checkout/payment_address" + WEBAPI
    var PAYMENTADDRESSSTEPVALIDATE = "${BASE}/index.php?route=checkout/payment_address/validate" + WEBAPI

    var SHIPPINGADDRESSSTEP = "${BASE}/index.php?route=checkout/shipping_address" + WEBAPI
    var SHIPPINGADDRESSSTEPVALIDATE = "${BASE}/index.php?route=checkout/shipping_address/validate" + WEBAPI

    var SHIPPINGMETHODSTEP = "${BASE}/index.php?route=checkout/shipping_method" + WEBAPI
    var SHIPPINGMETHODSTEPVALIDATE = "${BASE}/index.php?route=checkout/shipping_method/validate" + WEBAPI

    var PAYMENTMETHODSTEP = "${BASE}/index.php?route=checkout/payment_method" + WEBAPI
    var PAYMENTMETHODSTEPVALIDATE = "${BASE}/index.php?route=checkout/payment_method/validate" + WEBAPI

    var CONFIRMORDER = "${BASE}/index.php?route=checkout/confirm" + WEBAPI
    var SEARCH = "${BASE}/index.php?route=product/search/ajax" + WEBAPI
    val POPULAR_PRODUCT: String = "${BASE}/index.php?route=api/mobile/popular" + WEBAPI
    var SHPTIMAGE = "http://localhost:8080/image/cache/"

    var INSTAMOJO = "/instamojo/start"
    var INSTAMOJOMETHOD = "GET"
    var FSSNET = "${BASE}/index.php?route=payment/FssNet/paymentredirect"
    var FSSNETMETHOD = "POST"
    var COD = "${BASE}/index.php?route=payment/cod/confirm"
    var CODMETHOD = "${BASE}/index.php?route=payment/cod/confirm"

    var PAYMENTURL = "${BASE}/index.php?route=api/mobile/paymentconfirm" + WEBAPI
    var PAYMENT_CONFIRM = "checkout/success"


    var COOKIE = "login_shpt_cookie"
    var THEME = "my_theme"
    var DEFAULT_THEME = Theme.INDIGO
    var IMAGE_PATH = "${BASE}/image/"
    val DEFAULT_COUNTRY: String = "99"
    val BILLINGADDRESSID: String = "billing_address_id"
    val PAYMENTADDRESSID: String = "payment_address_id"
    val PAYMENTMETHOD: String = "payment_method"
    val SHIPPINGMETHOD: String = "shipping_method"

    //parse
    val WS_URL = "ws://10.0.2.2:4040/"
    val MY_APP_ID = "myAppId"
    var SERVER = "http://10.0.2.2:1337/parse/"
    var CLIENT_KEY = "2ead5328dda34e688816040a0e78948a"
    var LIMIT = 5


    //parseconfig
    val HOME_PRODUCT_BANNER = "HOME_PRODUCT_BANNER"
    val SLIDESHOW_INTERVAL: Int = 5500
    val RECENTPRODUCT_CLASS = "recent_searched"

}
