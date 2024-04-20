Chart.defaults.groupableBar = Chart.helpers.clone(Chart.defaults.bar);

var helpers = Chart.helpers;
Chart.controllers.groupableBar = Chart.controllers.bar.extend({
  calculateBarX: function (index, datasetIndex) {
    // position the bars based on the stack index
    var stackIndex = this.getMeta().stackIndex;
    return Chart.controllers.bar.prototype.calculateBarX.apply(this, [index, stackIndex]);
  },

  hideOtherStacks: function (datasetIndex) {
    var meta = this.getMeta();
    var stackIndex = meta.stackIndex;

    this.hiddens = [];
    for (var i = 0; i < datasetIndex; i++) {
      var dsMeta = this.chart.getDatasetMeta(i);
      if (dsMeta.stackIndex !== stackIndex) {
        this.hiddens.push(dsMeta.hidden);
        dsMeta.hidden = true;
      }
    }
  },

  unhideOtherStacks: function (datasetIndex) {
    var meta = this.getMeta();
    var stackIndex = meta.stackIndex;

    for (var i = 0; i < datasetIndex; i++) {
      var dsMeta = this.chart.getDatasetMeta(i);
      if (dsMeta.stackIndex !== stackIndex) {
        dsMeta.hidden = this.hiddens.unshift();
      }
    }
  },

  calculateBarY: function (index, datasetIndex) {
    this.hideOtherStacks(datasetIndex);
    var barY = Chart.controllers.bar.prototype.calculateBarY.apply(this, [index, datasetIndex]);
    this.unhideOtherStacks(datasetIndex);
    return barY;
  },

  calculateBarBase: function (datasetIndex, index) {
    this.hideOtherStacks(datasetIndex);
    var barBase = Chart.controllers.bar.prototype.calculateBarBase.apply(this, [datasetIndex, index]);
    this.unhideOtherStacks(datasetIndex);
    return barBase;
  },

  getBarCount: function () {
    var stacks = [];

    // put the stack index in the dataset meta
    Chart.helpers.each(this.chart.data.datasets, function (dataset, datasetIndex) {
      var meta = this.chart.getDatasetMeta(datasetIndex);
      if (meta.bar && this.chart.isDatasetVisible(datasetIndex)) {
        var stackIndex = stacks.indexOf(dataset.stack);
        if (stackIndex === -1) {
          stackIndex = stacks.length;
          stacks.push(dataset.stack);
        }
        meta.stackIndex = stackIndex;
      }
    }, this);

    this.getMeta().stacks = stacks;
    return stacks.length;
  },
});


//load on startup
//tickettypevssubtype('NTFTTH399000033');

//sevice wise payable
function tickettypevssubtype(FTTHDATA){
$.ajax({
	url : "../bar-charts/getCDRDATA",
	method : "GET",
	data: { DATANUMBER: FTTHDATA} ,
	success : function(data) {
	//	console.log(data);		
		
		var dynamicColors = function() {
			var r = Math.floor(Math.random() * 255);
			var g = Math.floor(Math.random() * 255);
			var b = Math.floor(Math.random() * 255);
			return "rgb(" + r + "," + g + "," + b + ")";
		};

		
	var labels = [];	
	var forward = [];
	 var newtoken = [];
	 var closed = [];
	 var solved = []; 
	 
	 //--
	 
	 
	 for ( var i in data) {
			if (!labels.includes(data[i].session_Date)) {
				// teampointer=data[i].SUB_TEAM_CODE;
				labels.push(data[i].session_Date);
				// if team doesn't exist
				// taking count of existing ticket inside one team
				var newcount = 0;
				var closecount = 0;
				var forwardcount = 0;
				var solvedcount = 0;
				for ( var j in data) {

					if (data[j].session_Date == data[i].session_Date) {

						
							forwardcount = data[j].download;
						
							newcount = data[j].upload;
											} // if for teaminsdie loop
					
					
				}// for loop

				// pusing all the count for one team
				forward.push(forwardcount);
			

				//
				newtoken.push(newcount);
			
				
				
			}
			
		}
	
		//--
	 
	 var newcolor=dynamicColors();
	 var closecolor=dynamicColors();
	 
	 

		var arrydata3 = {
		  labels: labels,
		  datasets: [
		    {
		      label: "Download",
		      backgroundColor: newcolor,
		      data: forward,
		      stack: 1
		    },
		    {
		      label: "Upload",
		      backgroundColor:  closecolor,
		      data: newtoken,
		      stack: 2
		    }
		    
		  ]
		};

		var ctx = document.getElementById("CDRDATA").getContext("2d");
		new Chart(ctx, {
		  type: 'groupableBar',
		  data: arrydata3,
		  options: {
		    scales: {
		      yAxes: [{
		        ticks: {
		        	beginAtZero: true
		        //	stepSize: 50,
		        },
		        stacked: true,
		      }]
		    }
		  }
		});
		

		
	},
	error : function(data) {

		console.log(data);
	},
});
}

//remove old graph if exist
function removeoldcanvas(canvasid,divid){
	$('#'+canvasid).remove(); 
	$('#'+divid).append('<canvas id="'+canvasid+'" 	height="200" width="800"></canvas>');
}




