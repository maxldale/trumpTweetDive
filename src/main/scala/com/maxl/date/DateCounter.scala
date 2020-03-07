package com.maxl.date

import java.time.ZoneId
import java.util.Date
import com.maxl.date.DateUtil._

class DateCounter(val startDate: Date) {

	def this(longDate: Long) {
		this(convertLongToDate(longDate))
	}

	def this(stringDate: String) {
		this(convertStringToDate(stringDate))
	}

	private var currentLocalDate = convertDateToLocalDate(startDate)

	def getCurrentDate: Date = {
		val date = convertLocalDateToDate(currentLocalDate)
		date
	}

	def incrementDate(): Unit = {
		val newCurrentDate = currentLocalDate.minusDays(1)
		currentLocalDate = newCurrentDate
	}
}
