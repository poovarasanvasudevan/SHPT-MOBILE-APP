package `in`.shpt.checkout

import `in`.shpt.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fcannizzaro.materialstepper.AbstractStep

/**
 * Created by poovarasanv on 24/11/16.
 */

class StepFour : AbstractStep() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step4, container, false)

        return v
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Payment Method"
    }

    override fun nextIf(): Boolean {
        return super.nextIf()
    }
}
