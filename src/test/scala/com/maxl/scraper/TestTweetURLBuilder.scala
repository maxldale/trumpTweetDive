package com.maxl.scraper

import java.text.SimpleDateFormat
import java.util.Date

import org.scalatest.FunSuite

class TestTweetURLBuilder extends FunSuite {

	test("Ensure proper formatting of convertDateToFormattedString") {
		val testDateString = "2018-02-01"
		val testDateFormat = new SimpleDateFormat("yyyy-MM-dd")
		val testDate = testDateFormat.parse(testDateString)
		val dateString = TweetURLBuilder.convertDateToFormattedString(testDate)
		assert(dateString == testDateString)
	}

}
