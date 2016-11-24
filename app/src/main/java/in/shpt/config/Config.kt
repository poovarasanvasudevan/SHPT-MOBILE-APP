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
    //config
    var COOKIE = "login_shpt_cookie"
    var THEME = "my_theme"
    var DEFAULT_THEME = Theme.RED
    var IMAGE_PATH = "${BASE}/image/"
}
