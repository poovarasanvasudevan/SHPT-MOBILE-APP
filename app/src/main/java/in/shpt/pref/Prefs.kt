package `in`.shpt.pref

/**
 * Created by poovarasanv on 14/11/16.
 */

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

/**
 * Created by grender on 7/05/16.
 */
class Prefs {

    private constructor(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        )
    }

    private constructor(context: Context, preferencesName: String) {
        sharedPreferences = context.applicationContext.getSharedPreferences(
                preferencesName,
                Context.MODE_PRIVATE
        )
    }

    // String related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun read(what: String): String {
        return sharedPreferences!!.getString(what, DEFAULT_STRING_VALUE)
    }

    /**
     * @param what
     * *
     * @param defaultString
     * *
     * @return Returns the stored value of 'what'
     */
    fun read(what: String, defaultString: String): String {
        return sharedPreferences!!.getString(what, defaultString)
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun write(where: String, what: String) {
        sharedPreferences!!.edit().putString(where, what).apply()
    }

    // int related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun readInt(what: String): Int {
        return sharedPreferences!!.getInt(what, DEFAULT_INT_VALUE)
    }

    /**
     * @param what
     * *
     * @param defaultInt
     * *
     * @return Returns the stored value of 'what'
     */
    fun readInt(what: String, defaultInt: Int): Int {
        return sharedPreferences!!.getInt(what, defaultInt)
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun writeInt(where: String, what: Int) {
        sharedPreferences!!.edit().putInt(where, what).apply()
    }

    // double related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun readDouble(what: String): Double {
        if (!contains(what))
            return DEFAULT_DOUBLE_VALUE
        return java.lang.Double.longBitsToDouble(readLong(what))
    }

    /**
     * @param what
     * *
     * @param defaultDouble
     * *
     * @return Returns the stored value of 'what'
     */
    fun readDouble(what: String, defaultDouble: Double): Double {
        if (!contains(what))
            return defaultDouble
        return java.lang.Double.longBitsToDouble(readLong(what))
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun writeDouble(where: String, what: Double) {
        writeLong(where, java.lang.Double.doubleToRawLongBits(what))
    }

    // float related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun readFloat(what: String): Float {
        return sharedPreferences!!.getFloat(what, DEFAULT_FLOAT_VALUE)
    }

    /**
     * @param what
     * *
     * @param defaultFloat
     * *
     * @return Returns the stored value of 'what'
     */
    fun readFloat(what: String, defaultFloat: Float): Float {
        return sharedPreferences!!.getFloat(what, defaultFloat)
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun writeFloat(where: String, what: Float) {
        sharedPreferences!!.edit().putFloat(where, what).apply()
    }

    // long related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun readLong(what: String): Long {
        return sharedPreferences!!.getLong(what, DEFAULT_LONG_VALUE)
    }

    /**
     * @param what
     * *
     * @param defaultLong
     * *
     * @return Returns the stored value of 'what'
     */
    fun readLong(what: String, defaultLong: Long): Long {
        return sharedPreferences!!.getLong(what, defaultLong)
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun writeLong(where: String, what: Long) {
        sharedPreferences!!.edit().putLong(where, what).apply()
    }

    // boolean related methods

    /**
     * @param what
     * *
     * @return Returns the stored value of 'what'
     */
    fun readBoolean(what: String): Boolean {
        return sharedPreferences!!.getBoolean(what, DEFAULT_BOOLEAN_VALUE)
    }

    /**
     * @param what
     * *
     * @param defaultBoolean
     * *
     * @return Returns the stored value of 'what'
     */
    fun readBoolean(what: String, defaultBoolean: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(what, defaultBoolean)
    }

    /**
     * @param where
     * *
     * @param what
     */
    fun writeBoolean(where: String, what: Boolean) {
        sharedPreferences!!.edit().putBoolean(where, what).apply()
    }

    // String set methods

    /**
     * @param key
     * *
     * @param value
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun putStringSet(key: String, value: Set<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sharedPreferences!!.edit().putStringSet(key, value).apply()
        } else {
            // Workaround for pre-HC's lack of StringSets
            putOrderedStringSet(key, value)
        }
    }

    /**
     * @param key
     * *
     * @param value
     */
    fun putOrderedStringSet(key: String, value: Set<String>) {
        var stringSetLength = 0
        if (sharedPreferences!!.contains(key + LENGTH)) {
            // First read what the value was
            stringSetLength = readInt(key + LENGTH)
        }
        writeInt(key + LENGTH, value.size)
        var i = 0
        for (aValue in value) {
            write("$key[$i]", aValue)
            i++
        }
        while (i < stringSetLength) {
            // Remove any remaining values
            remove("$key[$i]")
            i++
        }
    }

    /**
     * @param key
     * *
     * @param defValue
     * *
     * @return Returns the String Set with HoneyComb compatibility
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun getStringSet(key: String, defValue: Set<String>): Set<String> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return sharedPreferences!!.getStringSet(key, defValue)
        } else {
            // Workaround for pre-HC's missing getStringSet
            return getOrderedStringSet(key, defValue)
        }
    }

    /**
     * @param key
     * *
     * @param defValue
     * *
     * @return Returns the ordered String Set
     */
    fun getOrderedStringSet(key: String, defValue: Set<String>): Set<String> {
        if (contains(key + LENGTH)) {
            val set = LinkedHashSet<String>()
            val stringSetLength = readInt(key + LENGTH)
            if (stringSetLength >= 0) {
                for (i in 0..stringSetLength - 1) {
                    set.add(read("$key[$i]"))
                }
            }
            return set
        }
        return defValue
    }

    // end related methods

    /**
     * @param key
     */
    fun remove(key: String) {
        if (contains(key + LENGTH)) {
            // Workaround for pre-HC's lack of StringSets
            val stringSetLength = readInt(key + LENGTH)
            if (stringSetLength >= 0) {
                sharedPreferences!!.edit().remove(key + LENGTH).apply()
                for (i in 0..stringSetLength - 1) {
                    sharedPreferences!!.edit().remove("$key[$i]").apply()
                }
            }
        }
        sharedPreferences!!.edit().remove(key)
    }

    /**
     * @param key
     * *
     * @return Returns if that key exists
     */
    operator fun contains(key: String): Boolean {
        return sharedPreferences!!.contains(key)
    }

    /**
     * Clear all the preferences
     */
    fun clear() {
        sharedPreferences!!.edit().clear().apply()
    }

    companion object {

        private var sharedPreferences: SharedPreferences? = null
        private var prefsInstance: Prefs? = null

        private val PREFERENCES_NAME = "shpt_app_preferences"
        private val LENGTH = "_length"

        private val DEFAULT_STRING_VALUE = ""
        private val DEFAULT_INT_VALUE = -1
        private val DEFAULT_DOUBLE_VALUE = -1.0
        private val DEFAULT_FLOAT_VALUE = -1f
        private val DEFAULT_LONG_VALUE = -1L
        private val DEFAULT_BOOLEAN_VALUE = false

        /**
         * @param context
         * *
         * @return Returns a 'Prefs' instance
         */
        fun with(context: Context): Prefs {
            if (prefsInstance == null) {
                prefsInstance = Prefs(context)
            }
            return prefsInstance as Prefs
        }

        /**
         * @param context
         * *
         * @param forceInstantiation
         * *
         * @return Returns a 'Prefs' instance
         */
        fun with(context: Context, forceInstantiation: Boolean): Prefs? {
            if (forceInstantiation) {
                prefsInstance = Prefs(context)
            }
            return prefsInstance
        }

        /**
         * @param context
         * *
         * @param preferencesName
         * *
         * @return Returns a 'Prefs' instance
         */
        fun with(context: Context, preferencesName: String): Prefs {
            if (prefsInstance == null) {
                prefsInstance = Prefs(context, preferencesName)
            }
            return prefsInstance as Prefs
        }

        /**
         * @param context
         * *
         * @param preferencesName
         * *
         * @param forceInstantiation
         * *
         * @return Returns a 'Prefs' instance
         */
        fun with(context: Context, preferencesName: String,
                 forceInstantiation: Boolean): Prefs? {
            if (forceInstantiation) {
                prefsInstance = Prefs(context, preferencesName)
            }
            return prefsInstance
        }
    }

}
