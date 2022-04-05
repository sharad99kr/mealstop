package com.dalhousie.MealStop.customer.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.dalhousie.MealStop.common.CommonConstants;
import java.util.Date;

@Component
public class UserSearch
{
    @DateTimeFormat(pattern = CommonConstants.CUSTOMER_SEARCH_DATE_FORMAT)
    private Date startDate;

    @DateTimeFormat(pattern = CommonConstants.CUSTOMER_SEARCH_DATE_FORMAT)
    private Date endDate;

    public Date getStartDate()
    {
        return this.startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate=startDate;
    }

    public Date getEndDate()
    {
        return this.endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate=endDate;
    }
}
