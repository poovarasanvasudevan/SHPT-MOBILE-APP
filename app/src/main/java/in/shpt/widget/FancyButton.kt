package `in`.shpt.widget

/**
 * Created by poovarasanv on 14/11/16.
 */

import `in`.shpt.R
import `in`.shpt.utils.Utils
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

class FancyButton : LinearLayout {

    private var mContext: Context? = null

    // # Background Attributes
    private var mDefaultBackgroundColor = Color.BLACK
    private var mFocusBackgroundColor = 0
    private var mDisabledBackgroundColor = Color.parseColor("#f6f7f9")
    private var mDisabledTextColor = Color.parseColor("#bec2c9")
    private var mDisabledBorderColor = Color.parseColor("#dddfe2")

    // # Text Attributes
    private var mDefaultTextColor = Color.WHITE
    private var mDefaultIconColor = Color.WHITE
    private val mTextPosition = 1
    private var mDefaultTextSize = Utils.spToPx(context, 15f)
    private var mDefaultTextGravity = 0x11 // Gravity.CENTER
    private var mText: String? = null

    // # Icon Attributes
    private var mIconResource: Drawable? = null
    private var mFontIconSize = Utils.spToPx(context, 15f)
    private var mFontIcon: String? = null
    private var mIconPosition = 1

    private var mIconPaddingLeft = 10
    private var mIconPaddingRight = 10
    private var mIconPaddingTop = 0
    private var mIconPaddingBottom = 0


    private var mBorderColor = Color.TRANSPARENT
    private var mBorderWidth = 0

    private var mRadius = 0

    private var mTextAllCaps = false

    private var mTextTypeFace: Typeface? = null
    private var mIconTypeFace: Typeface? = null

    private val mDefaultIconFont = "fontawesome.ttf"
    private val mDefaultTextFont = "robotoregular.ttf"

    /**
     * Return Icon of the FancyButton

     * @return ImageView Object
     */
    var iconImageObject: ImageView? = null
        private set
    /**
     * Return Icon Font of the FancyButton

     * @return TextView Object
     */
    var iconFontObject: TextView? = null
        private set
    /**
     * Return TextView Object of the FancyButton

     * @return TextView Object
     */
    var textViewObject: TextView? = null
        private set

    private var mGhost = false // Default is a solid button !
    private var mUseSystemFont = false // Default is using robotoregular.ttf
    private val useRippleEffect = true

    /**
     * Default constructor

     * @param context : Context
     */
    constructor(context: Context) : super(context) {
        this.mContext = context

        mTextTypeFace = Utils.findFont(mContext!!, mDefaultTextFont, null!!)
        mIconTypeFace = Utils.findFont(mContext!!, mDefaultIconFont, null!!)
        initializeFancyButton()
    }


    /**
     * Default constructor called from Layouts

     * @param context : Context
     * *
     * @param attrs   : Attributes Array
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context

        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.FancyButton, 0, 0)
        initAttributesArray(attrs, attrsArray)
        attrsArray.recycle()

        initializeFancyButton()

    }

    /**
     * Initialize Button dependencies
     * - Initialize Button Container : The LinearLayout
     * - Initialize Button TextView
     * - Initialize Button Icon
     * - Initialize Button Font Icon
     */
    private fun initializeFancyButton() {

        initializeButtonContainer()

        textViewObject = setupTextView()
        iconImageObject = setupIconView()
        iconFontObject = setupFontIconView()

        val iconIndex: Int
        val textIndex: Int
        val view1: View
        val view2: View

        if (iconImageObject == null && iconFontObject == null && textViewObject == null) {
            val tempTextView = Button(mContext)
            tempTextView.text = "Fancy Button"
            this.addView(tempTextView)

        } else {
            this.removeAllViews()
            setupBackground()

            val views = ArrayList<View>()

            if (mIconPosition == POSITION_LEFT || mIconPosition == POSITION_TOP) {

                if (iconImageObject != null) {
                    views.add(iconImageObject!!)
                }

                if (iconFontObject != null) {
                    views.add(iconFontObject!!)
                }
                if (textViewObject != null) {
                    views.add(textViewObject!!)
                }

            } else {
                if (textViewObject != null) {
                    views.add(textViewObject!!)
                }

                if (iconImageObject != null) {
                    views.add(iconImageObject!!)
                }

                if (iconFontObject != null) {
                    views.add(iconFontObject!!)
                }

            }

            for (view in views) {
                this.addView(view)
            }
        }
    }

