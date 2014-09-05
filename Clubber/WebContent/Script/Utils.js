/******STATUS CODES*****/
//Auction status ids:
var ACTIVE_AUCTION_STATUS_ID=1;
var INACTIVE_AUCTION_STATUS_ID= 2;
var NOT_RELEVANT_AUCTION_STATUS_ID= 3;

//Offer status ids:
var ACCEPTED_OFFER_STATUS_ID=1; 
var PENDING_OFFER_STATUS_ID= 2;
var DENIED_OFFER_STATUS_ID=3; 
var NOT_RELEVANT_OFFER_STATUS_ID= 4;
/***********************/





function convert12to24(timeStr)
{
    var meridian = timeStr.substr(timeStr.length-2).toLowerCase();;
    var hours =  timeStr.substr(0, timeStr.indexOf(':'));
    var minutes = timeStr.substring(timeStr.indexOf(':')+1, timeStr.lastIndexOf(':'));
    if (meridian=='pm')
    {
        if (hours!=12)
        {
            hours=hours*1+12;
        }
        else
        {
            hours = (minutes!='00') ? '0' : '24' ;
        }
    }

    return hours+':'+minutes;
}

function formattedDate(date) {
    var d = new Date(date || Date.now()),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [day, month, year].join('/');
}

function convertLongToTimeString(longTime)
{
	return String(longTime).slice(0,2) + ":" + String(longTime).slice(2,4);
}

function convertStringToLongTime(stringTime)
{
	return parseInt(stringTime.slice(0,2)) * 10000 + parseInt(stringTime.slice(3,5)) * 100;
}

function convertTimeStampFormat(timestamp)
{
	var d = new Date(timestamp || Date.now()),
    year = d.getFullYear();
	var startIndex= timestamp.indexOf(year)+5;
	var time= timestamp.substr(startIndex);
	
	return (formattedDate(timestamp) + ' '+ convert12to24(time));
}


