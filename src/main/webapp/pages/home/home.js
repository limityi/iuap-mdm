define(['text!pages/home/home.html', 'pages/home/meta', 'css!pages/home/home.css'], function (html) {
    var init = function (element) {
        $(element).html(html);
    }
    return {
        'template': html,
        init: init
    }
});

