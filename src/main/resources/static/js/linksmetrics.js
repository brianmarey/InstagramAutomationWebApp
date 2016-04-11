
var thisHost;

function beginCheck(localhost) {
	thisHost = localhost;
	var domain = encodeURIComponent($('#website').val());
	var url = thisHost + "/MozMetrics/fetchLinksMetrics?domain="+domain;

	$('#entryForm').hide();
	$('#spinner').show();
	
	$.get(url, processLinksMetrics);
}

function processLinksMetrics(data) {
	//alert(data);
	
	$('#spinner').hide();
	
	if (data == "error") {
		$("#anotherRunButtonDiv").show();
		$("#errorSection").show();
		
	} else {
		$("#results").show();
		$("#buttonDiv").show();

		var json = JSON.parse(data);
		var linksBlock = "";
		
		if (json && json.length > 0) {
			var title = json[0].luut;
			var url = json[0].luuu;
			$("#url").html(url);
			$("#title").html(url);
		}
		
		for (var i=0;i<json.length;i++) {
			var obj = json[i];
			var url = obj.uu;
			linksBlock+="<p>";
			linksBlock+=url;
			linksBlock+"</p>"
		}

		$("#allLinks").html(linksBlock);		
		$("#anotherRunButtonDiv").show();
	}
}

function anotherRun() {
	$("#results").hide();
	$('#entryForm').show();
	$('#website').val("");
	$('#buttonDiv').show();
	$("#errorSection").hide();
	$("#anotherRunButtonDiv").hide();
}