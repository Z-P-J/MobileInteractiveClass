<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		查询考勤
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body form">
				<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form"
					  action="#">
					<input type="hidden" id="action" name="action" value="statistic_record"/>
					<div class="form-body">
						<div class="form-group">
							<label id="page_title" class="col-md-12">
								请在下面的条件设置里选择对应的条件，然后点【开始统计】按钮开始统计
							</label>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">
								请选择时间段
							</label>
							<div class="col-md-3">
								<span>从</span>
								<div class="input-group date form_datetime">
									<input type="text" id="time_from" name="time_from" size="16"
										   class="form-control" readonly="true">
									<span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button> </span>
								</div>
								<span>到</span>
								<div class="input-group date form_datetime">
									<input type="text" id="time_to" name="time_to" size="16"
										   class="form-control" readonly="true">
									<span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button> </span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">
								请选择统计间隔
							</label>
							<div class="col-md-3">
								<select class="table-group-action-input form-control input-medium"
										id="time_interval" name="time_interval">
									<option value="hour">
										按小时
									</option>
									<option value="day" selected="selected">
										按每天
									</option>
									<option value="month">
										按每月
									</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-1" style="display: none;">
								<input type="checkbox" class="icheck" id="address_select"
									   name="address_select" data-checkbox="icheckbox_minimal-grey"
									   style="border: 0px solid red;"/>
							</label>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button id="close_btn" type="button" class="btn btn-default" data-dismiss="modal">
		取消
	</button>
	<button type="button" class="btn btn-success" onclick="statistic();">
		开始统计
	</button>
</div>
<div id="result_image_div">
	<div id="result_image" style="height: 228px;">
	</div>
</div>

