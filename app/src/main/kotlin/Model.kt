class Model {
    //region Observer Related

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private fun notifyObservers() {
        for (view in views) {
            view.updateView()
        }
    }
    //endregion

    // model state
    var mutableMap = mutableMapOf<String, DataSet?>()
    var currentDataSet = "Increasing"

    fun populateData() {
        mutableMap["Increasing"] = createTestDataSet("Increasing")
        mutableMap["Middle"] = createTestDataSet("Middle")
        mutableMap["Large"] = createTestDataSet("Large")
        mutableMap["Single"] = createTestDataSet("Single")
        mutableMap["Range"] = createTestDataSet("Range")
        mutableMap["Percentage"] = createTestDataSet("Percentage")
        notifyObservers()
    }

    fun addToDataSet(name: String, ds: DataSet) {
        mutableMap[name] = ds
        currentDataSet = name
        notifyObservers()
    }

    fun changeData(s: String) {
        currentDataSet = s
        notifyObservers()
    }

    fun updateDataSet(index: Int, num: Int) {
        mutableMap[currentDataSet]?.data?.set(index, num)
        notifyObservers()
    }

    fun updateTitle(s: String) {
        mutableMap[currentDataSet]?.title = s
        notifyObservers()
    }

    fun updateX(s: String) {
        mutableMap[currentDataSet]?.xAxis = s
        notifyObservers()
    }

    fun updateY(s: String) {
        mutableMap[currentDataSet]?.yAxis = s
        notifyObservers()
    }
}