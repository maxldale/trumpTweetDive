package com.maxl.tweetAccumulation

import java.util.Date

import com.maxl.date.{DateCounter, DateUtil}
import com.maxl.scraper.{TweetScraper, TweetURLBuilder}
import com.maxl.tweets.{TweetHTMLParser, TweetInfo}

import scala.util.Random

object TrumpTweetAccumulator {
	val user: String = "realdonaldtrump"
	val timeToWaitInSec = 1
	val maxAddtlTimeToWaitInSec = 3

	private def yesterdaysDate: Date = {
		val longTime = System.currentTimeMillis()
		val todaysDate = DateUtil.convertLongToDate(longTime)
		val localDate = DateUtil.convertDateToLocalDate(todaysDate)
		val yesterdaysLocalDate = localDate.minusDays(1)
		val yesterdaysDate = DateUtil.convertLocalDateToDate(yesterdaysLocalDate)
		yesterdaysDate
	}

	def accumulateTweets(startDate: Date, endDate: Date): List[TweetInfo] = {
		val dateCounter = new DateCounter(endDate)
		dateCounter.incrementDate()
		val tweetAccumulator = new scala.collection.mutable.ListBuffer[TweetInfo]()
		if (endDate.after(yesterdaysDate)) {
			val date = dateCounter.getCurrentDate
			val firstURL = TweetURLBuilder.buildURLForCurrentDay(user, date)
			val firstPage = TweetScraper.get(firstURL)
			val firstTweets = TweetHTMLParser.parseTweetsFromHtml(firstPage)
			tweetAccumulator.addAll(firstTweets)
		}

		val randomNumberGen = new Random(System.currentTimeMillis())
		while (startDate.before(dateCounter.getCurrentDate)) {
			val currEndDate = dateCounter.getCurrentDate
			dateCounter.incrementDate()
			val currStartDate = dateCounter.getCurrentDate
			val url = TweetURLBuilder.buildURL(user, currStartDate, currEndDate)
			val randomWait = maxAddtlTimeToWaitInSec * randomNumberGen.nextDouble()
			val totalTimeToWait = (randomWait + timeToWaitInSec) * 1000
			Thread.sleep(totalTimeToWait.toLong)
			val page = TweetScraper.get(url)
			val tweets = TweetHTMLParser.parseTweetsFromHtml(page)
			tweetAccumulator.addAll(tweets)
			println(s"$currStartDate to $currEndDate: Found ${tweets.length} tweets, waited ${f"$randomWait%02.2f seconds from last request"}")
		}

		val allTweets = tweetAccumulator.toList
		allTweets
	}

}
