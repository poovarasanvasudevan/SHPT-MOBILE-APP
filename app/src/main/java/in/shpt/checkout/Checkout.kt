package `in`.shpt.checkout

import `in`.shpt.ext.theme
import android.os.Bundle
import com.github.fcannizzaro.materialstepper.style.TabStepper

/**
 * Created by poovarasanv on 24/11/16.
 */

class Checkout : TabStepper() {


    override fun onCreate(savedInstanceState: Bundle?) {

        theme()
        setErrorTimeout(1500);
        setLinear(false);
        setTitle("Checkout");
        setAlternativeTab(false);
        setDisabledTouch();
        setPreviousVisible();


        addStep(StepOne())
        addStep(StepTwo())
        addStep(StepThree())
        addStep(StepFour())
        addStep(StepFive())

        super.onCreate(savedInstanceState)
    }
}
