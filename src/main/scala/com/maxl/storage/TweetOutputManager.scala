package com.maxl.storage

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import com.google.gson.Gson
import com.maxl.tweets.TweetInfo

object TweetOutputManager {
	val tweetBaseDir = "tweets"

	def checkForOrCreateDirAndReturnPath(totalPath: String): Unit = {
		val dirExists = Files.exists(Paths.get(totalPath))
		if (!dirExists) {
			val path = Paths.get(totalPath)
			Files.createDirectory(path)
		}
	}

	def mapTweetsToGson(tweets: List[TweetInfo]): List[String] = {
		val gsonTweets = tweets.map{
			tweet =>
				val gson = new Gson()
				val gsonString = gson.toJson(tweet)
				gsonString
		}
		gsonTweets
	}

	def writeTweetsToFile(groupedTweetsList: List[(YearMonth, List[TweetInfo])]): Boolean = {
		val homePath = System.getProperty("user.home")
		val basePathTotal = s"$homePath/$tweetBaseDir"
		checkForOrCreateDirAndReturnPath(basePathTotal)
		for (monthOfTweets <- groupedTweetsList) {
			val yearMonth = monthOfTweets._1
			val tweets = monthOfTweets._2
			val year = yearMonth.year
			val yearPath = s"$basePathTotal/$year"
			checkForOrCreateDirAndReturnPath(yearPath)
			val month = yearMonth.month
			val monthPath = s"$yearPath/$month.json"
			val gsonTweets = mapTweetsToGson(tweets)
			val singleGsonTweets = gsonTweets.mkString("\n") + "\n"
			val path = Paths.get(monthPath)
			Files.writeString(path, singleGsonTweets, StandardCharsets.UTF_8)
		}
		false
	}

}
