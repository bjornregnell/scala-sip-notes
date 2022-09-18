// Two magic comments used by scala-cli (just copy-paste them):
//> using lib "taggy:taggy:0.0.3,url=https://github.com/bjornregnell/taggy/releases/download/v0.0.3/taggy_3-0.0.3.jar"
//> using scala "3.2.nightly"

//  run this command in terminal to create slides in target/out.pdf 
//  scala-cli run .

import scala.language.experimental.fewerBraces
import taggy.*

@main def run = slides.toPdf()

def slides = document("SIP-46 - Scala CLI -> scala"):
  frame("SIP-46 - Use Scala CLI to implement the 'scala' command"):
    itemize:
      p("Some highlights from discussions and SIP updates:")
      itemize:
        p("Backward compatibility with old `scala`")
        p("Motivation in relation to alternatives")
      p("My recommendation: accept")
      p("Remaining issues:")
      itemize:
        p("Magic comments or 'real' specifications?")
        p("New test-dir convention?")

    p("https://github.com/scala/improvement-proposals/pull/46/files")
