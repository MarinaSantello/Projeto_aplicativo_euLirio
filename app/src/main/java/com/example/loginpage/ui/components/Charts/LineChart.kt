package com.example.loginpage.ui.components.Charts

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.loginpage.models.Dias
import kotlin.math.ceil


@Composable
fun CreateLineChart (
    maxValue: Int,
    dates: List<Dias>,
    percent: List<Double>
) {
//    val maxChartValue = ((maxValue/5) + 1) * 5
    val step = maxValue/5
    var value = maxValue
    var height = 20

    var width = 200

    var htmlFontStyle =
        "<div>\n" +
                "    <div class=\"slds-p-top--medium\">\n" +
                "        <div>\n" +
                "            <svg version=\"1.2\" class=\"quiz-graph\">\n" +
                "                <defs>\n" +
                "                    <pattern y=\"15\" id=\"grid\" width=\"100\" height=\"100\" patternUnits=\"userSpaceOnUse\">\n" +
                "                        <path d=\"M 100 0 L 0 0 0 100\" fill=\"none\" stroke=\"#e5e5e5\" stroke-width=\"2\"></path>\n" +
                "                    </pattern>\n" +
                "                </defs>\n" +
                "                <rect x=\"200\" y=\"15\" width=\"calc(100% - 365px)\" height=\"500\" fill=\"url(#grid)\" stroke=\"gray\"></rect>\n" +
                "                <g class=\"label-title\">\n" +
                "                    <text x=\"-250\" y=\"120\" transform=\"rotate(-90)\">vendas</text>\n" +
                "                </g>\n" +
                "                <g class=\"x-labels\">\n"

    for (i in dates.indices) {
        val mouths = listOf(
            "Jan.",
            "Fev.",
            "Mar.",
            "Abr.",
            "Mai.",
            "Jun.",
            "Jul.",
            "Ago.",
            "Set.",
            "Out.",
            "Nov.",
            "Dez."
        )
        val date = dates[i].dataCompra.split("T")[0].split("-")
        val mouth = mouths[(date[1].toInt() - 1)]

        htmlFontStyle += "<text x=\"$width\" y=\"530\">${date[2]} $mouth</text>\n"

        width += 100
    }
    htmlFontStyle +=
        "                </g>\n" +
                "                <g class=\"y-labels\">\n"
    for (i in 0 .. 5) {
        htmlFontStyle += "<text x=\"182\" y=\"$height\">$value</text>\n"

        height += 100
        value -= step
    }
    htmlFontStyle +=
        "                </g>\n" +
                "                <linearGradient id=\"grad\" x1=\"0%\" y1=\"0%\" x2=\"0%\" y2=\"100%\">\n" +
                "                    <stop offset=\"0%\" style=\"stop-color:#381871;stop-opacity:1\"></stop>\n" +
                "                    <stop offset=\"100%\" style=\"stop-color:transparent;stop-opacity:0\"></stop>\n" +
                "                </linearGradient>\n" +
                "                <polyline fill=\"url(#grad)\" stroke=\"#381871\" stroke-width=\"0\" points=\"\n" +
                "            200,500\n"
    var widthShadow = 200

    for (i in percent.indices) {
        val data = (515 - (ceil((500 * percent[i])/100).toInt()))

        htmlFontStyle += "$widthShadow,$data\n"

        widthShadow += 100
    }
    htmlFontStyle +=
        "            800,500" +
                "                \"></polyline>\n" +
                "                <polyline fill=\"none\" stroke=\"#381871\" stroke-width=\"1\" points=\"\n"
    var widthStroke = 200

    for (i in percent.indices) {
        val data = (515 - (ceil((500 * percent[i])/100).toInt()))

        htmlFontStyle += "$widthStroke,$data\n"

        widthStroke += 100
    }
    htmlFontStyle +=
        "                \"></polyline>\n" +
                "                <g>\n"
    var widthDot = 200

    for (i in percent.indices) {
        val data = (515 - (ceil((500 * percent[i])/100).toInt()))

        htmlFontStyle += "<circle class=\"quiz-graph-dot\" cx=\"$widthDot\" cy=\"$data\" r=\"6\"></circle>\n"

        widthDot += 100
    }
    htmlFontStyle +=
        "                </g>\n" +
                "            </svg>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <style>\n" +
                "        .quiz-graph {\n" +
                "            height: 570px;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "        .quiz-graph .x-labels {\n" +
                "            text-anchor: middle;\n" +
                "            font-size: 24px;\n" +
                "        }" +
                "        .quiz-graph .y-labels {\n" +
                "            text-anchor: end;\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "        .quiz-graph .quiz-graph-grid {\n" +
                "            stroke: #ccc;\n" +
                "            stroke-dasharray: 0;\n" +
                "            stroke-width: 1;\n" +
                "        }\n" +
                "        .label-title {\n" +
                "            text-anchor: middle;\n" +
                "            text-transform: uppercase;\n" +
                "            font-size: 48px;\n" +
                "            margin: 12px;" +
                "            fill: #1E1E1E;\n" +
                "        }\n" +
                "        .quiz-graph-dot, .quiz-graph-start-dot{\n" +
                "            fill: #381871;\n" +
                "            stroke-width: 2;\n" +
                "            stroke: white;\n" +
                "        }\n" +
                "    </style>\n" +
                "</div>"

    WebViewComponent(htmlCode = htmlFontStyle)
}

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun WebViewComponent(htmlCode: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val webView = WebView(context)

                webView.apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

                    loadData(htmlCode, "text/html", "utf-8")
                    loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null)
                }
                webView
            }
        )
    }
}
