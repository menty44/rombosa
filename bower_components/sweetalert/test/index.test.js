"use strict";
var utils_1 = require('./utils');
var MODAL = utils_1.CLASS_NAMES.MODAL, OVERLAY = utils_1.CLASS_NAMES.OVERLAY, MODAL_TITLE = utils_1.CLASS_NAMES.MODAL_TITLE, MODAL_TEXT = utils_1.CLASS_NAMES.MODAL_TEXT, ICON = utils_1.CLASS_NAMES.ICON, FOOTER = utils_1.CLASS_NAMES.FOOTER;
afterEach(function () { return utils_1.removeSwal(); });
describe("init", function () {
    test("adds elements on first call", function () {
        expect(utils_1.$$(OVERLAY).length).toEqual(0);
        utils_1.swal("Hello world!");
        expect(utils_1.$$(OVERLAY).length).toBe(1);
        expect(utils_1.$$(MODAL).length).toBe(1);
    });
});
describe("string parameters", function () {
    test("shows text when using 1 param", function () {
        utils_1.swal("Hello world!");
        expect(utils_1.$$(MODAL_TEXT).is(':first-child')).toBeTruthy();
        expect(utils_1.$$(MODAL_TEXT).text()).toBe("Hello world!");
        expect(utils_1.$$(MODAL_TEXT).next().hasClass(FOOTER)).toBeTruthy();
    });
    test("shows title and text when using 2 params", function () {
        utils_1.swal("Title", "text");
        expect(utils_1.$$(MODAL_TITLE).is(':first-child')).toBeTruthy();
        expect(utils_1.$$(MODAL_TITLE).text()).toBe("Title");
        expect(utils_1.$$(MODAL_TEXT).text()).toBe("text");
        expect(utils_1.$$(MODAL_TEXT).next().hasClass(FOOTER)).toBeTruthy();
    });
    test("shows icon, title and text when using 3 params", function () {
        utils_1.swal("Oops", "text", "error");
        expect(utils_1.$$(ICON).is(':first-child')).toBeTruthy();
        expect(utils_1.$$(ICON).hasClass(ICON + "--error")).toBeTruthy();
        expect(utils_1.$$(MODAL_TITLE).is(':nth-child(2)')).toBeTruthy();
        expect(utils_1.$$(MODAL_TITLE).text()).toBe("Oops");
        expect(utils_1.$$(MODAL_TEXT).is(':nth-child(3)')).toBeTruthy();
        expect(utils_1.$$(MODAL_TEXT).text()).toBe("text");
        expect(utils_1.$$(MODAL_TEXT).next().hasClass(FOOTER)).toBeTruthy();
    });
});
//# sourceMappingURL=index.test.js.map