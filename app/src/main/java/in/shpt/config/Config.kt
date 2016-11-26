package `in`.shpt.config

import `in`.shpt.ext.Theme

/**
 * Created by poovarasanv on 14/11/16.
 */

object Config {

    var BASE = "http://localhost:8080/"
    var LOGIN_PAGE = "$BASE?route=account/login"
    var USERDETAILS = "${BASE}index.php?route=account/edit&webapi=true";
    var CATEGORIES = "${BASE}index.php?route=api/mobile/categories&webapi=true"
    var BANNER = "${BASE}/index.php?route=api/mobile/banner&webapi=true"
    var CART = "${BASE}/index.php?route=api/mobile/mycart&webapi=true"
    var MYORDERS = "${BASE}/index.php?route=account/order&webapi=true"
    var ORDERDETAIL = "${BASE}/index.php?route=account/order/info&webapi=true"
    var ADDRESSDETAIL = "${BASE}/index.php?route=account/address&webapi=true"
    var EDITADDRESSDETAIL = "${BASE}/index.php?route=account/address/update&webapi=true"
    var STATEURL = "${BASE}/index.php?route=account/address/country"
    var UPDATEADDRESS = "${BASE}/index.php?route=account/address/update&webapi=true"
    var DELETEADDRESS = "${BASE}/index.php?route=account/address/delete&webapi=true"
    var ADDADDRESS = "${BASE}/index.php?route=account/address/insert&webapi=true"
    var WISLIST = "${BASE}/index.php?route=account/wishlist&webapi=true"
    var PRODUCTDETAIL = "${BASE}/index.php?route=product/product&webapi=true"
    var CATEGORYPRODUCTDETAIL = "${BASE}/index.php?route=product/category&webapi=true"
    var CHECKOUTCART = "${BASE}/index.php?route=checkout/cart&webapi=true"
    var VOUCHERPURCHASE = "${BASE}/index.php?route=account/voucher&webapi=true"
    var ADDTOCART = "${BASE}/index.php?route=checkout/cart/add"
    var PAYMENTADDRESSSTEP = "${BASE}/index.php?route=checkout/payment_address&webapi=true"
    var PAYMENTADDRESSSTEPVALIDATE = "${BASE}/index.php?route=checkout/payment_address/validate&webapi=true"

    var SHIPPINGADDRESSSTEP = "${BASE}/index.php?route=checkout/shipping_address&webapi=true"
    var SHIPPINGADDRESSSTEPVALIDATE = "${BASE}/index.php?route=checkout/shipping_address/validate&webapi=true"

    var SHIPPINGMETHODSTEP = "${BASE}/index.php?route=checkout/shipping_method&webapi=true"
    var SHIPPINGMETHODSTEPVALIDATE = "${BASE}/index.php?route=checkout/shipping_method/validate&webapi=true"

    var PAYMENTMETHODSTEP = "${BASE}/index.php?route=checkout/payment_method&webapi=true"
    var PAYMENTMETHODSTEPVALIDATE = "${BASE}/index.php?route=checkout/payment_method/validate&webapi=true"

    var CONFIRMORDER = "${BASE}/index.php?route=checkout/confirm&webapi=true"

    //var SHPTIMAGE = "http://localhost:8080/image/cache/data/2397_FC-550x550.jpg"

    var INSTAMOJO = "${BASE}/index.php?route=payment/instamojo/start"
    var INSTAMOJOMETHOD = "GET"
    var FSSNET = "${BASE}/index.php?route=payment/FssNet/paymentredirect"
    var FSSNETMETHOD = "POST"
    var COD = "${BASE}/index.php?route=payment/cod/confirm"
    var CODMETHOD = "${BASE}/index.php?route=payment/cod/confirm"

    var PAYMENTURL = "${BASE}/index.php?route=api/mobile/paymentconfirm&webapi=true"
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
}
