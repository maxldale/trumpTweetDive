package com.maxl.date

import java.time.LocalDate
import java.util.Date

import org.scalatest.FunSuite

class TestDateCounter extends FunSuite {

	test("Different constructors [using date, long, and string] should give us same current date") {
		val currTime = System.currentTimeMillis()
		val currDate = new Date(currTime)
		val currDateString = currDate.toString
		val longDateCounter = new DateCounter(currTime)
		val dateDateCounter = new DateCounter(currDate)
		val stringDateCounter = new DateCounter(currDateString)
		val longDateCounterCurrDate = longDateCounter.getCurrentDate
		val dateDateCounterCurrDate = dateDateCounter.getCurrentDate
		val stringDateCounterCurrDate = stringDateCounter.getCurrentDate

		assert(longDateCounterCurrDate == dateDateCounterCurrDate)
		assert(dateDateCounterCurrDate == stringDateCounterCurrDate)
		assert(stringDateCounterCurrDate == longDateCounterCurrDate)
	}

	test("Increment Date should give us dates one day previous continuously") {
		val currTime = System.currentTimeMillis()
		val currDate = new Date(currTime)
		val currLocalDate: LocalDate = DateUtil.convertDateToLocalDate(currDate)
		val prevLocalDate: LocalDate = currLocalDate.minusDays(1)
		val prevPrevLocalDate = prevLocalDate.minusDays(1)
		val prevDate = DateUtil.convertLocalDateToDate(prevLocalDate)
		val prevPrevDate = DateUtil.convertLocalDateToDate(prevPrevLocalDate)
		val dateCounter = new DateCounter(currDate)
		dateCounter.incrementDate()
		val prevRes = dateCounter.getCurrentDate
		dateCounter.incrementDate()
		val prevPrevRes = dateCounter.getCurrentDate
		assert(prevDate == prevRes)
		assert(prevPrevDate == prevPrevRes)
	}

}
