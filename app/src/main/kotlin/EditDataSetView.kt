import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class EditDataSetView(
    private val model: Model
) : HBox(), IView {

    var nameField = TextField()
    var xField = TextField()
    var yField = TextField()
    var tmpName = nameField.text
    var tmpX = ""
    var tmpY = ""

    override fun updateView() {
        nameField.text = model.mutableMap[model.currentDataSet]?.title
        xField.text = model.mutableMap[model.currentDataSet]?.xAxis
        yField.text = model.mutableMap[model.currentDataSet]?.yAxis

        tmpName = nameField.text
        tmpX = xField.text
        tmpY = yField.text
    }

    init {
        nameField.textProperty().addListener { observable, oldValue, newValue ->
            model.updateTitle(newValue)
        }

        xField.textProperty().addListener { observable, oldValue, newValue ->
            model.updateX(newValue)
        }

        yField.textProperty().addListener { observable, oldValue, newValue ->
            model.updateY(newValue)
        }

        children.add(Label("Name:"))
        children.add(nameField)
        children.add(Label("X-Axis:"))
        children.add(xField)
        children.add(Label("Y-Axis:"))
        children.add(yField)
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")
        spacing = 10.0
        alignment = Pos.BASELINE_LEFT
        model.addView(this)
    }
}