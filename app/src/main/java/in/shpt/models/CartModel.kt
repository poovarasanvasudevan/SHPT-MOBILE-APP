package `in`.shpt.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by poovarasanv on 15/11/16.
 */

class CartModel : Parcelable {
    /**

     * @return
     * * The key
     */
    /**

     * @param key
     * * The key
     */
    var key: String? = null
    /**

     * @return
     * * The productId
     */
    /**

     * @param productId
     * * The product_id
     */
    var productId: String? = null
    /**

     * @return
     * * The name
     */
    /**

     * @param name
     * * The name
     */
    var name: String? = null
    /**

     * @return
     * * The model
     */
    /**

     * @param model
     * * The model
     */
    var model: String? = null
    /**

     * @return
     * * The shipping
     */
    /**

     * @param shipping
     * * The shipping
     */
    var shipping: String? = null
    /**

     * @return
     * * The image
     */
    /**

     * @param image
     * * The image
     */
    var image: String? = null
    /**

     * @return
     * * The option
     */
    /**

     * @param option
     * * The option
     */
    var option: List<Any> = ArrayList()
    /**

     * @return
     * * The download
     */
    /**

     * @param download
     * * The download
     */
    var download: List<Any> = ArrayList()
    /**

     * @return
     * * The quantity
     */
    /**

     * @param quantity
     * * The quantity
     */
    var quantity: Int? = null
    /**

     * @return
     * * The minimum
     */
    /**

     * @param minimum
     * * The minimum
     */
    var minimum: String? = null
    /**

     * @return
     * * The subtract
     */
    /**

     * @param subtract
     * * The subtract
     */
    var subtract: String? = null
    /**

     * @return
     * * The stock
     */
    /**

     * @param stock
     * * The stock
     */
    var stock: Boolean? = null
    /**

     * @return
     * * The price
     */
    /**

     * @param price
     * * The price
     */
    var price: Int? = null
    /**

     * @return
     * * The total
     */
    /**

     * @param total
     * * The total
     */
    var total: Int? = null
    /**

     * @return
     * * The reward
     */
    /**

     * @param reward
     * * The reward
     */
    var reward: Int? = null
    /**

     * @return
     * * The points
     */
    /**

     * @param points
     * * The points
     */
    var points: Int? = null
    /**

     * @return
     * * The taxClassId
     */
    /**

     * @param taxClassId
     * * The tax_class_id
     */
    var taxClassId: String? = null
    /**

     * @return
     * * The weight
     */
    /**

     * @param weight
     * * The weight
     */
    var weight: Int? = null
    /**

     * @return
     * * The weightClassId
     */
    /**

     * @param weightClassId
     * * The weight_class_id
     */
    var weightClassId: String? = null
    /**

     * @return
     * * The length
     */
    /**

     * @param length
     * * The length
     */
    var length: String? = null
    /**

     * @return
     * * The width
     */
    /**

     * @param width
     * * The width
     */
    var width: String? = null
    /**

     * @return
     * * The height
     */
    /**

     * @param height
     * * The height
     */
    var height: String? = null
    /**

     * @return
     * * The lengthClassId
     */
    /**

     * @param lengthClassId
     * * The length_class_id
     */
    var lengthClassId: String? = null
    /**

     * @return
     * * The profileId
     */
    /**

     * @param profileId
     * * The profile_id
     */
    var profileId: Int? = null
    /**

     * @return
     * * The profileName
     */
    /**

     * @param profileName
     * * The profile_name
     */
    var profileName: String? = null
    /**

     * @return
     * * The recurring
     */
    /**

     * @param recurring
     * * The recurring
     */
    var recurring: Boolean? = null
    /**

     * @return
     * * The recurringFrequency
     */
    /**

     * @param recurringFrequency
     * * The recurring_frequency
     */
    var recurringFrequency: Int? = null
    /**

     * @return
     * * The recurringPrice
     */
    /**

     * @param recurringPrice
     * * The recurring_price
     */
    var recurringPrice: Int? = null
    /**

     * @return
     * * The recurringCycle
     */
    /**

     * @param recurringCycle
     * * The recurring_cycle
     */
    var recurringCycle: Int? = null
    /**

     * @return
     * * The recurringDuration
     */
    /**

     * @param recurringDuration
     * * The recurring_duration
     */
    var recurringDuration: Int? = null
    /**

     * @return
     * * The recurringTrial
     */
    /**

     * @param recurringTrial
     * * The recurring_trial
     */
    var recurringTrial: Int? = null
    /**

     * @return
     * * The recurringTrialFrequency
     */
    /**

     * @param recurringTrialFrequency
     * * The recurring_trial_frequency
     */
    var recurringTrialFrequency: Int? = null
    /**

     * @return
     * * The recurringTrialPrice
     */
    /**

     * @param recurringTrialPrice
     * * The recurring_trial_price
     */
    var recurringTrialPrice: Int? = null
    /**

     * @return
     * * The recurringTrialCycle
     */
    /**

     * @param recurringTrialCycle
     * * The recurring_trial_cycle
     */
    var recurringTrialCycle: Int? = null
    /**

     * @return
     * * The recurringTrialDuration
     */
    /**

     * @param recurringTrialDuration
     * * The recurring_trial_duration
     */
    var recurringTrialDuration: Int? = null


