import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class StatsView(
    private val model: Model
) : VBox(), IView {

    var num = Label("0")
    var min = Label("0")
    var max = Label("0")
    var avg = Label("0")
    var sum2 = Label("0")

    override fun updateView() {
        num.text = model.mutableMap[model.currentDataSet]?.data?.size.toString()
        var tmpMin = 2147483647
        var tmpMax = -2147483648
        var tmpAvg = 0.0
        var tmpSum = 0

        var it = model.mutableMap[model.currentDataSet]?.data?.listIterator()
        if (it != null) {
            while(it.hasNext()){
                var i = it.next()
                tmpSum += i
                if (i < tmpMin) tmpMin = i
                if (i > tmpMax) tmpMax = i
            }
            tmpAvg = tmpSum.toDouble() / model.mutableMap[model.currentDataSet]?.data?.size!!
        }
        min.text = tmpMin.toString()
        max.text = tmpMax.toString()
        var str = tmpAvg.toString()
        var index = str.indexOf('.')
        str = str.substring(0, index+2)
        avg.text = str
        sum2.text = tmpSum.toString()
    }

    init {
        var col1 = VBox()
        col1.spacing = 10.0
        var number = Label("Number")
        var minimum = Label("Minimum")
        var maximum = Label("Maximum")
        var avgerage = Label("Average")
        var sum = Label("Sum")
        col1.children.add(number)
        col1.children.add(minimum)
        col1.children.add(maximum)
        col1.children.add(avgerage)
        col1.children.add(sum)

        var col2 = VBox()
        col2.spacing = 10.0
        col2.children.add(num)
        col2.children.add(min)
        col2.children.add(max)
        col2.children.add(avg)
        col2.children.add(sum2)

        var hbar = HBox()
        hbar.spacing = 10.0
        hbar.children.add(col1)
        hbar.children.add(col2)

        children.add(hbar)

        prefWidth = 125.0
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")
        spacing = 10.0

        model.addView(this)
    }
}