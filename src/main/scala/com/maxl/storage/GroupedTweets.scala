package com.maxl.storage

import com.maxl.date.DateUtil
import com.maxl.tweets.TweetInfo

case class GroupedTweets(yearMonth: YearMonth, tweets: List[TweetInfo])

object GroupedTweets {

	def groupTweetsByYearAndMonth(tweetList: List[TweetInfo]): List[(YearMonth, List[TweetInfo])] = {
		val uniqueTweetList = ensureUniquenessOfTweets(tweetList)
		val uniqueTweetListSorted = uniqueTweetList.sortBy {
			tweetInfo =>
				tweetInfo.time
		}
		val groupedByYearAndMonthTweetInfo = uniqueTweetListSorted.groupBy {
			tweetInfo =>
				val (year, month, day) = DateUtil.convertLongToYearMonthDayTuple(tweetInfo.time)
				YearMonth(year, month)
		}
		val groupedByYearAndMonthTweetInfoList = groupedByYearAndMonthTweetInfo.toList
		groupedByYearAndMonthTweetInfoList
	}

	def ensureUniquenessOfTweets(tweetList: List[TweetInfo]): List[TweetInfo] = {
		val tweetSet = tweetList.toSet
		val uniqueTweetList = tweetSet.toList
		uniqueTweetList
	}
}
