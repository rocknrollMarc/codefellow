
package de.tuxed.codefellow

import java.io.File

import scala.actors._
import scala.actors.Actor._
import scala.actors.remote.RemoteActor

import scala.collection.mutable.ListBuffer

import scala.tools.nsc.interactive.{Global, CompilerControl}
import scala.tools.nsc.{Settings, FatalError}
import scala.tools.nsc.reporters.{Reporter, ConsoleReporter}
import scala.tools.nsc.util.{ClassPath, MergedClassPath, SourceFile, Position, OffsetPosition, NoPosition}
import scala.collection.mutable.{ HashMap, HashEntry, HashSet }
import scala.collection.mutable.{ ArrayBuffer, SynchronizedMap,LinkedHashMap }
import scala.tools.nsc.symtab.Types
import scala.tools.nsc.symtab.Flags


case class Request(moduleIdentifierFile: String, message: Message)

class Project(modules: List[Module]) extends Actor {

  def act {
    modules foreach { _.start }
    modules foreach { _ ! StartCompiler } // TODO: Defer to improve startup time
    loop {
      try {
        receive {
          case Request(moduleIdentifierFile, message) =>
            val module = modules.filter(m => moduleIdentifierFile.startsWith(m.path))(0)
            println("REQUEST: Sending " + message + " to " + module + "")
            val result = module !? message
            println("REQUEST result:" + result)
            sender ! result
        }
      } catch {
        case e: Exception =>
          e.printStackTrace
          sender ! List("")
      }
    }
  }
  
}
