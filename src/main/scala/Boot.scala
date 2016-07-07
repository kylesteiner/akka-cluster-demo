import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging
import web._

object Boot extends App with LazyLogging {
  val nodeConfig = NodeConfig parse args

  nodeConfig map { c =>
    implicit val system = ActorSystem(c.clusterName, c.config)
    system.actorOf(Props[MonitorActor], "cluster-monitor")
  }

}