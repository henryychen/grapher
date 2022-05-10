import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import kotlin.random.Random


class ToolBarView(
    private val model: Model
) : HBox(), IView {

    var nameCounter = 1
    var numBars = 1

    // Toolbar variables
    var choiceBox: ChoiceBox<String> = ChoiceBox<String>()
    var newButton = Button()
    var spinner = Spinner<Integer>(1.0,20.0,1.0)

    override fun updateView() {
        choiceBox.selectionModel.select(model.currentDataSet)
    }

    init {
        choiceBox.items.add("Increasing")
        choiceBox.items.add("Large")
        choiceBox.items.add("Middle")
        choiceBox.items.add("Single")
        choiceBox.items.add("Range")
        choiceBox.items.add("Percentage")
        choiceBox.selectionModel.select(model.currentDataSet)
        choiceBox.selectionModel.selectedIndexProperty().addListener { observableValue, number, number2 ->
            var s = choiceBox.items[(number2 as Int?)!!]
            model.changeData(s)
        }

        newButton.text = "New"
        newButton.prefWidth = 80.0
        newButton.setOnAction {
            var name = "New$nameCounter"
            var title = LoremIpsum.getRandomSequence(3)
            var xAxis = LoremIpsum.getRandomSequence(1)
            var yAxis = LoremIpsum.getRandomSequence(1)
            var dataPoints = mutableListOf<Int>()
            for (i in 1..numBars) {
                dataPoints.add(Random.nextInt(0, 100))
            }
            var newX = DataSet(title, xAxis, yAxis, dataPoints)
            choiceBox.items.add(name)
            nameCounter++
            model.addToDataSet(name, newX)
        }

        spinner.prefWidth = 60.0
        spinner.editor.textProperty().addListener { obs, oldValue, newValue ->
            numBars = newValue.toInt()
        }

        // Toolbar styling
        var lab = Label("Dataset:")
        children.add(lab)
        children.add(choiceBox)
        var divider = Separator(Orientation.VERTICAL)
        divider.minHeight = 10.0
        children.add(divider)
        children.add(newButton)
        children.add(spinner)
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")
        alignment = Pos.CENTER_LEFT
        spacing = 10.0

        model.addView(this)
    }
}