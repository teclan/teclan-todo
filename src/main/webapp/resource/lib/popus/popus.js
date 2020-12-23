function _openPopups(body, url, iframSize) {

        if ($("#mainDiv").length > 0) {
            $("#mainDiv").remove();
        }
        if ($("#bottomDiv").length > 0) {
            $("#bottomDiv").remove();
        }
        var iframSizeWidth = iframSize.width;
        var iframeSizeHeight = iframSize.height;
        console.log("iframSizeWidth: " + iframSizeWidth);
        console.log("iframeSizeHeight: " + iframeSizeHeight);
        var mainDiv = $("<div></div>");
        var bottomDiv = $("<div></div>");
        var iframe = $("<iframe></iframe>");
        iframe.attr({
            name:'mainDivIframe',
            src: url,
            scrolling: "no",
            width: iframSizeWidth,
            height: iframeSizeHeight,
            border: 0,
            frameborder: "no"
        });
        var windowHeight = window.height;
        var windowWidth = window.width;
        var bodyHeight = body.height();
        var bodyWidth = body.width();
        bodyHeight = body.height();
        bodyWidth = body.width();
        var iframeHeight = iframe.height();
        var iframeWidth = iframe.width();
        console.log("bodyWidth: " + bodyWidth);
        var iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
        var iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
        iframeHeightCenter = parseInt(iframeHeightCenter);
        iframeWidthCenter = parseInt(iframeWidthCenter);
        console.log("iframeWidthCenter: " + iframeWidthCenter);
        bodyHeight = parseInt(bodyHeight);
        bodyWidth = parseInt(bodyWidth);
        bottomDiv.attr({
            id: "bottomDiv"
        });
        bottomDiv.css({
            "opacity": 0.5,
            "float": "left",
            "top": "0px",
            "left": "0px",
            "width": bodyWidth + "px",
            "height": bodyHeight + "px",
            "display": "inline-block",
            "position": "absolute",
            "z-index": "99",
            "background-color": "black",

        });
        mainDiv.css({
            "top": iframeHeightCenter + 'px',
            "left": iframeWidthCenter + 'px',
            "width": iframSizeWidth + 'px',
            "height": iframeSizeHeight + 'px',
            "display": "inline-block",
            "position": "absolute",
            "z-index": "100",
            "border-radius": "3px",
            "background-color": "#FFF"
        });
        mainDiv.attr({
            id: "mainDiv"

        });
       
        body.resize(function () {
            console.log('resize body');
            bodyHeight = body.height();
            bodyWidth = body.width();
            bodyHeight = parseInt(bodyHeight);
            bodyWidth = parseInt(bodyWidth);
            bottomDiv.css({
                "width": bodyWidth + "px",
                "height": bodyHeight + "px"
            });
            iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
            iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
            mainDiv.css({
                "top": iframeHeightCenter + 'px',
                "left": iframeWidthCenter + 'px'
            });
        });


        mainDiv.append(iframe);
        body.append(bottomDiv);
        body.append(mainDiv);
        /*bottomDiv.bind('click',function () {
            _closePopus();
        });*/
    }

function _closePopus() {
    $("#mainDiv").remove();
    $("#bottomDiv").remove();
}


function _closeCusPopus() {
    $("#mainDiv399").remove();
    $("#bottomDiv399").remove();
}
//第三层
function _openCusPopups(body, url, iframSize) {

    if ($("#mainDiv399").length > 0) {
        $("#mainDiv399").remove();
    }
    if ($("#bottomDiv399").length > 0) {
        $("#bottomDiv399").remove();
    }
    var iframSizeWidth = iframSize.width;
    var iframeSizeHeight = iframSize.height;
    console.log("iframSizeWidth: " + iframSizeWidth);
    console.log("iframeSizeHeight: " + iframeSizeHeight);
    var mainDiv = $("<div></div>");
    var bottomDiv = $("<div></div>");
    var iframe = $("<iframe></iframe>");
    iframe.attr({
        name:'mainDivIframe399',
        src: url,
        scrolling: "no",
        width: iframSizeWidth,
        height: iframeSizeHeight,
        border: 0,
        frameborder: "no"
    });
    var windowHeight = window.height;
    var windowWidth = window.width;
    var bodyHeight = body.height();
    var bodyWidth = body.width();
    bodyHeight = body.height();
    bodyWidth = body.width();
    var iframeHeight = iframe.height();
    var iframeWidth = iframe.width();
    console.log("bodyWidth: " + bodyWidth);
    var iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
    var iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
    iframeHeightCenter = parseInt(iframeHeightCenter);
    iframeWidthCenter = parseInt(iframeWidthCenter);
    console.log("iframeWidthCenter: " + iframeWidthCenter);
    bodyHeight = parseInt(bodyHeight);
    bodyWidth = parseInt(bodyWidth);
    bottomDiv.attr({
        id: "bottomDiv399"
    });
    bottomDiv.css({
        "opacity": 0.5,
        "float": "left",
        "top": "0px",
        "left": "0px",
        "width": bodyWidth + "px",
        "height": bodyHeight + "px",
        "display": "inline-block",
        "position": "absolute",
        "z-index": "399",
        "background-color": "black",

    });
    mainDiv.css({
        "top": iframeHeightCenter + 'px',
        "left": iframeWidthCenter + 'px',
        "width": iframSizeWidth + 'px',
        "height": iframeSizeHeight + 'px',
        "display": "inline-block",
        "position": "absolute",
        "z-index":"400",
        "border-radius": "3px",
        "background-color": "#FFF"



    });
    mainDiv.attr({
        id: "mainDiv399"

    });

    body.resize(function () {
        console.log('resize body');
        bodyHeight = body.height();
        bodyWidth = body.width();
        bodyHeight = parseInt(bodyHeight);
        bodyWidth = parseInt(bodyWidth);
        bottomDiv.css({
            "width": bodyWidth + "px",
            "height": bodyHeight + "px"
        });
        iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
        iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
        mainDiv.css({
            "top": iframeHeightCenter + 'px',
            "left": iframeWidthCenter + 'px'
        });
    });


    mainDiv.append(iframe);
    body.append(bottomDiv);
    body.append(mainDiv);
    /*bottomDiv.bind('click',function () {
     _closePopus();
     });*/
}

