"use strict";
exports.swal = require('../dist/sweetalert.min');
exports.$ = require('jquery');
exports.removeSwal = function () { return exports.$('.swal-overlay').remove(); };
exports.$$ = function (className) { return exports.$("." + className); };
var class_list_1 = require('../src/modules/class-list');
exports.CLASS_NAMES = class_list_1.CLASS_NAMES;
exports.delay = function (ms) {
    return new Promise(function (resolve) { return setTimeout(resolve, ms); });
};
//# sourceMappingURL=utils.js.map