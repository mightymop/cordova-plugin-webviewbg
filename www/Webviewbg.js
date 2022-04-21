var exec = require('cordova/exec');
var PLUGIN_NAME = 'Webviewbg';

var webviewbg = {

	setBG : function (val, success, error ) {
		exec(success, error, 'Webviewbg', 'setBG', [{value:val}]);
	},
	setTransparent : function (val, success, error ) {
		exec(success, error, 'Webviewbg', 'setTransparent', [{value:val}]);
	},
	isDarkMode : function (success, error ) {
		exec(success, error, 'Webviewbg', 'isDarkMode', []);
	}
};

module.exports = webviewbg;
