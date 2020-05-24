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



//sevice wise payable
$.ajax({
	url : "../bar-charts/subTeamSolveUnsolve",
	method : "GET",
	success : function(data) {
		console.log(data);		
		
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
	 var coloRF = [];
	 var coloRN = [];
	 var coloRC = [];
	 var coloRS = [];
		for ( var i in data) {	
			
			 
			if(!labels.includes(data[i].SUB_TEAM_CODE)){								
				labels.push(data[i].SUB_TEAM_CODE);				
		}
			
			if(data[i].SOLVE_FLAG == 'FORWARDED'){ 
				
				forward.push(data[i].SCOUNT);
				coloRF.push(dynamicColors());
			}
			
	if(data[i].SOLVE_FLAG=='NEW'){
		
		newtoken.push(data[i].SCOUNT);
		coloRN.push(dynamicColors());
	}
	if(data[i].SOLVE_FLAG == 'CLOSED'){ 
		
		closed.push(data[i].SCOUNT);
		coloRC.push(dynamicColors());
	}
			
	if(data[i].SOLVE_FLAG == 'SOLVED'){ 
		
		solved.push(data[i].SCOUNT);
		coloRS.push(dynamicColors());
	}
			
			
		}
		

		var arrydata2 = {
		  labels: labels,
		  datasets: [
		    {
		      label: "NEW",
		      backgroundColor: coloRN,
		      data: newtoken,
		      stack: 1
		    },
		    {
		      label: "CLOSED",
		      backgroundColor:  coloRC,
		      data: closed,
		      stack: 2
		    },
		    {
		      label: "FORWARDED",
		      backgroundColor: coloRF,
		      data: forward,
		      stack: 3
		    },
		    {
		        label: "SOLVED",
		        backgroundColor: coloRS,
		        data: solved,
		        stack: 4
		      }
		    
		  ]
		};

		var ctx = document.getElementById("subTeamSolveUnsolve").getContext("2d");
		new Chart(ctx, {
		  type: 'groupableBar',
		  data: arrydata2,
		  options: {
		    scales: {
		      yAxes: [{
		        ticks: {
		          max: 30,
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