    /**
     * Setup Text View

     * @return : TextView
     */
    private fun setupTextView(): TextView? {
        if (mText != null) {
            val textView = TextView(mContext)
            textView.text = mText
            textView.gravity = mDefaultTextGravity
            textView.setTextColor(if (isEnabled) mDefaultTextColor else mDisabledTextColor)
            textView.textSize = Utils.pxToSp(context, mDefaultTextSize.toFloat()).toFloat()

            textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            if (!isInEditMode && mTextTypeFace != null && !mUseSystemFont) {
                textView.typeface = mTextTypeFace
            }
            return textView
        }
        return null
    }

    /**
     * Setup Font Icon View

     * @return : TextView
     */
    private fun setupFontIconView(): TextView? {

        if (mFontIcon != null) {
            val fontIconView = TextView(mContext)
            fontIconView.setTextColor(if (isEnabled) mDefaultIconColor else mDisabledTextColor)

            val iconTextViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            iconTextViewParams.rightMargin = mIconPaddingRight
            iconTextViewParams.leftMargin = mIconPaddingLeft
            iconTextViewParams.topMargin = mIconPaddingTop
            iconTextViewParams.bottomMargin = mIconPaddingBottom

            if (textViewObject != null) {

                if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM) {
                    iconTextViewParams.gravity = Gravity.CENTER
                    fontIconView.gravity = Gravity.CENTER
                } else {
                    fontIconView.gravity = Gravity.CENTER_VERTICAL
                    iconTextViewParams.gravity = Gravity.CENTER_VERTICAL
                }
            } else {
                iconTextViewParams.gravity = Gravity.CENTER
                fontIconView.gravity = Gravity.CENTER_VERTICAL
            }


            fontIconView.layoutParams = iconTextViewParams
            if (!isInEditMode) {
                fontIconView.textSize = Utils.pxToSp(context, mFontIconSize.toFloat()).toFloat()
                fontIconView.text = mFontIcon
                fontIconView.typeface = mIconTypeFace
            } else {
                fontIconView.textSize = Utils.pxToSp(context, mFontIconSize.toFloat()).toFloat()
                fontIconView.text = "O"
            }
            return fontIconView
        }
        return null
    }

    /**
     * Text Icon resource view

     * @return : ImageView
     */
    private fun setupIconView(): ImageView? {
        if (mIconResource != null) {
            val iconView = ImageView(mContext)
            iconView.setImageDrawable(mIconResource)
            iconView.setPadding(mIconPaddingLeft, mIconPaddingTop, mIconPaddingRight, mIconPaddingBottom)

            val iconViewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            if (textViewObject != null) {
                if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM)
                    iconViewParams.gravity = Gravity.CENTER
                else
                    iconViewParams.gravity = Gravity.START

                iconViewParams.rightMargin = 10
                iconViewParams.leftMargin = 10
            } else {
                iconViewParams.gravity = Gravity.CENTER_VERTICAL
            }
            iconView.layoutParams = iconViewParams

            return iconView
        }
        return null
    }

    /**
     * Initialize Attributes arrays

     * @param attrs
     * *
     * @param attrsArray : Attributes array
     */
    private fun initAttributesArray(attrs: AttributeSet, attrsArray: TypedArray) {

        mDefaultBackgroundColor = attrsArray.getColor(R.styleable.FancyButton_fb_defaultColor, mDefaultBackgroundColor)
        mFocusBackgroundColor = attrsArray.getColor(R.styleable.FancyButton_fb_focusColor, mFocusBackgroundColor)
        mDisabledBackgroundColor = attrsArray.getColor(R.styleable.FancyButton_fb_disabledColor, mDisabledBackgroundColor)

        this.isEnabled = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "enabled", true)

        mDisabledTextColor = attrsArray.getColor(R.styleable.FancyButton_fb_disabledTextColor, mDisabledTextColor)
        mDisabledBorderColor = attrsArray.getColor(R.styleable.FancyButton_fb_disabledBorderColor, mDisabledBorderColor)
        mDefaultTextColor = attrsArray.getColor(R.styleable.FancyButton_fb_textColor, mDefaultTextColor)
        // if default color is set then the icon's color is the same (the default for icon's color)
        mDefaultIconColor = attrsArray.getColor(R.styleable.FancyButton_fb_iconColor, mDefaultTextColor)
        mDefaultTextSize = attrsArray.getDimension(R.styleable.FancyButton_fb_textSize, mDefaultTextSize.toFloat()).toInt()
        mDefaultTextGravity = attrsArray.getInt(R.styleable.FancyButton_fb_textGravity, mDefaultTextGravity)

        mBorderColor = attrsArray.getColor(R.styleable.FancyButton_fb_borderColor, mBorderColor)
        mBorderWidth = attrsArray.getDimension(R.styleable.FancyButton_fb_borderWidth, mBorderWidth.toFloat()).toInt()

        mRadius = attrsArray.getDimension(R.styleable.FancyButton_fb_radius, mRadius.toFloat()).toInt()
        mFontIconSize = attrsArray.getDimension(R.styleable.FancyButton_fb_fontIconSize, mFontIconSize.toFloat()).toInt()

        mIconPaddingLeft = attrsArray.getDimension(R.styleable.FancyButton_fb_iconPaddingLeft, mIconPaddingLeft.toFloat()).toInt()
        mIconPaddingRight = attrsArray.getDimension(R.styleable.FancyButton_fb_iconPaddingRight, mIconPaddingRight.toFloat()).toInt()
        mIconPaddingTop = attrsArray.getDimension(R.styleable.FancyButton_fb_iconPaddingTop, mIconPaddingTop.toFloat()).toInt()
        mIconPaddingBottom = attrsArray.getDimension(R.styleable.FancyButton_fb_iconPaddingBottom, mIconPaddingBottom.toFloat()).toInt()

        mTextAllCaps = attrsArray.getBoolean(R.styleable.FancyButton_fb_textAllCaps, false)

        mGhost = attrsArray.getBoolean(R.styleable.FancyButton_fb_ghost, mGhost)
        mUseSystemFont = attrsArray.getBoolean(R.styleable.FancyButton_fb_useSystemFont, mUseSystemFont)

        val text = attrsArray.getString(R.styleable.FancyButton_fb_text)
        mIconPosition = attrsArray.getInt(R.styleable.FancyButton_fb_iconPosition, mIconPosition)

        val fontIcon = attrsArray.getString(R.styleable.FancyButton_fb_fontIconResource)

        val iconFontFamily = attrsArray.getString(R.styleable.FancyButton_fb_iconFont)
        val textFontFamily = attrsArray.getString(R.styleable.FancyButton_fb_textFont)

        val icon: Drawable? = null
        try {
            mIconResource = attrsArray.getDrawable(R.styleable.FancyButton_fb_iconResource)

        } catch (e: Exception) {
            mIconResource = null
        }

        if (fontIcon != null)
            mFontIcon = fontIcon

        if (text != null)
            mText = if (mTextAllCaps) text.toUpperCase() else text

        if (!isInEditMode) {
            if (iconFontFamily != null) {
                mIconTypeFace = Utils.findFont(mContext!!, iconFontFamily, mDefaultIconFont)
            } else {
                mIconTypeFace = Utils.findFont(mContext!!, mDefaultIconFont, null!!)
            }

            if (textFontFamily != null) {
                mTextTypeFace = Utils.findFont(mContext!!, textFontFamily, mDefaultTextFont)
            } else {
                mTextTypeFace = Utils.findFont(mContext!!, mDefaultTextFont, null!!)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getRippleDrawable(defaultDrawable: Drawable, focusDrawable: Drawable, disabledDrawable: Drawable): Drawable {
        if (!isEnabled) {
            return disabledDrawable
        } else {
            return RippleDrawable(ColorStateList.valueOf(mFocusBackgroundColor), defaultDrawable, focusDrawable)
        }

    }


    @SuppressLint("NewApi")
    private fun setupBackground() {


        // Default Drawable
        val defaultDrawable = GradientDrawable()
        defaultDrawable.cornerRadius = mRadius.toFloat()
        if (mGhost) {
            defaultDrawable.setColor(resources.getColor(android.R.color.transparent)) // Hollow Background
        } else {
            defaultDrawable.setColor(mDefaultBackgroundColor)
        }

        //Focus Drawable
        val focusDrawable = GradientDrawable()
        focusDrawable.cornerRadius = mRadius.toFloat()
        focusDrawable.setColor(mFocusBackgroundColor)

        // Disabled Drawable
        val disabledDrawable = GradientDrawable()
        disabledDrawable.cornerRadius = mRadius.toFloat()
        disabledDrawable.setColor(mDisabledBackgroundColor)
        disabledDrawable.setStroke(mBorderWidth, mDisabledBorderColor)

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor)
        }

        // Handle disabled border color
        if (!isEnabled) {
            defaultDrawable.setStroke(mBorderWidth, mDisabledBorderColor)
            if (mGhost) {
                disabledDrawable.setColor(resources.getColor(android.R.color.transparent))
            }
        }


        if (useRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            this.background = getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable)

        } else {

            val states = StateListDrawable()

            // Focus/Pressed Drawable
            val drawable2 = GradientDrawable()
            drawable2.cornerRadius = mRadius.toFloat()
            if (mGhost) {
                drawable2.setColor(resources.getColor(android.R.color.transparent)) // No focus color
            } else {
                drawable2.setColor(mFocusBackgroundColor)
            }

            // Handle Button Border
            if (mBorderColor != 0) {
                if (mGhost) {
                    drawable2.setStroke(mBorderWidth, mFocusBackgroundColor) // Border is the main part of button now
                } else {
                    drawable2.setStroke(mBorderWidth, mBorderColor)
                }
            }

            if (!isEnabled) {
                if (mGhost) {
                    drawable2.setStroke(mBorderWidth, mDisabledBorderColor)
                } else {
                    drawable2.setStroke(mBorderWidth, mDisabledBorderColor)
                }
            }

            if (mFocusBackgroundColor != 0) {
                states.addState(intArrayOf(android.R.attr.state_pressed), drawable2)
                states.addState(intArrayOf(android.R.attr.state_focused), drawable2)
                states.addState(intArrayOf(-android.R.attr.state_enabled), disabledDrawable)
            }

            states.addState(intArrayOf(), defaultDrawable)


            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states)
            } else {
                this.background = states
            }

        }


    }


    /**
     * Initialize button container
     */
    private fun initializeButtonContainer() {

        if (mIconPosition == POSITION_TOP || mIconPosition == POSITION_BOTTOM) {
            this.orientation = LinearLayout.VERTICAL
        } else {
            this.orientation = LinearLayout.HORIZONTAL
        }
        val containerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        this.layoutParams = containerParams
        this.gravity = Gravity.CENTER
        this.isClickable = true
        this.isFocusable = true
        if (mIconResource == null && mFontIcon == null && paddingLeft == 0 && paddingRight == 0 && paddingTop == 0 && paddingBottom == 0) {
            this.setPadding(20, 20, 20, 20)
        }
    }

    /**
     * Set Text of the button

     * @param text : Text
     */
    fun setText(text: String) {
        var text = text
        text = if (mTextAllCaps) text.toUpperCase() else text
        this.mText = text
        if (textViewObject == null)
            initializeFancyButton()
        else
            textViewObject!!.text = text
    }

    /**
     * Set the capitalization of text

     * @param textAllCaps : is text to be capitalized
     */
    fun setTextAllCaps(textAllCaps: Boolean) {
        this.mTextAllCaps = textAllCaps
        setText(mText!!)
    }

    /**
     * Set the color of text

     * @param color : Color
     * *              use Color.parse('#code')
     */
    fun setTextColor(color: Int) {
        this.mDefaultTextColor = color
        if (textViewObject == null)
            initializeFancyButton()
        else
            textViewObject!!.setTextColor(color)

    }

    /**
     * Setting the icon's color independent of the text color

     * @param color : Color
     */
    fun setIconColor(color: Int) {
        if (iconFontObject != null) {
            iconFontObject!!.setTextColor(color)
        }
    }

    /**
     * Set Background color of the button

     * @param color : use Color.parse('#code')
     */
    override fun setBackgroundColor(color: Int) {
        this.mDefaultBackgroundColor = color
        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }
    }

    /**
     * Set Focus color of the button

     * @param color : use Color.parse('#code')
     */
    fun setFocusBackgroundColor(color: Int) {
        this.mFocusBackgroundColor = color
        if (iconImageObject != null || iconFontObject != null || textViewObject != null)
            this.setupBackground()

    }

    /**
     * Set Disabled state color of the button

     * @param color : use Color.parse('#code')
     */
    fun setDisableBackgroundColor(color: Int) {
        this.mDisabledBackgroundColor = color
        if (iconImageObject != null || iconFontObject != null || textViewObject != null)
            this.setupBackground()

    }

    /**
     * Set Disabled state color of the button text

     * @param color : use Color.parse('#code')
     */
    fun setDisableTextColor(color: Int) {
        this.mDisabledTextColor = color
        if (textViewObject == null)
            initializeFancyButton()
        else if (!isEnabled)
            textViewObject!!.setTextColor(color)

    }

    /**
     * Set Disabled state color of the button border

     * @param color : use Color.parse('#code')
     */
    fun setDisableBorderColor(color: Int) {
        this.mDisabledBorderColor = color
        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }

    }

    /**
     * Set the size of Text in sp

     * @param textSize : Text Size
     */
    fun setTextSize(textSize: Int) {
        this.mDefaultTextSize = Utils.spToPx(context, textSize.toFloat())
        if (textViewObject != null)
            textViewObject!!.textSize = textSize.toFloat()
    }

    /**
     * Set the gravity of Text

     * @param gravity : Text Gravity
     */

    fun setTextGravity(gravity: Int) {
        this.mDefaultTextGravity = gravity
        if (textViewObject != null) {
            textViewObject!!.gravity = gravity
        }
    }

    /**
     * Set Padding for mIconView and mFontIconSize

     * @param paddingLeft   : Padding Left
     * *
     * @param paddingTop    : Padding Top
     * *
     * @param paddingRight  : Padding Right
     * *
     * @param paddingBottom : Padding Bottom
     */
    fun setIconPadding(paddingLeft: Int, paddingTop: Int, paddingRight: Int, paddingBottom: Int) {
        this.mIconPaddingLeft = paddingLeft
        this.mIconPaddingTop = paddingTop
        this.mIconPaddingRight = paddingRight
        this.mIconPaddingBottom = paddingBottom
        if (iconImageObject != null) {
            iconImageObject!!.setPadding(this.mIconPaddingLeft, this.mIconPaddingTop, this.mIconPaddingRight, this.mIconPaddingBottom)
        }
        if (iconFontObject != null) {
            iconFontObject!!.setPadding(this.mIconPaddingLeft, this.mIconPaddingTop, this.mIconPaddingRight, this.mIconPaddingBottom)
        }
    }

    /**
     * Set an icon from resources to the button

     * @param drawable : Int resource
     */
    fun setIconResource(drawable: Int) {
        this.mIconResource = mContext!!.resources.getDrawable(drawable)
        if (iconImageObject == null || iconFontObject != null) {
            iconFontObject = null
            initializeFancyButton()
        } else
            iconImageObject!!.setImageDrawable(mIconResource)
    }

    /**
     * Set a drawable to the button

     * @param drawable : Drawable resource
     */
    fun setIconResource(drawable: Drawable) {
        this.mIconResource = drawable
        if (iconImageObject == null || iconFontObject != null) {
            iconFontObject = null
            initializeFancyButton()
        } else
            iconImageObject!!.setImageDrawable(mIconResource)
    }

    /**
     * Set a font icon to the button (eg FFontAwesome or Entypo...)

     * @param icon : Icon value eg : \uf082
     */
    fun setIconResource(icon: String) {
        this.mFontIcon = icon
        if (iconFontObject == null) {
            iconImageObject = null
            initializeFancyButton()
        } else
            iconFontObject!!.text = icon
    }

    /**
     * Set Icon size of the button (for only font icons) in sp

     * @param iconSize : Icon Size
     */
    fun setFontIconSize(iconSize: Int) {
        this.mFontIconSize = Utils.spToPx(context, iconSize.toFloat())
        if (iconFontObject != null)
            iconFontObject!!.textSize = iconSize.toFloat()
    }

    /**
     * Set Icon Position
     * Use the global variables (FancyButton.POSITION_LEFT, FancyButton.POSITION_RIGHT, FancyButton.POSITION_TOP, FancyButton.POSITION_BOTTOM)

     * @param position : Position
     */
    fun setIconPosition(position: Int) {
        if (position > 0 && position < 5)
            mIconPosition = position
        else
            mIconPosition = POSITION_LEFT

        initializeFancyButton()
    }

    /**
     * Set color of the button border

     * @param color : Color
     * *              use Color.parse('#code')
     */
    fun setBorderColor(color: Int) {
        this.mBorderColor = color
        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }
    }

    /**
     * Set Width of the button

     * @param width : Width
     */
    fun setBorderWidth(width: Int) {
        this.mBorderWidth = width
        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }
    }

    /**
     * Set Border Radius of the button

     * @param radius : Radius
     */
    fun setRadius(radius: Int) {
        this.mRadius = radius
        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }
    }

    /**
     * Set custom font for button Text

     * @param fontName : Font Name
     * *                 Place your text fonts in assets
     */
    fun setCustomTextFont(fontName: String) {
        mTextTypeFace = Utils.findFont(mContext!!, fontName, mDefaultTextFont)

        if (textViewObject == null)
            initializeFancyButton()
        else
            textViewObject!!.typeface = mTextTypeFace

    }

    /**
     * Set Custom font for button icon

     * @param fontName : Font Name
     * *                 Place your icon fonts in assets
     */
    fun setCustomIconFont(fontName: String) {

        mIconTypeFace = Utils.findFont(mContext!!, fontName, mDefaultIconFont)

        if (iconFontObject == null)
            initializeFancyButton()
        else
            iconFontObject!!.typeface = mIconTypeFace

    }

    /**
     * Setting the button to have hollow or solid shape

     * @param ghost
     */
    fun setGhost(ghost: Boolean) {
        this.mGhost = ghost

        if (iconImageObject != null || iconFontObject != null || textViewObject != null) {
            this.setupBackground()
        }

    }

    /**
     * If enabled, the button title will ignore its custom font and use the default system font

     * @param status : true || false
     */
    fun setUsingSystemFont(status: Boolean) {
        this.mUseSystemFont = status
    }

    /**
     * Return Text of the button

     * @return Text
     */
    val text: CharSequence
        get() {
            if (textViewObject != null)
                return textViewObject!!.text
            else
                return ""
        }

    companion object {

        /**
         * Tags to identify icon position
         */
        val POSITION_LEFT = 1
        val POSITION_RIGHT = 2
        val POSITION_TOP = 3
        val POSITION_BOTTOM = 4
    }

}
