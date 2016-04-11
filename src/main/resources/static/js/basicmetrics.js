
var thisHost;

function beginCheck(localhost) {
	thisHost = localhost;
	var domain = encodeURIComponent($('#website').val());
	var url = thisHost + "/MozMetrics/fetchBasicMetrics?domain="+domain;

	$('#entryForm').hide();
	$('#spinner').show();
	
	$.get(url, processBasicMetrics);
}

function processBasicMetrics(data) {
	//alert(data);
	$('#spinner').hide();
	
	if (data == "error") {
		$("#anotherRunButtonDiv").show();
		$("#errorSection").show();
	} else {	
		var json = JSON.parse(data);
		
		$("#results").show();
		$("#buttonDiv").show();
		
		var siteTitle = json.ut;
		var siteUrl = json.uu;
		var equityLinks = json.ueid;
		var allLinks = json.uid;
		var mozRank10 = json.umrp;
		var mozRankRaw = json.umrr;
		var pageAuthority = json.upa;
		var domainAuthority = json.pda;
		
		$("#siteTitle").html(siteTitle);
		$("#siteUrl").html(siteUrl);
		$("#equityLinks").html(equityLinks);
		$("#allLinks").html(allLinks);
		$("#mozRank10").html(mozRank10);
		//$("#mozRankRaw").html(mozRankRaw);
		$("#pageAuthority").html(pageAuthority);
		$("#domainAuthority").html(domainAuthority);
		
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