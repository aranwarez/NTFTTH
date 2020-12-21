/**
 * 
 */

$('.nepali-calendar').nepaliDatePicker();


async function getenglishdate() {
    const result = await $.ajax({
        url: '../common/getenglishdate',
        type: 'GET',
        data: { fromdate:$('#QFROM_DT').val(),todate:$('#QTO_DT').val()} 
    });

    return result;
}


async function reloadgraph() {
    const result = await $.ajax({
    	url : "../charts/srvreveneue",
    	method : "GET"
 //   	data: { fromdate: fromdate, todate : todate} 
    	
    });

    return result;
}


function getdashboard(){
	//alert(getenglishdate('2077/10/12'));
	//getenglishdate('2077/10/12');
	
	getenglishdate().then((message) => { 
	    console.log(message);
	    removeoldcanvas('areaChart','teamtrouble-container');
	    //srvwisetroubleticket
	    removeoldcanvas('pieChart','srvwisetroubleticket');
	    //groupbysolveflag
	    removeoldcanvas('barmonthly','groupbysolveflag');
	    //ticketvsubteam
	    removeoldcanvas('subTeamSolveUnsolve','ticketvsubteam');
	    //tickettypevssubteam
	    removeoldcanvas('subTeamServieType','tickettypevssubteam');
	    teamtroubleticket(message[0],message[1]);
	    subteamsolveflag(message[0],message[1]);
	    tickettypevssubtype(message[0],message[1]);
	
	}).catch((message) => { 
	    console.log(message);
	   // console.log('wwww');
	});
	
	
//	reloadgraph().then((message) => { 
//	    console.log('eeeee');
//	    console.log(message);
//	alert(message.responseText);
//	}).catch((message) => { 
//	    console.log(message);
//	    console.log('wwww');
//	});
	
	
}

//remove old graph if exist
function removeoldcanvas(canvasid,divid){
	$('#'+canvasid).remove(); 
	$('#'+divid).append('<canvas id="'+canvasid+'" style="height: 174px; width: 348px;"	height="217" width="435"></canvas>');
}


