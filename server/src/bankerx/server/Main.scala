package bankerx.server

import ox.*
import sttp.tapir.server.netty.sync.NettySyncServer

object Main extends OxApp.Simple:

  def run(using Ox): Unit =

    val port = sys.env.get("HTTP_PORT").flatMap(_.toIntOption).getOrElse(8080)
    val binding = useInScope(NettySyncServer().port(port).addEndpoints(Endpoints.all).start())(_.stop())
    println(s"Server started at http://localhost:${binding.port}. ")
    never