    constructor(key: String, productId: String, name: String, model: String, shipping: String, image: String, option: List<Any>, download: List<Any>, quantity: Int?, minimum: String, subtract: String, stock: Boolean?, price: Int?, total: Int?, reward: Int?, points: Int?, taxClassId: String, weight: Int?, weightClassId: String, length: String, width: String, height: String, lengthClassId: String, profileId: Int?, profileName: String, recurring: Boolean?, recurringFrequency: Int?, recurringPrice: Int?, recurringCycle: Int?, recurringDuration: Int?, recurringTrial: Int?, recurringTrialFrequency: Int?, recurringTrialPrice: Int?, recurringTrialCycle: Int?, recurringTrialDuration: Int?) {
        this.key = key
        this.productId = productId
        this.name = name
        this.model = model
        this.shipping = shipping
        this.image = image
        this.option = option
        this.download = download
        this.quantity = quantity
        this.minimum = minimum
        this.subtract = subtract
        this.stock = stock
        this.price = price
        this.total = total
        this.reward = reward
        this.points = points
        this.taxClassId = taxClassId
        this.weight = weight
        this.weightClassId = weightClassId
        this.length = length
        this.width = width
        this.height = height
        this.lengthClassId = lengthClassId
        this.profileId = profileId
        this.profileName = profileName
        this.recurring = recurring
        this.recurringFrequency = recurringFrequency
        this.recurringPrice = recurringPrice
        this.recurringCycle = recurringCycle
        this.recurringDuration = recurringDuration
        this.recurringTrial = recurringTrial
        this.recurringTrialFrequency = recurringTrialFrequency
        this.recurringTrialPrice = recurringTrialPrice
        this.recurringTrialCycle = recurringTrialCycle
        this.recurringTrialDuration = recurringTrialDuration
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.key)
        dest.writeString(this.productId)
        dest.writeString(this.name)
        dest.writeString(this.model)
        dest.writeString(this.shipping)
        dest.writeString(this.image)
        dest.writeList(this.option)
        dest.writeList(this.download)
        dest.writeValue(this.quantity)
        dest.writeString(this.minimum)
        dest.writeString(this.subtract)
        dest.writeValue(this.stock)
        dest.writeValue(this.price)
        dest.writeValue(this.total)
        dest.writeValue(this.reward)
        dest.writeValue(this.points)
        dest.writeString(this.taxClassId)
        dest.writeValue(this.weight)
        dest.writeString(this.weightClassId)
        dest.writeString(this.length)
        dest.writeString(this.width)
        dest.writeString(this.height)
        dest.writeString(this.lengthClassId)
        dest.writeValue(this.profileId)
        dest.writeString(this.profileName)
        dest.writeValue(this.recurring)
        dest.writeValue(this.recurringFrequency)
        dest.writeValue(this.recurringPrice)
        dest.writeValue(this.recurringCycle)
        dest.writeValue(this.recurringDuration)
        dest.writeValue(this.recurringTrial)
        dest.writeValue(this.recurringTrialFrequency)
        dest.writeValue(this.recurringTrialPrice)
        dest.writeValue(this.recurringTrialCycle)
        dest.writeValue(this.recurringTrialDuration)
    }

    protected constructor(`in`: Parcel) {
        this.key = `in`.readString()
        this.productId = `in`.readString()
        this.name = `in`.readString()
        this.model = `in`.readString()
        this.shipping = `in`.readString()
        this.image = `in`.readString()
        this.option = ArrayList<Any>()
        `in`.readList(this.option, Any::class.java.classLoader)
        this.download = ArrayList<Any>()
        `in`.readList(this.download, Any::class.java.classLoader)
        this.quantity = `in`.readValue(Int::class.java.classLoader) as Int
        this.minimum = `in`.readString()
        this.subtract = `in`.readString()
        this.stock = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.price = `in`.readValue(Int::class.java.classLoader) as Int
        this.total = `in`.readValue(Int::class.java.classLoader) as Int
        this.reward = `in`.readValue(Int::class.java.classLoader) as Int
        this.points = `in`.readValue(Int::class.java.classLoader) as Int
        this.taxClassId = `in`.readString()
        this.weight = `in`.readValue(Int::class.java.classLoader) as Int
        this.weightClassId = `in`.readString()
        this.length = `in`.readString()
        this.width = `in`.readString()
        this.height = `in`.readString()
        this.lengthClassId = `in`.readString()
        this.profileId = `in`.readValue(Int::class.java.classLoader) as Int
        this.profileName = `in`.readString()
        this.recurring = `in`.readValue(Boolean::class.java.classLoader) as Boolean
        this.recurringFrequency = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringPrice = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringCycle = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringDuration = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringTrial = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringTrialFrequency = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringTrialPrice = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringTrialCycle = `in`.readValue(Int::class.java.classLoader) as Int
        this.recurringTrialDuration = `in`.readValue(Int::class.java.classLoader) as Int
    }

    companion object {

        val CREATOR: Parcelable.Creator<CartModel> = object : Parcelable.Creator<CartModel> {
            override fun createFromParcel(source: Parcel): CartModel {
                return CartModel(source)
            }

            override fun newArray(size: Int): Array<CartModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
