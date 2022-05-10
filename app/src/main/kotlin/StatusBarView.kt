import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class StatusBarView(
    private val model: Model
): HBox(), IView {

    private var message = Label("${model.mutableMap.size} datasets")

    override fun updateView() {
        message.text = "${model.mutableMap.size} datasets"
    }

    init {
        background = (Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)))
        style = ("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-color: #c4c4c4;")
        spacing = 10.0
        children.add(message)

        model.addView(this)
    }
}