$.get('../charts/srvreveneue', {
	RbChartProLineRevDash : "RbChartProLineRevDash"
}, function(response) {
	// --------------------start

	// google.charts.load('current', {'packages': ['line']});
	// google.charts.load('current', {
	// packages: ['corechart', 'gauge']
	// });
	//
	// google.charts.setOnLoadCallback(drawChart);
	//
	// function drawChart() {
	// var data = new google.visualization.DataTable();
	// data.addColumn('string', 'SERVICE_CODE');
	// data.addColumn('number', 'SUM');
	//
	// var rows = [];
	// $.each(response, function (key, value) {
	// rows.push([value.SERVICE_CODE, value.SUM])
	// })
	// data.addRows(rows);
	// var options = {
	// isStacked: 'relative',
	// height: 300,
	// is3D: false,
	// pieHole: 0.4,
	// chart: {
	// title: 'Daily Sales',
	// subtitle: '( Quantity )'
	// },
	// vAxis: {
	// title: 'Millions',
	// format: 'decimal',
	// slantedText: true,
	// 0: {textPosition: 'true'},
	// 1: {},
	// minValue: 0,
	// baselineColor: '#ccc',
	// gridlineColor: '#ccc'
	// },
	// "hAxis": {
	// "title": "Months ", showTextEvery: 0, gridlines: {count: 13},
	// slantedText: true,
	// slantedTextAngle: -45,
	// textStyle: {fontSize: 11},
	// width: 900,
	// height: 500,
	// color: '#333'
	// },
	// annotations: {
	// alwaysOutside: true
	// },
	// pointSize: 2,
	// backgroundColor: "transparent"
	// };
	//
	//
	// google.charts.setOnLoadCallback(function () {
	// // begin drawing
	// var chart = new
	// google.visualization.PieChart(document.getElementById('RbChartProLineRevDash'));
	//
	// chart.draw(data, options);
	// });

	// }

	// ne start

	// --------------------end

});

// ------------------------------------------
$.ajax({
	url : "../charts/srvreveneue",
	method : "GET",
	success : function(data) {
		console.log(data);
		var ict_unit = [];
		var efficiency = [];
		var coloR = [];

		var dynamicColors = function() {
			var r = Math.floor(Math.random() * 255);
			var g = Math.floor(Math.random() * 255);
			var b = Math.floor(Math.random() * 255);
			return "rgb(" + r + "," + g + "," + b + ")";
		};

		for ( var i in data) {
			ict_unit.push(data[i].SERVICE_CODE);
			efficiency.push(data[i].SUM);
			coloR.push(dynamicColors());
		}
		var chartData = {

			labels : ict_unit,
			datasets : [ {
				label : 'Revenue Sum ',
				// strokeColor:backGround,

				backgroundColor : coloR,

				borderColor : 'rgba(200, 200, 200, 0.75)',
				// hoverBackgroundColor: 'rgba(200, 200, 200, 1)',
				hoverBorderColor : 'rgba(200, 200, 200, 1)',
				data : efficiency
			} ]
		};

		var ctx = $("#areaChart");
		var barGraph = new Chart(ctx, {
			type : 'pie',
			data : chartData
		})
	},
	error : function(data) {

		console.log(data);
	},
});

// sevice wise payable
$.ajax({
	url : "../charts/srvpayable",
	method : "GET",
	success : function(data) {
		console.log(data);
		var ict_unit = [];
		var efficiency = [];
		var coloR = [];

		var dynamicColors = function() {
			var r = Math.floor(Math.random() * 255);
			var g = Math.floor(Math.random() * 255);
			var b = Math.floor(Math.random() * 255);
			return "rgb(" + r + "," + g + "," + b + ")";
		};

		for ( var i in data) {
			ict_unit.push(data[i].SERVICE_CODE);
			efficiency.push(data[i].SUM);
			coloR.push(dynamicColors());
		}
		var chartData = {

			labels : ict_unit,
			datasets : [ {
				label : 'Revenue Sum ',
				// strokeColor:backGround,

				backgroundColor : coloR,

				borderColor : 'rgba(200, 200, 200, 0.75)',
				// hoverBackgroundColor: 'rgba(200, 200, 200, 1)',
				hoverBorderColor : 'rgba(200, 200, 200, 1)',
				data : efficiency
			} ]
		};

		var ctx = $("#pieChart");
		var barGraph = new Chart(ctx, {
			type : 'pie',
			data : chartData,
			options : {
				legend : {
					display : true,
					position : 'right',
					labels : {
						fontColor : 'rgb(255, 99, 132)'
					}
				}
			}
		})
	},
	error : function(data) {

		console.log(data);
	},
});

