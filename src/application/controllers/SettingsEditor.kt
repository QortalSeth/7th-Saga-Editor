package application.controllers

import application.ControllerInitilizer
import application.ROM
import application.Settings
import javafx.scene.control.CheckBox
import javafx.fxml.FXML



class SettingsEditor: ControllerInitilizer {

    @FXML  lateinit var emptyNamesCH: CheckBox
    @FXML  lateinit var dupMonstersCH: CheckBox
    @FXML  lateinit var excelCH: CheckBox
    @FXML  lateinit var belaineCH: CheckBox

    @FXML fun initialize()
    {
        belaineCH.selectedProperty().addListener { observable, oldValue, newValue ->
            ROM.settings.belaineSwordsMatchCosts = newValue}
        dupMonstersCH.selectedProperty().addListener { observable, oldValue, newValue ->  ROM.settings.showMonsterDuplicates = newValue}
    emptyNamesCH.selectedProperty().addListener { observable, oldValue, newValue ->  ROM.settings.showEmptyValues = newValue}
              excelCH.selectedProperty().addListener { observable, oldValue, newValue ->  ROM.settings.createExcelFilesOnSave = newValue}


        belaineCH.selectedProperty().set(ROM.settings.belaineSwordsMatchCosts)
        dupMonstersCH.selectedProperty().set(ROM.settings.showMonsterDuplicates)
        emptyNamesCH.selectedProperty().set(ROM.settings.showEmptyValues)
        excelCH.selectedProperty().set(ROM.settings.createExcelFilesOnSave)

    }
    override fun saveData()
    {

    }

    override fun saveState()
    {
        ROM.settings.writeSettings()
    }

}