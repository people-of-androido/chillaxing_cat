package com.hipaduck.chillaxingcat.data.repository

import com.google.gson.Gson
import com.hipaduck.base.domain.model.ResultModel
import com.hipaduck.base.util.loge
import com.hipaduck.chillaxingcat.BuildConfig
import com.hipaduck.chillaxingcat.data.dao.HolidayDao
import com.hipaduck.chillaxingcat.data.entity.Holiday
import com.hipaduck.chillaxingcat.data.remote.api.HolidayApi
import com.hipaduck.chillaxingcat.data.remote.model.toDateModel
import com.hipaduck.chillaxingcat.domain.model.DateModel
import com.hipaduck.chillaxingcat.domain.repository.HolidayRepository
import java.text.SimpleDateFormat
import java.util.*

internal class HolidayRepositoryImpl(
    private val holidayDao: HolidayDao,
    private val holidayApi: HolidayApi,
    private val gson: Gson
) : HolidayRepository {

    override suspend fun getHolidayWithPeriod(
        startPeriod: String,
        endPeriod: String
    ): ResultModel<List<DateModel>> {
        val periodIdDateFormat = SimpleDateFormat("yyyyMM")
        val monthDateFormat = SimpleDateFormat("MM")
        val yearDateFormat = SimpleDateFormat("yyyy")

        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        startDate.time = periodIdDateFormat.parse(startPeriod)
        endDate.time = periodIdDateFormat.parse(endPeriod)

        val holidayList = arrayListOf<DateModel>()

        while (startDate <= endDate) {
            //db 조회
            val periodId = periodIdDateFormat.format(startDate.time)

            val localList : List<Holiday> = try {
                holidayDao.getHolidayWithPeriod(periodId)
            } catch (e: Exception) {
                return ResultModel(1, "request error ${e.message}", arrayListOf())
            }

            if (localList.isNotEmpty()) {
                for (holiday in localList) {
                    holidayList.add(DateModel.fromHoliday(holiday))
                }
            } else {
                //서버 조회
                val year = yearDateFormat.format(startDate.time)
                val month = monthDateFormat.format(startDate.time)

                val response = holidayApi.requestHoliday(
                    year = year,
                    month = month,
                    BuildConfig.HOLIDAY_SERVER_KEY
                )

                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        val responseList = responseBody.response.body.item.toDateModel()
                        val insertList = arrayListOf<Holiday>()
                        for (date in responseList) {
                            holidayList.add(date)
                            insertList.add(Holiday.fromDomainModel(date))
                        }

                        try {
                            holidayDao.insertAll(insertList)
                        } catch (e: Exception) {
                            loge("getHolidayWithPeriod: adding error", e)
                        }
                    } ?: run {
                        loge("getHolidayWithPeriod: response body is null")
                        return ResultModel(1, "request error", arrayListOf())
                    }
                } else {
                    return ResultModel(1, "request error ${response.errorBody()}", arrayListOf())
                }
            }

            startDate.add(Calendar.MONTH, 1)
        }

        return ResultModel(0, "success", holidayList)
    }
}