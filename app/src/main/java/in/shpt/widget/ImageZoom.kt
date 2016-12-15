package `in`.shpt.widget

/**
 * Created by poovarasanv on 15/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 15/12/16 at 8:33 AM
 */

import `in`.shpt.R
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.lang.ref.WeakReference

/**
 * Created by viventhraarao on 25/11/2016.
 */

class ImageZoom(activity: Activity) {
    private var zoomableView: View? = null
    private var parentOfZoomableView: ViewGroup? = null
    private var zoomableViewLP: ViewGroup.LayoutParams? = null
    private var zoomableViewFrameLP: FrameLayout.LayoutParams? = null
    private var dialog: Dialog? = null
    private var placeholderView: View? = null
    private var viewIndex: Int = 0
    private var darkView: View? = null
    private var originalDistance: Double = 0.toDouble()
    private var twoPointCenter: IntArray? = null
    private var originalXY: IntArray? = null

    private val activityWeakReference: WeakReference<Activity>

    private var isAnimatingDismiss = false

    init {
        this.activityWeakReference = WeakReference(activity)
    }

    fun onDispatchTouchEvent(ev: MotionEvent): Boolean {
        val activity: Activity
        activity = activityWeakReference.get()
        if (activity == null) {
            return false
        }

        if (ev.pointerCount == 2) {
            if (zoomableView == null) {
                val view = findZoomableView(ev,
                        activity.findViewById(android.R.id.content))
                if (view != null) {
                    zoomableView = view

                    originalXY = IntArray(2)
                    view.getLocationInWindow(originalXY)

                    val frameLayout = FrameLayout(view.context)
                    darkView = View(view.context)
                    darkView!!.setBackgroundColor(Color.BLACK)
                    darkView!!.alpha = 0f
                    frameLayout.addView(darkView, FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT))

                    parentOfZoomableView = zoomableView!!.parent as ViewGroup
                    viewIndex = parentOfZoomableView!!.indexOfChild(zoomableView)
                    this.zoomableViewLP = zoomableView!!.layoutParams
                    parentOfZoomableView!!.removeView(zoomableView)

                    placeholderView = View(activity)
                    parentOfZoomableView!!.addView(placeholderView, zoomableViewLP!!.width, zoomableViewLP!!.height)

                    zoomableViewFrameLP = FrameLayout.LayoutParams(
                            view.width, view.height)
                    zoomableViewFrameLP!!.leftMargin = originalXY!![0]
                    zoomableViewFrameLP!!.topMargin = originalXY!![1]
                    frameLayout.addView(zoomableView, zoomableViewFrameLP)

                    dialog = Dialog(activity,
                            android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                    dialog!!.addContentView(frameLayout,
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT))
                    dialog!!.show()

                    val pointerCoords1 = MotionEvent.PointerCoords()
                    ev.getPointerCoords(0, pointerCoords1)

                    val pointerCoords2 = MotionEvent.PointerCoords()
                    ev.getPointerCoords(1, pointerCoords2)

                    originalDistance = getDistance(pointerCoords1.x.toDouble(), pointerCoords2.x.toDouble(),
                            pointerCoords1.y.toDouble(), pointerCoords2.y.toDouble()).toInt().toDouble()
                    twoPointCenter = intArrayOf(((pointerCoords2.x + pointerCoords1.x) / 2).toInt(), ((pointerCoords2.y + pointerCoords1.y) / 2).toInt())

                    return true
                }
            } else {
                val pointerCoords1 = MotionEvent.PointerCoords()
                ev.getPointerCoords(0, pointerCoords1)

                val pointerCoords2 = MotionEvent.PointerCoords()
                ev.getPointerCoords(1, pointerCoords2)

                val newCenter = intArrayOf(((pointerCoords2.x + pointerCoords1.x) / 2).toInt(), ((pointerCoords2.y + pointerCoords1.y) / 2).toInt())

                val currentDistance = getDistance(pointerCoords1.x.toDouble(), pointerCoords2.x.toDouble(),
                        pointerCoords1.y.toDouble(), pointerCoords2.y.toDouble()).toInt()
                val pctIncrease = (currentDistance - originalDistance) / originalDistance

                zoomableView!!.scaleX = (1 + pctIncrease).toFloat()
                zoomableView!!.scaleY = (1 + pctIncrease).toFloat()

                updateZoomableViewMargins((newCenter[0] - twoPointCenter!![0] + originalXY!![0]).toFloat(),
                        (newCenter[1] - twoPointCenter!![1] + originalXY!![1]).toFloat())

                darkView!!.alpha = (pctIncrease / 8).toFloat()

                return true
            }
        } else {
            if (zoomableView != null && !isAnimatingDismiss) {
                isAnimatingDismiss = true
                val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                valueAnimator.duration = activity.resources
                        .getInteger(android.R.integer.config_shortAnimTime).toLong()
                valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    internal var scaleYStart = zoomableView!!.scaleY
                    internal var scaleXStart = zoomableView!!.scaleX
                    internal var leftMarginStart = zoomableViewFrameLP!!.leftMargin
                    internal var topMarginStart = zoomableViewFrameLP!!.topMargin
                    internal var alphaStart = darkView!!.alpha

                    internal var scaleYEnd = 1f
                    internal var scaleXEnd = 1f
                    internal var leftMarginEnd = originalXY!![0]
                    internal var topMarginEnd = originalXY!![1]
                    internal var alphaEnd = 0f

                    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
                        val animatedFraction = valueAnimator.animatedFraction
                        if (animatedFraction < 1) {
                            zoomableView!!.scaleX = (scaleXEnd - scaleXStart) * animatedFraction + scaleXStart
                            zoomableView!!.scaleY = (scaleYEnd - scaleYStart) * animatedFraction + scaleYStart

                            updateZoomableViewMargins(
                                    (leftMarginEnd - leftMarginStart) * animatedFraction + leftMarginStart,
                                    (topMarginEnd - topMarginStart) * animatedFraction + topMarginStart)

                            darkView!!.alpha = (alphaEnd - alphaStart) * animatedFraction + alphaStart
                        } else {
                            dismissDialogAndViews()
                        }
                    }
                })
                valueAnimator.start()

                return true
            }
        }

        return false
    }

    internal fun updateZoomableViewMargins(left: Float, top: Float) {
        if (zoomableView != null && zoomableViewFrameLP != null) {
            zoomableViewFrameLP!!.leftMargin = left.toInt()
            zoomableViewFrameLP!!.topMargin = top.toInt()
            zoomableView!!.layoutParams = zoomableViewFrameLP
        }
    }

    /**
     * Dismiss dialog and set views to null for garbage collection
     */
    private fun dismissDialogAndViews() {
        if (zoomableView != null) {
            zoomableView!!.visibility = View.VISIBLE

            val parent = zoomableView!!.parent as ViewGroup
            parent.removeView(zoomableView)
            this.parentOfZoomableView!!.addView(zoomableView, viewIndex, zoomableViewLP)
            this.parentOfZoomableView!!.removeView(placeholderView)

            zoomableView!!.post { dismissDialog() }

            zoomableView = null
        } else {
            dismissDialog()
        }

        isAnimatingDismiss = false
    }

    private fun dismissDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }

        zoomableView = null
        darkView = null
    }

    /**
     * Get distance between two points

     * @return distance
     */
    private fun getDistance(x1: Double, x2: Double, y1: Double, y2: Double): Double {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))
    }

    /**
     * Finds the view that has the R.id.zoomable tag and also contains the x and y coordinations of
     * two pointers

     * @param event MotionEvent that contains two pointers
     * *
     * @param view  View to find in
     * *
     * @return zoomable View
     */
    private fun findZoomableView(event: MotionEvent, view: View): View? {
        if (view is ViewGroup) {
            val childCount = view.childCount

            val pointerCoords1 = MotionEvent.PointerCoords()
            event.getPointerCoords(0, pointerCoords1)

            val pointerCoords2 = MotionEvent.PointerCoords()
            event.getPointerCoords(1, pointerCoords2)

            for (i in 0..childCount - 1) {
                val child = view.getChildAt(i)

                if (child.getTag(R.id.unzoomable) == null) {
                    val visibleRect = Rect()
                    val location = IntArray(2)
                    child.getLocationOnScreen(location)
                    visibleRect.left = location[0]
                    visibleRect.top = location[1]
                    visibleRect.right = visibleRect.left + child.width
                    visibleRect.bottom = visibleRect.top + child.height

                    if (visibleRect.contains(pointerCoords1.x.toInt(), pointerCoords1.y.toInt()) && visibleRect.contains(pointerCoords2.x.toInt(), pointerCoords2.y.toInt())) {
                        if (child.getTag(R.id.zoomable) != null)
                            return child
                        else
                            return findZoomableView(event, child)
                    }
                }
            }
        }

        return null
    }

    companion object {

        /**
         * Set view to be zoomable
         */
        fun setViewZoomable(view: View) {
            view.setTag(R.id.zoomable, Any())
        }

        /**
         * Enable or disable zoom for view and it's children
         */
        fun setZoom(view: View, enabled: Boolean) {
            view.setTag(R.id.unzoomable, if (enabled) null else Any())
        }
    }
}