// --------------------------
// sevice wise monthly
//$.ajax({
//	url : "../charts/srvmonthly",
//	method : "GET",
//	success : function(data) {
//		console.log(data);
//		var ict_unit = [];
//		var efficiency = [];
//		var coloR = [];
//		var label=[];
//		var dynamicColors = function() {
//			var r = Math.floor(Math.random() * 255);
//			var g = Math.floor(Math.random() * 255);
//			var b = Math.floor(Math.random() * 255);
//			return "rgb(" + r + "," + g + "," + b + ")";
//		};
//
//		for ( var i in data) {
//			ict_unit.push(data[i].SERVICE_CODE);
//			efficiency.push(data[i].SUM);
//			label.push(data[i].IMP_MONTH);
//			coloR.push(dynamicColors());
//		}
//		var chartData = {
//
//			labels : label,
//			datasets : [ {
//				label : ict_unit,
//				// strokeColor:backGround,
//			//	xAxisID : label,
//
//				backgroundColor : coloR,
//
//				borderColor : 'rgba(200, 200, 200, 0.75)',
//				// hoverBackgroundColor: 'rgba(200, 200, 200, 1)',
//				hoverBorderColor : 'rgba(200, 200, 200, 1)',
//				data : efficiency
//			} ]
//		};
//		
//		var data = {
//				  labels: ["Chocolate", "Vanilla", "Strawberry"],
//				  datasets: [{
//				    label: "Blue",
//				    backgroundColor: "blue",
//				    data: [3, 7, 4]
//				  }, {
//				    label: "Red",
//				    backgroundColor: "red",
//				    data: [4, 3, 5]
//				  }, {
//				    label: "Green",
//				    backgroundColor: "green",
//				    data: [7, 2, 6]
//				  }]
//				};
//		
//
//		var ctx = $("#barmonthly");
//		var barGraph = new Chart(ctx, {
//			type : 'bar',
//			data : chartData,
//			options : {
//				scales: {
//			  		xAxes: [{stacked: true}],
//			    	yAxes: [{
//			      	stacked: true,
//			      	ticks: {
//			        	beginAtZero: true 
//			         }
//			      }]
//			    },
//				legend : {
//					display : true,
//					position : 'right',
//					labels : {
//						fontColor : 'rgb(255, 99, 132)'
//					}
//				}
//			}
//		})
//	},
//	error : function(data) {
//
//		console.log(data);
//	},
//});


//--test


//sevice wise monthly
var progress = document.getElementById('animationProgress');
progress.value=1;
$('.overlay').fadeIn();
$.ajax({
	beforeSend: function(XMLHttpRequest)
	  {$('.overlay').fadeIn();},
	url : "../charts/srvmonthly",
	method : "GET",
	success : function(data) {
		console.log(data);
		var ict_unit = [];
		var efficiency = [];
		var coloR = [];
		var label=[];
		var dynamicColors = function() {
			var r = Math.floor(Math.random() * 255);
			var g = Math.floor(Math.random() * 255);
			var b = Math.floor(Math.random() * 255);
			return "rgb(" + r + "," + g + "," + b + ")";
		};

		for ( var i in data) {
			ict_unit.push(data[i].SERVICE_CODE);
			efficiency.push(data[i].SUM);
			label.push(data[i].IMP_MONTH);
			coloR.push(dynamicColors());
		}
		 var labels = data.map(function(e) {
	         return e.NEP_MON;
	      }),
	      source1 = data.map(function(e) {
	         return e.PREV_YEAR;
	      }),
	      source2 = data.map(function(e) {
	         return e.THIS_YEAR_REV;
	      });
		 
		
		 var ctx = document.getElementById("barmonthly").getContext("2d");
		   var myChart = new Chart(ctx, {
		      type: 'line',
		      data: {
		         labels: labels,
		         datasets: [{
		            label: "Previous YEAR",
		            data: source1,
		            borderWidth: 2,
		            backgroundColor: "rgba(6, 167, 125, 0.1)",
		            borderColor: "rgba(6, 167, 125, 1)",
		            pointBackgroundColor: "rgba(225, 225, 225, 1)",
		            pointBorderColor: "rgba(6, 167, 125, 1)",
		            pointHoverBackgroundColor: "rgba(6, 167, 125, 1)",
		            pointHoverBorderColor: "#fff"
		         }, {
		            label: "This Year",
		            data: source2,
		            borderWidth: 2,
		            backgroundColor: "rgba(246, 71, 64, 0.1)",
		            borderColor: "rgba(246, 71, 64, 1)",
		            pointBackgroundColor: "rgba(225, 225, 225, 1)",
		            pointBorderColor: "rgba(246, 71, 64, 1)",
		            pointHoverBackgroundColor: "rgba(246, 71, 64, 1)",
		            pointHoverBorderColor: "#fff"
		         }]
		      },
		      options: {
	                title:{
	                    display:true,
	                    text: "Chart.js Line Chart - Animation Progress Bar"
	                },
	                animation: {
	                    duration: 2,
	                    onProgress: function(animation) {
	                        progress.value = animation.currentStep / animation.numSteps;
	                    },
	                    onComplete: function(animation) {
	                        window.setTimeout(function() {
	                            progress.value = 10;
	                            $('.overlay').fadeOut();
	                        }, 2000);
	                    }
	                }
	            }
		   });
		  
		//--ajax close
	},
	error : function(data) {

		console.log(data);
	},
});
