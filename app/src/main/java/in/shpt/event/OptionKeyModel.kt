package `in`.shpt.event

/**
 * Created by poovarasanv on 2/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 2/12/16 at 5:47 PM
 */

class OptionKeyModel {
    var componentId: Int = 0
    var componentDisplayName: String = ""
    var isRequired: Boolean = false
    var isEntered: Boolean = false
    var selectedValue: String = ""
    var optionId: String = ""

    constructor() {
    }

    constructor(componentId: Int, componentDisplayName: String, isRequired: Boolean, isEntered: Boolean, selectedValue: String, optionId: String) {
        this.componentId = componentId
        this.componentDisplayName = componentDisplayName
        this.isRequired = isRequired
        this.isEntered = isEntered
        this.selectedValue = selectedValue
        this.optionId = optionId
    }
}
