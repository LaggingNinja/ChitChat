package com.hep88.view
import akka.actor.typed.ActorRef
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.scene.control.{Label, ListView, TextField}
import com.hep88.ChatClient
import com.hep88.User
import com.hep88.Client
import scalafx.collections.ObservableBuffer
import scalafx.Includes._
@sfxml
class MainWindowController(private val txtName: TextField,
private val lblStatus: Label, private val listUser: ListView[User],
private val listMessage: ListView[String], private val listMessage2: ListView[String],
private val txtMessage: TextField) {

    var chatClientRef: Option[ActorRef[ChatClient.Command]] = None

    val receivedText: ObservableBuffer[String] =  new ObservableBuffer[String]()
    val sentText: ObservableBuffer[String] =  new ObservableBuffer[String]()

    listMessage.items = receivedText
    listMessage2.items = sentText

    def handleJoin(action: ActionEvent): Unit = {
        if(txtName != null)
          chatClientRef map (_ ! ChatClient.StartJoin(txtName.text()))
    }
    

    def displayStatus(text: String): Unit = {
        lblStatus.text = text
    }
  def updateList(x: Iterable[User]): Unit ={
    listUser.items = new ObservableBuffer[User]() ++= x
  }
  def handleSend(actionEvent: ActionEvent): Unit ={

    if (listUser.selectionModel().selectedIndex.value >= 0){
      Client.greeterMain ! ChatClient.SendMessageL(listUser.selectionModel().selectedItem.value.ref,
        txtMessage.text(), txtName.text())
        txtMessage.clear()
    }
  }
  def handleClear(action: ActionEvent): Unit = {
         txtMessage.clear()
    }
  def handleClearList(action: ActionEvent): Unit = {
        listMessage.getItems().clear()
  }
  def handleClearList2(action: ActionEvent): Unit = {
        listMessage2.getItems().clear()
  }
  def addSentText(text: String, target: ActorRef[ChatClient.Command], fromName: String): Unit = {
      sentText += text + " || Msg Sent to: " + target
  }
    
  def addText(text: String, from: ActorRef[ChatClient.Command], fromName: String): Unit = {
      receivedText += fromName + ": " + text
  }


}
