package mpcode.app.ui

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

object Dialogs {
    fun error(msg: String) =
        Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait()

    fun info(msg: String) =
        Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait()

    fun confirm(msg: String): Boolean =
        Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL)
            .showAndWait().filter { it == ButtonType.OK }.isPresent
}
