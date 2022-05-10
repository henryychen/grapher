import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class TableView(
    private val model: Model
) : ScrollPane(), IView {

    override fun updateView() {
        var vbox1 = VBox()
        vbox1.spacing = 8.0
        vbox1.alignment = Pos.BASELINE_RIGHT
        var vbox2 = VBox()
        var it = model.mutableMap[model.currentDataSet]?.data?.listIterator()

        if (it != null) {
            var counter = 1
            while(it.hasNext()) {
                vbox1.children.add(Label("$counter:"))
                var i = it.next()
                var spinner = Spinner<Integer>(0.0,100.0, i.toDouble())
                spinner.prefWidth = 80.0
                spinner.id = (counter-1).toString()

                spinner.editor.textProperty().addListener { obs, oldValue, newValue ->
                    model.updateDataSet((spinner.id).toInt(), newValue.toInt())
                }
                vbox2.children.add(spinner)
                counter++
            }
        }
        var hbox = HBox()
        hbox.spacing = 10.0
        hbox.children.add(vbox1)
        hbox.children.add(vbox2)
        content = hbox
    }

    init {
        isFitToWidth = true
        prefWidth = 150.0
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")

        model.addView(this)
    }
}