<script>


	var ChartsFlotcharts = function () {
		// bar chart:
		var data = GenerateSeries(0);

		function GenerateSeries(added) {
			var data = [];
			var start = 100 + added;
			var end = 200 + added;

			for (i = 1; i <= 20; i++) {
				var d = Math.floor(Math.random() * (end - start + 1) + start);
				data.push([i, d]);
				start++;
				end++;
			}
			return data;
		}

		var options = {
			xaxis: {
				tickLength: 0,
				tickDecimals: 0,
				mode: "categories",
				min: 0,
				font: {
					lineHeight: 14,
					style: "normal",
					variant: "small-caps",
					color: "#6F7B8A"
				}
			},
			yaxis: {
				ticks: 5,
				tickDecimals: 0,
				tickColor: "#eee",
				font: {
					lineHeight: 14,
					style: "normal",
					variant: "small-caps",
					color: "#6F7B8A"
				}
			},
			grid: {
				hoverable: true,
				clickable: true,
				tickColor: "#eee",
				borderColor: "#eee",
				borderWidth: 1
			}
		};
		var drawChart = function (data) {
			if ($('#result_image').size() !== 0) {
				initBarCharts(data);
			}
		}
		var initBarCharts = function (data) {
			if (data == undefined) {
				data = [{
					"year": 2009,
					"income": 23.5,
					"expenses": 18.1
				}, {
					"year": 2010,
					"income": 26.2,
					"expenses": 22.8
				}, {
					"year": 2011,
					"income": 30.1,
					"expenses": 23.9
				}, {
					"year": 2012,
					"income": 29.5,
					"expenses": 25.1
				}, {
					"year": 2013,
					"income": 30.6,
					"expenses": 27.2,
					"dashLengthLine": 5
				}, {
					"year": 2014,
					"income": 34.1,
					"expenses": 29.9,
					"dashLengthColumn": 5,
					"alpha": 0.2,
					"additional": "(projection)"
				}];
			}

			console.log(data);

			// AmCharts.makeChart("result_image", {
			//     "type": "serial",
			//     "theme": "light",
			//     "pathToImages": Metronic.getGlobalPluginsPath() + "amcharts/amcharts/images/",
			//     "autoMargins": false,
			//     "marginLeft": 30,
			//     "marginRight": 8,
			//     "marginTop": 10,
			//     "marginBottom": 26,
			//
			//     "fontFamily": 'Open Sans',
			//     "color": '#888',
			//
			//     "dataProvider": data,
			//     "valueAxes": [{
			//         "axisAlpha": 0,
			//         "position": "left"
			//     }],
			//     "startDuration": 1,
			//     "graphs": [{
			//         "alphaField": "alpha",
			//         "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
			//         "dashLengthField": "dashLengthColumn",
			//         "fillAlphas": 1,
			//         "title": "Income",
			//         "type": "column",
			//         "valueField": "count"
			//     }, {
			//         "balloonText": "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>",
			//         "bullet": "round",
			//         "dashLengthField": "dashLengthLine",
			//         "lineThickness": 3,
			//         "bulletSize": 7,
			//         "bulletBorderAlpha": 1,
			//         "bulletColor": "#FFFFFF",
			//         "useLineColorForBulletBorder": true,
			//         "bulletBorderThickness": 3,
			//         "fillAlphas": 0,
			//         "lineAlpha": 1,
			//         "title": "Expenses",
			//         "valueField": "expenses"
			//     }],
			//     "categoryField": "datetime",
			//     "categoryAxis": {
			//         "gridPosition": "start",
			//         "axisAlpha": 0,
			//         "tickLength": 0
			//     }
			// });

			am4core.ready(function() {

				// Themes begin
				am4core.useTheme(am4themes_animated);
				// Themes end

				// Create chart instance
				var chart = am4core.create("result_image", am4charts.XYChart);
				// chart.scrollbarX = new am4core.Scrollbar();

				// Add data
				chart.data = data;
//             chart.data = [{
//                 "country": "USA",
//                 "visits": 3025
//             }, {
//                 "country": "China",
//                 "visits": 1882
//             }, {
//                 "country": "Japan",
//                 "visits": 1809
//             }, {
//                 "country": "Germany",
//                 "visits": 1322
//             }, {
//                 "country": "UK",
//                 "visits": 1122
//             }, {
//                 "country": "France",
//                 "visits": 1114
//             }, {
//                 "country": "India",
//                 "visits": 984
//             }, {
//                 "country": "Spain",
//                 "visits": 711
//             }, {
//                 "country": "Netherlands",
//                 "visits": 665
//             }, {
//                 "country": "Russia",
//                 "visits": 580
//             }, {
//                 "country": "South Korea",
//                 "visits": 443
//             }, {
//                 "country": "Canada",
//                 "visits": 441
//             }];

				// Create axes
				var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
				categoryAxis.dataFields.category = "datetime";
				categoryAxis.renderer.grid.template.location = 0;
				categoryAxis.renderer.minGridDistance = 30;
				categoryAxis.renderer.labels.template.horizontalCenter = "right";
				categoryAxis.renderer.labels.template.verticalCenter = "middle";
				categoryAxis.renderer.labels.template.rotation = 270;
				categoryAxis.tooltip.disabled = true;
				categoryAxis.renderer.minHeight = 110;

				var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
				valueAxis.renderer.minWidth = 50;

				// Create series
				var series = chart.series.push(new am4charts.ColumnSeries());
				series.sequencedInterpolation = true;
				series.dataFields.valueY = "count";
				series.dataFields.categoryX = "datetime";
				series.tooltipText = "[{categoryX}: bold]{valueY}[/]";
				series.columns.template.strokeWidth = 0;

				// series.tooltip.pointerOrientation = "vertical";

				series.columns.template.column.cornerRadiusTopLeft = 10;
				series.columns.template.column.cornerRadiusTopRight = 10;
				series.columns.template.column.fillOpacity = 0.8;

				// on hover, make corner radiuses bigger
				var hoverState = series.columns.template.column.states.create("hover");
				hoverState.properties.cornerRadiusTopLeft = 0;
				hoverState.properties.cornerRadiusTopRight = 0;
				hoverState.properties.fillOpacity = 1;

				series.columns.template.adapter.add("fill", function(fill, target) {
					return chart.colors.getIndex(target.dataItem.index);
				});

				// Cursor
				chart.cursor = new am4charts.XYCursor();

			}); // end am4core.ready()
		}
		return {
			//main function to initiate the module
			init: function () {
				Metronic.addResizeHandler(function () {
					// Charts.initPieCharts();
				});
			},
			initBarCharts: function () {
				initBarCharts();
			},
			drawChart: function (data) {
				drawChart(data);
			}
		};
	}();

	//这个本页面要编写对应的对象，时间拾取控件
	ComponentsPickers.init();

	// 监听返回，当用户返回时关闭弹窗而不是直接返回当前网页
	$(function(){
		pushHistory();
		window.addEventListener("popstate", function(e) {
			// alert("我监听到了浏览器的返回按钮事件啦");
			$("#close_btn").click();
		}, false);
		function pushHistory() {
			var state = {
				title: "title",
				url: "#"
			};
			window.history.pushState(state, "title", "#");
		}
	});

	var change = function (list) {
		var data = [];
		for (var i = 0; i < list.length; i++) {
			console.log("==================");
			var r = {};
			console.log(JSON.stringify(list[i]));
			console.log(list[i][1] + "----" + list[i][2]);
			r["datetime"] = list[i][1];
			r["count"] = parseInt(list[i][2]);
			data.push(r);
			console.log("执行第:" + i);
		}
		console.log(JSON.stringify(data));
		return data;
	};

	// 统计
	function statistic() {
		var timeFrom = $("#time_from").val();
		var timeTo = $("#time_to").val();
		var timeInterval = $("#time_interval").val();
		var url = "../../statistic_servlet"
				+ "?action=start_statistic"
				+ "&table_name=" + Page.tableName
				+ "&time_from=" + timeFrom
				+ "&time_to=" + timeTo
				+ "&time_interval=" + timeInterval;
		alert(url);
		$.get(url, function (jsonObj) {
			if (jsonObj.result_code === 0) {
				alert(JSON.stringify(jsonObj));
				ChartsFlotcharts.drawChart(change(jsonObj.aaData));
			} else {
				alert("查询失败！");
			}
		}).error(function (xhr, errorText, errorType) {
			alert('错误信息：' + errorText + ",错误类型：" + errorType);
		});
	}

	function monthTime () {
		var today = new Date();
		var thisYear = today.getFullYear();
		var thisMonth = today.getMonth() + 1;
		var firstDay = thisYear + '-' + thisMonth + '-01';
		firstDay = ComponentsPickers.formatDate(ComponentsPickers.parseDate(firstDay), "yyyy-MM-dd") + " 00:00:00";
		var nowDay = ComponentsPickers.formatDate(today, "yyyy-MM-dd") + " 23:59:59";
		$("#time_from").val(firstDay);
		$("#time_to").val(nowDay);
	}

	monthTime();

	// //设置当前时间10天以后
	// Page.initLimitTime();
	// Page.initProjectInfo();
</script>



<%--<script src="../../assets/global/plugins/amcharts4/lib/core.js"></script>--%>
<%--<script src="../../assets/global/plugins/amcharts4/lib/charts.js"></script>--%>
<%--<script src="../../assets/global/plugins/amcharts4/lib/themes/material.js"></script>--%>
<%--<script src="../../assets/global/plugins/amcharts4/lib/lang/zh_Hans.js"></script>--%>
<%--<script src="../../assets/global/plugins/amcharts4/lib/themes/animated.js"></script>--%>



