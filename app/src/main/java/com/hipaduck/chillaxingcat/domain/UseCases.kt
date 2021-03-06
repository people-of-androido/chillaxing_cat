package com.hipaduck.chillaxingcat.domain

import com.hipaduck.chillaxingcat.domain.usecase.*

data class UseCases (
    val addDayOff: AddDayOff,
    val getDayOff: GetDayOff,
    val removeDayOff: RemoveDayOff,
    val getHolidayWithPeriod: GetHolidayWithPeriod,
    val getRestTime: GetRestTime,
    val addRestTime: AddRestTime,
    val findOutRestDaysInMonth: FindOutRestDaysInMonth, // 월을 주면(eg. 202203) 리스트로 조금이라도 쉬었던 날에 대한 목록을 돌려주는 UseCase 필요
    val getNotificationStatus: GetNotificationStatus,
    val putNotificationStatus: PutNotificationStatus,
    val getReminderText: GetReminderText,
    val getReminderTime: GetReminderTime,
    val getGoalRestTime: GetGoalRestTime,
    val putReminderText: PutReminderText,
    val putReminderTime: PutReminderTime,
    val putGoalRestingTime: PutGoalRestingTime,
    val isRequiredValuesEntered: IsRequiredValuesEntered,
    val writeRestTotalTime: WriteRestTotalTime,
    val getTodayDate: GetTodayDate,
    val putTodayDate: PutTodayDate,
    val getTodayHistory: GetTodayHistory,
    val putTodayHistory: PutTodayHistory,
    val getTodayStatus: GetTodayStatus,
    val putTodayStatus: PutTodayStatus,
)