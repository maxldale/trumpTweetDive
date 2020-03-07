package com.maxl.scraper

import java.util.Date

import com.maxl.date.DateUtil

object TweetURLBuilder {
	val baseUrl: String = "https://twitter.com/search?l=&q="

	def buildURL(user: String, startDate: Date, endDate: Date): String = {
		val start: String = convertDateToFormattedString(startDate)
		val end: String = convertDateToFormattedString(endDate)
		val combinedUrl: String = s"${baseUrl}from%3A${user}%20since%3A${start}%20until%3A${end}&src=typd&lang=en"
		combinedUrl
	}

	def buildURLForCurrentDay(user: String, startDate: Date): String = {
		val start: String = convertDateToFormattedString(startDate)
		val combinedUrl: String = s"${baseUrl}from%3A${user}%20since%3A${start}&src=typd&lang=en"
		combinedUrl
	}

	val yearOffset = 1900
	val monthOffset = 1

	def convertDateToFormattedString(date: Date): String = {
		val localDate = DateUtil.convertDateToLocalDate(date)
		val year = localDate.getYear
		val month = localDate.getMonthValue
		val day = localDate.getDayOfMonth
		val formattedString = f"$year%4d-$month%02d-$day%02d"
		formattedString
	}
}
