import scala.collection.mutable.ListBuffer
import scala.io.Source

//санаторная , пионерская
object Main extends App {
  private val data = new Array[String](30000)
  private val buf = new Array[Int](300)
  private var cursor = 0: Int
  private var cursorData = 0: Int
  private var loopNumber = 0: Int
  private var lastIndex = 0: Int

  def run() {
    val PATH = getClass.getResource("").getPath
    val file = Source.fromFile(PATH + "brainfuck.txt")

    try {
      var i = 0
      var lastChar = ""
      for (line <- file.getLines()) {
        for (char <- line) {
          data(i) = char.toString
          lastChar = char.toString
          i += 1
        }
      }
      //      println(data.lastIndexOf(lastChar))
      lastIndex = data.lastIndexOf(lastChar)
      eachData(cursorData, loopNumber, lastIndex)
    } finally {
      file.close()
    }
  }

  private def eachData(from: Int, loop: Int, to: Int): Unit = {
    cursorData = from
    for (i <- from until to) {
//      print(data(i))
//      println(loop, loopNumber, data(i))

//      if (loopNumber != loop) {
//        return
//      }
//      if (loopNumber == loop) {
        parse(data(i))
        cursorData += 1
//      }
    }


  }

  private def parse(char: String): Unit = {
    if (char.equals("+")) buf(cursor) += 1
    else if (char.equals("-")) buf(cursor) -= 1
    else if (char.equals(">")) cursor += 1
    else if (char.equals("<")) cursor -= 1
    else if (char.equals(".")) println(buf(cursor).toChar)
    else if (char.equals("[")) {
//      loopNumber += 1
      each(cursorData, cursor, loopNumber)
    }
    else if (char.equals("]")) {
//      loopNumber -= 1
    }
  }

  private def each(from: Int, buferCursor: Int, loop: Int): Unit = {
    val count = buf(buferCursor)
    val to = findTo(from)
    for (i <- 1 until count) {
//      println("iter: " + i)
      eachData(from + 1, loop, to)
    }
    cursor = buferCursor
  }

  private def findTo(from: Int): Int = {
    for (i <- from until lastIndex) {
//      println(data(i))
      if (data(i).equals("]")) {
        return i
      }
    }
    println("error ] not found")
    0
  }

  this.run()
}

