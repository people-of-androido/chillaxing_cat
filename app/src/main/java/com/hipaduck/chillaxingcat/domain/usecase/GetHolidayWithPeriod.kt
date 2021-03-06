package com.hipaduck.chillaxingcat.domain.usecase

import com.hipaduck.base.domain.Result
import com.hipaduck.base.domain.ResultHandler
import com.hipaduck.base.domain.model.ResultModel
import com.hipaduck.chillaxingcat.domain.model.DateModel
import com.hipaduck.chillaxingcat.domain.repository.HolidayRepository
import kotlin.Exception

class GetHolidayWithPeriod(
    private val repository: HolidayRepository,
    private val resultHandler: ResultHandler
) {
    suspend operator fun invoke(startPeriod: String, endPeriod: String): Result<List<DateModel>> {
        val result: ResultModel<List<DateModel>>
        try {
            result = repository.getHolidayWithPeriod(startPeriod, endPeriod)
        } catch (e: Exception) {
            e.printStackTrace()
            return resultHandler.handleFailure(e)
        }

        return try {
            if (result.code == 0) {
                result.data?.let {
                    resultHandler.handleSuccess(it)
                } ?: run {
                    resultHandler.handleFailure("result data is null")
                }
            } else {
                resultHandler.handleFailure(result.message)
            }
        } catch (e: Exception) {
            resultHandler.handleFailure(e)
        }
    }
}