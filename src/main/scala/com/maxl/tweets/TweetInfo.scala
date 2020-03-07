package com.maxl.tweets

import java.time.ZoneId

import com.maxl.date.DateUtil

case class TweetInfo(id: String, time: Long, favoriteCount: Int, replyCount: Int, retweetCount: Int, tweetText: String) {
	private val date = DateUtil.convertLongToDate(time)
	private val localDate = DateUtil.convertDateToLocalDate(date)
	private val localDateTime = date.toInstant.atZone(ZoneId.systemDefault())
	val readableDate = f"${localDate.getYear}%04d-${localDate.getMonthValue}%02d-${localDate.getDayOfMonth}%02d"
	val timeOfDay = f"${localDateTime.getHour}%02d:${localDateTime.getMinute}%02d"

	def prettyPrint: String = {
		s"""{
        |$tweetText
        |ID: $id
        |Date:   $readableDate
        |Time:        $timeOfDay
        |Likes:    ${f"$favoriteCount%8d"}
        |Replies:  ${f"$replyCount%8d"}
        |Retweets: ${f"$retweetCount%8d"}
        |}""".stripMargin
	}

}