function _closeFourPopus() {
    $("#mainDiv499").remove();
    $("#bottomDiv499").remove();
}
//第三层
function _openFourPopups(body, url, iframSize) {

    if ($("#mainDiv499").length > 0) {
        $("#mainDiv499").remove();
    }
    if ($("#bottomDiv499").length > 0) {
        $("#bottomDiv499").remove();
    }
    var iframSizeWidth = iframSize.width;
    var iframeSizeHeight = iframSize.height;
    console.log("iframSizeWidth: " + iframSizeWidth);
    console.log("iframeSizeHeight: " + iframeSizeHeight);
    var mainDiv = $("<div></div>");
    var bottomDiv = $("<div></div>");
    var iframe = $("<iframe></iframe>");
    iframe.attr({
        name:'mainDivIframe499',
        src: url,
        scrolling: "no",
        width: iframSizeWidth,
        height: iframeSizeHeight,
        border: 0,
        frameborder: "no"
    });
    var windowHeight = window.height;
    var windowWidth = window.width;
    var bodyHeight = body.height();
    var bodyWidth = body.width();
    bodyHeight = body.height();
    bodyWidth = body.width();
    var iframeHeight = iframe.height();
    var iframeWidth = iframe.width();
    console.log("bodyWidth: " + bodyWidth);
    var iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
    var iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
    iframeHeightCenter = parseInt(iframeHeightCenter);
    iframeWidthCenter = parseInt(iframeWidthCenter);
    console.log("iframeWidthCenter: " + iframeWidthCenter);
    bodyHeight = parseInt(bodyHeight);
    bodyWidth = parseInt(bodyWidth);
    bottomDiv.attr({
        id: "bottomDiv499"
    });
    bottomDiv.css({
        "opacity": 0.5,
        "float": "left",
        "top": "0px",
        "left": "0px",
        "width": bodyWidth + "px",
        "height": bodyHeight + "px",
        "display": "inline-block",
        "position": "absolute",
        "z-index": "499",
        "background-color": "black",

    });
    mainDiv.css({
        "top": iframeHeightCenter + 'px',
        "left": iframeWidthCenter + 'px',
        "width": iframSizeWidth + 'px',
        "height": iframeSizeHeight + 'px',
        "display": "inline-block",
        "position": "absolute",
        "z-index":"500",
        "border-radius": "3px",
        "background-color": "#FFF"



    });
    mainDiv.attr({
        id: "mainDiv499"

    });

    body.resize(function () {
        console.log('resize body');
        bodyHeight = body.height();
        bodyWidth = body.width();
        bodyHeight = parseInt(bodyHeight);
        bodyWidth = parseInt(bodyWidth);
        bottomDiv.css({
            "width": bodyWidth + "px",
            "height": bodyHeight + "px"
        });
        iframeHeightCenter = (bodyHeight - iframeSizeHeight) / 2;
        iframeWidthCenter = (bodyWidth - iframSizeWidth) / 2;
        mainDiv.css({
            "top": iframeHeightCenter + 'px',
            "left": iframeWidthCenter + 'px'
        });
    });


    mainDiv.append(iframe);
    body.append(bottomDiv);
    body.append(mainDiv);
    /*bottomDiv.bind('click',function () {
     _closePopus();
     });*/
}