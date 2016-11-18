package `in`.shpt.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by poovarasanv on 14/11/16.
 */

class BannerModel : Parcelable {
    var productId: Int = 0
    var productName: String
    var price: String
    var desc: String
    var image: String


    constructor(productId: Int, productName: String, price: String, desc: String, image: String) {
        this.productId = productId
        this.productName = productName
        this.price = price
        this.desc = desc
        this.image = image
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.productId)
        dest.writeString(this.productName)
        dest.writeString(this.price)
        dest.writeString(this.desc)
        dest.writeString(this.image)
    }

    protected constructor(`in`: Parcel) {
        this.productId = `in`.readInt()
        this.productName = `in`.readString()
        this.price = `in`.readString()
        this.desc = `in`.readString()
        this.image = `in`.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<BannerModel> = object : Parcelable.Creator<BannerModel> {
            override fun createFromParcel(source: Parcel): BannerModel {
                return BannerModel(source)
            }

            override fun newArray(size: Int): Array<BannerModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
