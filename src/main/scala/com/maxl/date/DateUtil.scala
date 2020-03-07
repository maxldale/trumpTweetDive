package com.maxl.date

import java.time.{Instant, LocalDate, ZoneId}
import java.util.Date

object DateUtil {

	def convertLongToDate(longDate: Long): Date = {
		val theDate = new Date(longDate)
		theDate
	}

	def convertStringToDate(stringDate: String): Date = {
		val longDate = Date.parse(stringDate)
		val theDate = convertLongToDate(longDate)
		theDate
	}

	def convertDateToLocalDate(date: Date): LocalDate = {
		val dateLong = date.getTime
		val instantDate = Instant.ofEpochMilli(dateLong)
		val zonedDateTime = instantDate.atZone(ZoneId.systemDefault)
		val localDate = zonedDateTime.toLocalDate
		localDate
	}

	def convertLocalDateToDate(localDate: LocalDate): Date = {
		val localDateTime = localDate.atStartOfDay()
		val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault)
		val instantDateTime = zonedDateTime.toInstant
		val date = Date.from(instantDateTime)
		date
	}

	def convertLongToYearMonthDayTuple(longDate: Long): (Int, Int, Int) = {
		val date = convertLongToDate(longDate)
		val localDate = convertDateToLocalDate(date)
		val day = localDate.getDayOfMonth
		val month = localDate.getMonthValue
		val year = localDate.getYear
		(year, month, day)
	}

}
