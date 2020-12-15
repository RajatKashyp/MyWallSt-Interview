package com.interview.mywallst_assignment

import com.interview.mywallst_assignment.data_payload.MeteorsApiResponse
import com.interview.mywallst_assignment.data_payload.MeteorsApiResponseItem
import com.interview.mywallst_assignment.ui.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ResponseUnitTest {

    lateinit var validResponseItem: MeteorsApiResponseItem
    lateinit var invalidYear: MeteorsApiResponseItem
    lateinit var yearBefore1900: MeteorsApiResponseItem
    lateinit var invalidLatLong: MeteorsApiResponseItem
    lateinit var emptyFields: MeteorsApiResponseItem
    lateinit var yearBefore: MeteorsApiResponse
    lateinit var yearAfter: MeteorsApiResponse
    lateinit var activity: MainActivity

    @Before
    fun SetupData(){

        validResponseItem = MeteorsApiResponseItem(
            ":@computed_region_cbhk_fwbd",
            ":@computed_region_nnqa_25f4",
            "Fell",
            "1",
            "21",
            "Aachen",
            "Valid",
            "L5",
            "50.775000",
            "6.083330",
            "1920-01-01T00:00:00.000"
        )
        invalidYear = MeteorsApiResponseItem(
            ":@computed_region_cbhk_fwbd",
            ":@computed_region_nnqa_25f4",
            "Fell",
            "1",
            "21",
            "Aachen",
            "Valid",
            "L5",
            "50.775000",
            "6.083330",
            "1920-0"
        )
        yearBefore1900 = MeteorsApiResponseItem(
            ":@computed_region_cbhk_fwbd",
            ":@computed_region_nnqa_25f4",
            "Fell",
            "1",
            "21",
            "Aachen",
            "Valid",
            "L5",
            "50.775000",
            "6.083330",
            "1820-01-01T00:00:00.000"
        )
        invalidLatLong = MeteorsApiResponseItem(
            ":@computed_region_cbhk_fwbd",
            ":@computed_region_nnqa_25f4",
            "Fell",
            "1",
            "21",
            "Aachen",
            "Valid",
            "L5",
            "5075000",
            "683330",
            "1920-01-01T00:00:00.000"
        )
        emptyFields = MeteorsApiResponseItem("", "", "", "", "", "", "", "", "", "", "")

        yearBefore = MeteorsApiResponse()
        yearBefore.add(yearBefore1900)

        yearAfter = MeteorsApiResponse()
        yearAfter.add(validResponseItem)

        activity = MainActivity()
    }

    @Test
    fun validFormatYear_Check(){
        val year = activity.formatYearFromResponse(validResponseItem.year)
        assertEquals("1920", year)
    }

    @Test
    fun invalidFormatYear_Check(){
        val year = activity.formatYearFromResponse(invalidYear.year)
        assertEquals("Date format Incorrect", year)
    }

    @Test
    fun emptyFormatYear_Check(){
        val year = activity.formatYearFromResponse(emptyFields.year)
        assertEquals("Date format Incorrect", year)
    }

    @Test
    fun checkForYearBefore_Check(){
        val actualCheck = activity.checkForYear(yearBefore, arrayListOf(), 0)
        assertEquals(false, actualCheck)
    }

    @Test
    fun checkForYearAfter_Check(){
        val actualCheck = activity.checkForYear(yearAfter, arrayListOf(), 0)
        assertEquals(true, actualCheck)
    }

}