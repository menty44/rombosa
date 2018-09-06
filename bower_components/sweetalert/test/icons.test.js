"use strict";
var utils_1 = require('./utils');
var ICON = utils_1.CLASS_NAMES.ICON;
afterEach(function () { return utils_1.removeSwal(); });
describe("show icons", function () {
    test("shows icon depending on third argument", function () {
        utils_1.swal("Error", "An error occured!", "error");
        expect(utils_1.$$(ICON).length).toBe(1);
        expect(utils_1.$$(ICON).hasClass(ICON + "--error")).toBeTruthy();
    });
    test("shows icon when using 'icon' object key", function () {
        utils_1.swal({
            icon: 'warning'
        });
        expect(utils_1.$$(ICON).length).toBe(1);
        expect(utils_1.$$(ICON).hasClass(ICON + "--warning")).toBeTruthy();
    });
    test("hides icon when setting 'icon' key to 'false'", function () {
        utils_1.swal({
            icon: false
        });
        expect(utils_1.$$(ICON).length).toBe(0);
    });
});
//# sourceMappingURL=icons.test.js.map