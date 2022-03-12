package com.dalhousie.MealStop.customer.customersearch;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserSearch
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
