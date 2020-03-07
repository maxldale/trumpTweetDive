package main

import java.text.SimpleDateFormat
import java.util.Date

import com.maxl.date.DateCounter
import com.maxl.scraper.TweetScraper
import com.maxl.storage.{GroupedTweets, TweetOutputManager}
import com.maxl.tweetAccumulation.TrumpTweetAccumulator
import com.maxl.tweets.TweetHTMLParser

import scala.util.Random

object Runner {

	def main(args: Array[String]): Unit = {
		val endDate = new Date(System.currentTimeMillis())
		val startDateString = "01/05/2009"
		val dateFormat = new SimpleDateFormat("dd/MM/yyyy")
		val startDate = dateFormat.parse(startDateString);
		try {
			val tweets = TrumpTweetAccumulator.accumulateTweets(startDate, endDate)
			val groupedTweets = GroupedTweets.groupTweetsByYearAndMonth(tweets)
			TweetOutputManager.writeTweetsToFile(groupedTweets)
		} catch {
			case ioE: java.io.IOException => println("IO Exception")
			case stE: java.net.SocketTimeoutException => println("Timed Out")
		}
	}
}

class Runner {

	def generateDocumentFromHTML(htmlAsString: String): String = {
		import org.jsoup.Jsoup
		import org.jsoup.nodes.{Document, Element}
		import scala.collection.JavaConverters._
		val doc: Document = Jsoup.parse(htmlAsString)
		val contentList = doc.getElementsByClass("content").asScala
		val asStrings = contentList.map {
			ele =>
				println(s"\n\n{\n$ele\n}\n\n")
				ele.text()
		}
		val combinedVal = asStrings.fold("")(_ + "\n" + _)
		combinedVal
	}

	@throws(classOf[java.io.IOException])
	def writeToFile(content: String): Unit = {
		import java.io.{File, BufferedWriter, FileWriter}
		val file = new File("output.txt")
		val bw = new BufferedWriter(new FileWriter(file))
		bw.write(content)
		bw.close()
	}
}
