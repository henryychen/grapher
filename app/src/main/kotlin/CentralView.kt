import javafx.beans.value.ChangeListener
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.scene.transform.Rotate
import java.lang.Integer.max

class CentralView(
    private val model: Model
) : Pane(), IView {

    var chart = Group()
    var paneWidth = width
    var paneHeight = height

    override fun updateView() {
        drawGraph()
    }

    init {
        children.add(chart)
        background = (Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)))
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")
        model.addView(this)

        val stageSizeListener: ChangeListener<Number> =
            ChangeListener<Number> { observable, oldValue, newValue ->
                paneWidth = this.width
                paneHeight = this.height
                drawGraph()
            }

        this.widthProperty().addListener(stageSizeListener)
        this.heightProperty().addListener(stageSizeListener)
        drawGraph()
    }

    private fun drawGraph() {
        chart.children.clear()

        // Find the max y-val in the dataset
        var maxHeight = 0
        var it = model.mutableMap[model.currentDataSet]?.data?.listIterator()
        if (it != null) {
            while (it.hasNext()) {
                var curHeight = it.next()
                maxHeight = max(curHeight, maxHeight)
            }
        }

        // Format axis and labels
        var xAxis = Line()
        with(xAxis) {
            startX = 50.0
            startY = paneHeight - 50.0
            endX = paneWidth - 50.0
            endY = paneHeight - 50.0
            stroke = Color.BLACK
        }
        var yAxis = Line()
        with(yAxis) {
            startX = 50.0
            startY = paneHeight - 50.0
            endX = 50.0
            endY = 50.0
            stroke = Color.BLACK
        }
        var rect = Rectangle(paneWidth-100.0, paneHeight-100.0)
        with(rect) {
            x = 50.0
            y = 50.0
            stroke = Color.LIGHTGRAY
            fill = Color.TRANSPARENT
        }
        var title = Label(model.mutableMap[model.currentDataSet]?.title)
        with (title) {
            layoutX = paneWidth/2 - (model.mutableMap[model.currentDataSet]?.title?.length ?: 0) * 3
            layoutY = 15.0
            textFill = Color.BLACK
        }
        var yText = Label(model.mutableMap[model.currentDataSet]?.yAxis)
        with (yText) {
            layoutX = 15.0
            layoutY = paneHeight/2 + (model.mutableMap[model.currentDataSet]?.yAxis?.length ?: 0) * 3
            textFill = Color.BLACK
            val rotate = Rotate(-90.0)
            transforms.add(rotate)
        }
        var xText = Label(model.mutableMap[model.currentDataSet]?.xAxis)
        with (xText) {
            layoutX = paneWidth/2 - (model.mutableMap[model.currentDataSet]?.xAxis?.length ?: 0) * 3
            layoutY = paneHeight - 35
            textFill = Color.BLACK
        }
        var zero = Label("0")
        with (zero) {
            layoutX = 35.0
            layoutY = paneHeight - 55
            textFill = Color.BLACK
        }
        var top = Label(maxHeight.toString())
        with (top) {
            layoutX = 25.0
            layoutY = 45.0
            textFill = Color.BLACK
        }
        chart.children.add(xAxis)
        chart.children.add(yAxis)
        chart.children.add(rect)
        chart.children.add(title)
        chart.children.add(yText)
        chart.children.add(xText)
        chart.children.add(zero)
        chart.children.add(top)

        // Format bars
        it = model.mutableMap[model.currentDataSet]?.data?.listIterator()
        var numBars = model.mutableMap[model.currentDataSet]?.data?.size
        if (it != null) {
            var counter = 5
            var hue = 0.0
            while(it.hasNext()) {
                var cur = it.next()
                var bar = Rectangle()
                var ratio = (paneHeight-100)/maxHeight
                with (bar) {
                    x = 50.0 + counter
                    y = (paneHeight - 50) - cur * ratio
                    width = (paneWidth - 100) / numBars!! - 5
                    height = cur * ratio
                    stroke = Color.LIGHTGRAY
                    fill = Color.hsb(hue,1.0,1.0)
                }

                chart.children.add(bar)
                counter += ((paneWidth - 100) / numBars!!).toInt()
                hue += 360/numBars!!
            }
        }
    }
}