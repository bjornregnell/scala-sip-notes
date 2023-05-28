// Two magic comments used by scala-cli (just copy-paste them):
//> using lib "taggy:taggy:0.0.3,url=https://github.com/bjornregnell/taggy/releases/download/v0.0.3/taggy_3-0.0.3.jar"
//> using scala "3.2.nightly"

//  run this command in terminal to create slides in target/out.pdf 
//  scala-cli run .

import scala.language.experimental.fewerBraces
import taggy.*

@main def run = slides.toPdf()

def slides = document("More ergonomic union types"):
  frame("Outline"):
    itemize:
      p("Brief summary of this long thread")
      itemize:
        p("https://contributors.scala-lang.org/t/making-union-types-even-more-useful/4927")
      p("Next steps?")
    p("~\\\\These slides are here: https://github.com/bjornregnell/scala-sip-notes")

  frame("Better type inference for union types"):
    p("Improvements since 3.1.x")
    itemize:
      p("More precise types in 3.2.1 for some cases:")
      itemize:
        p("https://github.com/lampepfl/dotty/issues/11449")
      p("But some problems remains:")
      itemize:
        p("https://github.com/lampepfl/dotty/issues/14642")
      p("''De-duplication'' of union types:")
      itemize:  
        p("https://github.com/lampepfl/dotty/issues/10693")

  frame("Lagom widening"):
    code("""|scala> var p = true
            |var p: Boolean = true
            |                                                                                                                               
            |scala> val x = if p then 42 else "hello"
            |val x: Matchable = 42
            |
            |// why not infer the more precise Int | String  ?
            |""".stripMargin)


  frame("Scrap boilerplate by generating match on unions"):
    code("""scala> class A(val x: Int)
            |                                                                                                                               
            |scala> class B(val x: Int)
            |                                                                                                                               
            |scala> val ab: A | B = A(42)
            |                                                                                                                               
            |scala> ab.x
            |-- [E008] Not Found Error: ----------------------------------------------------------------------------------------------------
            |1 |ab.x
            |^^^^
            |value x is not a member of A | B
            |""".stripMargin)
    itemize:
      p("You can fix this with match boilerplate:")
    code("""|scala> ab match
            |   case a: A => a.x
            |   case b: B => b.x
            |""".stripMargin)
    itemize:
      p("But the compiler knows statically that both A and B has x")
      p("Proposal: make the compiler synthesize the match")

  frame("Discussions on member selection"):
    itemize:
      p("*Martin Odersky:* One problem here is that union types are supposed to be commutative, but the match is order-dependent, unless we can prove somehow that all alternatives are disjoint. ")
      p("*...after some discussions...*")
      p("*Martin Odersky:* Yes, I think you are right, as long as we restrict our focus to member selection. If there is an overlap then dynamic dispatch will provide the right method. ")
    code("""|scala> extension (x: Int) def size = x
            |def size(x: Int): Int
            |                                                                                                                               
            |scala> List[Int|String](42,"fortytwo").map(_.size)
            |// value size is not a member of Int | String
            |""".stripMargin)
    itemize:
      p("*Sébastien Doeraene*: Including extension methods of individual components of the unions is an even bigger can of worms...")

  frame("Notes from SIP meeting discussions"):
    itemize:
      p("More precise type inference is being worked on and some things have already improved") 
      p("Generating a match for common members in a union is against then nominal typing philosophy of Scala (as opposed to the structural typing of e.g. typescript")
      p("There are workarounds, e.g. creating extensions methods on the union to make it more convenient") 
      p("Automatic match could be done with macros but it is still dubious in terms of nominal typing")
      p("**Decision:** *We will not work on a SIP proposal related to structural member selection for unions*")