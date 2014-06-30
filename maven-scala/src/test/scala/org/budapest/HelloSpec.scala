package org.budapest

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
	"A Hello with parameter 'world'" should "say 'Hello world'" in {
		val hello = Hello("world")
		hello.say should be ("Hello world")
	}
}
