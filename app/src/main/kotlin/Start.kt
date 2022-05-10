import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.stage.Stage

class Start : Application() {

    override fun start(stage: Stage) {
        val model = Model()
        model.populateData()
        val borderpane = BorderPane()
        val body = BorderPane()

        body.top = EditDataSetView(model)
        body.left = TableView(model)
        body.center = CentralView(model)
        body.right = StatsView(model)
        borderpane.top = ToolBarView(model)
        borderpane.center = body
        borderpane.bottom = StatusBarView(model)

        val scene = Scene(borderpane, 800.0, 600.0)
        stage.title = "A2 Grapher (hy5chen)"
        stage.scene = scene
        stage.minWidth = 600.0
        stage.minHeight = 400.0
        stage.show()
    }
}
