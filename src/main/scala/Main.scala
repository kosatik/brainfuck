import scala.io.Source

//санаторная , пионерская
object Main extends App {
  private val data = new Array[String](30000)
  private val buf = new Array[Int](300)
  private var cursor = 0: Int
  private var cursorData = 0: Int
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
      lastIndex = data.lastIndexOf(lastChar)
      eachData(cursorData, lastIndex)
    } finally {
      file.close()
    }
  }

  private def eachData(from: Int, to: Int): Unit = {
    cursorData = from
    for (i <- from until to) {
        parse(data(i))
        cursorData += 1
    }


  }

  private def parse(char: String): Unit = {
    if (char.equals("+")) buf(cursor) += 1
    else if (char.equals("-")) buf(cursor) -= 1
    else if (char.equals(">")) cursor += 1
    else if (char.equals("<")) cursor -= 1
    else if (char.equals(".")) print(buf(cursor).toChar)
    else if (char.equals("[")) {
      each(cursorData, cursor)
    }
    else if (char.equals("]")) {
    }
  }

  private def each(from: Int, buferCursor: Int): Unit = {
    val count = buf(buferCursor)
    val to = findTo(from)
    for (i <- 1 until count) {
      eachData(from + 1, to)
    }
    cursor = buferCursor
  }

  private def findTo(from: Int): Int = {
    for (i <- from until lastIndex) {
      if (data(i).equals("]")) {
        return i
      }
    }
    println("error ] not found")
    0
  }

  this.run()
}

