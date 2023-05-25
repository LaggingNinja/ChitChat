import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.receptionist.{Receptionist,ServiceKey}
import com.hep88.Upnp

object ServerHello {
  sealed trait Command
  case class Message(value: String, from: ActorRef[ClientHello.Command]) extends Command
  val ServerKey: ServiceKey[ServerHello.Command] = ServiceKey("Server")

  def apply(): Behavior[ServerHello.Command] =
    Behaviors.setup { context =>
      val upnpRef = context.spawn(Upnp(), Upnp.name)
      context.system.receptionist ! Receptionist.Register(ServerKey, context.self)
      
      Behaviors.receiveMessage { message =>
        message match {
            case Message(value, from) =>
                println(s"Server received message '${value}'")
                from ! ClientHello.Message("how are you", context.self)
            Behaviors.same
        }
      }
    }
}

object Server extends App {
  val greeterMain: ActorSystem[ServerHello.Command] = ActorSystem(ServerHello(), "HelloSystem")
}
