package web

import akka.actor._
import akka.cluster._
import akka.cluster.ClusterEvent._
import com.typesafe.scalalogging.LazyLogging

class MonitorActor extends Actor with LazyLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) =>
      logger.info("Member is Up: {}", member.address)
    case UnreachableMember(member) =>
      logger.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      logger.info("Member is Removed: {} after {}",
        member.address, previousStatus)
    case _: MemberEvent => // ignore
  }
}