package com.maxl.tweets

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.collection.JavaConverters._

object TweetHTMLParser {

	def getDataTime(ele: Element): Long = {
		val timeEle = ele.getElementsByClass("tweet-timestamp").first()
		val timeSpan = timeEle.getElementsByTag("span")
		val dataTime = timeSpan.attr("data-time-ms")
		val dataTimeLong = dataTime.toLong
		dataTimeLong
	}

	def getId(ele: Element): String = {
		val timeEle = ele.getElementsByClass("tweet-timestamp").first()
		val id = timeEle.attr("data-conversation-id")
		id
	}

	def getFavoriteCount(ele: Element): Int = {
		val favoriteEle = ele.getElementsByClass("ProfileTweet-action--favorite").first()
		val favoriteSpan = favoriteEle.getElementsByTag("span")
		val favoriteCount = favoriteSpan.attr("data-tweet-stat-count")
		val favoriteCountInt = favoriteCount.toInt
		favoriteCountInt
	}

	def getReplyCount(ele: Element): Int = {
		val replyEle = ele.getElementsByClass("ProfileTweet-action--reply").first()
		val replySpan = replyEle.getElementsByTag("span")
		val replyCount = replySpan.attr("data-tweet-stat-count")
		val replyCountInt = replyCount.toInt
		replyCountInt
	}

	def getRetweetCount(ele: Element): Int = {
		val retweetEle = ele.getElementsByClass("ProfileTweet-action--retweet").first()
		val retweetSpan = retweetEle.getElementsByTag("span")
		val retweetCount = retweetSpan.attr("data-tweet-stat-count")
		val retweetCountInt = retweetCount.toInt
		retweetCountInt

	}

	def getTweetText(ele: Element): String = {
		val tweetEle = ele.getElementsByClass("js-tweet-text-container").first()
		val tweetText = tweetEle.text()
		tweetText
	}

	def parseTweetsFromHtml(rawHtml: String): List[TweetInfo] = {
		val doc = Jsoup.parse(rawHtml)
		val tweetContainers = doc.getElementsByClass("content").asScala
		val tweets = tweetContainers.map {
			tweetContainer =>
				val id = getId(tweetContainer)
				val dataTime = getDataTime(tweetContainer)
				val favoriteCount = getFavoriteCount(tweetContainer)
				val replyCount = getReplyCount(tweetContainer)
				val retweetCount = getRetweetCount(tweetContainer)
				val tweetText = getTweetText(tweetContainer)
				val tweetInfo = TweetInfo(id, dataTime, favoriteCount, replyCount, retweetCount, tweetText)
				tweetInfo
		}
		val tweetList = tweets.toList
		tweetList
	}

}
