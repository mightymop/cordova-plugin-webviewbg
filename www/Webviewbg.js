var exec = require('cordova/exec');
var PLUGIN_NAME = 'Webviewbg';

var webviewbg = {

	enable : function (val, success, error ) {
		exec(success, error, 'Webviewbg', 'setBG', [{value:val}]);
	},
	disable : function (val, success, error ) {
		exec(success, error, 'Webviewbg', 'setTransparent', [{value:val}]);
	}
};

module.exports = webviewbg;
